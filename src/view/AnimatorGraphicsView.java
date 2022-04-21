package view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.model.AnimatorModelState;

/**
 * A class for displaying a graphic version of a Shape animation. An AnimatorGraphicsView has
 * a Model, quit button, time panel, button panel, shape panel and time label. This view has
 * its refresh method called every tick, and updates its shape panel by passing it the most updated
 * list of shapes.
 */
public class AnimatorGraphicsView extends JFrame implements VisualView, AnimatorView {
  private final AnimatorModelState model;
  private JButton quitButton;
  protected JPanel buttonPanel;
  protected JPanel timePanel;
  protected ShapePanel shapePanel;
  protected JLabel currentTick;

  /**
   * Public constructor for an AnimatorGraphicsTextView. Takes in a read only version of the model.
   *
   * @param model Model
   */
  public AnimatorGraphicsView(AnimatorModelState model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Invalid view parameters");
    }

    this.setTitle("Animator View");
    this.setSize(model.getCanvasWidth(), model.getCanvasHeight());
    this.model = model;


    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    timePanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(timePanel, BorderLayout.NORTH);

    shapePanel = new ShapePanel();
    shapePanel.setPreferredSize(new Dimension(model.getCanvasWidth(), model.getCanvasHeight()));
    this.add(shapePanel, BorderLayout.CENTER);
    shapePanel.takeCommandList(model.getCommandMap());

    currentTick = new JLabel("0");
    timePanel.add(currentTick);

    //quit button
    quitButton = new JButton("Quit");
    buttonPanel.add(quitButton);

    this.pack();
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setTicksPerSecond(double ticksPerSecond) {
    throw new UnsupportedOperationException("Cannot set tick rate in view");
  }

  @Override
  public void updatePanel(double tick) {
    shapePanel.takeShapeList(model.getShapesAtTick(tick));
    currentTick.setText("Current tick: " + (int) tick);
  }

}
