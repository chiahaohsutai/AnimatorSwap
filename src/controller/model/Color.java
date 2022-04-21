package controller.model;

import java.util.Objects;

/**
 * A class that represent a Color. It has three fields representing the number of red, green and
 * blue. These are each positive integers between 0-256.
 */
public class Color {
  // Invariant: red, green and blue are all positive integers between 0-256
  private double red;
  private double blue;
  private double green;

  /**
   * Public constructor for Color.
   *
   * @param red   double red value
   * @param green double green value
   * @param blue  double blue value
   */
  public Color(double red, double green, double blue) {
    if (!validColors(red, green, blue)) {
      throw new IllegalArgumentException("Colors must be doubles greater then 0 and sum to 256");
    }

    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  /**
   * Copy constructor for a Color.
   * @param color Color to be copied
   */
  public Color(Color color) {
    this.red = color.getRed();
    this.green = color.getGreen();
    this.blue = color.getBlue();
  }


  /**
   * Determines if three color fields are valid.
   *
   * @param r int red
   * @param g int green
   * @param b int blue
   * @return boolean are the colors valid?
   */
  private boolean validColors(double r, double g, double b) {
    return (validColor(r) && validColor(g) && validColor(b));
  }

  /**
   * Determines if a given color is valid.
   *
   * @param color double color to be checked
   * @return boolean if it is between 0-256
   */
  private boolean validColor(double color) {
    return color >= 0 && color <= 256;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Color) {
      Color other = (Color) o;
      return other.red == red && other.blue == blue && other.green == green;
    }
    return false;
  }

  @Override
  public String toString() {
    return "rgb(" + red + "," + blue + "," + green + ")";
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, blue, green);
  }

  /**
   * Gets the red value of this Color.
   *
   * @return int red
   */
  public double getRed() {
    return red;
  }

  /**
   * Gets the blue value of this Color.
   *
   * @return
   */
  public double getBlue() {
    return blue;
  }

  /**
   * Gets the red value of this Color.
   *
   * @return
   */
  public double getGreen() {
    return green;
  }

  /**
   * Adds a given color to this Color.
   *
   * @param deltaColor color to be added
   * @throws IllegalArgumentException If adding to this color creates an invalid Color
   */
  public void addColor(Color deltaColor) {
    Color temp = new Color(this.getRed(), this.getBlue(), this.getGreen());
    temp.red += deltaColor.red;
    temp.blue += deltaColor.blue;
    temp.green += deltaColor.green;

    if (validColor(temp.red) && validColor(temp.green) && validColor(temp.blue)) {
      this.red += deltaColor.red;
      this.blue += deltaColor.blue;
      this.green += deltaColor.green;
    } else {
      throw new IllegalArgumentException("Can not add to this color");
    }
  }
}
