import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.SVGController;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.SimpleAnimatorModel;
import model.Size;
import model.animatorLayersImp.AnimatorLayer;
import model.animatorLayersImp.AnimatorLayers;
import view.AnimatorTextView;
import view.AnimatorView;
import view.SVGView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
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
}
