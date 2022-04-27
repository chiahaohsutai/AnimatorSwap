package controller.controllerLayers;

import java.awt.event.ActionEvent;
import java.util.List;
import controller.InteractiveVisualController;
import io.EditInputReader;
import model.Command;
import model.Shape;
import model.animatorLayersImp.AnimatorLayer;
import model.animatorLayersImp.AnimatorLayers;
import view.LayerViews.NewInteractiveView;

/**
 * Represents the concrete implementation of the controller for the interactive view with the
 * additional feature of adding shapes to the currently playing animation. The controller will
 * handle the processing of the info provided in the view and communicate with the model
 * accordingly.
 */
public class NewInteractiveVisualController extends InteractiveVisualController
        implements NewVisualAnimatorController {

  /**
   * Constructor for a NewInteractiveVisualController.
   *
   * @param model       Model
   * @param view        View
   * @param ticksPerSec tickRate
   */
  public NewInteractiveVisualController(AnimatorLayers model,
                                        NewInteractiveView view,
                                        double ticksPerSec) {
    super(model, view, ticksPerSec);
  }

  @Override
  public void processEdit(String edits) {
    EditInputReader inputReader = new EditInputReader((AnimatorLayers) model,
            (NewInteractiveView) view);
    inputReader.readInput(edits);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.equals("Edit")) {
      String newEdits = ((NewInteractiveView) view).getEdits();
      processEdit(newEdits);
    } else {
      super.actionPerformed(e);
    }
  }

  @Override
  public void startProgram() {
    view.makeVisible();
    view.setListeners(this);

    // Determine last tick from model
    // System.out.println(model.getLastTick());

    do {
      isReset = false;
      double currentTick = 0;
      model.setShapeList(copyShapes(originalShapes));

      while (currentTick < model.getLastTick() && !isReset) {
        if (isPaused) {
          // do nothing
        } else {
          try {
            Thread.sleep((long) tickRate);
          } catch (InterruptedException e) {
            throw new IllegalStateException("Cannot sleep");
          }
          if (((AnimatorLayers)model).checkShapeQueue()) {
            model.addShape(((AnimatorLayers) model).getShapesQueue().toArray(Shape[]::new));
            ((AnimatorLayer)model).clearShapesQueue();
          }
          if (((AnimatorLayers)model).checkCommandQueue()) {
            List<Command> cmds = ((AnimatorLayers)model).getCommandsQueue();
            cmds.forEach(c -> model.addCommand(c.getShape(), c));
          }
          view.updatePanel(currentTick);
          view.refresh();
          currentTick += tickRate;
        }
      }

    }
    while (repeated || isReset);
  }
}
