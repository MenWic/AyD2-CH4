package menwic.ayd2.ex4.orders.command;

import java.util.List;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandExecutor {
  private final List<OrderCommand> commands;

  public OrderCommandExecutor(List<OrderCommand> commands) {
    this.commands = commands;
  }

  public void executeAll(ProcessedOrder order) {
    commands.forEach(command -> command.execute(order));
  }
}
