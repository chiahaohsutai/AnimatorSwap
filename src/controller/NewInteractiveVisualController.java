package controller;

import model.AnimatorModel;
import view.InteractiveAnimatorGraphicsView;

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
  public NewInteractiveVisualController(AnimatorModel model, InteractiveAnimatorGraphicsView view,
                                        double ticksPerSec) {
    super(model, view, ticksPerSec);
  }

  @Override
  public String processEdit(String edits) {

    return null;
  }
}
