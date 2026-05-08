package menwic.ayd2.ex4.orders.support;

import java.util.ArrayList;
import java.util.List;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import org.springframework.stereotype.Component;

@Component
public class InMemoryOrderStore implements OrderStore {
  private final List<ProcessedOrder> orders = new ArrayList<>();

  @Override
  public void save(ProcessedOrder order) {
    orders.add(order);
  }

  @Override
  public List<ProcessedOrder> findAll() {
    return new ArrayList<>(orders);
  }
}
