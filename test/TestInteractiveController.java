import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import controller.InteractiveVisualController;
import controller.model.AnimatorModel;
import controller.model.CartPt;
import controller.model.Color;
import controller.model.Command;
import controller.model.Rectangle;
import controller.model.Shape;
import controller.model.SimpleAnimatorModel;
import controller.model.Size;
import view.InteractiveAnimatorGraphicsView;

import static org.junit.Assert.assertEquals;

/**
 * A test class for testing an IneractiveController.
 */
public class TestInteractiveController {

  @Test
  public void testControllerPauseRewind() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList<>());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    Shape square = new Rectangle("B", new CartPt(100, 100),
            new Size(10.0D, 10.0D), new Color(255, 0, 0));
    model.addShape(square);

    InteractiveVisualController controller = new InteractiveVisualController(
            model, view, 10);


    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(500, 500), new Size(70, 70),
            new Color(255, 0, 0));
    model.addCommand(square, c);

    controller.startProgram();
    controller.pause();

    Shape newSquare = new Rectangle("Square",
            new CartPt(499.9999999999933, 499.9999999999933),
            new Size(69.99999999999899, 69.99999999999899),
            new Color(255, 0, 0));
    assertEquals(model.getShapes(), new ArrayList<>(Arrays.asList(newSquare)));

    controller.pause();

    controller.rewind();
    assertEquals(model.getShapes(), new ArrayList<>(Arrays.asList(square)));
  }
}
