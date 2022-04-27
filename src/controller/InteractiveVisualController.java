package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.AnimatorModel;
import model.Shape;
import view.InteractiveView;

/**
 * A class for controlling an interactive version of the Animator program. An Interactive controller
 * supports the ability to pause, play, loop, and rewind an animation.
 * The InteractiveController keeps
 * track of a copy of the orginalShapes list to reset an animation.
 */
public class InteractiveVisualController implements VisualAnimatorController, ActionListener {
  protected final double tickRate;
  protected boolean repeated;
  protected final List<Shape> originalShapes;
  protected AnimatorModel model;
  protected InteractiveView view;
  protected boolean isPaused = false;
  protected boolean isReset = false;

  /**
   * Constructor for a SimpleAnimatorController.
   *
   * @param model       Model
   * @param view        View
   * @param ticksPerSec tickRate
   */
  public InteractiveVisualController(AnimatorModel model, InteractiveView view,
                                     double ticksPerSec) {
    this.model = model;
    this.view = view;
    this.tickRate = ticksPerSec / 1000;
    this.repeated = false;

    this.originalShapes = copyShapes(model.getShapes());
    System.out.println(originalShapes);
  }

  /**
   * Copies a list of shape into a new List.
   *
   * @param shapes List of shapes to be copied
   * @return List copy of given shape list
   */
  protected List<Shape> copyShapes(List<Shape> shapes) {
    List<Shape> ret = new ArrayList<>();
    for (Shape s : shapes) {
      ret.add(s.createShape());
    }
    return ret;
  }


  @Override
  public void startProgram() {
    view.makeVisible();
    view.setListeners(this);

    // Determine last tick from model
    System.out.println(model.getLastTick());

    do {
      isReset = false;
      double currentTick = 0;
      model.setShapeList(copyShapes(originalShapes));

      while (currentTick < model.getLastTick() && !isReset) {
        if (isPaused) {
          // do nothing
        } else {
          try {
            Thread.sleep((long) tickRate);
          } catch (InterruptedException e) {
            throw new IllegalStateException("Cannot sleep");
          }
          view.updatePanel(currentTick);
          view.refresh();
          currentTick += tickRate;
        }
      }

    }
    while (repeated || isReset);
  }

  @Override
  public void rewind() {
    isReset = true;
    model.setShapeList(originalShapes);
  }

  @Override
  public void pause() {
    if (isPaused) {
      isPaused = false;
    } else {
      isPaused = true;
    }
  }

  @Override
  public void loop() {
    if (repeated) {
      repeated = false;
    } else {
      repeated = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    switch (command) {
      case "Play":
      case "Pause":
        pause();
        view.togglePausePlay();
        break;

      case "Rewind":
        rewind();
        break;

      case "Loop":
        loop();
        break;
      default:
        throw new IllegalStateException("Invalid Command");
    }
  }
}
