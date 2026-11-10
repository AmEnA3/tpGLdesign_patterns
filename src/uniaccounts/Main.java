package uniaccounts;

import java.util.Arrays;
import java.util.List;

import uniaccounts.audit.AuditLogger;
import uniaccounts.model.StudentAccount;
import uniaccounts.repository.AccountRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== UniAccounts | Work 1 ==========");

        // Singleton: obtain the one central repository and the one audit logger.
        AccountRepository repository = AccountRepository.getInstance();
        AuditLogger logger = AuditLogger.getInstance();

        // BUILDER: create a reusable template with common academic information.
        StudentAccount template = new StudentAccount.Builder(
                "TEMPLATE", "Template Student", "template@univ.dz")
                .level("L3")
                .major("Informatique")
                .scholarship(false)
                .courses(Arrays.asList("POO", "Systemes", "Reseaux"))
                .build();

        // PROTOTYPE: duplicate the template while changing the student's identity.
        StudentAccount amina = template.copyWithIdentity(
                "20260001", "Amina Benali", "amina@univ.dz");
        StudentAccount youssef = template.copyWithIdentity(
                "20260002", "Youssef Ali", "youssef@univ.dz");

        // BUILDER: another account built with optional fields chosen individually.
        StudentAccount sara = new StudentAccount.Builder(
                "20260003", "Sara Ouali", "sara@univ.dz")
                .phone("0550123456")
                .level("M1")
                .major("Informatique")
                .scholarship(true)
                .addCourse("Architecture")
                .addCourse("Bases de donnees")
                .build();

        repository.addAccount(amina);
        repository.addAccount(youssef);
        repository.addAccount(sara);

        System.out.println("\n--- Accounts in central repository ---");
        for (StudentAccount account : repository.getAllAccounts()) {
            System.out.println(account);
        }

        System.out.println("\nTotal accounts: " + repository.count());

        System.out.println("\n--- Search test ---");
        System.out.println(repository.findById("20260002").orElse(null));

        System.out.println("\n--- Duplicate ID validation test ---");
        try {
            StudentAccount duplicate = new StudentAccount.Builder(
                    "20260001", "Another Student", "another@univ.dz").build();
            repository.addAccount(duplicate);
        } catch (IllegalArgumentException exception) {
            System.out.println("Expected validation: " + exception.getMessage());
        }

        System.out.println("\n--- Singleton identity test ---");
        AccountRepository repository2 = AccountRepository.getInstance();
        AuditLogger logger2 = AuditLogger.getInstance();
        System.out.println("Same repository instance? " + (repository == repository2));
        System.out.println("Same audit logger instance? " + (logger == logger2));

        System.out.println("\n--- Audit log entries ---");
        for (String entry : logger.getEntries()) {
            System.out.println(entry);
        }

        System.out.println("\n========== Work 1 finished ==========");
    }
}
