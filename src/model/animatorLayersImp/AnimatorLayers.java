package model.animatorLayersImp;

import java.util.List;
import model.AnimatorModel;
import model.Shape;

/**
 * Animator model that includes the ability to layer the shapes in the animation.
 */
public interface AnimatorLayers extends AnimatorModel {

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

  /**
   * Sets the bounds for the model.
   *
   * @param width is the width of the canvas.
   * @param height is the height of the canvas.
   * @throws IllegalArgumentException if any dimension is 0 or negative.
   */
  void setCanvasDim(int width, int height);
}
