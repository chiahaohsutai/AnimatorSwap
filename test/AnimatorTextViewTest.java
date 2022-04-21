import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import controller.AnimatorController;
import controller.TextController;
import model.AnimatorModel;
import view.AnimationFileReader;
import view.AnimatorTextView;
import view.AnimatorView;
import view.SimpleAnimatorModelBuilder;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing AnimatorTextView.
 */
public class AnimatorTextViewTest {

  @Test
  public void testTextView() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-3.txt", new SimpleAnimatorModelBuilder());
    AnimatorView view = new AnimatorTextView(model, System.out);
    AnimatorController ctrl = new TextController(view);
    ctrl.startProgram();
    assertEquals(ctrl, ctrl);
  }

  @Test
  public void testTextViewWriteToFile() throws IOException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-3.txt", new SimpleAnimatorModelBuilder());
    AnimatorView view = new AnimatorTextView(model, "test-text.txt");
    AnimatorController ctrl = new TextController(view);
    ctrl.startProgram();

    assertEquals(Files.readString(Path.of("test-text.txt")), "#   " +
            "               start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion disk1 25  190   180   20 30  0 0  0    35  190 50  20 30  0 0  0\n" +
            "motion disk1 36  190   50   20 30  0 0  0    46  490 50  20 30  0 0  0\n" +
            "motion disk1 47  490   50   20 30  0 0  0    57  490 240  20 30  0 0  0\n" +
            "motion disk1 89  490   240   20 30  0 0  0    99  490 50  20 30  0 0  0\n" +
            "motion disk1 100  490   50   20 30  0 0  0    110  340 50  20 30  0 0  0\n" +
            "motion disk1 111  340   50   20 30  0 0  0    121  340 210  20 30  0 0  0\n" +
            "motion disk1 153  340   210   20 30  0 0  0    163  340 50  20 30  0 0  0\n" +
            "motion disk1 164  340   50   20 30  0 0  0    174  190 50  20 30  0 0  0\n" +
            "motion disk1 175  190   50   20 30  0 0  0    185  190 240  20 30  0 0  0\n" +
            "motion disk1 217  190   240   20 30  0 0  0    227  190 50  20 30  0 0  0\n" +
            "motion disk1 228  190   50   20 30  0 0  0    238  490 50  20 30  0 0  0\n" +
            "motion disk1 239  490   50   20 30  0 0  0    249  490 180  20 30  0 0  0\n" +
            "motion disk1 249  190   180   20 30  0 0  0    257  190 180  20 30  0 1  0\n" +
            "\n" +
            "motion disk2 57  167   210   65 30  0 0  0    67  167 50  65 30  0 0  0\n" +
            "motion disk2 68  167   50   65 30  0 0  0    78  317 50  65 30  0 0  0\n" +
            "motion disk2 79  317   50   65 30  0 0  0    89  317 240  65 30  0 0  0\n" +
            "motion disk2 185  317   240   65 30  0 0  0    195  317 50  65 30  0 0  0\n" +
            "motion disk2 196  317   50   65 30  0 0  0    206  467 50  65 30  0 0  0\n" +
            "motion disk2 207  467   50   65 30  0 0  0    217  467 210  65 30  0 0  0\n" +
            "motion disk2 217  167   210   65 30  0 0  0    225  167 210  65 30  0 1  0\n" +
            "\n" +
            "motion disk3 121  145   240   110 30  0 0  0    131  145 50  110 30  0 0  0\n" +
            "motion disk3 132  145   50   110 30  0 0  0    142  445 50  110 30  0 0  0\n" +
            "motion disk3 143  445   50   110 30  0 0  0    153  445 240  110 30  0 0  0\n" +
            "motion disk3 153  145   240   110 30  0 0  0    161  145 240  110 30  0 1  0\n" +
            "\n" +
            "\n");
  }

}