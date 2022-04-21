package model;

import java.util.List;
import java.util.Map;

/**
 * An interface for describing the state of an AnimatorModel.
 */
public interface AnimatorModelState {
  /**
   * Gets the current list of shapes in the model. Shapes are different geometric items to be
   * displayed by the animator.
   *
   * @return List of shapes
   */
  List<Shape> getShapes();

  /**
   * Prints a motion table for every shape in the model. A motion table represnets a Shapes
   * starting position, end position, start size, end size, start color, end color, start tick and
   * end tick.
   *
   * @return String representation of all movement.
   */
  String printMotionTable();

  /**
   * Gets a list of commands for a given Shape.
   * A command list represents commands to be executed on a given shape.
   *
   * @param shape Shape commands to get.
   * @return
   */
  List getCommands(Shape shape);

  /**
   * Gets a copy of this command map. A command map has keys of Strings and ArrayList as values.
   * A command map represents commands to be executed on shapes with a corresponding name.
   * @return
   */
  Map getCommandMap();

  /**
   * Updates all Shapes according to the given tickRate. This must be called everytime the clock
   * ticks. If this method is called with a current tick that is not the next actual next tick,
   * movement will not function. The controller MUST call this method everytime the clock ticks
   * to ensure movement, size changes and color changes are made correctly
   *
   * @param tick double current tick
   * @return List current list of shapes
   */
  List<Shape> getShapesAtTick(double tick);

  /**
   * Gets the last tick that a command occurs. Determines how long an animation should run.
   * @return
   */
  double getLastTick();

  /**
   * Gets the canvas width for this animation. Determines the window size of a JPannel.
   * @return int canvas width
   */
  int getCanvasWidth();

  /**
   * Gets the canvas height for this animation. Determines the window size of a JPannel.
   * @return canvas height
   */
  int getCanvasHeight();
}
