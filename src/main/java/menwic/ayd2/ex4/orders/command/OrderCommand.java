package menwic.ayd2.ex4.orders.command;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;

public interface OrderCommand {
  void execute(ProcessedOrder order);
}
