import model.CartPt;
import model.Color;
import model.Shape;
import model.Size;

import org.junit.Assert;
import org.junit.Test;

/**
 * An abstract class for testing AShape.
 */
public abstract class AShapeTest {
  Color red = new Color(255.0D, 0.0D, 0.0D);


  public abstract Shape createShape(String var1, CartPt var2, Size var3, Color var4);

  @Test
  public abstract void testCreateShapeNullLoc();

  @Test
  public abstract void testCreateShapeInvalidColors();

  @Test
  public void testSetLocation() {
    Shape s = this.createShape("", new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(s.getLocation(), new CartPt(0.0D, 0.0D));
    s.setLocation(new CartPt(130.0D, 120.0D));
    Assert.assertEquals(s.getLocation(), new CartPt(130.0D, 120.0D));
  }

  @Test
  public void testSetLocationNullArg() {
    Shape s = this.createShape("", new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(s.getLocation(), new CartPt(0.0D, 0.0D));

    try {
      s.setLocation(null);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var3) {
      Assert.assertEquals(var3.getMessage(), "Given CartPt can't be null");
    }

  }

  @Test
  public void testGetSetColor() {
    Shape s = this.createShape("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Assert.assertEquals(s.getColor(), this.red);
    s.setColor(85, 88, 85);
    Assert.assertEquals(s.getColor(), new Color(85.0D, 88.0D, 85.0D));
  }

  @Test
  public void testNegativeColorSums256() {
    Shape s = this.createShape("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);

    try {
      s.setColor(-10, 266, 0);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var3) {
      Assert.assertEquals(var3.getMessage(), "Invalid color arguments");
    }

  }

  @Test
  public void testColorSumOver256() {
    try {
      this.createShape("", new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D),
              new Color(1000.0D, 0.0D, 0.0D));
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var2) {
      Assert.assertEquals(var2.getMessage(), "Colors must be doubles greater t" +
              "hen 0 and sum to 256");
    }

  }
}
