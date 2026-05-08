package menwic.ayd2.ex4.orders.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.junit.jupiter.api.Test;

class BulkPricingStrategyTest {
  private final BulkPricingStrategy strategy = new BulkPricingStrategy();

  @Test
  void bulkAmountUpToTwoThousandAppliesFivePercentDiscount() {
    Order order = baseOrder(new BigDecimal("2000.00"));
    assertMoney("1900.00", strategy.calculateSubtotal(order));
  }

  @Test
  void bulkAmountJustAbove2000AppliesMidTierDiscount() {
    Order order = baseOrder(new BigDecimal("2000.01"));
    assertMoney("1700.01", strategy.calculateSubtotal(order));
  }

  @Test
  void bulkAmountJustAbove5000AppliesHighTierDiscount() {
    Order order = baseOrder(new BigDecimal("5000.01"));
    assertMoney("3750.01", strategy.calculateSubtotal(order));
  }

  private Order baseOrder(BigDecimal amount) {
    return Order.builder()
        .type(OrderType.BULK)
        .customerType(CustomerType.WHOLESALE)
        .amount(amount)
        .build();
  }

  private void assertMoney(String expected, BigDecimal actual) {
    assertEquals(0, new BigDecimal(expected).compareTo(actual));
  }
}
