package model.animatorlayersimp;

import model.Command;
import model.Shape;

/**
 * Animator model that includes the ability to layer the shapes in the animation.
 */
public interface AnimatorLayers extends AnimatorLayersState {

  /**
   * Sets the layer for the given shape. Higher layers are drawn on top. This means that if
   * shape1 has layer 1 and shape2 has layer 2, shape2 is drawn on top of shape2. Shapes which are
   * in the same layer will be drawn at the in the order that they were added to the animator.
   *
   * @param name is the name/id of the shape in the animation.
   * @param layerNumber is the layer to which the shape is set to.
   * @throws IllegalArgumentException if the name is null or not in the animator.
   * @throws IllegalArgumentException if the layerNumber is negative of 0.
   */
  void setLayer(String name, int layerNumber);

  /**
   * Sets the bounds for the model.
   *
   * @param width is the width of the canvas.
   * @param height is the height of the canvas.
   * @throws IllegalArgumentException if any dimension is 0 or negative.
   */
  void setCanvasDim(int width, int height);

  /**
   * Queues a shape to be added to the model.
   *
   * @param shapes an array of shapes to be added queued to the model.
   * @throws IllegalArgumentException if the given shape list is null.
   * @throws IllegalArgumentException if a Shape with the same name already exists in the model.
   */
  void queueShapes(Shape... shapes);

  /**
   * Queues a command to be added to a shape in the model. You can only add commands to newly added
   * shapes. Meaning that you may only add new transforms to shape that where added through
   * queueing.
   *
   * @param commands an array of commands to be queued to the model.
   * @throws IllegalArgumentException if the given Command is null.
   */
  void queueCommands(Command... commands);

  /**
   * Clears the shapes in the queue.
   */
  void clearShapesQueue();

  /**
   * Clears the commands in the queue.
   */
  void clearCommandsQueue();
}
