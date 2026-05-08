package menwic.ayd2.ex4.orders.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.springframework.stereotype.Service;

@Service
public class BulkPricingStrategy implements PricingStrategy {
  private static final BigDecimal TWO_THOUSAND = new BigDecimal("2000");
  private static final BigDecimal FIVE_THOUSAND = new BigDecimal("5000");
  private static final BigDecimal LOW_TIER_FACTOR = new BigDecimal("0.95");
  private static final BigDecimal MID_TIER_FACTOR = new BigDecimal("0.85");
  private static final BigDecimal HIGH_TIER_FACTOR = new BigDecimal("0.75");

  @Override
  public BigDecimal calculateSubtotal(Order order) {
    BigDecimal amount = normalize(order.getAmount());
    if (amount.compareTo(TWO_THOUSAND) <= 0) {
      return applyFactor(amount, LOW_TIER_FACTOR);
    }
    if (amount.compareTo(FIVE_THOUSAND) <= 0) {
      return applyFactor(amount, MID_TIER_FACTOR);
    }
    return applyFactor(amount, HIGH_TIER_FACTOR);
  }

  @Override
  public OrderType supports() {
    return OrderType.BULK;
  }

  private BigDecimal applyFactor(BigDecimal base, BigDecimal factor) {
    return base.multiply(factor).setScale(2, RoundingMode.HALF_UP);
  }

  private BigDecimal normalize(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_UP);
  }
}
