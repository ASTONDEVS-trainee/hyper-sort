package ru.astondevs.projects.hypersort.model;

import java.util.Objects;

public class Animal implements Comparable<Animal> {
    private final String species;
    private final String eyeColor;
    private final boolean hasFur;

    private Animal(Builder builder) {
        this.species = builder.species;
        this.eyeColor = builder.eyeColor;
        this.hasFur = builder.hasFur;
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
            validate();
            return new Animal(this);
        }

        private void validate() {
            if (species == null || species.isEmpty()) {
                throw new IllegalArgumentException("The field should not be empty");
            }
            if (eyeColor == null || eyeColor.isEmpty()) {
                throw new IllegalArgumentException("The field should not be empty");
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, eyeColor, hasFur);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Animal other)) return false;
        return hasFur == other.hasFur &&
                Objects.equals(species, other.species) &&
                Objects.equals(eyeColor, other.eyeColor);
    }

    @Override
    public int compareTo(Animal o) {
        int result = this.species.compareTo(o.species);
        return result != 0 ? result : this.eyeColor.compareTo(o.eyeColor);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "species='" + species + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", hasFur=" + hasFur +
                '}';
    }
}
