//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import controller.model.CartPt;
import controller.model.Color;
import controller.model.Elipse;
import controller.model.Shape;
import controller.model.Size;

import org.junit.Assert;
import org.junit.Test;

/**
 * A class for testing an Elipse.
 */
public class ElipseTest extends AShapeTest {


  @Override
  public Shape createShape(String name, CartPt location, Size s, Color c) {
    return new Elipse(name, location, s, c);
  }

  @Test
  public void testCreateShapeNullLoc() {
    try {
      new Elipse("", null, new Size(10.0D, 10.0D),
              new Color(255.0D, 0.0D, 0.0D));
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var2) {
      Assert.assertEquals(var2.getMessage(), "Location can't be null");
    }

  }

  @Test
  public void testCreateShapeInvalidColors() {
    try {
      new Elipse("", null, new Size(10.0D, 10.0D), new Color(0.0D, 0.0D, 0.0D));
    } catch (IllegalArgumentException var2) {
      Assert.assertEquals(var2.getMessage(), "Location can't be null");
    }

  }
}
