package menwic.ayd2.ex4.orders.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ProcessedOrder {
  private final OrderType orderType;
  private final CustomerType customerType;
  private final BigDecimal amount;
  private final boolean premium;
  private final boolean weekend;
  private final PromotionCode promotionCode;
  private final BigDecimal subtotal;
  private final BigDecimal total;
  private final LocalDateTime processedAt;

  public ProcessedOrder(
      OrderType orderType,
      CustomerType customerType,
      BigDecimal amount,
      boolean premium,
      boolean weekend,
      PromotionCode promotionCode,
      BigDecimal subtotal,
      BigDecimal total,
      LocalDateTime processedAt
  ) {
    this.orderType = orderType;
    this.customerType = customerType;
    this.amount = amount;
    this.premium = premium;
    this.weekend = weekend;
    this.promotionCode = promotionCode;
    this.subtotal = subtotal;
    this.total = total;
    this.processedAt = processedAt;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public boolean isPremium() {
    return premium;
  }

  public boolean isWeekend() {
    return weekend;
  }

  public PromotionCode getPromotionCode() {
    return promotionCode;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public LocalDateTime getProcessedAt() {
    return processedAt;
  }
}
