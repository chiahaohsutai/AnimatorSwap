package model.animatorLayersImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import model.Command;
import model.Shape;
import model.SimpleAnimatorModel;

/**
 * Represents an animator where the shapes can be order in layers. This means that the shapes
 * will have the ability to be drawn on top of each other based on the users input.
 */
public class AnimatorLayer extends SimpleAnimatorModel implements AnimatorLayers {
  private final Map<Integer, List<String>> layering;
  private final List<Shape> newShapes;
  private final List<Command> newCommands;

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
    this.newShapes = new ArrayList<>();
    this.newCommands = new ArrayList<>();
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

      // checks that the shape is already there. Do nothing if already there.
      if (names.stream().anyMatch(name::equals)) {
        return;
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
  public Shape getShapeByName(String name) {
    // validate inputs.
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name/ID cannot be null.");
    }

    for (Shape singleShape : shapes) {
      if (singleShape.getName().equals(name)) {
        return singleShape.createShape();
      }
    }
    throw new IllegalArgumentException("No shape exists with given name.");
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
  public boolean checkShapeQueue() {
    return newShapes.size() > 0;
  }

  @Override
  public boolean checkCommandQueue() {
    return newCommands.size() > 0;
  }

  @Override
  public List<Shape> getShapesQueue() {
    return newShapes.stream().map(Shape::createShape).collect(Collectors.toList());
  }

  @Override
  public List<Command> getCommandsQueue() {
    return newCommands.stream().map(Command::new).collect(Collectors.toList());
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
  public void queueShapes(Shape... shapes) {
    if (Arrays.stream(shapes).anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Shapes cannot be null");
    }
    List<String> names = this.shapes.stream().map(Shape::getName).collect(Collectors.toList());
    if (Arrays.stream(shapes).anyMatch(s -> names.contains(s.getName()))) {
      throw new IllegalArgumentException("At least one shape has the same name as an existing " +
              "shape in the animator.");
    }
    newShapes.addAll(Arrays.asList(shapes));
  }

  @Override
  public void queueCommands(Command... commands) {
    if (Arrays.stream(commands).anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Commands cannot be null");
    }
    List<String> names = newShapes.stream().map(Shape::getName).collect(Collectors.toList());
    if (Arrays.stream(commands).noneMatch(c -> names.contains(c.getShape().getName()))) {
      throw new IllegalArgumentException("Invalid shape id for the transform.\nYou need to " +
              "add the shape before transforming it");
    }
    newCommands.addAll(Arrays.asList(commands));
  }

  @Override
  public void clearShapesQueue() {
    newShapes.clear();
  }

  @Override
  public void clearCommandsQueue() {
    newCommands.clear();
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

  @Override
  public void removeShape(Shape s) {
    super.removeShape(s);
    int index = getShapeLayer(s.getName());
    layering.get(index).remove(s.getName());
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
