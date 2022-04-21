package view;

/**
 * Represents all functionality of an AnimatorView. Views should have a ModelState object and a
 * Controller object. A VisualView is used to display an animation of shapes.
 */
public interface VisualView extends AnimatorView {

  /**
   * Takes in the current tick and draws the shape how they should be.
   *
   * @param tick double current tick.
   */
  void updatePanel(double tick);

  /**
   * Make the view visible. This is usually called
   * after the view is constructed.
   */
  void makeVisible();


}
