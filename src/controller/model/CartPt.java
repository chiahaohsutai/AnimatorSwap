package controller.model;

import java.util.Objects;

/**
 * Represents a two double field that keeps track of location.
 */
public class CartPt {
  private double x;
  private double y;

  /**
   * Constructor for CartPt.
   *
   * @param x double x location
   * @param y double y location
   */
  public CartPt(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor for a CartPt.
   *
   * @param prev CartPt to copy
   */
  public CartPt(CartPt prev) {
    this.x = prev.x;
    this.y = prev.y;
  }

  /**
   * Gets a copy of x.
   *
   * @return K x.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets a copy of y.
   *
   * @return K y.
   */
  public double getY() {
    return y;
  }

  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Object can't be null");
    } else if (o instanceof CartPt) {
      CartPt temp = (CartPt) o;
      return temp.x == x && temp.y == y;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * Increases this location by a given amount.
   *
   * @param other Location to be added
   */
  public void addDistance(CartPt other) {
    this.x += other.x;
    this.y += other.y;
  }
}
