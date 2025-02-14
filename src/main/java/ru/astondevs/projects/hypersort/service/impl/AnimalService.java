package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Animal;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.projects.hypersort.service.exceptions.SortMethodException;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.projects.hypersort.service.io.DataReader;

import java.io.IOException;


public class AnimalService extends AbstractService<Animal> {
    @Override
    public void generateRandomObjects(int count) {
        for (int i = 0; i <= count; i++) {
            container.add(
                    new Animal.Builder()
                            .setSpecies(randomString(8))
                            .setEyeColor(randomString(4))
                            .setHasFur(randomBoolean())
                            .build()
            );
        }
    }

    @Override
    public void readObjects(String pathFile, int limitCountObjects) throws IOException {
        container = DataReader.readFromFile(pathFile, Animal.class, limitCountObjects);
    }

    @Override
    public ObjectList<Animal> getObjects(boolean isSorted) {
        if (isSorted) {
            if (sortedContainer.isEmpty()) {
                sortObjects(SortMethod.DEFAULT);
            }
            return sortedContainer;
        }
        return container;
    }

    @Override
    public void sortObjects(SortMethod method) {
        switch (method) {
            case SortMethod.DEFAULT -> sort();

            case SortMethod.BY_INT_FIELD -> throw new SortMethodException(
                    "\tЭта сортировка не доступна для классов без числового поля"
            );
        }
    }

    @Override
    public Animal getObject(int objectIndex) {
        return sortedContainer.get(objectIndex);
    }

    @Override
    public void addObject(CollectionObject item) {
        container.add((Animal) item);
    }

    @Override
    public int searchObject(CollectionObject object) {
        if (sortedContainer.isEmpty()) {
            sortObjects(SortMethod.DEFAULT);
        }

        return sortedContainer.indexOf((Animal) object);
    }
}
