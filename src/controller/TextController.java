package controller;

import view.AnimatorView;

/**
 * A class for controller the text view. A TextController does not need access to the model. The
 * textController's only job is to the views refresh method once.
 */
public class TextController implements AnimatorController {
  private final AnimatorView view;

  /**
   * Constructor for a TextController.
   *
   * @param view View
   */
  public TextController(AnimatorView view) {
    this.view = view;
  }

  @Override
  public void startProgram() {
    view.refresh();
  }
}
