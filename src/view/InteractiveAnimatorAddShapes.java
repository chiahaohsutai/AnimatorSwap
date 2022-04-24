package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.AnimatorModelState;

/**
 * Represents another version of the interactive view for an animation. This version consists of
 * the additional feature of allowing the client to add a new shape to the currently open
 * animation, and specify motions for the new shape. The new shape should appear in the animation
 * as specified and persist as the animation is played, paused, restarted, etc.
 */
public class InteractiveAnimatorAddShapes extends InteractiveAnimatorGraphicsView
        implements InteractiveView {
  private JTextArea editText;
  private JButton editButton;

  /**
   * Public constructor for an AnimatorGraphicsTextView.
   *
   * @param model Model
   */
  public InteractiveAnimatorAddShapes(AnimatorModelState model) {
    super(model);

    super.setSize(new Dimension(900, 600));

    // set up edit panel
    JPanel editPanel = new JPanel();
    editPanel.setLayout(new FlowLayout());

    editText = new JTextArea(10, 20);
    JScrollPane scrollPane = new JScrollPane(editText);
    editText.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Describe shape and motions " +
            "to be added"));
    editPanel.add(scrollPane);

    editButton = new JButton();
    editButton.setActionCommand("Edit Button");
    editPanel.add(editButton);

    super.add(editPanel, BorderLayout.SOUTH);
  }

  @Override
  public void setListeners(ActionListener actionEvent) {
    super.setListeners(actionEvent);
  }
}
