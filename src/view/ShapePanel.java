package view;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.CartPt;
import model.Command;
import model.Shape;
import model.Size;

/**
 * A class for drawing a Shape panel. A Shape panel for displaying Shapes. A ShapePanel has a
 * Map of commands and a List of shapes. ShapePanel extends JPanel and displays each Shape on the
 * panel.
 */
public class ShapePanel extends JPanel {
  private Map<String, ArrayList<Command>> commands = new HashMap<>();
  private List<Shape> shapes = new ArrayList<>();


  /**
   * Constructor for a ShapePannel. Sets background to white.
   */
  public ShapePanel() {
    this.setBackground(Color.WHITE);
  }

  /**
   * Takes a command list for this ShapePanel. Sets the command map to the given.
   *
   * @param commands Commands
   */
  public void takeCommandList(Map commands) {
    if (commands == null) {
      throw new IllegalArgumentException("List can't be null");
    }
    this.commands = commands;
  }

  /**
   * Assigns this panel's shape list to a given list.
   *
   * @param shapes Shapes to be assigned
   */
  public void takeShapeList(List<Shape> shapes) {
    if (shapes == null) {
      throw new IllegalArgumentException("Shapes can't be null");
    }
    this.shapes = shapes;

  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    this.commands.keySet();


    for (Shape s : shapes) {
      CartPt loc = s.getLocation();
      Size size = s.getSize();

      model.Color modelColor = s.getColor();
      double red = modelColor.getRed();
      double blue = modelColor.getBlue();
      double green = modelColor.getGreen();

      if (modelColor.getBlue() <= 1 && modelColor.getGreen() <= 1 && modelColor.getRed() <= 1) {
        red = 255 * modelColor.getRed();
        green = 255 * modelColor.getGreen();
        blue = 255 * modelColor.getBlue();
      }

      g2d.setColor(new Color((int) red, (int) green, (int) blue));

      switch (s.getType()) {
        case "rect":
          g2d.fillRect((int) loc.getX(), (int) loc.getY(), (int) size.getWidth(),
                  (int) size.getHeight());
          break;
        case "ellipse":
          g2d.fillOval((int) loc.getX(), (int) loc.getY(), (int) size.getWidth(),
                  (int) size.getHeight());
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
  }

  /**
   * Gets the current Shape list for this panel.
   *
   * @return
   */
  public List getShapeList() {
    return new ArrayList(this.shapes);
  }
}
