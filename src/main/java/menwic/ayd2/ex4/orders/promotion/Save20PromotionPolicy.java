package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class Save20PromotionPolicy implements PromotionPolicy {
  private static final BigDecimal SAVE20_FACTOR = new BigDecimal("0.80");

  @Override
  public BigDecimal apply(BigDecimal subtotal) {
    return subtotal.multiply(SAVE20_FACTOR).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PromotionCode supports() {
    return PromotionCode.SAVE20;
  }
}
