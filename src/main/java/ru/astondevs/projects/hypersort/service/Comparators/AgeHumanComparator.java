package ru.astondevs.projects.hypersort.service.Comparators;

import ru.astondevs.projects.hypersort.model.Human;

import java.util.Comparator;


public class AgeHumanComparator implements Comparator<Human> {
    @Override
    public int compare(Human o1, Human o2) {
        return o1.getAge().compareTo(o2.getAge());
    }
}
