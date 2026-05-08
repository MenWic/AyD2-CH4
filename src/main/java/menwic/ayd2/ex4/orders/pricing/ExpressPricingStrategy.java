package menwic.ayd2.ex4.orders.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.springframework.stereotype.Service;

@Service
public class ExpressPricingStrategy implements PricingStrategy {
  private static final BigDecimal EXPRESS_SURCHARGE = new BigDecimal("1.30");
  private static final BigDecimal WHOLESALE_COMPATIBILITY_FACTOR = new BigDecimal("0.92");
  private static final BigDecimal PREMIUM_FACTOR = new BigDecimal("0.90");

  @Override
  public BigDecimal calculateSubtotal(Order order) {
    BigDecimal subtotal = normalize(order.getAmount());
    subtotal = applyFactor(subtotal, EXPRESS_SURCHARGE);

    if (order.getCustomerType() == CustomerType.WHOLESALE) {
      subtotal = applyFactor(subtotal, WHOLESALE_COMPATIBILITY_FACTOR);
    }

    if (order.isPremium()) {
      subtotal = applyFactor(subtotal, PREMIUM_FACTOR);
    }

    return subtotal;
  }

  @Override
  public OrderType supports() {
    return OrderType.EXPRESS;
  }

  private BigDecimal applyFactor(BigDecimal base, BigDecimal factor) {
    return base.multiply(factor).setScale(2, RoundingMode.HALF_UP);
  }

  private BigDecimal normalize(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_UP);
  }
}
