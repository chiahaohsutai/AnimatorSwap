package view.LayerViews;

import java.util.Comparator;
import java.util.List;
import model.Shape;
import model.animatorLayersImp.AnimatorLayersState;
import view.AnimatorGraphicsView;
import view.VisualView;

/**
 * Represents a visual view with the ability to layer the shapes.
 */
public class NewVisualView extends AnimatorGraphicsView implements VisualView {

  /**
   * Creates a visual view with a read only model. This view has the ability to have layers in the
   * animation.
   *
   * @param model the read only model. The model is the object that keeps track of the animation
   *     state.
   */
  public NewVisualView(AnimatorLayersState model) {
    super(model);
  }

  @Override
  public void updatePanel(double tick) {
    List<Shape> shapes = this.model.getShapesAtTick(tick);
    AnimatorLayersState m = (AnimatorLayersState)model;
    shapes.sort(Comparator.comparingInt(s -> m.getShapeLayer(s.getName())));
    shapePanel.takeShapeList(shapes);
    currentTick.setText("Current tick: " + (int) tick);
  }
}
