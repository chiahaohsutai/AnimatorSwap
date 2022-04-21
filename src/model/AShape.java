package model;

import java.util.Objects;

/**
 * An abstract class for a Shape. Implements some shape methods and leaves some to be implemented
 * by extending classes. Each Shape is expected to have a unique name field, as it is used to
 * map Shapes to their commands. Shapes have a name, location, size and color. Each of these fields
 * can be modified to change properties of a shape. Shapes are mainly modified in runCommand,
 * where this shape is moved, scaled, and faded by a given amount.
 */
public abstract class AShape implements Shape {

  protected final String name;
  protected CartPt location;
  protected Size size;
  protected Color color;

  /**
   * Constructor for a Circle.
   *
   * @param location CartPt location
   */
  public AShape(String name, CartPt location, Size s, Color color) {
    if (location == null || s == null || color == null) {
      throw new IllegalArgumentException("Location can't be null");
    }
    this.name = name;
    this.location = location;
    this.size = s;
    this.color = color;
  }

  @Override
  public CartPt getLocation() {
    return new CartPt(location);
  }

  @Override
  public void setLocation(CartPt cp) {
    if (cp == null) {
      throw new IllegalArgumentException("Given CartPt can't be null");
    }
    this.location = new CartPt(cp);
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setColor(int r, int g, int b) {
    try {
      color = new Color(r, g, b);

    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid color arguments");
    }
  }

  @Override
  public Size getSize() {
    return size;
  }

  @Override
  public void setSize(Size s) {
    if (size == null) {
      throw new IllegalArgumentException();
    }
    size = s;
  }

  @Override
  public void runCommand(Command c, double currentTime) {
    double startTime = c.getStartTime();
    double endTime = c.getEndTime();

    double firstMultiplier = (endTime - currentTime ) / (endTime - startTime);
    double secondMultiplier = (currentTime - startTime ) / (endTime - startTime);

    double updatedX = c.getStartLoc().getX() * firstMultiplier + c.getEndLoc().getX() *
            secondMultiplier;
    double updatedY = c.getStartLoc().getY() * firstMultiplier + c.getEndLoc().getY() *
            secondMultiplier;

    double updatedHeight = c.getStartSize().getHeight() *
            firstMultiplier + c.getEndSize().getHeight() * secondMultiplier;
    double updatedWidth = c.getStartSize().getWidth() * firstMultiplier + c.getEndSize().getWidth()
            * secondMultiplier;

    double updatedR = c.getStartColor().getRed() * firstMultiplier + c.getEndColor().getRed()  *
            secondMultiplier;
    double updatedG = c.getStartColor().getGreen() * firstMultiplier + c.getEndColor().getGreen()
            * secondMultiplier;
    double updatedB = c.getStartColor().getBlue() * firstMultiplier + c.getEndColor().getBlue()  *
            secondMultiplier;

    location = new CartPt(updatedX, updatedY);
    size = new Size(updatedHeight, updatedWidth);
    color = new Color(updatedR, updatedG, updatedB);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Shape) {
      Shape other = (Shape) o;
      return location.equals(other.getLocation()) && size.equals(other.getSize()) &&
              color.equals(other.getColor());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, size, color);
  }

  @Override
  public String toString() {
    return "Name:" + name + " Location: " + location + " Size: " + size + " Color: " + color;
  }
}
