package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class WelcomePromotionPolicy implements PromotionPolicy {
  private static final BigDecimal WELCOME_FACTOR = new BigDecimal("0.85");

  @Override
  public BigDecimal apply(BigDecimal subtotal) {
    return subtotal.multiply(WELCOME_FACTOR).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PromotionCode supports() {
    return PromotionCode.WELCOME;
  }
}
