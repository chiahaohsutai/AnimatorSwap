import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import controller.SVGController;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.Size;
import model.animatorLayersImp.AnimatorLayer;
import model.animatorLayersImp.AnimatorLayers;
import io.TweenModelBuilder;
import io.AnimationFileReader;
import io.AnimatorModelBuilder;
import view.AnimatorTextView;
import view.AnimatorView;
import view.SVGView;

import static org.junit.Assert.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;

/**
 * To test the functionality of layering in the animator. Checks that layering works in the model
 * and the views.
 */
public class AnimatorLayeringTests {
  // generates shapes and model.
  private Shape ellipse0;
  private Shape rect0;
  private Shape ellipse1;
  private Shape rect1;
  private AnimatorLayers animator;

  // makes an animator with 4 shapes.
  private void initAnimator() {
    ellipse0 = new Elipse("pancho", new CartPt(0, 0), new Size(10, 10),
            new Color(10, 10, 10));
    ellipse1 = new Elipse("pancho1", new CartPt(0, 0), new Size(10, 10),
            new Color(10, 10, 10));
    rect0 = new Rectangle("pancho2", new CartPt(0, 0), new Size(10, 10),
            new Color(10, 10, 10));
    rect1 = new Rectangle("pancho3", new CartPt(0, 0), new Size(10, 10),
            new Color(10, 10, 10));
    animator = new AnimatorLayer(Arrays.asList(ellipse0, ellipse1, rect0, rect1));
  }

  //////// TEST FOR LAYERING IN THE MODEL ////////

  @Test
  public void testAdd() {
    initAnimator();
    List<Shape> list = animator.getShapes();
    assertEquals(0, list.size());
    animator.addShape(ellipse1, ellipse0);
    list = animator.getShapes();
    assertEquals(2, list.size());
    assertEquals(1, animator.getShapeLayer("pancho"));
    assertEquals(1, animator.getShapeLayer("pancho1"));
  }

  @Test
  public void testGetShapesAtLayer() {
    initAnimator();
    assertEquals(0, animator.getShapesAtLayer(1).size());
    animator.addShape(ellipse0, ellipse1);
    assertEquals(2, animator.getShapesAtLayer(1).size());
    assertEquals(0, animator.getShapesAtLayer(3).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeShapesAtLayer() {
    initAnimator();
    animator.getShapesAtLayer(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroShapesAtLayer() {
    initAnimator();
    animator.getShapesAtLayer(0);
  }

  @Test
  public void testSetLayer() {
    initAnimator();
    animator.addShape(ellipse1);
    assertEquals(1, animator.getShapeLayer("pancho1"));
    animator.setLayer("pancho1", 2);
    assertEquals(2, animator.getShapeLayer("pancho1"));
    assertEquals(0, animator.getShapesAtLayer(1).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullNameSetLayer() {
    initAnimator();
    animator.setLayer(null, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNameSetLayer() {
    initAnimator();
    animator.setLayer("total", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroSetLayer() {
    initAnimator();
    animator.addShape(ellipse1);
    animator.setLayer("pancho1", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeSetLayer() {
    initAnimator();
    animator.addShape(ellipse1);
    animator.setLayer("pancho1", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNameGetShapeLayer() {
    initAnimator();
    animator.getShapeLayer("pancho");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullGetShapeLayer() {
    initAnimator();
    animator.getShapeLayer(null);
  }

  @Test
  public void getShapes() {
    initAnimator();
    animator.addShape(ellipse0, ellipse1, rect0, rect1);
    animator.setLayer("pancho", 2);
    animator.setLayer("pancho3", 2);
    List<Shape> shapes = animator.getShapesAtLayer(1);
    String[] names = shapes.stream().map(Shape::getName).toArray(String[]::new);
    assertArrayEquals(new String[] {"pancho1", "pancho2"}, names);
    shapes = animator.getShapesAtLayer(2);
    names = shapes.stream().map(Shape::getName).toArray(String[]::new);
    assertArrayEquals(new String[] {"pancho", "pancho3"}, names);
  }

  @Test
  public void printShapeMotionsTest() {
    initAnimator();
    animator.addShape(ellipse0, ellipse1, rect0, rect1);
    animator.setLayer("pancho", 2);
    animator.setLayer("pancho3", 2);

    Command c0 = new Command(ellipse0, 0.0D, 1000.0D,
            new CartPt(100.0D, 100.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));
    Command c1 = new Command(ellipse1, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));

    animator.addCommand(ellipse0, c0);
    animator.addCommand(ellipse1, c1);
    String expected = "The format for the text is the following:\n" +
            "motion shapeID   startTick xPos yPos height width R G B   " +
            "endTick xPos yPos height width R G B\n" +
            "(the first half is the original values and the second half is the " +
            "values after being transformed)\n" +
            "\n" +
            "Add shape pancho\n" +
            "Add shape pancho1\n" +
            "Add shape pancho2\n" +
            "Add shape pancho3\n" +
            "\n" +
            "Layer 1\n" +
            "motion pancho1 0  0   0   10 10  10 10  10    10  100 10  100 100  1 1  1\n" +
            "Layer 2\n" +
            "motion pancho 0  0   0   10 10  10 10  10    1000  100 100  100 100  1 1  1\n";
    assertEquals(expected, animator.printMotionTable());
  }

  @Test
  public void printEmptyShapeMotionsTest() {
    initAnimator();
    String expected = "The format for the text is the following:\n" +
            "motion shapeID   startTick xPos yPos height width R G B   " +
            "endTick xPos yPos height width R G B\n" +
            "(the first half is the original values and the second half is the " +
            "values after being transformed)\n" +
            "\n" +
            "\n" +
            "Layer 1\n";
    assertEquals(expected, animator.printMotionTable());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHeightZero() {
    initAnimator();
    animator.setCanvasDim(10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHeightNegative() {
    initAnimator();
    animator.setCanvasDim(10, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWidthZero() {
    initAnimator();
    animator.setCanvasDim(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWidthNegative() {
    initAnimator();
    animator.setCanvasDim(-10, 1);
  }

  @Test
  public void testSetDim() {
    initAnimator();
    assertEquals(500, animator.getCanvasHeight());
    assertEquals(500, animator.getCanvasWidth());
    animator.setCanvasDim(10, 10);
    assertEquals(10, animator.getCanvasHeight());
    assertEquals(10, animator.getCanvasWidth());
  }

  @Test
  public void testRemove() {
    initAnimator();
    assertEquals(0, animator.getShapes().size());
    assertEquals(0, animator.getShapesAtLayer(1).size());
    animator.addShape(ellipse1, ellipse0);
    assertEquals(2, animator.getShapesAtLayer(1).size());
    animator.removeShape(ellipse0);
    assertEquals(1, animator.getShapesAtLayer(1).size());
  }

  //////// TEST FOR THE SVG ////////

  @Test
  public void testSVGOutput() {
    initAnimator();
    SVGView view = new SVGView(animator, "SVGData.xml");
    SVGController ctrl = new SVGController(view, 1.0D);
    animator.addShape(ellipse0, ellipse1, rect0, rect1);
    animator.setLayer("pancho", 2);
    animator.setLayer("pancho3", 2);

    Command c0 = new Command(ellipse0, 0.0D, 1000.0D,
            new CartPt(100.0D, 100.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));
    Command c1 = new Command(ellipse1, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));

    animator.addCommand(ellipse0, c0);
    animator.addCommand(ellipse1, c1);
    ctrl.startProgram();

    try {
      Assert.assertEquals(Files.readString(Path.of("SVGData.xml")),
              "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
                      "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                      "\n" +
                      "<ellipse id=\"pancho1\" cx=\"0.0\" cy=\"0.0\" ry=\"10.0\" rx=\"10.0\" " +
                      "fill=\"rgb(10.0,10.0,10.0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"1.0E7ms\" " +
                      "attributeName=\"width\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"1.0E7ms\" " +
                      "attributeName=\"height\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<rect id=\"pancho2\" x=\"0.0\" y=\"0.0\" width=\"10.0\" " +
                      "height=\"10.0\" fill=\"rgb(10.0,10.0,10.0)\" visibility=\"visible\" >\n" +
                      "</rect>\n" +
                      "<ellipse id=\"pancho\" cx=\"0.0\" cy=\"0.0\" ry=\"10.0\" rx=\"10.0\" " +
                      "fill=\"rgb(10.0,10.0,10.0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"1.0E9ms\" " +
                      "attributeName=\"width\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"1.0E9ms\" " +
                      "attributeName=\"height\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<rect id=\"pancho3\" x=\"0.0\" y=\"0.0\" width=\"10.0\" " +
                      "height=\"10.0\" fill=\"rgb(10.0,10.0,10.0)\" visibility=\"visible\" >\n" +
                      "</rect>\n" +
                      "</svg>\n" +
                      "\n");
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }
  }

  @Test
  public void testSVGWithLayering() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    Appendable ap = new StringBuilder();
    try {
      AnimatorLayers anim = new AnimationFileReader().readFile("buildings.txt",
              builder);
      SVGView view = new SVGView(anim, "SVGData.xml");
      SVGController ctrl = new SVGController(view, 1.0D);
      ctrl.startProgram();
    } catch (FileNotFoundException | IllegalStateException | InputMismatchException e) {
      e.printStackTrace();
    }
    String expected = "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "\n" +
            "<rect id=\"background\" x=\"0.0\" y=\"0.0\" width=\"800.0\" height=\"800.0\" " +
            "fill=\"rgb(0.13330000638961792,0.9760000109672546,0.3700000047683716)\" " +
            "visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"B0\" x=\"80.0\" y=\"424.0\" width=\"100.0\" height=\"326.0\" " +
            "fill=\"rgb(0.0,0.0,0.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"B1\" x=\"260.0\" y=\"365.0\" width=\"100.0\" height=\"385.0\" " +
            "fill=\"rgb(0.0,0.0,0.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"B2\" x=\"440.0\" y=\"375.0\" width=\"100.0\" height=\"375.0\" " +
            "fill=\"rgb(0.0,0.0,0.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"B3\" x=\"620.0\" y=\"445.0\" width=\"100.0\" height=\"305.0\" " +
            "fill=\"rgb(0.0,0.0,0.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window000\" x=\"100.0\" y=\"850.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window001\" x=\"140.0\" y=\"850.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window010\" x=\"100.0\" y=\"890.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window011\" x=\"140.0\" y=\"890.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window020\" x=\"100.0\" y=\"930.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window021\" x=\"140.0\" y=\"930.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window030\" x=\"100.0\" y=\"970.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window031\" x=\"140.0\" y=\"970.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window040\" x=\"100.0\" y=\"1010.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window041\" x=\"140.0\" y=\"1010.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window050\" x=\"100.0\" y=\"1050.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window051\" x=\"140.0\" y=\"1050.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window060\" x=\"100.0\" y=\"1090.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window061\" x=\"140.0\" y=\"1090.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window100\" x=\"280.0\" y=\"-750.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window101\" x=\"320.0\" y=\"-750.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window110\" x=\"280.0\" y=\"-710.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window111\" x=\"320.0\" y=\"-710.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window120\" x=\"280.0\" y=\"-670.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window121\" x=\"320.0\" y=\"-670.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window130\" x=\"280.0\" y=\"-630.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window131\" x=\"320.0\" y=\"-630.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window140\" x=\"280.0\" y=\"-590.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window141\" x=\"320.0\" y=\"-590.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window150\" x=\"280.0\" y=\"-550.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window151\" x=\"320.0\" y=\"-550.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window160\" x=\"280.0\" y=\"-510.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window161\" x=\"320.0\" y=\"-510.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window170\" x=\"280.0\" y=\"-470.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window171\" x=\"320.0\" y=\"-470.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window180\" x=\"280.0\" y=\"-430.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window181\" x=\"320.0\" y=\"-430.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window200\" x=\"460.0\" y=\"850.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window201\" x=\"500.0\" y=\"850.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window210\" x=\"460.0\" y=\"890.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window211\" x=\"500.0\" y=\"890.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window220\" x=\"460.0\" y=\"930.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window221\" x=\"500.0\" y=\"930.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window230\" x=\"460.0\" y=\"970.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window231\" x=\"500.0\" y=\"970.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window240\" x=\"460.0\" y=\"1010.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window241\" x=\"500.0\" y=\"1010.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window250\" x=\"460.0\" y=\"1050.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window251\" x=\"500.0\" y=\"1050.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window260\" x=\"460.0\" y=\"1090.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window261\" x=\"500.0\" y=\"1090.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window270\" x=\"460.0\" y=\"1130.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window271\" x=\"500.0\" y=\"1130.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window300\" x=\"640.0\" y=\"-750.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window301\" x=\"680.0\" y=\"-750.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window310\" x=\"640.0\" y=\"-710.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window311\" x=\"680.0\" y=\"-710.0\" width=\"20.0\" height=\"20.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window320\" x=\"640.0\" y=\"-670.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window321\" x=\"680.0\" y=\"-670.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window330\" x=\"640.0\" y=\"-630.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window331\" x=\"680.0\" y=\"-630.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window340\" x=\"640.0\" y=\"-590.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window341\" x=\"680.0\" y=\"-590.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window350\" x=\"640.0\" y=\"-550.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window351\" x=\"680.0\" y=\"-550.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window360\" x=\"640.0\" y=\"-510.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<rect id=\"window361\" x=\"680.0\" y=\"-510.0\" width=\"20.0\" " +
            "height=\"20.0\" fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "<ellipse id=\"moon\" cx=\"250.0\" cy=\"250.0\" ry=\"50.0\" rx=\"50.0\" " +
            "fill=\"rgb(0.8999999761581421,1.0,0.8999999761581421)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"eclipse\" cx=\"450.0\" cy=\"50.0\" ry=\"50.0\" rx=\"50.0\" " +
            "fill=\"rgb(0.13330000638961792,0.9760000109672546,0.3700000047683716)\" " +
            "visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star0\" cx=\"226.0\" cy=\"69.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star1\" cx=\"588.0\" cy=\"214.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star2\" cx=\"492.0\" cy=\"80.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star3\" cx=\"377.0\" cy=\"289.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star4\" cx=\"711.0\" cy=\"284.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star5\" cx=\"511.0\" cy=\"263.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star6\" cx=\"532.0\" cy=\"73.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star7\" cx=\"335.0\" cy=\"68.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star8\" cx=\"314.0\" cy=\"150.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star9\" cx=\"173.0\" cy=\"284.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star10\" cx=\"722.0\" cy=\"105.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star11\" cx=\"527.0\" cy=\"267.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star12\" cx=\"771.0\" cy=\"197.0\" ry=\"3.0\" rx=\"3.0\"" +
            " fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star13\" cx=\"769.0\" cy=\"182.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star14\" cx=\"513.0\" cy=\"81.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star15\" cx=\"624.0\" cy=\"152.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star16\" cx=\"494.0\" cy=\"255.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star17\" cx=\"408.0\" cy=\"66.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star18\" cx=\"553.0\" cy=\"270.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star19\" cx=\"111.0\" cy=\"200.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star20\" cx=\"740.0\" cy=\"81.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star21\" cx=\"798.0\" cy=\"140.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star22\" cx=\"187.0\" cy=\"128.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star23\" cx=\"137.0\" cy=\"233.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star24\" cx=\"247.0\" cy=\"156.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star25\" cx=\"262.0\" cy=\"122.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star26\" cx=\"325.0\" cy=\"272.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star27\" cx=\"415.0\" cy=\"185.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star28\" cx=\"677.0\" cy=\"140.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star29\" cx=\"49.0\" cy=\"249.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star30\" cx=\"391.0\" cy=\"318.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star31\" cx=\"188.0\" cy=\"239.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star32\" cx=\"553.0\" cy=\"235.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star33\" cx=\"659.0\" cy=\"104.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star34\" cx=\"286.0\" cy=\"114.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star35\" cx=\"652.0\" cy=\"329.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star36\" cx=\"694.0\" cy=\"270.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star37\" cx=\"116.0\" cy=\"279.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star38\" cx=\"607.0\" cy=\"305.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "<ellipse id=\"star39\" cx=\"465.0\" cy=\"165.0\" ry=\"3.0\" rx=\"3.0\" " +
            "fill=\"rgb(1.0,1.0,1.0)\" visibility=\"visible\" >\n" +
            "</ellipse>\n" +
            "</svg>\n\n";
    try {
      assertEquals(expected, Files.readString(Path.of("SVGData.xml")));
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }
  }

  //////// TEST FOR THE TEXT ////////

  @Test
  public void testTextView() {
    initAnimator();
    Appendable output = new StringBuilder();
    AnimatorView view = new AnimatorTextView(animator, output);
    animator.addShape(ellipse0, ellipse1, rect0, rect1);
    animator.setLayer("pancho", 2);
    animator.setLayer("pancho3", 2);

    Command c0 = new Command(ellipse0, 0.0D, 1000.0D,
            new CartPt(100.0D, 100.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));
    Command c1 = new Command(ellipse1, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D),
            new Size(100.0D, 100.0D),
            new Color(1.0, 1.0, 1.0));

    animator.addCommand(ellipse0, c0);
    animator.addCommand(ellipse1, c1);
    view.refresh();
    String expected = "The format for the text is the following:\n" +
            "motion shapeID   startTick xPos yPos height width R G B   " +
            "endTick xPos yPos height width R G B\n" +
            "(the first half is the original values and the second half is the " +
            "values after being transformed)\n" +
            "\n" +
            "Add shape pancho\n" +
            "Add shape pancho1\n" +
            "Add shape pancho2\n" +
            "Add shape pancho3\n" +
            "\n" +
            "Layer 1\n" +
            "motion pancho1 0  0   0   10 10  10 10  10    10  100 10  100 100  1 1  1\n" +
            "Layer 2\n" +
            "motion pancho 0  0   0   10 10  10 10  10    1000  100 100  100 100  1 1  1\n";
    assertEquals(expected, output.toString());
  }

  @Test
  public void readSmallDemoText() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    Appendable ap = new StringBuilder();
    try {
      AnimatorLayers anim = new AnimationFileReader().readFile("smalldemo.txt", builder);
      AnimatorView view = new AnimatorTextView(anim, ap);
      view.refresh();
    } catch (FileNotFoundException | IllegalStateException | InputMismatchException e) {
      e.printStackTrace();
    }
    String expected = "The format for the text is the following:\n" +
            "motion shapeID   startTick xPos yPos height width R G B   " +
            "endTick xPos yPos height width R G B\n" +
            "(the first half is the original values and the second half is " +
            "the values after being transformed)\n" +
            "\n" +
            "Add shape R\n" +
            "Add shape C\n" +
            "\n" +
            "Layer 1\n" +
            "motion R 10  200   200   50 100  1 0  0    50  300 300  50 100  1 0  0\n" +
            "motion R 70  300   300   50 100  1 0  0    100  200 200  50 100  1 0  0\n" +
            "motion R 51  200   200   100 50  1 0  0    70  200 200  100 25  1 0  0\n" +
            "motion C 20  500   100   60 30  0 0  1    70  500 400  60 30  0 0  1\n" +
            "motion C 50  500   100   60 30  0 0  1    80  500 100  60 30  0 1  0\n";
    assertEquals(expected, ap.toString());
  }

  @Test
  public void readBuildingsText() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    Appendable ap = new StringBuilder();
    try {
      AnimatorLayers anim = new AnimationFileReader().readFile("buildings.txt",
              builder);
      AnimatorView view = new AnimatorTextView(anim, ap);
      view.refresh();
    } catch (FileNotFoundException | IllegalStateException | InputMismatchException e) {
      e.printStackTrace();
    }
    String expected = "The format for the text is the following:\n" +
            "motion shapeID   startTick xPos yPos height width R G B   endTick xPos yPos " +
            "height width R G B\n" +
            "(the first half is the original values and the second half is the values after " +
            "being transformed)\n" +
            "\n" +
            "Add shape background\n" + "Add shape B0\n" + "Add shape B1\n" + "Add shape B2\n" +
            "Add shape B3\n" + "Add shape window000\n" + "Add shape window001\n" +
            "Add shape window010\n" + "Add shape window011\n" + "Add shape window020\n" +
            "Add shape window021\n" + "Add shape window030\n" + "Add shape window031\n" +
            "Add shape window040\n" + "Add shape window041\n" + "Add shape window050\n" +
            "Add shape window051\n" + "Add shape window060\n" + "Add shape window061\n" +
            "Add shape window100\n" + "Add shape window101\n" + "Add shape window110\n" +
            "Add shape window111\n" + "Add shape window120\n" + "Add shape window121\n" +
            "Add shape window130\n" + "Add shape window131\n" + "Add shape window140\n" +
            "Add shape window141\n" + "Add shape window150\n" + "Add shape window151\n" +
            "Add shape window160\n" + "Add shape window161\n" + "Add shape window170\n" +
            "Add shape window171\n" + "Add shape window180\n" + "Add shape window181\n" +
            "Add shape window200\n" + "Add shape window201\n" + "Add shape window210\n" +
            "Add shape window211\n" + "Add shape window220\n" + "Add shape window221\n" +
            "Add shape window230\n" + "Add shape window231\n" + "Add shape window240\n" +
            "Add shape window241\n" + "Add shape window250\n" + "Add shape window251\n" +
            "Add shape window260\n" + "Add shape window261\n" + "Add shape window270\n" +
            "Add shape window271\n" + "Add shape window300\n" + "Add shape window301\n" +
            "Add shape window310\n" + "Add shape window311\n" + "Add shape window320\n" +
            "Add shape window321\n" + "Add shape window330\n" + "Add shape window331\n" +
            "Add shape window340\n" + "Add shape window341\n" + "Add shape window350\n" +
            "Add shape window351\n" + "Add shape window360\n" + "Add shape window361\n" +
            "Add shape moon\n" + "Add shape eclipse\n" + "Add shape star0\n" +
            "Add shape star1\n" + "Add shape star2\n" + "Add shape star3\n" +
            "Add shape star4\n" + "Add shape star5\n" + "Add shape star6\n" +
            "Add shape star7\n" + "Add shape star8\n" + "Add shape star9\n" +
            "Add shape star10\n" + "Add shape star11\n" + "Add shape star12\n" +
            "Add shape star13\n" + "Add shape star14\n" + "Add shape star15\n" +
            "Add shape star16\n" + "Add shape star17\n" + "Add shape star18\n" +
            "Add shape star19\n" + "Add shape star20\n" + "Add shape star21\n" +
            "Add shape star22\n" + "Add shape star23\n" + "Add shape star24\n" +
            "Add shape star25\n" + "Add shape star26\n" + "Add shape star27\n" +
            "Add shape star28\n" + "Add shape star29\n" + "Add shape star30\n" +
            "Add shape star31\n" + "Add shape star32\n" + "Add shape star33\n" +
            "Add shape star34\n" + "Add shape star35\n" + "Add shape star36\n" +
            "Add shape star37\n" + "Add shape star38\n" + "Add shape star39\n" + "\n" +
            "Layer 1\n" +
            "motion background 50  0   0   800 800  0 0  0    90  0 0  800 800  0 0  0\n" +
            "motion window000 20  100   850   20 20  1 1  1    60  100 710  20 20  1 1  1\n" +
            "motion window001 20  140   850   20 20  1 1  1    60  140 710  20 20  1 1  1\n" +
            "motion window010 20  100   890   20 20  1 1  1    60  100 670  20 20  1 1  1\n" +
            "motion window011 20  140   890   20 20  1 1  1    60  140 670  20 20  1 1  1\n" +
            "motion window020 20  100   930   20 20  1 1  1    60  100 630  20 20  1 1  1\n" +
            "motion window021 20  140   930   20 20  1 1  1    60  140 630  20 20  1 1  1\n" +
            "motion window021 82  140   930   20 20  1 1  1    87  140 930  20 20  1 1  0\n" +
            "motion window030 20  100   970   20 20  1 1  1    60  100 590  20 20  1 1  1\n" +
            "motion window031 20  140   970   20 20  1 1  1    60  140 590  20 20  1 1  1\n" +
            "motion window040 20  100   1010   20 20  1 1  1    60  100 550  20 20  1 1  1\n" +
            "motion window041 20  140   1010   20 20  1 1  1    60  140 550  20 20  1 1  1\n" +
            "motion window041 147  140   1010   20 20  1 1  1    152  140 1010  20 20  1 1  0\n" +
            "motion window050 20  100   1050   20 20  1 1  1    60  100 510  20 20  1 1  1\n" +
            "motion window051 20  140   1050   20 20  1 1  1    60  140 510  20 20  1 1  1\n" +
            "motion window060 20  100   1090   20 20  1 1  1    60  100 470  20 20  1 1  1\n" +
            "motion window060 147  100   1090   20 20  1 1  1    152  100 1090  20 20  1 1  0\n" +
            "motion window061 20  140   1090   20 20  1 1  1    60  140 470  20 20  1 1  1\n" +
            "motion window061 93  140   1090   20 20  1 1  1    98  140 1090  20 20  1 1  0\n" +
            "motion window100 20  280   -750   20 20  1 1  1    60  280 710  20 20  1 1  1\n" +
            "motion window101 20  320   -750   20 20  1 1  1    60  320 710  20 20  1 1  1\n" +
            "motion window101 108  320   -750   20 20  1 1  1    113  320 -750  20 20  1 1  0\n" +
            "motion window110 20  280   -710   20 20  1 1  1    60  280 670  20 20  1 1  1\n" +
            "motion window110 95  280   -710   20 20  1 1  1    100  280 -710  20 20  1 1  0\n" +
            "motion window111 20  320   -710   20 20  1 1  1    60  320 670  20 20  1 1  1\n" +
            "motion window120 20  280   -670   20 20  1 1  1    60  280 630  20 20  1 1  1\n" +
            "motion window121 20  320   -670   20 20  1 1  1    60  320 630  20 20  1 1  1\n" +
            "motion window130 20  280   -630   20 20  1 1  1    60  280 590  20 20  1 1  1\n" +
            "motion window131 20  320   -630   20 20  1 1  1    60  320 590  20 20  1 1  1\n" +
            "motion window131 148  320   -630   20 20  1 1  1    153  320 -630  20 20  1 1  0\n" +
            "motion window140 20  280   -590   20 20  1 1  1    60  280 550  20 20  1 1  1\n" +
            "motion window141 20  320   -590   20 20  1 1  1    60  320 550  20 20  1 1  1\n" +
            "motion window150 20  280   -550   20 20  1 1  1    60  280 510  20 20  1 1  1\n" +
            "motion window151 20  320   -550   20 20  1 1  1    60  320 510  20 20  1 1  1\n" +
            "motion window151 99  320   -550   20 20  1 1  1    104  320 -550  20 20  1 1  0\n" +
            "motion window160 20  280   -510   20 20  1 1  1    60  280 470  20 20  1 1  1\n" +
            "motion window161 20  320   -510   20 20  1 1  1    60  320 470  20 20  1 1  1\n" +
            "motion window170 20  280   -470   20 20  1 1  1    60  280 430  20 20  1 1  1\n" +
            "motion window171 20  320   -470   20 20  1 1  1    60  320 430  20 20  1 1  1\n" +
            "motion window180 20  280   -430   20 20  1 1  1    60  280 390  20 20  1 1  1\n" +
            "motion window181 20  320   -430   20 20  1 1  1    60  320 390  20 20  1 1  1\n" +
            "motion window200 20  460   850   20 20  1 1  1    60  460 710  20 20  1 1  1\n" +
            "motion window200 127  460   850   20 20  1 1  1    132  460 850  20 20  1 1  0\n" +
            "motion window201 20  500   850   20 20  1 1  1    60  500 710  20 20  1 1  1\n" +
            "motion window210 20  460   890   20 20  1 1  1    60  460 670  20 20  1 1  1\n" +
            "motion window210 83  460   890   20 20  1 1  1    88  460 890  20 20  1 1  0\n" +
            "motion window211 20  500   890   20 20  1 1  1    60  500 670  20 20  1 1  1\n" +
            "motion window220 20  460   930   20 20  1 1  1    60  460 630  20 20  1 1  1\n" +
            "motion window221 20  500   930   20 20  1 1  1    60  500 630  20 20  1 1  1\n" +
            "motion window230 20  460   970   20 20  1 1  1    60  460 590  20 20  1 1  1\n" +
            "motion window231 20  500   970   20 20  1 1  1    60  500 590  20 20  1 1  1\n" +
            "motion window240 20  460   1010   20 20  1 1  1    60  460 550  20 20  1 1  1\n" +
            "motion window241 20  500   1010   20 20  1 1  1    60  500 550  20 20  1 1  1\n" +
            "motion window241 112  500   1010   20 20  1 1  1    117  500 1010  20 20  1 1  0\n" +
            "motion window250 20  460   1050   20 20  1 1  1    60  460 510  20 20  1 1  1\n" +
            "motion window250 128  460   1050   20 20  1 1  1    133  460 1050  20 20  1 1  0\n" +
            "motion window251 20  500   1050   20 20  1 1  1    60  500 510  20 20  1 1  1\n" +
            "motion window260 20  460   1090   20 20  1 1  1    60  460 470  20 20  1 1  1\n" +
            "motion window261 20  500   1090   20 20  1 1  1    60  500 470  20 20  1 1  1\n" +
            "motion window261 96  500   1090   20 20  1 1  1    101  500 1090  20 20  1 1  0\n" +
            "motion window270 20  460   1130   20 20  1 1  1    60  460 430  20 20  1 1  1\n" +
            "motion window271 20  500   1130   20 20  1 1  1    60  500 430  20 20  1 1  1\n" +
            "motion window300 20  640   -750   20 20  1 1  1    60  640 710  20 20  1 1  1\n" +
            "motion window301 20  680   -750   20 20  1 1  1    60  680 710  20 20  1 1  1\n" +
            "motion window301 135  680   -750   20 20  1 1  1    140  680 -750  20 20  1 1  0\n" +
            "motion window310 20  640   -710   20 20  1 1  1    60  640 670  20 20  1 1  1\n" +
            "motion window310 103  640   -710   20 20  1 1  1    108  640 -710  20 20  1 1  0\n" +
            "motion window311 20  680   -710   20 20  1 1  1    60  680 670  20 20  1 1  1\n" +
            "motion window320 20  640   -670   20 20  1 1  1    60  640 630  20 20  1 1  1\n" +
            "motion window321 20  680   -670   20 20  1 1  1    60  680 630  20 20  1 1  1\n" +
            "motion window330 20  640   -630   20 20  1 1  1    60  640 590  20 20  1 1  1\n" +
            "motion window330 81  640   -630   20 20  1 1  1    86  640 -630  20 20  1 1  0\n" +
            "motion window331 20  680   -630   20 20  1 1  1    60  680 590  20 20  1 1  1\n" +
            "motion window331 119  680   -630   20 20  1 1  1    124  680 -630  20 20  1 1  0\n" +
            "motion window340 20  640   -590   20 20  1 1  1    60  640 550  20 20  1 1  1\n" +
            "motion window341 20  680   -590   20 20  1 1  1    60  680 550  20 20  1 1  1\n" +
            "motion window350 20  640   -550   20 20  1 1  1    60  640 510  20 20  1 1  1\n" +
            "motion window351 20  680   -550   20 20  1 1  1    60  680 510  20 20  1 1  1\n" +
            "motion window351 100  680   -550   20 20  1 1  1    105  680 -550  20 20  1 1  0\n" +
            "motion window360 20  640   -510   20 20  1 1  1    60  640 470  20 20  1 1  1\n" +
            "motion window360 99  640   -510   20 20  1 1  1    104  640 -510  20 20  1 1  0\n" +
            "motion window361 20  680   -510   20 20  1 1  1    60  680 470  20 20  1 1  1\n" +
            "motion window361 99  680   -510   20 20  1 1  1    104  680 -510  20 20  1 1  0\n" +
            "motion eclipse 50  450   50   50 50  0 0  0    90  280 230  50 50  0 0  0\n" +
            "motion eclipse 50  450   50   50 50  0 0  0    90  450 50  50 50  0 0  0\n";
    assertEquals(expected, ap.toString());
  }

  //////// Animation File Reader ////////

  @Test
  public void testAnimationFileReader() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    try {
      AnimatorLayers anim = new AnimationFileReader().readFile("testReadingWithLayers.txt",
              builder);
      assertEquals(3, anim.getShapeLayer("R"));
      assertEquals(1, anim.getShapeLayer("C"));
    } catch (FileNotFoundException | IllegalStateException | InputMismatchException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = InputMismatchException.class)
  public void testLayeringInReader() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    String dest = "testReadingWithLayersFail.txt";
    try {
      AnimatorLayers anim = new AnimationFileReader().readFile(dest, builder);
    } catch (FileNotFoundException | IllegalStateException | InputMismatchException e) {
      if (e instanceof InputMismatchException) {
        throw new InputMismatchException();
      }
    }
  }

  //////// Test for the builder ////////

  @Test
  public void testBuilderSetLayer() {
    TweenModelBuilder<AnimatorLayers> builder = new AnimatorModelBuilder();
    builder.addRectangle("rect", 1, 1, 10, 10, 12, 13,
            56, 0, 10);
    builder.setLayer("rect", 4);
    AnimatorLayers anim = builder.build();
    assertEquals(4, anim.getShapeLayer("rect"));
  }
}
