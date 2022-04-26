package io;

/**
 * This interface contains the operations of a text reader whose sole purpose is to
 * read the input of the user. The input of the user represents the shapes and motions that
 * the client would like to add to the currently playing animation.
 */
public interface ReadEdits {
  /**
   * Processes the edits to the currently playing animation typed by the user.
   *
   * @param edits the edits typed by user in text area.
   */
  void readInput(String edits);
}
