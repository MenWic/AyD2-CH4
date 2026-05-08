package menwic.ayd2.ex4.orders.support;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;

public interface OrderAudit {
  void register(ProcessedOrder order);
}
