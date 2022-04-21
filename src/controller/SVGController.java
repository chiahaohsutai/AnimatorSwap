package controller;

import view.SVGView;

/**
 * A controller that controls SVG version of the Animator. An SVG controller takes in a view
 * and a ticks per second. The controller sends the view the tick rate and lets the view
 * compute what is necessary for an SVG view.
 */
public class SVGController implements AnimatorController {
  private final SVGView view;
  private final double tickRate;

  /**
   * A class for controlling a SVG view.
   *
   * @param view           SVGView view
   * @param ticksPerSecond double
   */
  public SVGController(SVGView view, double ticksPerSecond) {
    this.view = view;
    this.tickRate = ticksPerSecond / 1000;
  }


  @Override
  public void startProgram() {
    view.setTicksPerSecond(tickRate);
    view.refresh();
  }
}
