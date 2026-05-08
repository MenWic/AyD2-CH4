package menwic.ayd2.ex4.orders.support;

import java.util.List;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;

public interface OrderStore {
  void save(ProcessedOrder order);

  List<ProcessedOrder> findAll();
}
