package io;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.Size;
import model.animatorLayersImp.AnimatorLayers;
import view.LayerViews.NewInteractiveView;

/**
 * Represents a text reader that processes the user inputted text describing the shapes and motions
 * to be added to the currently playing animation. This text reader operates similarly to the
 * AnimationFileReader.
 * The input to add a shape must be formatted as the following:
 * "(shape type) name (shape name) min-x (x-position) min-y (y-position)
 * width (shape width) height (shape height) color (color as r,g,b) from (start time) to (end time)"
 * The input to add a motion must be formatted as the following:
 * "(transform type) name (shape name) (moveto/colorto/scaleto based on transform type)
 * (start state according to transform type) (end state according to transform type)
 * from (start time) to (end time)"
 * The description of shapes and motions to be added must be entirely correct for them to be added
 * to the currently playing animation. If the description contains incorrect formatting/information,
 * nothing will be done to the animation and the user will be prompted to enter a different input.
 */
public class EditInputReader extends AnimationFileReader implements ReadEdits {

  AnimatorLayers model;
  NewInteractiveView view;
  List<Shape> shapesAddedByClientTemp;
  List<Command> commandsAddedByClientTemp;

  /**
   *
   */
  public EditInputReader(AnimatorLayers model, NewInteractiveView view) {
    this.model = model;
    this.view = view;
    shapesAddedByClientTemp = new ArrayList<>();
    commandsAddedByClientTemp = new ArrayList<>();
  }

  @Override
  public void readInput(String edits) {
    Scanner s;

    s = new Scanner(edits);

    while (s.hasNext()) {
      String command = s.next();

      switch (command) {
        case "rectangle":
          try {
            RectangleInfo rinfo = readRectangleInfo(s);
            Rectangle r = new Rectangle(rinfo.getName(),
                    new CartPt(rinfo.getX(), rinfo.getY()),
                    new Size(rinfo.getHeight(), rinfo.getWidth()),
                    new Color(rinfo.getR(), rinfo.getG(), rinfo.getB()));
            shapesAddedByClientTemp.add(r);
          } catch (IllegalStateException | InputMismatchException e) {
            view.setEditStatusLabel("Invalid input. Please try again.");
            return;
          }
          break;
        case "oval":
          try {
            OvalInfo cinfo = readOvalInfo(s);
            Elipse c = new Elipse(cinfo.getName(),
                    new CartPt(cinfo.getX(), cinfo.getY()),
                    new Size(cinfo.getYRadius(), cinfo.getXRadius()),
                    new Color(cinfo.getR(), cinfo.getG(), cinfo.getB()));

            shapesAddedByClientTemp.add(c);
          } catch (IllegalStateException | InputMismatchException e) {
            view.setEditStatusLabel("Invalid input. Please try again.");
            return;
          }
          break;
        case "move":
          try {
            MoveInfo minfo = readMoveInfo(s);

            if (doesShapeWithNameExist(minfo.getName())) {
              Shape shapeBeingMoved = model.getShapeByName(minfo.getName());
              CartPt startLoc = new CartPt(minfo.getFromX(), minfo.getFromY());
              CartPt endLoc = new CartPt(minfo.getToX(), minfo.getToY());

              Command moveCommand = new Command(shapeBeingMoved,
                      minfo.getStart(), minfo.getEnd(), startLoc, endLoc,
                      shapeBeingMoved.getSize(), shapeBeingMoved.getSize(),
                      shapeBeingMoved.getColor(), shapeBeingMoved.getColor());

              commandsAddedByClientTemp.add(moveCommand);
            } else {
              view.setEditStatusLabel("Invalid input. The shape does not exist.");
              return;
            }
          } catch (IllegalStateException | InputMismatchException e) {
            view.setEditStatusLabel("Invalid input. Please try again.");
            return;
          }
          break;
        case "change-color":
          try {
            ChangeColorInfo ccinfo = readChangeColorInfo(s);

            if (doesShapeWithNameExist(ccinfo.getName())) {
              Shape shapeChangingColor = model.getShapeByName(ccinfo.getName());
              Color startColor = new Color(ccinfo.getFromR(), ccinfo.getFromG(), ccinfo.getFromB());
              Color endColor = new Color(ccinfo.getToR(), ccinfo.getToG(), ccinfo.getToB());

              Command colorChangeCommand = new Command(shapeChangingColor,
                      ccinfo.getStart(), ccinfo.getEnd(),
                      shapeChangingColor.getLocation(), shapeChangingColor.getLocation(),
                      shapeChangingColor.getSize(), shapeChangingColor.getSize(),
                      startColor, endColor);

              commandsAddedByClientTemp.add(colorChangeCommand);
            } else {
              view.setEditStatusLabel("Invalid input. The shape does not exist.");
              return;
            }
          } catch (IllegalStateException | InputMismatchException e) {
            view.setEditStatusLabel("Invalid input. Please try again.");
            return;
          }
          break;
        case "scale":
          try {
            ScaleByInfo sinfo = readScaleByInfo(s);

            if (doesShapeWithNameExist(sinfo.getName())) {
              Shape shapeBeingScaled = model.getShapeByName(sinfo.getName());
              Size startSize = new Size(sinfo.getFromXScale(), sinfo.getFromYScale());
              Size endSize = new Size(sinfo.getToXScale(), sinfo.getToYScale());

              Command scaleCommand = new Command(shapeBeingScaled,
                      sinfo.getStart(), sinfo.getEnd(),
                      shapeBeingScaled.getLocation(), shapeBeingScaled.getLocation(),
                      startSize, endSize,
                      shapeBeingScaled.getColor(), shapeBeingScaled.getColor());

              commandsAddedByClientTemp.add(scaleCommand);
            } else {
              view.setEditStatusLabel("Invalid input. The shape does not exist.");
              return;
            }
          } catch (IllegalStateException | InputMismatchException e) {
            view.setEditStatusLabel("Invalid input. Please try again.");
            return;
          }
          break;
        default:
          view.setEditStatusLabel("Invalid input. Please try again.");
      }
    }

    // add each shape inputted by user to the model.
    model.addShape(shapesAddedByClientTemp.toArray(Shape[]::new));

    // add each motion inputted by user to the model.
    for (Command singleMotion : commandsAddedByClientTemp) {
      Shape shapeBeingTransformed = singleMotion.getShape();
      model.addCommand(shapeBeingTransformed, singleMotion);
    }
  }

  /**
   * Checks if the shape exists in the animation.
   *
   * @param name the name of the shape.
   * @return if the shape with given name exists in animation.
   */
  private boolean doesShapeWithNameExist(String name) {
    return model.getShapes().stream().anyMatch(shape -> shape.getName().equals(name));
  }
}
