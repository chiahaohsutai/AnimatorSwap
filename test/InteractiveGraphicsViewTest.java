import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.AnimatorController;
import controller.InteractiveVisualController;
import model.AnimatorModel;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.SimpleAnimatorModel;
import model.Size;
import view.AnimationFileReader;
import view.InteractiveAnimatorGraphicsView;
import view.SimpleAnimatorModelBuilder;

import static org.junit.Assert.assertEquals;

/**
 * A test class for rendering an interactive animation.
 */
public class InteractiveGraphicsViewTest {

  @Test
  public void testInteractiveView() {
    AnimatorModel model = new SimpleAnimatorModel(new ArrayList<>());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    Shape square = new Rectangle("B", new CartPt(100, 100),
            new Size(10.0D, 10.0D), new Color(255, 0, 0));
    model.addShape(square);

    AnimatorController controller = new InteractiveVisualController(model, view, 1);


    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(500, 500), new Size(70, 70),
            new Color(255, 0, 0));
    model.addCommand(square, c);

    controller.startProgram();
    assertEquals(model.getShapes(), new ArrayList<Shape>(Arrays.asList(
            new Rectangle("B", new CartPt(500, 500),
                    new Size(70, 70),
                    new Color(255, 0, 0)))));
  }

  @Test
  public void testInteractiveReadFile() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-12.txt", new SimpleAnimatorModelBuilder());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    AnimatorController controller = new InteractiveVisualController(model, view, 10);
    controller.startProgram();
    List<Shape> shapes = model.getShapes();
    assertEquals(shapes, model.getShapes());
  }

  @Test
  public void testInteractiveReadFile3() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("big-bang-big-crunch.txt",
            new SimpleAnimatorModelBuilder());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    AnimatorController controller = new InteractiveVisualController(model, view, 10);
    System.out.println(model.getCanvasWidth() + " " + model.getCanvasHeight());
    controller.startProgram();
    List<Shape> shapes = model.getShapes();
    assertEquals(shapes, model.getShapes());

  }

  @Test
  public void testHosYJuke() {
    Shape qb = new Elipse("QB", new CartPt(250, 385), new Size(10, 10),
            new Color(12, 100, 200));
    Command dropBack = new Command(qb, 0, 100, new CartPt(250, 400),
            new Size(10, 10), new Color(12, 0, 200));

    Shape c = new Rectangle("C", new CartPt(250, 375), new Size(10, 10),
            new Color(255, 0, 0));
    Shape rg = new Elipse("RG", new CartPt(265, 377), new Size(10, 10),
            new Color(0, 0, 0));
    Shape rt = new Elipse("RT", new CartPt(280, 381), new Size(10, 10),
            new Color(0, 0, 0));
    Shape lg = new Elipse("LG", new CartPt(235, 377), new Size(10, 10),
            new Color(0, 0, 0));
    Shape lt = new Elipse("LT", new CartPt(220, 381), new Size(10, 10),
            new Color(0, 0, 0));

    Shape wrX = new Elipse("wrX", new CartPt(100, 375), new Size(10, 10),
            new Color(0, 0, 0));
    Command xRoute = new Command(wrX, 0, 100, new CartPt(100, 300),
            new Size(10, 10),
            new Color(0, 0, 200));

    Shape wrY = new Elipse("wrY", new CartPt(150, 375), new Size(10, 10),
            new Color(200, 100, 0));
    Command yRoute = new Command(wrY, 0, 300, new CartPt(190, 100),
            new Size(10, 10),
            new Color(200, 100, 0));

    Shape hb = new Elipse("HB", new CartPt(200, 385), new Size(10, 10),
            new Color(0, 100, 0));

    Command hbRoute = new Command(hb, 0, 100, new CartPt(240, 300),
            new Size(10, 10),
            new Color(0, 100, 0));

    Shape wrZ = new Elipse("wrZ", new CartPt(350, 390), new Size(10, 10),
            new Color(0, 150, 150));
    Command zRoute = new Command(wrZ, 0, 300, new CartPt(360, 100),
            new Size(10, 10),
            new Color(0, 150, 150));

    Shape te = new Elipse("TE", new CartPt(360, 380), new Size(10, 10),
            new Color(150, 0, 150));
    Command teRoute = new Command(te, 0, 300, new CartPt(360, 300),
            new Size(10, 10),
            new Color(150, 0, 150));

    Shape ball = new Elipse("Ball", new CartPt(250, 380), new Size(7, 3),
            new Color(210, 105, 30));
    Command ballDropBack = new Command(ball, 0, 100, new CartPt(250, 400),
            new Size(7, 3),
            new Color(210, 105, 30));
    Command letItRip = new Command(ball, 100, 300, new CartPt(190, 100),
            new Size(7, 3),
            new Color(210, 105, 30));


    AnimatorModel model = new SimpleAnimatorModel(
            new ArrayList<>(), 1000, 1000);
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);


    model.addShape(qb, c, rt, rg, lg, lt, wrX, wrY, hb, wrZ, te, ball);
    model.addCommand(qb, dropBack);
    model.addCommand(wrX, xRoute);
    model.addCommand(wrY, yRoute);
    model.addCommand(hb, hbRoute);
    model.addCommand(wrZ, zRoute);
    model.addCommand(te, teRoute);
    model.addCommand(ball, ballDropBack);
    model.addCommand(ball, letItRip);

    AnimatorController controller = new InteractiveVisualController(
            model, view, 1);
    controller.startProgram();
    List<Shape> shapes = model.getShapes();
    assertEquals(shapes, model.getShapes());
  }

  @Test
  public void testInteractiveReadFile2() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("buildings.txt",
            new SimpleAnimatorModelBuilder());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    AnimatorController controller = new InteractiveVisualController(
            model, view, 1);
    controller.startProgram();

    List<Shape> shapes = model.getShapes();
    assertEquals(shapes, model.getShapes());
  }

  @Test
  public void testInteractiveViewProgrammatic() throws FileNotFoundException {
    try {
      File newAnimation = new File("ProgrammaticAnimation.txt");
      if (!newAnimation.createNewFile()) {
        System.out.println("Animation file already exists.");
      }
      FileWriter myWriter = new FileWriter("ProgrammaticAnimation.txt");


      myWriter.write("canvas 800 800\n" +
              "rectangle name background min-x 0 min-y 0 " +
              "width 800 height 800 color 0.7333 0.37 0.976 from 1 to 4000\n");

      for (int i = 0; i < 8; i++) {
        myWriter.write("rectangle name B" + String.valueOf(i) +
                "  min-x " + String.valueOf(i) + "0 min-y 424 width 100 height 326 color 0 0."
                + String.valueOf(i) + " 0 from 1 to 4000\n");
      }

      for (int i = 0; i < 8; i++) {
        myWriter.write("move name B" + (i) + " moveto 100.0 "
                + (i * 5) + "0.0 200.0 " + (i) + "0.0 from 50" +
                "0 to 2000\n");
      }

      for (int i = 0; i < 8; i++) {
        myWriter.write("move name B" + (i) + " moveto 200.0 " + (i)
                + "0.0 100.0 " + (i) + "0.0 from 2000 to 3000\n");
      }

      for (int i = 0; i < 8; i++) {
        myWriter.write("move name B" + (i)
                + " moveto 100.0 " + (i)
                + "0.0 100.0 70.0 from 3000 to 3500\n");
      }

      for (int i = 0; i < 8; i++) {
        myWriter.write("move name B"
                + String.valueOf(i) + " moveto 100.0 70.0 100.0 70.0 from 3500 to 4000\n");
      }


      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred while trying to build an animation file.");
      e.printStackTrace();
    }

    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("ProgrammaticAnimation.txt",
            new SimpleAnimatorModelBuilder());
    InteractiveAnimatorGraphicsView view = new InteractiveAnimatorGraphicsView(model);
    AnimatorController controller = new InteractiveVisualController(
            model, view, 10);
    controller.startProgram();
    List<Shape> shapes = model.getShapes();
    assertEquals(shapes, model.getShapes());
  }

}
