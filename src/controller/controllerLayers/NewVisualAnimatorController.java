package controller.controllerLayers;

import controller.VisualAnimatorController;

/**
 * Represents a controller for a GUI for the interactive view of an animation with the additional
 * feature of adding shapes and transformations to the currently playing animation.
 */
public interface NewVisualAnimatorController extends VisualAnimatorController {
  /**
   * Process the shapes and transformations added by the client using the text box. Return the
   * status or error message.
   *
   * @param edits the given edits to be processed
   * @return the status or error message
   */
  void processEdit(String edits);
}
