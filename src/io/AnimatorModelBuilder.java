package io;

import java.util.ArrayList;
import java.util.HashMap;
import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.Size;
import model.animatorLayersImp.AnimatorLayer;
import model.animatorLayersImp.AnimatorLayers;

/**
 * A builder class for SimpleAnimatorModel. Uses the TweenModelBuilder interface methods to
 * construct a SimpleAnimatorModel from text. Used with the AnimatorFileReader class to turn text
 * into a new model.
 */
public class AnimatorModelBuilder implements TweenModelBuilder<AnimatorLayers> {
  private final AnimatorLayers model;
  private final HashMap<String, Shape> shapeNames;

  public AnimatorModelBuilder() {
    this.model = new AnimatorLayer(new ArrayList<>());
    this.shapeNames = new HashMap<>();
  }

  @Override
  public TweenModelBuilder<AnimatorLayers> setLayer(String name, int layerNumber) {
    model.setLayer(name, layerNumber);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorLayers> setBounds(int width, int height) {
    this.model.setCanvasDim(width, height);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorLayers> addOval(String name, float cx, float cy,
                                                        float xRadius, float yRadius, float red,
                                                        float green, float blue, int startOfLife,
                                                        int endOfLife) {
    Elipse e = new Elipse(name, new CartPt(cx, cy), new Size(yRadius, xRadius),
            new Color(red, green, blue));
    model.addShape(e);
    shapeNames.put(name, e);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorLayers> addRectangle(String name, float lx, float ly,
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
  public TweenModelBuilder<AnimatorLayers> addMove(String name, float moveFromX,
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
  public TweenModelBuilder<AnimatorLayers> addColorChange(String name, float oldR, float oldG,
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
  public TweenModelBuilder<AnimatorLayers> addScaleToChange(String name, float fromSx,
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
  public AnimatorLayers build() {
    return model;
  }
}

