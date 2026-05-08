package menwic.ayd2.ex4.orders.command;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import menwic.ayd2.ex4.orders.support.OrderAudit;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service
public class AuditOrderCommand implements OrderCommand {
  private final OrderAudit orderAudit;

  public AuditOrderCommand(OrderAudit orderAudit) {
    this.orderAudit = orderAudit;
  }

  @Override
  public void execute(ProcessedOrder order) {
    orderAudit.register(order);
  }
}
