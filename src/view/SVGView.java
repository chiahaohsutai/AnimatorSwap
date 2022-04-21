package view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import model.AnimatorModelState;
import model.CartPt;
import model.Color;
import model.Command;
import model.Shape;
import model.Size;

/**
 * A class for displaying an SVG version of a view. SVG is an online tool for displaying animations
 * This view formats animations in a way that an internet browser could render any animation
 * created by this program.
 */
public class SVGView implements AnimatorView {
  private final AnimatorModelState model;

  private final File svgData;
  StringBuilder build;
  String outputFile;
  double ticksPerSecond = 2;

  /**
   * Constructor for outputing SVGTExt to a file.
   *
   * @param model      Model
   * @param outputFile String
   */
  public SVGView(AnimatorModelState model, String outputFile) {
    if (model == null || outputFile == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.outputFile = outputFile;
    this.model = model;
    svgData = new File(outputFile);
  }

  /**
   * Translates and inserts commands into the SVG file.
   */
  @Override
  public void refresh() {
    build = new StringBuilder("<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n\n");
    Map<String, List<Command>> m = model.getCommandMap();

    for (Shape s : model.getShapes()) {
      build.append(toSVGString(s, m.get(s.getName())));
    }

    build.append("</svg>\n");

    String svgText = build.toString();

    try {
      List<String> lines = List.of(svgText);
      Path file = Paths.get(String.valueOf(svgData));
      Files.write(file, lines, StandardCharsets.UTF_8);
    } catch (IOException | NullPointerException e) {
      throw new IllegalArgumentException("Can not write to file.");
    }

  }

  /**
   * Converts the commands and shapes into XML formatting to make an SVG file.
   *
   * @param s        These are the shapes that will be drawn.
   * @param commands These are the animation commands for the shapes.
   * @return This is the shapes and commands formatted to be readable by XML.
   */
  private String toSVGString(Shape s, List<Command> commands) {
    CartPt location = s.getLocation();
    Size size = s.getSize();
    Color tempColor = s.getColor();
    Color color = new Color(tempColor.getRed(), tempColor.getGreen() * 255,
            tempColor.getBlue() * 255);

    String x;
    String y;
    String height;
    String width;
    if (s.getType().equals("rect")) {
      x = "x";
      y = "y";
      height = "height";
      width = "width";
    } else {
      x = "cx";
      y = "cy";
      height = "rx";
      width = "ry";
    }

    StringBuilder temp = new StringBuilder("<" + s.getType() + " id=\"" + s.getName() +
            "\" " + x + "=\"" +
            location.getX() + "\" " + y + "=\"" + location.getY() + "\" " + width + "=\"" +
            size.getWidth() + "\" " + height + "=\"" + size.getHeight() + "\" fill=\"" +
            color.toString() + "\" visibility=\"visible\" >\n");
    // next, iterate through list of commands and make animate commands in xml

    for (Command c : commands) {
      double startTimeInMilis = c.getStartTime() * (1000 / ticksPerSecond);
      double endTimeInMilis = c.getEndTime() * (1000 / ticksPerSecond);

      double dur = endTimeInMilis - startTimeInMilis;
      if (c.hasSizeChange()) {
        Size startSize = c.getStartSize();
        Size endSize = c.getEndSize();
        // animation over x-axis. Assumes all animations happen in milliseconds
        temp.append("<animate attributeType=\"xml\" begin=\"")
                .append(c.getStartTime())
                .append("ms\" dur=\"")
                .append(dur)
                .append("ms\" attributeName=\"width\" from=\"")
                .append(startSize.getWidth())
                .append("\" to=\"")
                .append(endSize.getWidth())
                .append("\" fill=\"freeze\" />\n");
        temp.append("<animate attributeType=\"xml\" begin=\"")
                .append(c.getStartTime())
                .append("ms\" dur=\"")
                .append(dur)
                .append("ms\" attributeName=\"height\" from=\"")
                .append(startSize.getHeight())
                .append("\" to=\"")
                .append(endSize.getHeight())
                .append("\" fill=\"freeze\" />\n");
      }
    }
    temp.append("</").append(s.getType()).append(">\n");
    return temp.toString();
  }

  @Override
  public void setTicksPerSecond(double ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
  }

}
