package menwic.ayd2.ex4.orders.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import menwic.ayd2.ex4.orders.command.AuditOrderCommand;
import menwic.ayd2.ex4.orders.command.NotifyCustomerCommand;
import menwic.ayd2.ex4.orders.command.OrderCommandExecutor;
import menwic.ayd2.ex4.orders.command.SaveOrderCommand;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.Order;
import menwic.ayd2.ex4.orders.domain.OrderType;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import menwic.ayd2.ex4.orders.domain.PromotionCode;
import menwic.ayd2.ex4.orders.exception.InvalidOrderException;
import menwic.ayd2.ex4.orders.pricing.BulkPricingStrategy;
import menwic.ayd2.ex4.orders.pricing.ExpressPricingStrategy;
import menwic.ayd2.ex4.orders.pricing.PricingStrategyResolver;
import menwic.ayd2.ex4.orders.pricing.StandardPricingStrategy;
import menwic.ayd2.ex4.orders.promotion.BlackFridayPromotionPolicy;
import menwic.ayd2.ex4.orders.promotion.NoPromotionPolicy;
import menwic.ayd2.ex4.orders.promotion.PromotionPolicyResolver;
import menwic.ayd2.ex4.orders.promotion.Save10PromotionPolicy;
import menwic.ayd2.ex4.orders.promotion.Save20PromotionPolicy;
import menwic.ayd2.ex4.orders.promotion.WelcomePromotionPolicy;
import menwic.ayd2.ex4.orders.support.InMemoryOrderStore;
import menwic.ayd2.ex4.orders.support.OrderAudit;
import menwic.ayd2.ex4.orders.support.OrderNotifier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

class OrderProcessorFacadeTest {
  @Test
  void processesOrderAndAppliesPromotionAfterSubtotal() {
    InMemoryOrderStore orderStore = new InMemoryOrderStore();
    TrackingAudit audit = new TrackingAudit();
    TrackingNotifier notifier = new TrackingNotifier();
    OrderProcessorFacade facade = buildFacade(orderStore, audit, notifier);

    Order order = Order.builder()
        .type(OrderType.STANDARD)
        .customerType(CustomerType.RETAIL)
        .amount(new BigDecimal("100.00"))
        .premium(true)
        .promotionCode(PromotionCode.SAVE10)
        .build();

    ProcessedOrder processedOrder = facade.process(order);

    assertMoney("90.00", processedOrder.getSubtotal());
    assertMoney("81.00", processedOrder.getTotal());
    assertEquals(1, orderStore.findAll().size());
    assertTrue(audit.wasCalled());
    assertTrue(notifier.wasCalled());
  }

  @ParameterizedTest
  @MethodSource("invalidOrders")
  void rejectsInvalidOrders(Order invalidOrder) {
    OrderProcessorFacade facade = buildFacade(new InMemoryOrderStore(), new TrackingAudit(), new TrackingNotifier());
    assertThrows(InvalidOrderException.class, () -> facade.process(invalidOrder));
  }

  @Test
  void processStandardInternationalWeekendBlackFridayAppliesPromotionAfterSubtotal() {
    InMemoryOrderStore orderStore = new InMemoryOrderStore();
    OrderProcessorFacade facade = buildFacade(orderStore, new TrackingAudit(), new TrackingNotifier());

    Order order = Order.builder()
        .type(OrderType.STANDARD)
        .customerType(CustomerType.INTERNATIONAL)
        .amount(new BigDecimal("100.00"))
        .weekend(true)
        .promotionCode(PromotionCode.BLACKFRIDAY)
        .build();

    ProcessedOrder processedOrder = facade.process(order);

    assertMoney("126.00", processedOrder.getSubtotal());
    assertMoney("88.20", processedOrder.getTotal());
  }

  private OrderProcessorFacade buildFacade(
      InMemoryOrderStore orderStore,
      OrderAudit audit,
      OrderNotifier notifier
  ) {
    PricingStrategyResolver pricingStrategyResolver = new PricingStrategyResolver(
        List.of(
            new StandardPricingStrategy(),
            new ExpressPricingStrategy(),
            new BulkPricingStrategy()
        )
    );

    NoPromotionPolicy noPromotionPolicy = new NoPromotionPolicy();
    PromotionPolicyResolver promotionPolicyResolver = new PromotionPolicyResolver(
        List.of(
            noPromotionPolicy,
            new Save10PromotionPolicy(),
            new Save20PromotionPolicy(),
            new BlackFridayPromotionPolicy(),
            new WelcomePromotionPolicy()
        ),
        noPromotionPolicy
    );

    OrderCommandExecutor orderCommandExecutor = new OrderCommandExecutor(
        List.of(
            new SaveOrderCommand(orderStore),
            new AuditOrderCommand(audit),
            new NotifyCustomerCommand(notifier)
        )
    );

    return new OrderProcessorFacade(
        pricingStrategyResolver,
        promotionPolicyResolver,
        orderCommandExecutor
    );
  }

  private void assertMoney(String expected, BigDecimal actual) {
    assertEquals(0, new BigDecimal(expected).compareTo(actual));
  }

  private static Stream<Arguments> invalidOrders() {
    return Stream.of(
        Arguments.of((Order) null),
        Arguments.of(
            Order.builder()
                .type(OrderType.STANDARD)
                .customerType(CustomerType.RETAIL)
                .amount(BigDecimal.ZERO)
                .build()
        ),
        Arguments.of(
            Order.builder()
                .type(OrderType.STANDARD)
                .customerType(CustomerType.RETAIL)
                .amount(new BigDecimal("-1.00"))
                .build()
        ),
        Arguments.of(
            Order.builder()
                .type(null)
                .customerType(CustomerType.RETAIL)
                .amount(new BigDecimal("100.00"))
                .build()
        ),
        Arguments.of(
            Order.builder()
                .type(OrderType.STANDARD)
                .customerType(null)
                .amount(new BigDecimal("100.00"))
                .build()
        )
    );
  }

  private static final class TrackingAudit implements OrderAudit {
    private boolean called;

    @Override
    public void register(ProcessedOrder order) {
      called = true;
    }

    private boolean wasCalled() {
      return called;
    }
  }

  private static final class TrackingNotifier implements OrderNotifier {
    private boolean called;

    @Override
    public void notify(ProcessedOrder order) {
      called = true;
    }

    private boolean wasCalled() {
      return called;
    }
  }
}
