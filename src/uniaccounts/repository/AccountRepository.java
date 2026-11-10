package uniaccounts.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import uniaccounts.audit.AuditLogger;
import uniaccounts.model.StudentAccount;

/**
 * Singleton: central repository shared by all parts of the application.
 */
public final class AccountRepository {
    private final Map<String, StudentAccount> accounts =
            new LinkedHashMap<String, StudentAccount>();

    // Initialization-on-demand holder: lazy and thread-safe singleton creation.
    private static class Holder {
        private static final AccountRepository INSTANCE = new AccountRepository();
    }

    private AccountRepository() {
        // Private constructor prevents external instantiation.
    }

    public static AccountRepository getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void addAccount(StudentAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null.");
        }
        if (accounts.containsKey(account.getId())) {
            throw new IllegalArgumentException(
                    "An account with id " + account.getId() + " already exists.");
        }
        accounts.put(account.getId(), account);
        AuditLogger.getInstance().log("ACCOUNT_CREATED",
                "Stored account " + account.getId() + " (" + account.getFullName() + ")");
    }

    public synchronized Optional<StudentAccount> findById(String id) {
        StudentAccount account = accounts.get(id);
        return Optional.ofNullable(account);
    }

    public synchronized List<StudentAccount> getAllAccounts() {
        return Collections.unmodifiableList(
                new ArrayList<StudentAccount>(accounts.values()));
    }

    public synchronized int count() {
        return accounts.size();
    }
}
