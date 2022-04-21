package controller.model;

import java.util.Objects;

/**
 * Represents a Shape that has a rounded and possibly elongated shape, looking like an egg.
 */
public class Elipse extends AShape {

  /**
   * Constructor for an Elipse.
   *
   * @param location CartPt location
   * @param s Size of this circle
   * @param color Color
   */
  public Elipse(String name, CartPt location, Size s, Color color) {
    super(name, location, s, color);
  }


  @Override
  public Shape createShape() {
    return new Elipse(this.name, new CartPt(location), new Size(size), new Color(color));
  }

  @Override
  public String getType() {
    return "ellipse";
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Elipse) {
      Elipse other = (Elipse) o;
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
