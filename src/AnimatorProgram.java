import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import controller.AnimatorController;
import controller.InteractiveVisualController;
import controller.SVGController;
import controller.TextController;
import controller.InteractiveController;
import model.AnimatorModel;
import model.SimpleAnimatorModel;
import io.AnimationFileReader;
import view.AnimatorGraphicsView;
import view.AnimatorTextView;
import view.AnimatorView;
import view.InteractiveAnimatorGraphicsView;
import view.SVGView;
import io.AnimatorModelBuilder;
import view.VisualView;

/**
 * Main class for the Animator. Parses command line arguments and creates an SimpleAnimatorModel
 * from text. Can read and write to a file, write to the console, and display interactive, visual,
 * SVG and text versions of the view.
 */
public class AnimatorProgram {

  /**
   * Main method for the Animator.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    if (args == null) {
      throw new IllegalArgumentException();
    }

    AnimatorModel model = new SimpleAnimatorModel(new ArrayList<>());
    AnimatorView view = null;
    AnimatorController controller = null;
    Appendable output = System.out;
    String outfileName = null;
    double inputSpeed = 1;

    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-in":
          try {
            AnimationFileReader reader = new AnimationFileReader();
            model = reader.readFile(args[i + 1], new AnimatorModelBuilder());
          } catch (FileNotFoundException e) {
            displayErrorMsg("Input file can't be found");
          }
          break;

        case "-view":
          switch (args[i + 1]) {
            case "text":
              try {
                if (outfileName == null) {
                  view = new AnimatorTextView(model, output);
                } else {
                  view = new AnimatorTextView(model, outfileName);
                }
              } catch (IllegalArgumentException e) {
                displayErrorMsg("Model is null");
              }
              break;
            case "svg":
              try {
                view = new SVGView(model, outfileName);
              } catch (IllegalArgumentException e) {
                displayErrorMsg("Model is null or no output file given");
              }
              break;

            case "visual":
              try {
                view = new AnimatorGraphicsView(model);
              } catch (IllegalArgumentException e) {
                displayErrorMsg("Model is null");
              }
              break;

            case "interactive":
              try {
                view = new InteractiveAnimatorGraphicsView(model);
              } catch (IllegalArgumentException e) {
                displayErrorMsg("Model is null");
              }
              break;

            default:
              displayErrorMsg("Invalid view");
          }
          break;

        case "-out":
          outfileName = args[i + 1];
          if (view instanceof AnimatorTextView) {
            view = new AnimatorTextView(model, outfileName);
          } else {
            view = new SVGView(model, outfileName);
          }
          break;

        case "-speed":
          try {
            inputSpeed = Double.parseDouble(args[i + 1]);
          } catch (NumberFormatException e) {
            displayErrorMsg("Invalid speed");
          }
          break;

        default:
          displayErrorMsg("Invalid argument");
      }
    }

    if (view instanceof SVGView) {
      SVGView svgView = new SVGView(model, outfileName);
      controller = new SVGController(svgView, inputSpeed);
    } else if (view instanceof InteractiveAnimatorGraphicsView) {
      InteractiveAnimatorGraphicsView interactiveAnimatorGraphicsView =
              (InteractiveAnimatorGraphicsView) view;
      controller = new InteractiveVisualController(
              model, interactiveAnimatorGraphicsView, inputSpeed);
    } else if (view instanceof VisualView) {

      VisualView visualView = (VisualView) view;
      controller = new InteractiveController(model, visualView, inputSpeed);
    } else if (view instanceof AnimatorTextView) {
      AnimatorTextView textView = (AnimatorTextView) view;
      controller = new TextController(textView);
    }

    try {
      controller.startProgram();
    } catch (NullPointerException e) {
      displayErrorMsg("cannot start program with given arguments");
    }

  }

  /**
   * Displays an error message to a JOPotionPanel.
   *
   * @param msg message to be written
   */
  private static void displayErrorMsg(String msg) {
    JOptionPane.showMessageDialog(new JFrame(),
            msg);
    System.exit(0);

  }


}
