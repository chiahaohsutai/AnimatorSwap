package controller;

import controller.model.AnimatorModel;
import view.VisualView;

/**
 * A class for controlling a visual view of the Animator. A visual controller handles time for the
 * animation and decides when shapes need to move. It interacts with both the view and a read only
 * version of the model.
 */
public class InteractiveController implements AnimatorController {

  protected final AnimatorModel model;
  protected final VisualView view;
  protected final double deltaTick;

  /**
   * Constructor for a SimpleAnimatorController.
   *
   * @param model     Model
   * @param view      View
   * @param deltaTick tickRate
   */
  public InteractiveController(AnimatorModel model, VisualView view,
                               double deltaTick) {
    this.model = model;
    this.view = view;
    this.deltaTick = deltaTick;
  }

  @Override
  public void startProgram() {
    this.view.makeVisible();

    // Determine last tick from model
    for (double tick = 0; tick < model.getLastTick(); tick += deltaTick) {
      try {
        Thread.sleep((long) (100 * deltaTick));
      } catch (InterruptedException e) {
        throw new IllegalStateException("Can not sleep");
      }
      view.updatePanel(tick);
      view.refresh();
    }
  }
}
