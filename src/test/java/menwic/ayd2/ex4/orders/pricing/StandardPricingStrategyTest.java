package menwic.ayd2.ex4.orders.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.junit.jupiter.api.Test;

class StandardPricingStrategyTest {
  private final StandardPricingStrategy strategy = new StandardPricingStrategy();

  @Test
  void standardRetailPremiumAppliesTenPercentDiscount() {
    Order order = baseOrder(CustomerType.RETAIL, new BigDecimal("100.00"), true, false);
    assertMoney("90.00", strategy.calculateSubtotal(order));
  }

  @Test
  void standardWholesaleAmountOverOneThousandAppliesFifteenPercentDiscount() {
    Order order = baseOrder(CustomerType.WHOLESALE, new BigDecimal("1500.00"), false, false);
    assertMoney("1275.00", strategy.calculateSubtotal(order));
  }

  @Test
  void standardInternationalWeekendAppliesAdditionalFivePercentSurcharge() {
    Order order = baseOrder(CustomerType.INTERNATIONAL, new BigDecimal("100.00"), false, true);
    assertMoney("126.00", strategy.calculateSubtotal(order));
  }

  private Order baseOrder(CustomerType customerType, BigDecimal amount, boolean premium, boolean weekend) {
    return Order.builder()
        .type(OrderType.STANDARD)
        .customerType(customerType)
        .amount(amount)
        .premium(premium)
        .weekend(weekend)
        .build();
  }

  private void assertMoney(String expected, BigDecimal actual) {
    assertEquals(0, new BigDecimal(expected).compareTo(actual));
  }
}
