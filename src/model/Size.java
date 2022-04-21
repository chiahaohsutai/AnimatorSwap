package model;

import java.util.Objects;

/**
 * A class that represents a size. Size has a double height and width. Height and width must be
 * greater than 0.
 */
public class Size {
  private double height;
  private double width;

  /**
   * Constructor for size.
   *
   * @param height double height value
   * @param width  double width value
   */
  public Size(double height, double width) {
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Height or width cannot be below 0");
    }
    this.height = height;
    this.width = width;
  }

  /**
   * Copy constructor for a size.
   * @param size Size to be copied
   */
  public Size(Size size) {
    this.height = size.getHeight();
    this.width = size.getWidth();
  }

  /**
   * Gets the height.
   *
   * @return double height
   */
  public double getHeight() {
    return height;
  }

  /**
   * Gets the width.
   *
   * @return double width
   */
  public double getWidth() {
    return width;
  }

  @Override
  public String toString() {
    return width + " " + height;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Size) {
      Size other = (Size) o;
      return other.height == height && other.width == width;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(height, width);
  }


  /**
   * Adds size to this height and width.
   *
   * @param deltaSize change in size
   */
  public void addSize(Size deltaSize) {
    height += deltaSize.height;
    width += deltaSize.width;
  }
}
