package menwic.ayd2.ex4.orders.promotion;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import org.springframework.stereotype.Service;

@Service
public class PromotionPolicyResolver {
  private final Map<PromotionCode, PromotionPolicy> policyByCode;
  private final NoPromotionPolicy noPromotionPolicy;

  public PromotionPolicyResolver(List<PromotionPolicy> policies, NoPromotionPolicy noPromotionPolicy) {
    this.noPromotionPolicy = noPromotionPolicy;
    this.policyByCode = new EnumMap<>(PromotionCode.class);
    for (PromotionPolicy policy : policies) {
      if (policy.supports() != null) {
        this.policyByCode.put(policy.supports(), policy);
      }
    }
  }

  public PromotionPolicy resolve(PromotionCode promotionCode) {
    if (promotionCode == null) {
      return noPromotionPolicy;
    }
    PromotionPolicy policy = policyByCode.get(promotionCode);
    if (policy == null) {
      throw new IllegalArgumentException("No hay politica para el codigo de promocion: " + promotionCode);
    }
    return policy;
  }
}
