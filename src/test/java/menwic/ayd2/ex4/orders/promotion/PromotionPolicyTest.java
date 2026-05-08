package menwic.ayd2.ex4.orders.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.junit.jupiter.api.Test;

class PromotionPolicyTest {
  @Test
  void save10AppliesTenPercentDiscount() {
    Save10PromotionPolicy policy = new Save10PromotionPolicy();
    assertMoney("90.00", policy.apply(new BigDecimal("100.00")));
  }

  @Test
  void blackFridayAppliesThirtyPercentDiscount() {
    BlackFridayPromotionPolicy policy = new BlackFridayPromotionPolicy();
    assertMoney("70.00", policy.apply(new BigDecimal("100.00")));
  }

  @Test
  void noPromotionKeepsSubtotal() {
    NoPromotionPolicy policy = new NoPromotionPolicy();
    assertMoney("100.00", policy.apply(new BigDecimal("100.00")));
  }

  @Test
  void resolverWithNullReturnsNoPromotionPolicy() {
    NoPromotionPolicy noPromotionPolicy = new NoPromotionPolicy();
    PromotionPolicyResolver resolver = new PromotionPolicyResolver(
        List.of(
            noPromotionPolicy,
            new Save10PromotionPolicy(),
            new Save20PromotionPolicy(),
            new BlackFridayPromotionPolicy(),
            new WelcomePromotionPolicy()
        ),
        noPromotionPolicy
    );

    PromotionPolicy resolvedPolicy = resolver.resolve(null);
    assertSame(noPromotionPolicy, resolvedPolicy);
  }

  @Test
  void resolveThrowsWhenPolicyIsMissing() {
    NoPromotionPolicy noPromotionPolicy = new NoPromotionPolicy();
    PromotionPolicyResolver resolver = new PromotionPolicyResolver(
        List.of(
            noPromotionPolicy,
            new Save10PromotionPolicy(),
            new Save20PromotionPolicy(),
            new BlackFridayPromotionPolicy()
        ),
        noPromotionPolicy
    );

    assertThrows(IllegalArgumentException.class, () -> resolver.resolve(PromotionCode.WELCOME));
  }

  private void assertMoney(String expected, BigDecimal actual) {
    assertEquals(0, new BigDecimal(expected).compareTo(actual));
  }
}
