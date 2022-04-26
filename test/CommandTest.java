import model.CartPt;
import model.Color;
import model.Command;
import model.Elipse;
import model.Rectangle;
import model.Shape;
import model.Size;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing a command.
 */
public class CommandTest {
  Color red = new Color(255.0D, 0.0D, 0.0D);

  @Test
  public void testCreateCommand() {
    Shape square = new Rectangle("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    assertEquals(c, c);
  }

  @Test
  public void testCreateCommand2() {
    Shape square = new Elipse("", new CartPt(10.0D, 10.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(1000.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    assertEquals(c, c);

  }

  @Test
  public void testCreateInvalidCommand() {
    Elipse square = new Elipse("", new CartPt(0.0D, 0.0D), new Size(10.0D,
            10.0D), this.red);

    try {
      new Command(square, 0.0D, 10.0D, null, new Size(10.0D,
              10.0D), this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var8) {
      assertEquals(var8.getMessage(), "Invalid arguments");
    }

    try {
      new Command(square, 0.0D, 10.0D, new CartPt(0.0D, 0.0D),
              null, this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var7) {
      assertEquals(var7.getMessage(), "Invalid arguments");
    }

    try {
      new Command(square, 0.0D, 10.0D, new CartPt(0.0D, 0.0D),
              new Size(10.0D, 10.0D), null);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var6) {
      assertEquals(var6.getMessage(), "Invalid arguments");
    }

    try {
      new Command(square, -12.0D, 10.0D,
              new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D), this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var5) {
      assertEquals(var5.getMessage(), "Illegal tick rate");
    }

    try {
      new Command(square, 0.0D, -10.0D,
              new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D), this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var4) {
      assertEquals(var4.getMessage(), "Illegal tick rate");
    }

    try {
      new Command(square, 10.0D, 9.0D,
              new CartPt(0.0D, 0.0D), new Size(10.0D, 10.0D), this.red);
      Assert.fail("Expected exception did not occur");
    } catch (IllegalArgumentException var3) {
      assertEquals(var3.getMessage(), "Illegal tick rate");
    }

  }

  @Test
  public void testInsideTicks() {
    Shape square = new Rectangle("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    Assert.assertTrue(c.insideTicks(7.0D));
    Assert.assertTrue(c.insideTicks(1.0D));
    Assert.assertTrue(c.insideTicks(9.0D));
    Assert.assertTrue(c.insideTicks(10.0D));
    Assert.assertFalse(c.insideTicks(0.0D));
    Assert.assertFalse(c.insideTicks(11.0D));
    Assert.assertFalse(c.insideTicks(-12.0D));
    Assert.assertFalse(c.insideTicks(90.0D));
  }

  @Test
  public void testGetShape() {
    Shape square = new Rectangle("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 10.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    assertEquals(square, c.getShape());
  }

  @Test
  public void testToString() {
    Shape square = new Rectangle("C", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 10.0D, new CartPt(100.0D, 10.0D)
            , new Size(10.0D, 10.0D), this.red);
    assertEquals(c.toString(), "motion C 0  0   0   10 10  255 0  0    10  100 10  10 1" +
            "0  255 0  0\n");
  }
}
