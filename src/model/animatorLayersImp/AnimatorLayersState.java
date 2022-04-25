package model.animatorLayersImp;

import java.util.List;
import model.AnimatorModel;
import model.Shape;

/**
 * Represents all the methods from the Animator with Layers that return a value.
 * This interface is a read only instance of the animator.
 */
public interface AnimatorLayersState extends AnimatorModel {

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
   * @throws IllegalArgumentException if the layer number is negative or zero.
   * @return the list with all the shapes at the given layer, or an empty list if there are no
   *     shapes at the given layer.
   */
  List<Shape> getShapesAtLayer(int layerNumber);
}
