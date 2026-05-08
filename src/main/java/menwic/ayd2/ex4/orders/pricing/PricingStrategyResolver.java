package menwic.ayd2.ex4.orders.pricing;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.springframework.stereotype.Service;

@Service
public class PricingStrategyResolver {
  private final Map<OrderType, PricingStrategy> strategyByType;

  public PricingStrategyResolver(List<PricingStrategy> strategies) {
    this.strategyByType = new EnumMap<>(OrderType.class);
    for (PricingStrategy strategy : strategies) {
      this.strategyByType.put(strategy.supports(), strategy);
    }
  }

  public PricingStrategy resolve(OrderType orderType) {
    PricingStrategy strategy = strategyByType.get(orderType);
    if (strategy == null) {
      throw new IllegalArgumentException("No hay estrategia para el tipo de orden: " + orderType);
    }
    return strategy;
  }
}
