import model.Color;

import org.junit.Assert;
import org.junit.Test;

/**
 * A class for testing color.
 */
public class ColorTest {


  @Test
  public void testMakeColor() {
    new Color(0.0D, 0.0D, 0.0D);

    try {
      new Color(-12.0D, 21.0D, 12.0D);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var5) {
      Assert.assertEquals(var5.getMessage(),
              "Colors must be doubles greater then 0 and sum to 256");
    }

    try {
      new Color(12.0D, -21.0D, 12.0D);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var4) {
      Assert.assertEquals(var4.getMessage(),
              "Colors must be doubles greater then 0 and sum to 256");
    }

    try {
      new Color(12.0D, 21.0D, -12.0D);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var3) {
      Assert.assertEquals(var3.getMessage(),
              "Colors must be doubles greater then 0 and sum to 256");
    }

  }

  @Test
  public void testGetRedGreenBlue() {
    Color c = new Color(21.0D, 16.0D, 91.0D);
    Assert.assertEquals(c.getBlue(), 91.0D, 0.001D);
    Assert.assertEquals(c.getGreen(), 16.0D, 0.001D);
    Assert.assertEquals(c.getRed(), 21.0D, 0.001D);
  }

  @Test
  public void testEquals() {
    Color c1 = new Color(12.0D, 12.0D, 12.0D);
    Color c2 = new Color(12.0D, 12.0D, 12.0D);
    Assert.assertEquals(c1, c2);
    Assert.assertFalse(c1.equals(new Color(12.0D, 32.0D, 12.0D)));
  }

  @Test
  public void testAddColors() {
    Color c1 = new Color(12.0D, 12.0D, 12.0D);
    Color c2 = new Color(143.0D, 12.0D, 12.0D);
    c1.addColor(c2);
    Assert.assertEquals(c1, new Color(155.0D, 24.0D, 24.0D));
  }

  @Test
  public void testAddColorsOutOfRange() {
    Color c1 = new Color(160.0D, 12.0D, 12.0D);
    Color c2 = new Color(143.0D, 12.0D, 12.0D);

    try {
      c1.addColor(c2);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var4) {
      Assert.assertEquals(var4.getMessage(), "Can not add to this color");
    }

  }

  @Test
  public void testToString() {
    Color c1 = new Color(160.0D, 12.0D, 12.0D);
    Assert.assertEquals(c1.toString(), "rgb(160,12,12)");
  }
}
