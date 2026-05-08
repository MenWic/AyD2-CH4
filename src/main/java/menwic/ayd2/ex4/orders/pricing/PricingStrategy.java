package menwic.ayd2.ex4.orders.pricing;

import java.math.BigDecimal;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;

public interface PricingStrategy {
  BigDecimal calculateSubtotal(Order order);

  OrderType supports();
}
