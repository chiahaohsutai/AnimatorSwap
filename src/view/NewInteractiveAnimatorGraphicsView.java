package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.AnimatorModelState;

/**
 * Represents another version of the interactive view for an animation. This version consists of
 * the additional feature of allowing the client to add a new shape to the currently open
 * animation, and specify motions for the new shape. The new shape should appear in the animation
 * as specified and persist as the animation is played, paused, restarted, etc. The GUI for this
 * view will have a text box in which the client types the shapes and transformations to be
 * added and a button that processes these edits once pressed.
 */
public class NewInteractiveAnimatorGraphicsView extends InteractiveAnimatorGraphicsView
        implements NewInteractiveView {
  private JLabel editStatusLabel;
  private JTextArea editText;
  private JButton editButton;

  /**
   * Public constructor for an AnimatorGraphicsTextView.
   *
   * @param model Model
   */
  public NewInteractiveAnimatorGraphicsView(AnimatorModelState model) {
    super(model);

    super.setSize(new Dimension(1500, 600));

    // set up edit panel
    JPanel editPanel = new JPanel();
    editPanel.setLayout(new BorderLayout());

    editText = new JTextArea(30, 35);
    JScrollPane scrollPane = new JScrollPane(editText);
    editText.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Describe shape and motions " +
            "to be added"));
    editPanel.add(scrollPane, BorderLayout.CENTER);

    editButton = new JButton("Add Edits");
    editButton.setActionCommand("Edit Button");
    editPanel.add(editButton, BorderLayout.SOUTH);

    editStatusLabel = new JLabel("");
    editPanel.add(editStatusLabel, BorderLayout.NORTH);

    super.add(editPanel, BorderLayout.LINE_END);
  }

  @Override
  public void setListeners(ActionListener actionEvent) {
    super.setListeners(actionEvent);
    editButton.addActionListener(actionEvent);
  }

  @Override
  public String getEdits() {
    String edits = editText.getText();
    editText.setText("");
    return edits;
  }
}
