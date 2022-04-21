package view;

import java.util.ArrayList;
import java.util.HashMap;

import controller.model.CartPt;
import controller.model.Color;
import controller.model.Command;
import controller.model.Elipse;
import controller.model.Rectangle;
import controller.model.Shape;
import controller.model.SimpleAnimatorModel;
import controller.model.Size;

/**
 * A builder class for SimpleAnimatorModel. Uses the TweenModelBuilder interface methods to
 * construct a SimpleAnimatorModel from text. Used with the AnimatorFileReader class to turn text
 * into a new model.
 */
public class SimpleAnimatorModelBuilder
        implements TweenModelBuilder<SimpleAnimatorModel> {

  private SimpleAnimatorModel model;
  private HashMap<String, Shape> shapeNames;


  public SimpleAnimatorModelBuilder() {
    this.model = new SimpleAnimatorModel(new ArrayList<Shape>());
    shapeNames = new HashMap<String, Shape>();
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> setBounds(int width, int height) {
    this.model = new SimpleAnimatorModel(new ArrayList<Shape>(), width, height);
    return this;
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> addOval(String name, float cx, float cy,
                                                        float xRadius, float yRadius, float red,
                                                        float green, float blue, int startOfLife,
                                                        int endOfLife) {
    Elipse e = new Elipse(name, new CartPt(cx, cy), new Size(xRadius, yRadius),
            new Color(red, green, blue));
    model.addShape(e);
    shapeNames.put(name, e);
    return this;
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> addRectangle(String name, float lx, float ly,
                                                             float width, float height, float red,
                                                             float green, float blue,
                                                             int startOfLife, int endOfLife) {
    Rectangle r = new Rectangle(name, new CartPt(lx, ly), new Size(height, width),
            new Color(red, green, blue));
    model.addShape(r);
    shapeNames.put(name, r);
    return this;
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> addMove(String name, float moveFromX,
                                                        float moveFromY, float moveToX,
                                                        float moveToY, int startTime, int endTime) {
    if (shapeNames.containsKey(name)) {
      Shape shape = shapeNames.get(name);
      CartPt startLoc = new CartPt(moveFromX, moveFromY);
      CartPt endLoc = new CartPt(moveToX, moveToY);

      model.addCommand(shape, new Command(shape, startTime, endTime, startLoc, endLoc,
              shape.getSize(), shape.getSize(), shape.getColor(), shape.getColor()));
    }
    return this;
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> addColorChange(String name, float oldR, float oldG,
                                                               float oldB, float newR, float newG,
                                                               float newB, int startTime,
                                                               int endTime) {
    if (shapeNames.containsKey(name)) {
      Shape shape = shapeNames.get(name);
      Color startColor = new Color(oldR, oldG, oldB);
      Color endColor = new Color(newR, newG, newB);
      model.addCommand(shape, new Command(shape, startTime, endTime, shape.getLocation(),
              shape.getLocation(), shape.getSize(), shape.getSize(), startColor, endColor));
    }
    return this;
  }

  @Override
  public TweenModelBuilder<SimpleAnimatorModel> addScaleToChange(String name, float fromSx,
                                                                 float fromSy, float toSx,
                                                                 float toSy, int startTime,
                                                                 int endTime) {
    if (shapeNames.containsKey(name)) {
      Shape shape = shapeNames.get(name);
      Size startSize = new Size(fromSx, fromSy);
      Size endSize = new Size(toSx, toSy);

      model.addCommand(shape, new Command(shape, startTime, endTime, shape.getLocation(),
              shape.getLocation(), startSize, endSize, shape.getColor(), shape.getColor()));
    }
    return this;
  }

  @Override
  public SimpleAnimatorModel build() {
    return model;
  }
}

