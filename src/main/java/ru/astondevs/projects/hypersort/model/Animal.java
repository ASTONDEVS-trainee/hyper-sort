package ru.astondevs.projects.hypersort.model;

import java.util.Comparator;
import java.util.Objects;


public class Animal extends CollectionObject implements Comparable<Animal> {
    private final String species;
    private final String eyeColor;
    private final boolean hasFur;

    private Animal(Builder builder) {
        this.species = builder.species;
        this.eyeColor = builder.eyeColor;
        this.hasFur = builder.hasFur;
    }

    public String getSpecies() {
        return species;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public boolean isHasFur() {
        return hasFur;
    }

    @Override
    public boolean validate() {
        return species != null && !species.isBlank() &&
                eyeColor != null && !eyeColor.isBlank();
    }

    @Override
    public String toString() {
        return "species: " + species + "\n" +
                "eyeColor: " + eyeColor + "\n" +
                "hasFur: " + hasFur + "\n" +
                "--------------------------------------------------------------------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return hasFur == animal.hasFur &&
                Objects.equals(species, animal.species) &&
                Objects.equals(eyeColor, animal.eyeColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, eyeColor, hasFur);
    }

    @Override
    public int compareTo(Animal other) {
        return Comparator.comparing(Animal::getSpecies)
                .thenComparing(Animal::getEyeColor)
                .thenComparing(Animal::isHasFur)
                .compare(this, other);
    }

    public static class Builder {
        private String species;
        private String eyeColor;
        private boolean hasFur;

        public Builder setSpecies(String species) {
            this.species = species;
            return this;
        }

        public Builder setEyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }

        public Builder setHasFur(boolean hasFur) {
            this.hasFur = hasFur;
            return this;
        }

        public Animal build() {
            Animal animal = new Animal(this);
            if (!animal.validate()) {
                throw new IllegalArgumentException("Validation is not passed");
            }
            return animal;
        }
    }
}