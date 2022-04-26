package view;

import java.awt.event.ActionListener;

// edit: made InteractiveView extend VisualView because
// in other places where InteractiveView is used, we have access to the helpful methods that are
// offered in the VisualView interface
/**
 * An interface for adding functionality to a view. The interactive view supports the ability
 * to pause, play, loop and rewind an Animation.
 */
public interface InteractiveView extends VisualView {

  /**
   * Switch between pause and play text for the pause button. Lets the pause button change its text
   * so the user knows if the Animator is paused or not.
   */
  void togglePausePlay();

  /**
   * Provide the view with an action listener for
   * the button that should cause the program to
   * process a command. This is so that when the button
   * is pressed, control goes to the action listener.
   *
   * @param actionEvent ActionEvent to be given
   */
  void setListeners(ActionListener actionEvent);

}
