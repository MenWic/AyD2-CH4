package menwic.ayd2.ex4.orders.support;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import menwic.ayd2.ex4.orders.domain.CustomerType;
import menwic.ayd2.ex4.orders.domain.OrderType;
import menwic.ayd2.ex4.orders.domain.ProcessedOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileOrderAuditTest {
  @TempDir
  Path tempDir;

  @Test
  void registerWritesAuditDataToFile() throws IOException {
    Path logPath = tempDir.resolve("orders-test.log");
    FileOrderAudit fileOrderAudit = new FileOrderAudit(logPath);
    ProcessedOrder processedOrder = new ProcessedOrder(
        OrderType.STANDARD,
        CustomerType.RETAIL,
        new BigDecimal("100.00"),
        false,
        false,
        null,
        new BigDecimal("100.00"),
        new BigDecimal("100.00"),
        LocalDateTime.now()
    );

    fileOrderAudit.register(processedOrder);

    assertTrue(Files.exists(logPath));
    String content = Files.readString(logPath);
    assertTrue(content.contains("type=STANDARD"));
    assertTrue(content.contains("customer=RETAIL"));
    assertTrue(content.contains("subtotal=100.00"));
    assertTrue(content.contains("total=100.00"));
  }
}
