import java.util.ArrayList;
import java.util.Arrays;

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

import view.AnimatorGraphicsView;
import view.ShapePanel;
import view.VisualView;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing a ShapePanel.
 */
public class ShapePanelTest {

  @Test
  public void testShapePanel() {
    ShapePanel sp = new ShapePanel();
    Shape circle = new Elipse("circle", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), new Color(255.0D, 0.0D, 0.0D));
    sp.takeShapeList(new ArrayList(Arrays.asList(circle)));
    assertEquals(sp.getShapeList(), new ArrayList(Arrays.asList(circle)));
  }

  @Test
  public void testVisualShapeView() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList<>());
    VisualView view = new AnimatorGraphicsView(model);

    Color red = new Color(255, 0, 0);
    Shape square = new Rectangle("B", new CartPt(100, 100),
            new Size(10, 10),
            red);
    Shape bigSquare = new Rectangle("Q", new CartPt(0, 0),
            new Size(100, 100),
            red);
    Shape circle = new Elipse("C", new CartPt(0, 0), new Size(40, 100),
            red);

    model.addShape(square);
    model.addShape(bigSquare);
    model.addShape(circle);

    Command c = new Command(square, 0, 10,
            new CartPt(100, 300), new Size(10, 10), red);
    Command c2 = new Command(bigSquare, 0, 10,
            new CartPt(500, 500), new Size(10, 10), new Color(0, 100, 100));
    Command c3 = new Command(bigSquare, 0, 10,
            new CartPt(120, 500), new Size(300, 10), new Color(0, 255, 0));
    model.addCommand(square, c);
    model.addCommand(bigSquare, c2);
    model.addCommand(circle, c3);

    AnimatorController controller = new InteractiveController(model, view, 0.1);
    controller.startProgram();
    assertEquals(view.toString(), "");
  }

  @Test
  public void testToh3Data() {

    AnimatorModel model = new SimpleAnimatorModel(new ArrayList<>());
    VisualView view = new AnimatorGraphicsView(model);

    Color red = new Color(255, 0, 0);
    Shape disc1 = new Rectangle("disc1", new CartPt(190, 180), new Size(30, 20),
            red);
    Shape disc2 = new Rectangle("disc2", new CartPt(167.5, 210), new Size(30, 65),
            red);
    Shape disc3 = new Rectangle("disc3", new CartPt(145, 240), new Size(30, 110),
            red);

    model.addShape(disc1);
    model.addShape(disc2);
    model.addShape(disc3);


    AnimatorController controller = new InteractiveController(model, view, 0.1);
    controller.startProgram();
    assertEquals(controller, controller);

  }


}
