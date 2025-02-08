package ru.astondevs.projects.hypersort.model;


public class Human {
    private final String gender;
    private final Integer age;
    private final String lastName;

    private Human(Builder builder) {
        this.gender = builder.gender;
        this.age = builder.age;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private String gender;
        private Integer age;
        private String lastName;

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setAge(Integer age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Human build() {
            validate();
            return new Human(this);
        }

        private void validate() {
            if (gender == null || gender.isEmpty()) {
                throw new IllegalArgumentException("Gender cannot be empty");
            }
            if (lastName == null || lastName.isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be empty");
            }
        }
    }

    @Override
    public String toString() {
        return "Human{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
