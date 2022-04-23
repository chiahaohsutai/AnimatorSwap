package model.animatorLayersImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import model.Shape;
import model.SimpleAnimatorModel;

/**
 * Represents an animator where the shapes can be order in layers. This means that the shapes
 * will have the ability to be drawn on top of each other based on the users input.
 */
public class AnimatorLayer extends SimpleAnimatorModel implements AnimatorLayers {
  private final Map<Integer, List<String>> layering;

  /**
   * Instantiate a new Animator with the same abilities as the SimpleAnimatorModel. This model
   * will allow shapes to be put into layers.
   *
   * @param shapes   is a list of shapes to be added to the model.
   */
  public AnimatorLayer(List<Shape> shapes) {
    // casting is safe since ShapeLayers is a subtype of shapes.
    super(shapes);
    this.layering = new TreeMap<>(Integer::compareTo);
    layering.put(1, new ArrayList<>());
  }

  @Override
  public void addShape(Shape... s) {
    super.addShape(s);
    List<String> layer1 = layering.get(1);
    Arrays.stream(s).forEach(shape -> layer1.add(shape.getName()));
  }

  @Override
  public void setLayer(String name, int layerNumber) {
    // validation of inputs.
    if (Objects.isNull(name) || layerNumber <= 0) {
      throw new IllegalArgumentException("Invalid parameters for the layer.");
    }
    if (shapes.stream().noneMatch(s -> s.getName().equals(name))) {
      throw new IllegalArgumentException("No shape has the given name.");
    }

    // gets the layer where the shape is currently.
    int previousLayer = getShapeLayer(name);

    // checks if the layer already exists.
    List<String> names;
    if (layering.containsKey(layerNumber)) {
      names = layering.get(layerNumber);

      // checks that the shape is not already there.
      if (names.stream().anyMatch(name::equals)) {
        throw new IllegalArgumentException("The given shape is already at the given layer.");
      }

    } else {
      // initializes the layer.
      names = new ArrayList<>();
      layering.put(layerNumber, names);
    }

    // add the shape to the new layer and remove shape from the original layer.
    names.add(name);
    layering.get(previousLayer).remove(name);
  }

  @Override
  public int getShapeLayer(String name) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name/ID cannot be null.");
    }
    for (Integer i : layering.keySet()) {
      if (layering.get(i).contains(name)) {
        return i;
      }
    }
    throw new IllegalArgumentException("No shape has the given name.");
  }

  @Override
  public List<Shape> getShapesAtLayer(int layerNumber) {
    if (layerNumber <= 0) {
      throw new IllegalArgumentException("The layer number is invalid.");
    }
    List<Shape> list = new ArrayList<>();
    if (layering.containsKey(layerNumber)) {
      layering.get(layerNumber).forEach(s -> list.add(getShape(s)));
    }
    return list;
  }

  @Override
  public void setCanvasDim(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Dimensions are invalid");
    }
    this.canvasWidth = width;
    this.canvasHeight = height;
  }

  @Override
  public List<Shape> getShapes() {
    List<Shape> shapesCopy = super.getShapes();
    shapesCopy.sort((s1, s2) -> getShapeLayer(s1.getName()) - getShapeLayer(s2.getName()));
    return shapesCopy;
  }

  @Override
  public String printMotionTable() {

    // print the instructions for reading the text output.
    StringBuilder ret = new StringBuilder();
    ret.append("The format for the text is the following:\n");
    ret.append("motion shapeID   startTick xPos yPos height width R G B   " +
            "endTick xPos yPos height width R G B\n");
    ret.append("(the first half is the original values and the second half is " +
            "the values after being transformed)\n\n");

    for (Shape s : shapes) {
      ret.append(String.format("Add shape %s", s.getName())).append("\n");
    }
    ret.append("\n");
    // loop through the shapes based on layering order (smallest to highest)
    for (int l : layering.keySet()) {
      ret.append(String.format("Layer %d\n", l));
      List<String> names = layering.get(l);
      names.forEach(s -> ret.append(printShapeMotion(Objects.requireNonNull(getShape(s)))));
    }
    return ret.toString();
  }

  /**
   * Gets the shape from the animator with the given name.
   *
   * @param name is the name/id of the shape.
   * @return a copy of the shape, if the shape is not in the animator it returns null.
   */
  private Shape getShape(String name) {
    for (Shape s : shapes) {
      if (s.getName().equals(name)) {
        return s.createShape();
      }
    }
    return null;
  }
}
