package ru.astondevs.projects.hypersort.model;

import java.util.Comparator;
import java.util.Objects;


public class Human extends CollectionObject implements Comparable<Human> {
    private final String gender;
    private final Integer age;
    private final String lastName;

    private Human(Builder builder) {
        this.gender = builder.gender;
        this.age = builder.age;
        this.lastName = builder.lastName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean validate() {
        return age != null && age > 0 &&
                gender != null && !gender.isBlank() &&
                lastName != null && !lastName.isBlank();
    }

    @Override
    public String toString() {
        return "gender: " + gender + "\n" +
                "age: " + age + "\n" +
                "lastName: " + lastName + "\n" +
                "--------------------------------------------------------------------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(gender, human.gender) &&
                Objects.equals(age, human.age) &&
                Objects.equals(lastName, human.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, age, lastName);
    }

    @Override
    public int compareTo(Human o) {
        return Comparator.comparing(Human::getGender)
                .thenComparing(Human::getAge)
                .thenComparing(Human::getLastName)
                .compare(this, o);
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
            this.age = age;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Human build() {
            Human human = new Human(this);
            if (!human.validate()) {
                throw new IllegalArgumentException("Validation is not passed");
            }
            return human;
        }
    }
}
