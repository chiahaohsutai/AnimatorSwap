package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents an instance of an Animator model. Keeps track of Shapes by using
 * a List of Shapes and tracks commands by storing a Map of type String to type List of commands.
 * The map maps the name of a Shape to its list of commands, starts as a new empty list. Assumes
 * that each shape's name is unique.
 */
public class SimpleAnimatorModel implements AnimatorModel {
  protected final Map<String, ArrayList<Command>> commands = new HashMap<>();
  protected final int canvasHeight;
  protected final int canvasWidth;
  protected List<Shape> shapes = new ArrayList<>();


  /**
   * Constructor for a SimpleAnimatorModel. Takes ina list of shapes
   *
   * @param shapes List to be assigned
   */
  public SimpleAnimatorModel(List<Shape> shapes) {
    if (shapes == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    canvasHeight = 500;
    canvasWidth = 500;
  }

  /**
   * Constructor that defines a canvas size.
   *
   * @param shapes       List of shapes
   * @param canvasHeight int
   * @param canvasWidth  int
   */
  public SimpleAnimatorModel(List<Shape> shapes, int canvasHeight, int canvasWidth) {
    if (shapes == null || canvasHeight < 0 || canvasWidth < 0) {
      throw new IllegalArgumentException();
    }
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
  }

  @Override
  public List<Shape> getShapesAtTick(double currentTime) {

    for (Shape s : shapes) {
      List<Command> cmds = commands.get(s.getName());
      List<Command> activeCommands = new ArrayList<>();

      if (!commands.containsKey(s.getName())) {
        throw new IllegalArgumentException("");
      }

      // Determines which commands on this shape are currently active
      for (Command c : cmds) {
        if (c.insideTicks(currentTime)) {
          activeCommands.add(c);
        }
      }

      // Run each active command on a shape
      for (Command c : activeCommands) {
        s.runCommand(c, currentTime);
      }
    }

    return copyShapes(shapes);
  }

  /**
   * Copies a list of shape into a new List.
   *
   * @param shapes List of shapes to be copied
   * @return
   */
  private List<Shape> copyShapes(List<Shape> shapes) {
    List<Shape> ret = new ArrayList<>();
    for (Shape s : shapes) {
      Shape temp = s.createShape();
      ret.add(temp);
    }
    return ret;
  }

  @Override
  public double getLastTick() {
    double lastTick = 100;

    for (String key : commands.keySet()) {

      for (Command c : commands.get(key)) {
        if (c.getEndTime() > lastTick) {
          lastTick = c.getEndTime();
        }
      }

    }
    return lastTick;
  }

  @Override
  public int getCanvasWidth() {
    return canvasWidth;
  }

  @Override
  public int getCanvasHeight() {
    return canvasHeight;
  }

  @Override
  public void addCommand(Shape shape, Command c) {
    if (commands.containsKey(shape.getName())) {
      List cmds = commands.get(shape.getName());
      cmds.add(c);
    }
  }

  @Override
  public void removeCommand(Shape shape, Command c) {
    if (shape == null || c == null) {
      throw new IllegalArgumentException("Shape or command cannot be null");
    }
    if (commands.containsKey(shape.getName()) && commands.get(shape.getName()).contains(c)) {
      commands.get(shape.getName()).remove(c);
    }
  }

  @Override
  public void setShapeList(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void addShape(Shape... s) {
    if (s == null) {
      throw new IllegalArgumentException("List can not be null");
    }

    List<String> check = new ArrayList<>();
    for (Shape currShape : s) {
      check.add(currShape.getName());
    }

    for (Shape currShape : s) {
      if (commands.containsKey(currShape.getName()) ||
              Collections.frequency(check, currShape.getName()) > 1) {
        throw new IllegalArgumentException("Names must be unique to each shape");
      }
    }

    for (Shape curr : s) {
      shapes.add(curr);
      commands.put(curr.getName(), new ArrayList<>());
    }
  }

  @Override
  public List<Shape> getShapes() {
    List ret = new ArrayList();
    for (Shape s : shapes) {
      ret.add(s.createShape());
    }
    return ret;
  }

  @Override
  public void removeShape(Shape s) {
    if (shapes.contains(s)) {
      shapes.remove(s);
      commands.remove(s);
    } else {
      throw new IllegalArgumentException("The given shape is not in the list");
    }
  }


  /**
   * Prints all motion for a given Shape.
   *
   * @param s Shape to be printed
   * @return String representation of all the given shape's movement
   */
  protected String printShapeMotion(Shape s) {
    String ret = "";
    List<Command> cmds = commands.get(s.getName());
    for (Command c : cmds) {
      ret = ret + c.toString();
    }
    return ret;
  }

  @Override
  public String printMotionTable() {
    String ret = "#                  start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n";

    for (Shape s : shapes) {
      ret = ret + printShapeMotion(s) + "\n";
    }
    return ret;
  }

  /**
   * Getter method for the map of commands.
   *
   * @return List of commands for a given shape
   * @throws IllegalArgumentException if shape is not in list
   */
  public List getCommands(Shape s) {
    if (commands.containsKey(s.getName())) {
      return new ArrayList(commands.get(s.getName()));
    }
    throw new IllegalArgumentException("Shape not in list");
  }

  @Override
  public Map getCommandMap() {
    return new HashMap(this.commands);
  }

}
