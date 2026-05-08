package menwic.ayd2.ex4.orders.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class NoPromotionPolicy implements PromotionPolicy {
  @Override
  public BigDecimal apply(BigDecimal subtotal) {
    return subtotal.setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PromotionCode supports() {
    return null;
  }
}
