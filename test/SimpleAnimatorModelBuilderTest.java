import java.io.FileNotFoundException;
import java.util.ArrayList;

import controller.AnimatorController;
import controller.InteractiveController;
import model.AnimatorModel;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.SimpleAnimatorModel;
import model.Size;

import org.junit.Test;

import view.AnimationFileReader;
import view.AnimatorGraphicsView;
import view.SimpleAnimatorModelBuilder;
import view.VisualView;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing a SImpleAnimatorBuilder.
 */
public class SimpleAnimatorModelBuilderTest {


  @Test
  public void testBuildtoh3() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-3.txt", new SimpleAnimatorModelBuilder());
    VisualView view = new AnimatorGraphicsView(model);
    AnimatorController ctrl = new InteractiveController(model, view, 0.1);
    ctrl.startProgram();
    assertEquals(model, model);
  }

  @Test
  public void testBuildtoh8() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-8.txt", new SimpleAnimatorModelBuilder());
    VisualView view = new AnimatorGraphicsView(model);
    AnimatorController ctrl = new InteractiveController(model, view, 0.01);
    ctrl.startProgram();
    assertEquals(model, model);

  }

  @Test
  public void testBuildtoh12() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-12.txt",
            new SimpleAnimatorModelBuilder());
    VisualView view = new AnimatorGraphicsView(model);
    AnimatorController ctrl = new InteractiveController(model, view, 0.01);
    ctrl.startProgram();
    assertEquals(model, model);

  }

  @Test
  public void whenReceivingLine_shouldPaintLine() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    new AnimatorGraphicsView(model);
    Color red = new Color(255.0D, 0.0D, 0.0D);
    Shape square = new Rectangle("B", new CartPt(100.0D, 100.0D),
            new Size(10.0D, 10.0D), red);
    Shape bigSquare = new Rectangle("Q", new CartPt(0.0D, 0.0D),
            new Size(100.0D, 100.0D), red);
    Shape circle = new Elipse("C", new CartPt(0.0D, 0.0D),
            new Size(100.0D, 100.0D), red);
    model.addShape(square);
    model.addShape(bigSquare);
    model.addShape(circle);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 300.0D), new Size(10.0D, 10.0D), red);
    Command c2 = new Command(bigSquare, 0.0D, 10.0D,
            new CartPt(500.0D, 500.0D), new Size(10.0D, 10.0D), red);
    Command c3 = new Command(bigSquare, 0.0D, 10.0D,
            new CartPt(500.0D, 500.0D), new Size(10.0D, 10.0D),
            new Color(0.0D, 255.0D, 0.0D));
    model.addCommand(square, c);
    model.addCommand(bigSquare, c2);
    model.addCommand(circle, c3);
    assertEquals(model, model);
  }
}