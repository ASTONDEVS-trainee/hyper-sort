package ru.astondevs.projects.hypersort.model;


public class Barrel {
    private final Integer volume;
    private final String storedMaterial;
    private final String material;

    private Barrel(Builder builder) {
        this.volume = builder.volume;
        this.storedMaterial = builder.storedMaterial;
        this.material = builder.material;
    }

    public static class Builder {
        private Integer volume;
        private String storedMaterial;
        private String material;

        public Builder setVolume(Integer volume) {
            if (volume <= 0) {
                throw new IllegalArgumentException("Volume must be greater than ZERO(0)");
            }
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
            validate();
            return new Barrel(this);
        }

        private void validate() {
            if (storedMaterial == null || storedMaterial.isEmpty()) {
                throw new IllegalArgumentException("Stored material cannot be empty");
            }
            if (material == null || material.isEmpty()) {
                throw new IllegalArgumentException("Material cannot be empty");
            }
        }

    }

    @Override
    public String toString() {
        return "Barrel{" +
                "volume=" + volume +
                ", storedMaterial='" + storedMaterial + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
