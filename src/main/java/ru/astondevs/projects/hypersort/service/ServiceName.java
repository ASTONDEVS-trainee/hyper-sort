package ru.astondevs.projects.hypersort.service;


public enum ServiceName {
    ANIMAL {
        @Override
        public String toString() {
            return "Животное";
        }
    },

    BARREL {
        @Override
        public String toString() {
            return "Бочка";
        }
    },

    HUMAN {
        @Override
        public String toString() {
            return "Человек";
        }
    }
}
