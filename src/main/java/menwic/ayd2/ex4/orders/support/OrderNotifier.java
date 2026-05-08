package menwic.ayd2.ex4.orders.support;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;

public interface OrderNotifier {
  void notify(ProcessedOrder order);
}
