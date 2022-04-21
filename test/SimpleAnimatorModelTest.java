import java.util.ArrayList;
import java.util.Arrays;

import model.AnimatorModel;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.SimpleAnimatorModel;
import model.Size;

import org.junit.Assert;
import org.junit.Test;


/**
 * A class for testing a SImpleAnimatorMode;.
 */
public class SimpleAnimatorModelTest {
  Color red = new Color(255.0D, 0.0D, 0.0D);

  @Test
  public void testMakeAnimatorModel() {
    try {
      new SimpleAnimatorModel(null);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var4) {
      Assert.assertEquals(var4.getMessage(), "Invalid arguments");
    }
  }


  @Test
  public void addShapes() {
    SimpleAnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("B", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square)));
    Shape bigSquare = new Rectangle("G", new CartPt(0.0D, 0.0D),
            new Size(100.0D, 100.0D), this.red);
    model.addShape(bigSquare);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square, bigSquare)));
    Shape elipse = new Elipse("Q", new CartPt(0.0D, 0.0D),
            new Size(15.0D, 15.0D), this.red);
    model.addShape(elipse);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square, bigSquare, elipse)));
  }

  @Test
  public void testAddCommand() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("B", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape bigSquare = new Rectangle("G", new CartPt(0.0D, 0.0D),
            new Size(100.0D, 100.0D), this.red);
    Shape circle = new Elipse("", new CartPt(0.0D, 0.0D),
            new Size(15.0D, 15.0D), this.red);
    model.addShape(square);
    model.addShape(bigSquare);
    model.addShape(circle);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(model.getCommands(square), new ArrayList());
    model.addCommand(square, c);
    Assert.assertEquals(model.getCommands(square), new ArrayList(Arrays.asList(c)));
  }

  @Test
  public void printShapes() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("B", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square);

    Assert.assertEquals(model.printMotionTable(), "#                  start   " +
            "                        end\n#        --------------------------    ---" +
            "-------------------------\n#        t  x   y   w  h   r   g  b    t   " +
            "x   y   w  h   r   g  b\n\n");
  }

  @Test
  public void testPrintShapesOneShapeOneCmd() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    model.addCommand(square, c);
    Assert.assertEquals(model.printMotionTable(), "#                  " +
            "start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n");
  }

  @Test
  public void testPrintShapesTwoShapes() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square, circle);
    Command elpCmd = new Command(circle, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Command sqrCmd = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    model.addCommand(square, sqrCmd);
    model.addCommand(circle, elpCmd);
    Assert.assertEquals(model.printMotionTable(), "#                  " +
            "start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n" +
            "motion C 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n");
    model.removeShape(circle);
    Assert.assertEquals(model.printMotionTable(), "#                  s" +
            "tart                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n");
  }

  @Test
  public void testPrintNoCommands() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(model.printMotionTable(), "#              " +
            "    start                           end\n#        --------------------------" +
            "    ----------------------------\n#        t  x   y   w  h   r   g  b    t   " +
            "x   y   w  h   r   g  b\n");
    model.addShape(square, circle);
    Assert.assertEquals(model.printMotionTable(), "#                  start    " +
            "                       end\n#        --------------------------    ------------" +
            "----------------\n#        t  x   y   w  h   r   g  b    t   x   y   w  h   " +
            "r   g  b\n\n\n");
  }

  @Test
  public void testPrintShapesAddShapes() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D), new Size(10.0D,
            10.0D), this.red);
    model.addShape(square, circle);
    Command elpCmd = new Command(circle, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Command sqrCmd = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    model.addCommand(square, sqrCmd);
    Assert.assertEquals(model.printMotionTable(), "#                " +
            "  start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n" +
            "\n");
    model.addCommand(circle, elpCmd);
    Assert.assertEquals(model.printMotionTable(), "#                  s" +
            "tart                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n" +
            "motion C 0  0   0   10 10  255 0  0    10  100 10  10 10  255 0  0\n" +
            "\n");
  }

  @Test
  public void testRemoveShape() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square, circle);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)), model.getShapes());
    model.removeShape(circle);
    Assert.assertEquals(new ArrayList(Arrays.asList(square)), model.getShapes());

    try {
      model.removeShape(new Elipse("", new CartPt(0.0D, 0.0D),
              new Size(23.0D, 12.0D), this.red));
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var5) {
      Assert.assertEquals(var5.getMessage(), "The given shape is not in the list");
    }

  }

  @Test
  public void testUpdateShapesAtTickMovingUpAndGrowing() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square, circle);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)),
            model.getShapes());
    Command sqrCmd = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Command elpCmd = new Command(circle, 0.0D, 10.0D,
            new CartPt(0.0D, 0.0D), new Size(110.0D, 1010.0D), this.red);
    model.addCommand(square, sqrCmd);
    model.addCommand(circle, elpCmd);
    model.getShapesAtTick(1.0D);
    Shape square1Tick = new Rectangle("R", new CartPt(10.0D, 1.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle1Tick = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(20.0D, 110.0D), this.red);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square1Tick, circle1Tick)));
    model.getShapesAtTick(2.0D);
    Shape square2Tick = new Rectangle("R", new CartPt(20.0D, 2.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle2Tick = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(30.0D, 210.0D), this.red);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square2Tick, circle2Tick)));
    model.getShapesAtTick(3.0D);
    model.getShapesAtTick(4.0D);
    model.getShapesAtTick(5.0D);
    model.getShapesAtTick(6.0D);
    model.getShapesAtTick(7.0D);
    model.getShapesAtTick(8.0D);
    model.getShapesAtTick(9.0D);
    model.getShapesAtTick(10.0D);
    Shape square10Tick = new Rectangle("R", new CartPt(100.0D, 10.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle10Tick = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(110.0D, 1010.0D), this.red);
    Assert.assertEquals(model.getShapes(), new ArrayList(Arrays.asList(square10Tick,
            circle10Tick)));
  }

  @Test
  public void testUpdateShapeListNoActiveCommands() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square, circle);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)), model.getShapes());
    Command sqrCmd = new Command(square, 10.0D, 20.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Command elpCmd = new Command(circle, 10.0D, 15.0D,
            new CartPt(0.0D, 0.0D), new Size(110.0D, 1010.0D), this.red);
    model.addCommand(square, sqrCmd);
    model.addCommand(circle, elpCmd);
    model.getShapesAtTick(1.0D);
    model.getShapesAtTick(2.0D);
    model.getShapesAtTick(3.0D);
    model.getShapesAtTick(4.0D);
    model.getShapesAtTick(5.0D);
    model.getShapesAtTick(6.0D);
    model.getShapesAtTick(7.0D);
    model.getShapesAtTick(8.0D);
    model.getShapesAtTick(9.0D);
    model.getShapesAtTick(10.0D);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)), model.getShapes());
  }

  @Test
  public void testUpdateShapeListDelayedCommands() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    model.addShape(square, circle);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)), model.getShapes());
    Command sqrCmd = new Command(square, 10.0D, 20.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Command elpCmd = new Command(circle, 10.0D, 15.0D,
            new CartPt(0.0D, 0.0D), new Size(110.0D, 1010.0D), this.red);
    model.addCommand(square, sqrCmd);
    model.addCommand(circle, elpCmd);
    model.getShapesAtTick(1.0D);
    model.getShapesAtTick(2.0D);
    model.getShapesAtTick(3.0D);
    model.getShapesAtTick(4.0D);
    model.getShapesAtTick(5.0D);
    model.getShapesAtTick(6.0D);
    model.getShapesAtTick(7.0D);
    model.getShapesAtTick(8.0D);
    model.getShapesAtTick(9.0D);
    model.getShapesAtTick(10.0D);
    Assert.assertEquals(new ArrayList(Arrays.asList(square, circle)), model.getShapes());
    model.getShapesAtTick(11.0D);
    model.getShapesAtTick(12.0D);
    model.getShapesAtTick(13.0D);
    model.getShapesAtTick(14.0D);
    model.getShapesAtTick(15.0D);
    Shape square15Tick = new Rectangle("R", new CartPt(50.0D, 5.0D),
            new Size(10.0D, 10.0D), this.red);
    Shape circle15Tick = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(110.0D, 1010.0D), this.red);
    Assert.assertEquals(new ArrayList(Arrays.asList(square15Tick, circle15Tick)),
            model.getShapes());
    model.getShapesAtTick(16.0D);
    model.getShapesAtTick(17.0D);
    model.getShapesAtTick(18.0D);
    model.getShapesAtTick(19.0D);
    model.getShapesAtTick(20.0D);
    Shape square20Tick = new Rectangle("R", new CartPt(100.0D, 10.0D),
            new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(new ArrayList(Arrays.asList(square20Tick, circle15Tick)),
            model.getShapes());
  }

  @Test
  public void testAddShapeSameName() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    Shape square = new Rectangle("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Elipse circle = new Elipse("R", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);

    try {
      model.addShape(square, circle);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var5) {
      Assert.assertEquals(var5.getMessage(), "Names must be unique to each shape");
    }

  }
}