package ru.astondevs.projects.hypersort.service.Comparators;

import ru.astondevs.projects.hypersort.model.Barrel;

import java.util.Comparator;

public class VolumeBarrelComparator implements Comparator<Barrel> {

    @Override
    public int compare(Barrel o1, Barrel o2) {
        return o1.getVolume().compareTo(o2.getVolume());
    }
}
