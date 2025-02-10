package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.utils.collections.ArrayList;


public class BarrelService extends AbstractService<Barrel> {
    @Override
    public void generateRandomObjects(int count) {
        for (int i = 0; i <= count; i++) {
            container.add(
                    new Barrel.Builder()
                            .setVolume(random.nextInt(1000))
                            .setMaterial(randomString(6))
                            .setStoredMaterial(randomString(6))
                            .build()
            );
        }
    }

    @Override
    public ArrayList<Barrel> getObjects() {
        return container;
    }

    @Override
    public ArrayList<Barrel> getSortedObjects() {
        return container;
    }

    @Override
    public Barrel getObject(int objectIndex) {
        return sortedContainer.get(objectIndex);
    }

    @Override
    public void addObject(CollectionObject item) {
        container.add((Barrel) item);
    }

    @Override
    public int searchObject(CollectionObject object) {
        if (sortedContainer == null) {
            sortObjects();
        }

        return sortedContainer.indexOf((Barrel) object);
    }
}
