package menwic.ayd2.ex4.orders.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import menwic.ayd2.ex4.orders.command.OrderCommandExecutor;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import menwic.ayd2.ex4.orders.exception.InvalidOrderException;
import menwic.ayd2.ex4.orders.pricing.PricingStrategy;
import menwic.ayd2.ex4.orders.pricing.PricingStrategyResolver;
import menwic.ayd2.ex4.orders.promotion.PromotionPolicy;
import menwic.ayd2.ex4.orders.promotion.PromotionPolicyResolver;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessorFacade {
  private final PricingStrategyResolver pricingStrategyResolver;
  private final PromotionPolicyResolver promotionPolicyResolver;
  private final OrderCommandExecutor orderCommandExecutor;

  public OrderProcessorFacade(
      PricingStrategyResolver pricingStrategyResolver,
      PromotionPolicyResolver promotionPolicyResolver,
      OrderCommandExecutor orderCommandExecutor
  ) {
    this.pricingStrategyResolver = pricingStrategyResolver;
    this.promotionPolicyResolver = promotionPolicyResolver;
    this.orderCommandExecutor = orderCommandExecutor;
  }

  public ProcessedOrder process(Order order) {
    validate(order);

    PricingStrategy pricingStrategy = pricingStrategyResolver.resolve(order.getType());
    BigDecimal subtotal = pricingStrategy.calculateSubtotal(order).setScale(2, RoundingMode.HALF_UP);

    PromotionPolicy promotionPolicy = promotionPolicyResolver.resolve(order.getPromotionCode());
    BigDecimal total = promotionPolicy.apply(subtotal).setScale(2, RoundingMode.HALF_UP);

    ProcessedOrder processedOrder = new ProcessedOrder(
        order.getType(),
        order.getCustomerType(),
        order.getAmount().setScale(2, RoundingMode.HALF_UP),
        order.isPremium(),
        order.isWeekend(),
        order.getPromotionCode(),
        subtotal,
        total,
        LocalDateTime.now()
    );

    orderCommandExecutor.executeAll(processedOrder);
    return processedOrder;
  }

  private void validate(Order order) {
    if (order == null) {
      throw new InvalidOrderException("La orden no puede ser nula");
    }
    if (order.getType() == null) {
      throw new InvalidOrderException("El tipo de orden es obligatorio");
    }
    if (order.getCustomerType() == null) {
      throw new InvalidOrderException("El tipo de cliente es obligatorio");
    }
    if (order.getAmount() == null) {
      throw new InvalidOrderException("El monto es obligatorio");
    }
    if (order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidOrderException("El monto debe ser mayor a cero");
    }
  }
}
