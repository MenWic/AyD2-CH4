package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import menwic.ayd2.ex4.orders.domain.PromotionCode;

public interface PromotionPolicy {
  BigDecimal apply(BigDecimal subtotal);

  PromotionCode supports();
}
