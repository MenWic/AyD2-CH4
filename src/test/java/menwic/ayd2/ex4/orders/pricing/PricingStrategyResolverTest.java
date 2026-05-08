package menwic.ayd2.ex4.orders.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import menwic.ayd2.ex4.orders.domain.OrderType;
import org.junit.jupiter.api.Test;

class PricingStrategyResolverTest {
  @Test
  void resolvesStrategyByOrderType() {
    PricingStrategyResolver resolver = new PricingStrategyResolver(
        List.of(
            new StandardPricingStrategy(),
            new ExpressPricingStrategy(),
            new BulkPricingStrategy()
        )
    );

    PricingStrategy strategy = resolver.resolve(OrderType.EXPRESS);

    assertEquals(OrderType.EXPRESS, strategy.supports());
    assertTrue(strategy instanceof ExpressPricingStrategy);
  }

  @Test
  void resolveThrowsWhenStrategyIsMissing() {
    PricingStrategyResolver resolver = new PricingStrategyResolver(
        List.of(
            new StandardPricingStrategy(),
            new ExpressPricingStrategy()
        )
    );

    assertThrows(IllegalArgumentException.class, () -> resolver.resolve(OrderType.BULK));
  }
}
