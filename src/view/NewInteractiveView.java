package view;

/**
 * An interface for adding functionality to an interactive view with the additional feature of
 * adding shapes to the currently playing animation.
 */
public interface NewInteractiveView extends InteractiveView {
  /**
   * Gets the shapes and motions to be added that was typed by the user.
   *
   * @return
   */
  String getEdits();
}
