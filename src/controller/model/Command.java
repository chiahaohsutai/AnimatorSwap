package controller.model;

import java.util.Objects;

/**
 * This class represents a command that is used to move, scale or change the color of a Shape over
 * a given start and end tick.
 */
public class Command {

  private final Shape shape;

  private final double startTick;
  private final double endTick;

  private final CartPt startLoc;
  private final CartPt endLoc;

  private final Size startSize;
  private final Size endSize;

  private final Color startColor;
  private final Color endColor;

  /**
   * Convenience Constructor that only takes in a starting shape, start tick, end tick, and end
   * fields for location size and color. Calls the main constructor with the given Shapes starting
   * fields.
   *
   * @param shape     Shape
   * @param startTick double
   * @param endTick   double
   * @param endLoc    Location
   * @param endSize   Size
   * @param endColor  Color
   */
  public Command(Shape shape, double startTick, double endTick, CartPt endLoc,
                 Size endSize, Color endColor) {
    this(shape, startTick, endTick, shape.getLocation(), endLoc, shape.getSize(), endSize,
            shape.getColor(), endColor);
  }


  /**
   * Main constructor for a command. Takes in a Shape to have the command execu
   * ted on, starting tick,
   * end tick, starting location, end locatin, start size, end size, start color, and end color.
   *
   * @param shape Shape to have command executed on
   * @param startTick double starting tick
   * @param endTick double ending tick
   * @param startLoc CartPt starting location
   * @param endLoc CartPt ending location
   * @param startSize Size starting size
   * @param endSize Size ending size
   * @param startColor Color starting color
   * @param endColor Color ending color
   *                    * @throws IllegalArgumentException if any fields given are null.
   */
  public Command(Shape shape, double startTick, double endTick, CartPt startLoc, CartPt endLoc,
                 Size startSize, Size endSize, Color startColor, Color endColor) {
    if (startTick > endTick || startTick < 0 || endTick < 0) {
      throw new IllegalArgumentException("Illegal tick rate");
    } else if (shape == null || endColor == null || endSize == null || endLoc == null ||
            startColor == null || startLoc == null || startSize == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    // Test for if start location isn't actually where shape starts

    this.shape = shape;

    this.startTick = startTick;
    this.endTick = endTick;

    this.startLoc = startLoc;
    this.endLoc = endLoc;
    //this.avgDistanceChange = setAvgDistanceChange();

    this.startSize = startSize;
    this.endSize = endSize;
    //this.avgSizeChange = setAvgSizeChange();

    this.startColor = startColor;
    this.endColor = endColor;
    //this.deltaColor = setAvgDeltaColor();
  }

  /**
   * Determines if a given tick happens between this commands start and end.
   *
   * @param tick double current tick
   * @return boolean if the tick is between the start and end
   */
  public boolean insideTicks(double tick) {
    return tick > startTick && tick <= endTick;
  }


  /**
   * Gets the average distance change for this command.
   *
   * @return CartPt average distance change
   */
  public CartPt getStartLoc() {
    return startLoc;
  }

  /**
   * Gets the average distance change for this command.
   *
   * @return CartPt average distance change
   */
  public CartPt getEndLoc() {
    return endLoc;
  }

  /**
   * Gets the average change in Size for this command.
   *
   * @return Size average size change
   */
  public Size getStartSize() {
    return startSize;
  }

  /**
   * Gets the average change in Size for this command.
   *
   * @return Size average size change
   */
  public Size getEndSize() {
    return endSize;
  }

  /**
   * Gets the average change in Color for this command.
   *
   * @return Color average color change
   */
  public Color getStartColor() {
    return startColor;
  }

  /**
   * Gets the average change in Color for this command.
   *
   * @return Color average color change
   */
  public Color getEndColor() {
    return endColor;
  }


  /**
   * Gets the starting time for this command.
   * @return double start tick
   */
  public double getStartTime() {
    return startTick;
  }

  /**
   * Gets the ending time for this command.
   * @return double end tick
   */
  public double getEndTime() {
    return endTick;
  }

  @Override
  public String toString() {
    // Casts each field to an int to remove decimals in the output.
    int startTick = (int) this.startTick;
    int endTick = (int) this.endTick;

    int startX = (int) this.startLoc.getX();
    int startY = (int) this.startLoc.getY();
    int endX = (int) this.endLoc.getX();
    int endY = (int) this.endLoc.getY();

    int startW = (int) this.startSize.getWidth();
    int startH = (int) this.startSize.getHeight();
    int endW = (int) this.endSize.getWidth();
    int endH = (int) this.endSize.getHeight();

    int startR = (int) this.startColor.getRed();
    int startG = (int) this.startColor.getGreen();
    int startB = (int) this.startColor.getBlue();

    int endR = (int) this.endColor.getRed();
    int endG = (int) this.endColor.getGreen();
    int endB = (int) this.endColor.getBlue();

    String ret = "motion " + shape.getName() + " " + startTick + "  " +
            startX + "   " + startY + "   " +
            startW + " " + startH + "  "
            + startR + " " + startG + "  " +
            startB;

    ret = ret + "    " + endTick + "  " +
            endX + " " + endY + "  " +
            endW + " " + endH + "  "
            + endR + " " + endG + "  " +
            endB + "\n";
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Command) {
      Command other = (Command) o;
      return shape == other.shape && startTick == other.startTick && endTick == other.endTick &&
              startSize.equals(other.startSize) && endSize.equals(other.endSize) && startLoc.
              equals(other.startLoc) && endColor.equals(other.startLoc) && startColor.
              equals(other.startColor) && endColor.equals(other.endColor);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            shape, startTick, endTick, startSize, endSize, startLoc, endLoc, startColor, endColor);
  }

  /**
   * Determines if this command has a location change.
   *
   * @return boolean is the location changed
   */
  public boolean hasLocChange() {
    return !startLoc.equals(endLoc);
  }

  /**
   * Determines if this command has a Color change.
   *
   * @return boolean is the size changed
   */
  public boolean hasColorChange() {
    return !startColor.equals(endColor);
  }

  /**
   * Determines if this command has a size change.
   *
   * @return boolean is the size changed
   */
  public boolean hasSizeChange() {
    return !startSize.equals(endSize);
  }
}
