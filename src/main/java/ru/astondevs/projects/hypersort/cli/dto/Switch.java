package ru.astondevs.projects.hypersort.cli.dto;


public enum Switch {
    // Default selector:
    SILENT,
    COMPLETE,

    BACK,
    BACK_TO_INPUT,
    BACK_TO_CLASS,

    EXIT,


    // Selector CLASS:
    ANIMAL,
    BARREL,
    HUMAN,


    // Selector INPUT:
    USER,
    INPUT_USER,

    FILE,
    FILE_PATH,
    FILE_COUNT_OBJECTS,

    RANDOM,
    RANDOM_COUNT_OBJECTS,


    // Selector PROCESSING:
    SAVE,
    SAVE_PATH,
    SAVE_APPEND,

    SORTED_SAVE,
    SORTED_SAVE_PATH,
    SORTED_SAVE_APPEND,

    DISPLAY,
    SORTED_DISPLAY,
    DIFF_DISPLAY,

    SORT,
    SORT_BY_INT,

    FIND,
    INPUT_FIND,

    CLEAR
}
