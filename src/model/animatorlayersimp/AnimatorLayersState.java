package model.animatorlayersimp;

import java.util.List;
import model.AnimatorModel;
import model.Command;
import model.Shape;

/**
 * Represents all the methods from the Animator with Layers that return a value.
 * This interface is a read only instance of the animator.
 */
public interface AnimatorLayersState extends AnimatorModel {

  /**
   * Gets a copy of the shape with the given name.
   *
   * @param name is the name/id of the shape
   * @return a copy of the shape with the given name
   * @throws IllegalArgumentException if the name is null or not in the animator
   */
  Shape getShapeByName(String name);

  /**
   * Gets the layer at which the shape resides.
   *
   * @param name is the name/id of the shape.
   * @return the layer at which the shape resides.
   * @throws IllegalArgumentException if the name is null or not in the animator.
   */
  int getShapeLayer(String name);

  /**
   * Gets all the shapes in the given layer.
   *
   * @param layerNumber is the layer at which you want the shapes from.
   * @return the list with all the shapes at the given layer, or an empty list if there are no
   *     shapes at the given layer.
   * @throws IllegalArgumentException if the layer number is negative or zero.
   */
  List<Shape> getShapesAtLayer(int layerNumber);

  /**
   * Checks if any shapes have been queued to the animator.
   *
   * @return true if there are new shapes to be added to the animator.
   */
  boolean checkShapeQueue();

  /**
   * Checks if there are any commands to be queued to the animator.
   *
   * @return true if there exists new commands ro be added to the animator.
   */
  boolean checkCommandQueue();

  /**
   * Get the shapes queue.
   *
   * @returns all the shapes in the queue.
   */
  List<Shape> getShapesQueue();

  /**
   * Get the commands queue.
   *
   * @returns all the commands in the queue.
   */
  List<Command> getCommandsQueue();
}
