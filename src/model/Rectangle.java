package model;

import java.util.Objects;

/**
 * Represents Shape that has 90 degree angles and four sides.
 */
public class Rectangle extends AShape {
  /**
   * Constructor for a Circle.
   *
   * @param location CartPt location

   */
  public Rectangle(String name, CartPt location, Size size, Color color) {
    super(name, location, size, color);
  }

  @Override
  public Shape createShape() {
    Shape ret = new Rectangle(this.name, new CartPt(location), new Size(size), new Color(color));
    return ret;
  }

  @Override
  public String getType() {
    return "rect";
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Rectangle) {
      Rectangle other = (Rectangle) o;
      return size.equals(other.size) &&
              location.equals(other.location) && color.equals(other.color);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(size, location, color);
  }
}
