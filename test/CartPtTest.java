import controller.model.CartPt;

import org.junit.Assert;
import org.junit.Test;

/**
 * A class for testing CartPt.
 */
public class CartPtTest {


  @Test
  public void makeCartPt() {
    CartPt cp = new CartPt(12.0D, 24.0D);
    CartPt cp2 = new CartPt(cp);
    Assert.assertEquals(cp, cp2);
  }

  @Test
  public void testGetXGetY() {
    CartPt cp = new CartPt(12.0D, 24.0D);
    Assert.assertEquals(cp.getX(), 12.0D, 0.001D);
    Assert.assertEquals(cp.getY(), 24.0D, 0.001D);
  }

  @Test
  public void testToString() {
    CartPt cp = new CartPt(12.0D, 24.0D);
    Assert.assertEquals(cp.toString(), "(12.0, 24.0)");
  }

  @Test
  public void testAddCartPt() {
    CartPt cp = new CartPt(12.0D, 24.0D);
    CartPt cp2 = new CartPt(10.0D, 10.0D);
    cp.addDistance(cp);
    Assert.assertEquals(cp, new CartPt(24.0D, 48.0D));
    cp.addDistance(cp2);
    Assert.assertEquals(cp, new CartPt(34.0D, 58.0D));
  }

  @Test
  public void testEquals() {
    CartPt cp = new CartPt(12.0D, 24.0D);
    CartPt cp2 = new CartPt(10.0D, 10.0D);
    Assert.assertFalse(cp.equals(cp2));
    CartPt cp3 = new CartPt(12.0D, 24.0D);
    Assert.assertTrue(cp.equals(cp3));
    Assert.assertTrue(cp.equals(cp));
  }
}
