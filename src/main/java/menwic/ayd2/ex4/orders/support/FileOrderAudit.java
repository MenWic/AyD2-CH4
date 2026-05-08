package menwic.ayd2.ex4.orders.support;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import org.springframework.stereotype.Component;

@Component
public class FileOrderAudit implements OrderAudit {
  private static final Path DEFAULT_LOG_PATH = Path.of("orders.log");
  private final Path logPath;

  public FileOrderAudit() {
    this(DEFAULT_LOG_PATH);
  }

  public FileOrderAudit(Path logPath) {
    this.logPath = logPath;
  }

  @Override
  public void register(ProcessedOrder order) {
    String logLine = String.format(
        "processedAt=%s type=%s customer=%s subtotal=%s total=%s%n",
        order.getProcessedAt(),
        order.getOrderType(),
        order.getCustomerType(),
        order.getSubtotal(),
        order.getTotal()
    );
    try {
      Files.writeString(logPath, logLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException exception) {
      throw new UncheckedIOException("No se pudo registrar la auditoría de la orden", exception);
    }
  }
}
