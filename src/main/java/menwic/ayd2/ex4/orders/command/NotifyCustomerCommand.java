package menwic.ayd2.ex4.orders.command;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import menwic.ayd2.ex4.orders.support.OrderNotifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(3)
@Service
public class NotifyCustomerCommand implements OrderCommand {
  private final OrderNotifier orderNotifier;

  public NotifyCustomerCommand(OrderNotifier orderNotifier) {
    this.orderNotifier = orderNotifier;
  }

  @Override
  public void execute(ProcessedOrder order) {
    orderNotifier.notify(order);
  }
}
