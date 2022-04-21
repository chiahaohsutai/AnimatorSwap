//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import controller.model.CartPt;
import controller.model.Color;
import controller.model.Rectangle;
import controller.model.Shape;
import controller.model.Size;

import org.junit.Assert;
import org.junit.Test;

/**
 * A class for testing a rectangle.
 */
public class RectangleTest extends AShapeTest {

  @Override
  public Shape createShape(String name, CartPt location, Size size, Color color) {
    return new Rectangle(name, location, size, color);
  }

  @Test
  public void testCreateShapeNullLoc() {
    try {
      new Rectangle("", null, new Size(0.0D, 0.0D), this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var2) {
      Assert.assertEquals(var2.getMessage(), "Location can't be null");
    }

  }

  @Test
  public void testCreateShapeInvalidColors() {
    try {
      new Rectangle("", null, new Size(0.0D, 0.0D), new Color(0.0D, 0.0D, 0.0D));
    } catch (IllegalArgumentException var2) {
      Assert.assertEquals(var2.getMessage(), "Location can't be null");
    }

  }
}
