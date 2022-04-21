package model;

/**
 * Represents a Shape with a location, size and color. These are methods that every Shape must
 * implement. A shape has a name, location, size and color. Name cannot change, but location, size
 * and color may change so they can be animated on the screen.
 */
public interface Shape {

  /**
   * Gets the current location of this Shape.
   *
   * @returns CartPt representing a two dimensional location.
   */
  CartPt getLocation();

  /**
   * Sets this shapes location to the given CartPt.
   *
   * @param cp location to be set
   * @throw IllegalArgumentException if the given location is null
   */
  void setLocation(CartPt cp);

  /**
   * Gets the current Color of this shape.
   *
   * @return A 3 element list of the current color. Ordered in red, green, blue.
   */
  Color getColor();


  /**
   * Changes this shapes Color to a given Color.
   *
   * @param r amount of red in the color
   * @param g amount of green in the color
   * @param b amount of blue in the color
   */
  void setColor(int r, int g, int b);

  /**
   * Gets the width of this Shape.
   *
   * @return double current width
   */
  Size getSize();

  /**
   * Sets a shape to givne size coordinates. Implementation specific.
   *
   * @param s Size first Size parameter
   */
  void setSize(Size s);

  /**
   * Creates a copy of this shape.
   *
   * @return Shape copy
   */
  Shape createShape();

  /**
   * Runs a given command for this Shape.
   *
   * @param c Command to be run
   */
  void runCommand(Command c, double currentTime);

  /**
   * Gets the name of this Shape.
   *
   * @return String name of the shape
   */
  String getName();

  /**
   * Type checker for a Shape. Used in drawing a Shape
   * Note: Professor said that a type checker is acceptable for this assignment
   * @return String representation of this type
   */
  String getType();
}
