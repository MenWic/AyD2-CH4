package menwic.ayd2.ex4.orders.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.springframework.stereotype.Service;

@Service
public class StandardPricingStrategy implements PricingStrategy {
  private static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
  private static final BigDecimal WHOLESALE_LOW_FACTOR = new BigDecimal("0.95");
  private static final BigDecimal WHOLESALE_HIGH_FACTOR = new BigDecimal("0.85");
  private static final BigDecimal RETAIL_PREMIUM_FACTOR = new BigDecimal("0.90");
  private static final BigDecimal INTERNATIONAL_SURCHARGE = new BigDecimal("1.20");
  private static final BigDecimal INTERNATIONAL_WEEKEND_SURCHARGE = new BigDecimal("1.05");

  @Override
  public BigDecimal calculateSubtotal(Order order) {
    CustomerType customerType = order.getCustomerType();
    BigDecimal amount = normalize(order.getAmount());

    if (customerType == CustomerType.RETAIL) {
      if (order.isPremium()) {
        return applyFactor(amount, RETAIL_PREMIUM_FACTOR);
      }
      return amount;
    }

    if (customerType == CustomerType.WHOLESALE) {
      if (amount.compareTo(ONE_THOUSAND) > 0) {
        return applyFactor(amount, WHOLESALE_HIGH_FACTOR);
      }
      return applyFactor(amount, WHOLESALE_LOW_FACTOR);
    }

    if (customerType == CustomerType.INTERNATIONAL) {
      BigDecimal subtotal = applyFactor(amount, INTERNATIONAL_SURCHARGE);
      if (order.isWeekend()) {
        subtotal = applyFactor(subtotal, INTERNATIONAL_WEEKEND_SURCHARGE);
      }
      return subtotal;
    }

    throw new IllegalArgumentException("Tipo de cliente no soportado para STANDARD: " + customerType);
  }

  @Override
  public OrderType supports() {
    return OrderType.STANDARD;
  }

  private BigDecimal applyFactor(BigDecimal base, BigDecimal factor) {
    return base.multiply(factor).setScale(2, RoundingMode.HALF_UP);
  }

  private BigDecimal normalize(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_UP);
  }
}
