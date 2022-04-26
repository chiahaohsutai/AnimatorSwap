package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import io.EditInputReader;
import model.Shape;
import model.animatorLayersImp.AnimatorLayers;
import view.NewInteractiveView;

/**
 * Represents the concrete implementation of the controller for the interactive view with the
 * additional feature of adding shapes to the currently playing animation. The controller will
 * handle the processing of the info provided in the view and communicate with the model
 * accordingly.
 */
public class NewInteractiveVisualController extends InteractiveVisualController
        implements NewVisualAnimatorController {

  /**
   * Constructor for a NewInteractiveVisualController.
   *
   * @param model       Model
   * @param view        View
   * @param ticksPerSec tickRate
   */
  public NewInteractiveVisualController(AnimatorLayers model,
                                        NewInteractiveView view,
                                        double ticksPerSec) {
    super(model, view, ticksPerSec);
  }

  @Override
  public void processEdit(String edits) {
    EditInputReader inputReader = new EditInputReader((AnimatorLayers) model,
            (NewInteractiveView) view);
    inputReader.readInput(edits);
  }

  @Override
  public void startProgram() {
    view.makeVisible();
    view.setListeners(this);

    // Determine last tick from model
    System.out.println(model.getLastTick());

    do {
      isReset = false;
      double currentTick = 0;
      model.setShapeList(copyShapes(originalShapes));

      while (currentTick < model.getLastTick() && !isReset) {
        if (isPaused) {
          // do nothing
        } else {
          try {
            Thread.sleep((long) tickRate);
          } catch (InterruptedException e) {
            throw new IllegalStateException("Cannot sleep");
          }
          view.updatePanel(currentTick);
          view.refresh();
          currentTick += tickRate;
        }
      }

    }
    while (repeated || isReset);

  }

  /**
   * Copies a list of shape into a new List.
   *
   * @param shapes List of shapes to be copied
   * @return List copy of given shape list
   */
  private List<Shape> copyShapes(List<Shape> shapes) {
    List<Shape> ret = new ArrayList<>();
    for (Shape s : shapes) {
      ret.add(s.createShape());
    }
    return ret;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    switch (command) {
      case "Play":
      case "Pause":
        pause();
        view.togglePausePlay();
        break;

      case "Rewind":
        rewind();
        break;

      case "Loop":
        loop();
        break;

      case "Edit":
        String newEdits = ((NewInteractiveView) view).getEdits();
        processEdit(newEdits);
        break;

      default:
        throw new IllegalStateException("Invalid Command");
    }
  }
}
