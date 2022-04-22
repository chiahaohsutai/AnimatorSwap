package view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.AnimatorModelState;

/**
 * Represents a text version of an AnimatorView. A text view has a read only model, an output field,
 * and can support both writing to a file or an appendable. A TextView can take in either an
 * Appendable to write to or a file to write to.
 */
public class AnimatorTextView implements AnimatorView {
  private final AnimatorModelState model;
  private Appendable output;
  private boolean written = false;
  private final boolean writeToFile;
  private String fileName;

  /**
   * Constructor for an AnimatorTextView. Takes in a model and a place to display text
   *
   * @param model  Model
   * @param output Appendable
   */
  public AnimatorTextView(AnimatorModelState model, Appendable output) {
    if (model == null || output == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.model = model;
    this.output = output;
    writeToFile = false;
  }

  /**
   * File name Constructor for an AnimatorTextView. Takes in a model and a name of a file to write
   * to.
   *
   * @param model    Model
   * @param fileName String
   */
  public AnimatorTextView(AnimatorModelState model, String fileName) {
    if (model == null || fileName == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.model = model;
    writeToFile = true;
    this.fileName = fileName;

  }

  /**
   * Writes a given string to the appendable. Catches a IllegalArgumentException if it cannot
   * write to the appendable.
   *
   * @param out String to be written
   */
  private void writeToOutput(String out) {
    try {
      output.append(out);
    } catch (IOException e) {
      throw new IllegalArgumentException("Can not append to output");
    }
  }

  @Override
  public void refresh() {
    if (!written) {
      if (writeToFile) {
        try {
          List<String> lines = List.of(model.printMotionTable());
          File textData = new File(fileName);
          Path file = Paths.get(String.valueOf(textData));
          Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) {
          throw new IllegalArgumentException("Can not write to file.");
        }
      } else {
        writeToOutput(model.printMotionTable());
        written = true;
      }
    }
  }

  @Override
  public void setTicksPerSecond(double ticksPerSecond) {
    throw new UnsupportedOperationException("Cannot set tick rate of text view");
  }
}

