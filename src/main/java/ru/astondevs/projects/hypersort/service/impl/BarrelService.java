package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.projects.hypersort.service.io.DataReader;

import java.io.IOException;


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
    public void readObjects(String pathFile, int limitCountObjects) throws IOException {
        container = DataReader.readFromFile(pathFile, Barrel.class, limitCountObjects);
    }

    @Override
    public ObjectList<Barrel> getObjects(boolean isSorted) {
        if (isSorted) {
            if (sortedContainer.isEmpty()) {
                sortObjects(SortMethod.DEFAULT);
            }
            return sortedContainer;
        }
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
        if (sortedContainer.isEmpty()) {
            sortObjects(SortMethod.DEFAULT);
        }

        return sortedContainer.indexOf((Barrel) object);
    }
}
