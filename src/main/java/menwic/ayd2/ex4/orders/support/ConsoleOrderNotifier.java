package menwic.ayd2.ex4.orders.support;

import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOrderNotifier implements OrderNotifier {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleOrderNotifier.class);

  @Override
  public void notify(ProcessedOrder order) {
    LOGGER.info("Notificacion enviada. type={} total={}", order.getOrderType(), order.getTotal());
  }
}
