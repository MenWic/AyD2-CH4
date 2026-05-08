package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class BlackFridayPromotionPolicy implements PromotionPolicy {
  private static final BigDecimal BLACKFRIDAY_FACTOR = new BigDecimal("0.70");

  @Override
  public BigDecimal apply(BigDecimal subtotal) {
    return subtotal.multiply(BLACKFRIDAY_FACTOR).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PromotionCode supports() {
    return PromotionCode.BLACKFRIDAY;
  }
}
