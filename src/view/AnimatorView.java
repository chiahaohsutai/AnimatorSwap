package view;

/**
 * All methods and functionality of all Views.
 */
public interface AnimatorView {
  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Sets a tick per second rate for a view. Sets the tick rate to the given double / 1000.
   *
   * @param ticksPerSecond double ticksPerSecond
   * @throws IllegalArgumentException if the ticksPerSecond double is <= 0
   */
  void setTicksPerSecond(double ticksPerSecond);
}
