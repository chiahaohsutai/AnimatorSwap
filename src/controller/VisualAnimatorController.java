package controller;

/**
 * An interface that represents A visual version of a controller. A visual controller
 * has the features to rewind. pause and loop. VisualAnimatorController has all functionality
 * of an AnimatorController as well as the interactive controls
 */
public interface VisualAnimatorController extends AnimatorController {
  /**
   * Rewinds the model back to its original shape list. The current tick should reset back to 0.
   * This is similar to starting the animation over again
   */
  void rewind();

  /**
   * Pauses the controller, so tick is not advancing. The model will stay the same while the
   * controller is paused. The shapes will not animate while the controller is paused, and no time
   * will pass.
   */
  void pause();

  /**
   * Determines if an Animation loops. Toggles between true and false. While true, the animation
   * will reset once completed. An animation is completed once its final animation is completed.
   * When false, the program will run until its final animation and quit.
   */
  void loop();

}
