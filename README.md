# UniAccounts — Work 1

Java implementation of the first part of the Design Patterns TP.

## Implemented patterns

1. **Builder** — `StudentAccount.Builder` builds a student account with three required fields and optional fields.
2. **Singleton** — `AccountRepository` guarantees one central repository; `AuditLogger` guarantees one audit logger.
3. **Prototype** — `StudentAccount.copyWithIdentity()` creates accounts by copying a reusable academic template and replacing the student's identity. `clone()` also creates an independent copy.

## Requirements

- JDK 8 or later.
- No external libraries are required.

## Compile and run (Linux / macOS)

From the project root:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out uniaccounts.Main
```

## Compile and run (Windows PowerShell)

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out uniaccounts.Main
```

## Project structure

```text
src/
  uniaccounts/
    Main.java
    model/StudentAccount.java
    repository/AccountRepository.java
    audit/AuditLogger.java
docs/
  RUNNING.md
  uml/
    work1.puml
    work1.png
```

## Important behavior

- Required: `id`, `fullName`, `email`.
- Email must contain `@`.
- Allowed levels: `L1`, `L2`, `L3`, `M1`, `M2`; default is `L1`.
- Defaults: `phone = null`, `major = null`, `scholarship = false`, empty `courses`.
- Duplicate student IDs are rejected by the repository.
- Adding an account is recorded in the shared audit log.

## Git tags requested for the work

After committing the finished first part:

```bash
git add .
git commit -m "Complete Work 1 - Builder Singleton Prototype"
git tag work1
```

The tag marks the commit containing Work 1. The GitHub repository must be created and pushed by the student.
# tpGLdesign_patterns
# tpGLdesign_patterns
