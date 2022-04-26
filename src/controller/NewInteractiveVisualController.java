package controller;

import java.awt.event.ActionEvent;

import io.EditInputReader;
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
    StringBuilder status = new StringBuilder();
    EditInputReader inputReader = new EditInputReader((AnimatorLayers)model);

    inputReader.readInput(edits);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.equals("Edit Button")) {
      String newEdits = ((NewInteractiveView)view).getEdits();
      processEdit(newEdits);
    }

    else {
      super.actionPerformed(e);
    }
  }
}
