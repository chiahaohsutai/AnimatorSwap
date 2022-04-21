package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import controller.model.AnimatorModelState;

/**
 * A class for adding interactive view options. InteractiveAnimatorGraphicsView extends
 * AnimatorGraphicsView and uses its buttonPanel and shapePanel. The InteractiveView supports the
 * ability to pause, rewind and loop an animation.
 */
public class InteractiveAnimatorGraphicsView extends AnimatorGraphicsView
        implements InteractiveView {

  private JButton pauseButton;
  private JButton rewindButton;
  private JCheckBox loopBox;

  /**
   * Public constructor for an AnimatorGraphicsTextView.
   *
   * @param model Model
   */
  public InteractiveAnimatorGraphicsView(AnimatorModelState model) {
    super(model);

    pauseButton = new JButton("Pause");
    super.buttonPanel.add(pauseButton);

    rewindButton = new JButton("Rewind");
    super.buttonPanel.add(rewindButton);

    loopBox = new JCheckBox("Loop");
    super.buttonPanel.add(loopBox);
  }

  /**
   * Provide the view with an action listener for
   * the button that should cause the program to
   * process a command. This is so that when the button
   * is pressed, control goes to the action listener.
   *
   * @param actionEvent ActionEvent to be given
   */
  public void setListeners(ActionListener actionEvent) {
    pauseButton.addActionListener(actionEvent);
    rewindButton.addActionListener(actionEvent);
    loopBox.addActionListener(actionEvent);
  }


  @Override
  public void togglePausePlay() {
    if (pauseButton.getText().equals("Pause")) {
      pauseButton.setText("Play");
    } else {
      pauseButton.setText("Pause");
    }
  }

}
