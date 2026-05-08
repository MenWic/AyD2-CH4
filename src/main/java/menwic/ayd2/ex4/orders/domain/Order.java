package menwic.ayd2.ex4.orders.domain;

import java.math.BigDecimal;

public final class Order {
  private final OrderType type;
  private final CustomerType customerType;
  private final BigDecimal amount;
  private final boolean premium;
  private final boolean weekend;
  private final PromotionCode promotionCode;

  private Order(Builder builder) {
    this.type = builder.type;
    this.customerType = builder.customerType;
    this.amount = builder.amount;
    this.premium = builder.premium;
    this.weekend = builder.weekend;
    this.promotionCode = builder.promotionCode;
  }

  public static Builder builder() {
    return new Builder();
  }

  public OrderType getType() {
    return type;
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

  public static final class Builder {
    private OrderType type;
    private CustomerType customerType;
    private BigDecimal amount;
    private boolean premium;
    private boolean weekend;
    private PromotionCode promotionCode;

    private Builder() {
    }

    public Builder type(OrderType type) {
      this.type = type;
      return this;
    }

    public Builder customerType(CustomerType customerType) {
      this.customerType = customerType;
      return this;
    }

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder premium(boolean premium) {
      this.premium = premium;
      return this;
    }

    public Builder weekend(boolean weekend) {
      this.weekend = weekend;
      return this;
    }

    public Builder promotionCode(PromotionCode promotionCode) {
      this.promotionCode = promotionCode;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
