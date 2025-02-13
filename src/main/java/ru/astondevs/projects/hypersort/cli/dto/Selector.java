package ru.astondevs.projects.hypersort.cli.dto;


public enum Selector {
    CLASS {
        @Override
        public Switch select(Integer num) {
            return switch (num) {
                case 1 -> Switch.ANIMAL;
                case 2 -> Switch.BARREL;
                case 3 -> Switch.HUMAN;

                case 4 -> Switch.EXIT;

                default -> throw new RuntimeException("Unknown switch");
            };
        }
    },

    INPUT {
        @Override
        public Switch select(Integer num) {
            return switch (num) {
                case 1 -> Switch.USER;
                case 2 -> Switch.FILE;
                case 3 -> Switch.RANDOM;

                case 4 -> Switch.BACK;
                case 5 -> Switch.EXIT;

                default -> throw new RuntimeException("Unknown switch");
            };
        }
    },

    PROCESSING {
        @Override
        public Switch select(Integer num) {
            return switch (num) {
                case 1 -> Switch.DISPLAY;
                case 2 -> Switch.SORTED_DISPLAY;
                case 3 -> Switch.DIFF_DISPLAY;
                case 4 -> Switch.SAVE;
                case 5 -> Switch.SORTED_SAVE;
                case 6 -> Switch.SORT;
                case 7 -> Switch.SORT_BY_INT;
                case 8 -> Switch.FIND;
                case 9 -> Switch.CLEAR;

                case 10 -> Switch.BACK_TO_INPUT;
                case 11 -> Switch.BACK_TO_CLASS;
                case 12 -> Switch.EXIT;

                default -> throw new RuntimeException("Unknown switch");
            };
        }
    };

    public abstract Switch select(Integer num);
}
