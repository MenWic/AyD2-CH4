package menwic.ayd2.ex4.orders.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.junit.jupiter.api.Test;

class ExpressPricingStrategyTest {
  private final ExpressPricingStrategy strategy = new ExpressPricingStrategy();

  @Test
  void expressWholesalePremiumAppliesSurchargeWholesaleDiscountAndPremiumDiscount() {
    Order order = baseOrder(CustomerType.WHOLESALE, new BigDecimal("100.00"), true);
    assertMoney("107.64", strategy.calculateSubtotal(order));
  }

  private Order baseOrder(CustomerType customerType, BigDecimal amount, boolean premium) {
    return Order.builder()
        .type(OrderType.EXPRESS)
        .customerType(customerType)
        .amount(amount)
        .premium(premium)
        .weekend(false)
        .build();
  }

  private void assertMoney(String expected, BigDecimal actual) {
    assertEquals(0, new BigDecimal(expected).compareTo(actual));
  }
}
