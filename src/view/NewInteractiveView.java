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

  /**
   * Sets the text displayed by the label. This label displays whether the user inputted text
   * was processed successfully or not. If it was processed successfully, that means the shapes
   * and motions were added to the currently playing animation. If not, that means the input was
   * invalid.
   *
   * @param update the edit status update (whether it was successfully processed or not)
   */
  void setEditStatusLabel(String update);
}
