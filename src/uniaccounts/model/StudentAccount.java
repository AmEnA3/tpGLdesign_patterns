package uniaccounts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents one university student account.
 * Builder: creates validated accounts with required and optional fields.
 * Prototype: clone() and copyWithIdentity() duplicate an existing account template.
 */
public final class StudentAccount implements Cloneable {
    private final String id;
    private final String fullName;
    private final String email;
    private final String phone;
    private final String level;
    private final String major;
    private final boolean scholarship;
    private final List<String> courses;

    private StudentAccount(Builder builder) {
        this(builder.id, builder.fullName, builder.email, builder.phone,
             builder.level, builder.major, builder.scholarship, builder.courses);
    }

    private StudentAccount(String id, String fullName, String email, String phone,
                           String level, String major, boolean scholarship,
                           List<String> courses) {
        this.id = requireText(id, "id");
        this.fullName = requireText(fullName, "fullName");
        this.email = validateEmail(email);
        this.phone = phone;
        this.level = validateLevel(level);
        this.major = major;
        this.scholarship = scholarship;
        this.courses = new ArrayList<String>(courses);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be empty.");
        }
        return value.trim();
    }

    private static String validateEmail(String value) {
        String email = requireText(value, "email");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("email must contain '@'.");
        }
        return email;
    }

    private static String validateLevel(String value) {
        String level = (value == null || value.trim().isEmpty()) ? "L1" : value.trim();
        if (!level.equals("L1") && !level.equals("L2") && !level.equals("L3")
                && !level.equals("M1") && !level.equals("M2")) {
            throw new IllegalArgumentException("level must be L1, L2, L3, M1 or M2.");
        }
        return level;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLevel() { return level; }
    public String getMajor() { return major; }
    public boolean hasScholarship() { return scholarship; }

    // Return a read-only view so callers cannot modify the account's internal list.
    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    /**
     * Prototype: returns an independent copy with the same data.
     */
    @Override
    public StudentAccount clone() {
        return new StudentAccount(id, fullName, email, phone, level, major,
                                  scholarship, courses);
    }

    /**
     * Prototype: copies the template's academic settings, but assigns a new identity.
     */
    public StudentAccount copyWithIdentity(String newId, String newFullName, String newEmail) {
        return new StudentAccount(newId, newFullName, newEmail, phone, level, major,
                                  scholarship, courses);
    }

    @Override
    public String toString() {
        return "StudentAccount{id='" + id + "', fullName='" + fullName
                + "', email='" + email + "', level='" + level
                + "', major='" + major + "', scholarship=" + scholarship
                + ", courses=" + courses + "}";
    }

    /**
     * Builder pattern: required fields are supplied first; optional fields are chained.
     */
    public static class Builder {
        private final String id;
        private final String fullName;
        private final String email;
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private List<String> courses = new ArrayList<String>();

        public Builder(String id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder phone(String phone) {
            this.phone = (phone == null || phone.trim().isEmpty()) ? null : phone.trim();
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder major(String major) {
            this.major = (major == null || major.trim().isEmpty()) ? null : major.trim();
            return this;
        }

        public Builder scholarship(boolean scholarship) {
            this.scholarship = scholarship;
            return this;
        }

        public Builder courses(List<String> courses) {
            this.courses = (courses == null) ? new ArrayList<String>()
                                             : new ArrayList<String>(courses);
            return this;
        }

        public Builder addCourse(String course) {
            if (course == null || course.trim().isEmpty()) {
                throw new IllegalArgumentException("Course must not be empty.");
            }
            this.courses.add(course.trim());
            return this;
        }

        public StudentAccount build() {
            return new StudentAccount(this);
        }
    }
}
