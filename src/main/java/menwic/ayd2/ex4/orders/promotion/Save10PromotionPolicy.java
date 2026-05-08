package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class Save10PromotionPolicy implements PromotionPolicy {
  private static final BigDecimal SAVE10_FACTOR = new BigDecimal("0.90");

  @Override
  public BigDecimal apply(BigDecimal subtotal) {
    return subtotal.multiply(SAVE10_FACTOR).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PromotionCode supports() {
    return PromotionCode.SAVE10;
  }
}
