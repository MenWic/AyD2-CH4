package menwic.ayd2.ex4.orders.command;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import menwic.ayd2.ex4.orders.support.OrderStore;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service
public class SaveOrderCommand implements OrderCommand {
  private final OrderStore orderStore;

  public SaveOrderCommand(OrderStore orderStore) {
    this.orderStore = orderStore;
  }

  @Override
  public void execute(ProcessedOrder order) {
    orderStore.save(order);
  }
}
