package uniaccounts.audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton: one shared audit log for the whole application.
 */
public final class AuditLogger {
    private static final AuditLogger INSTANCE = new AuditLogger();
    private final List<String> entries = new ArrayList<String>();
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditLogger() {
        // Private constructor prevents creating another logger.
    }

    public static AuditLogger getInstance() {
        return INSTANCE;
    }

    public synchronized void log(String action, String details) {
        String entry = "[" + LocalDateTime.now().format(FORMAT) + "] "
                + action + " - " + details;
        entries.add(entry);
        System.out.println("[AUDIT] " + entry);
    }

    public synchronized List<String> getEntries() {
        return Collections.unmodifiableList(new ArrayList<String>(entries));
    }
}
