package ru.astondevs.projects.hypersort.model;

import java.util.Comparator;
import java.util.Objects;


public class Barrel implements CollectionObject, Comparable<Barrel> {
    private final Integer volume;
    private final String storedMaterial;
    private final String material;

    private Barrel(Builder builder) {
        this.volume = builder.volume;
        this.storedMaterial = builder.storedMaterial;
        this.material = builder.material;
    }

    public Integer getVolume() {
        return volume;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public boolean validate() {
        return volume != null && volume > 0 &&
                storedMaterial != null && !storedMaterial.isBlank() &&
                material != null && !material.isBlank();
    }

    @Override
    public String toString() {
        return "volume: " + volume + "\n" +
                "storedMaterial: " + storedMaterial + "\n" +
                "material: " + material + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barrel barrel = (Barrel) o;
        return Objects.equals(volume, barrel.volume) &&
                Objects.equals(storedMaterial, barrel.storedMaterial) &&
                Objects.equals(material, barrel.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, storedMaterial, material);
    }

    @Override
    public int compareTo(Barrel other) {
        return Comparator.comparing(Barrel::getVolume)
                .thenComparing(Barrel::getStoredMaterial)
                .thenComparing(Barrel::getMaterial)
                .compare(this, other);
    }

    public static class Builder {
        private Integer volume;
        private String storedMaterial;
        private String material;

        public Builder setVolume(Integer volume) {
            this.volume = volume;
            return this;
        }

        public Builder setStoredMaterial(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }

        public Builder setMaterial(String material) {
            this.material = material;
            return this;
        }

        public Barrel build() {
            Barrel barrel = new Barrel(this);
            if (!barrel.validate()) {
                throw new IllegalArgumentException("Validation is not passed");
            }
            return barrel;
        }
    }
}

