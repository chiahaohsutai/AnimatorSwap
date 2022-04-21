package controller.model;

import java.util.List;

/**
 * Represents all information for an Animator program. An Animator program will keep track of
 * Shapes and commands to be executed on its shapes. A model has no control over time, and is only
 * modified when the controller tells it to. A Model uses the Shape interface to represent shapes
 * like eclipses and rectangles.
 */
public interface AnimatorModel extends AnimatorModelState {

  /**
   * Adds a shape to this model. Takes in multiple shapes to be easier to use.
   *
   * @param s Shape to be added
   * @throws IllegalArgumentException if the given shape list is null
   * @throws IllegalArgumentException if a Shape with the same name already exists in the model
   */
  void addShape(Shape... s);


  /**
   * Removes a given shape from the world.
   *
   * @param s Shape to be removed
   * @throws IllegalArgumentException if model does not contain the shape
   */
  void removeShape(Shape s);


  /**
   * Adds a command for a given shape. Does nothing if a given Shape does not exist in the model
   *
   * @param shape to be given a Command
   * @param c     Command given
   * @throws IllegalArgumentException if the given Shape or Command is null
   */
  void addCommand(Shape shape, Command c);

  /**
   * Removes a command for a given shape. If a given Shape does not exist, does nothing.
   *
   * @param shape to have a Command removed
   * @param c Command to be removed
   */
  void removeCommand(Shape shape, Command c);

  /**
   * Assigns the model's representation of Shapes to a given List. Used for resetting the model
   * to its original state or any state in between. Does not take in any commands, and reuses
   * commands already in the model.
   *
   * @param shapes List of Shapes
   * @throws IllegalArgumentException if the given Shape list is null
   */
  void setShapeList(List<Shape> shapes);
}
