package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.utils.io.DataReader;


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
    public void readObjectsFrom(String pathFile, int limitCountObjects) {
        container = DataReader.readFromFile(pathFile, Barrel.class);
    }

    @Override
    public ObjectList<Barrel> getObjects() {
        return container;
    }

    @Override
    public ObjectList<Barrel> getSortedObjects() {
        if (sortedContainer.isEmpty()) {
            sortObjects(SortMethod.DEFAULT);
        }

        return sortedContainer;
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
        if (sortedContainer.isEmpty()) {
            sortObjects(SortMethod.DEFAULT);
        }

        return sortedContainer.indexOf((Barrel) object);
    }
}
