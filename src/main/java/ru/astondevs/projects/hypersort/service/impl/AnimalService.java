package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Animal;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.utils.collections.ArrayList;


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
    public ArrayList<Animal> getObjects() {
        return container;
    }

    @Override
    public ArrayList<Animal> getSortedObjects() {
        return container;
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
        if (sortedContainer == null) {
            sortObjects();
        }

        return sortedContainer.indexOf((Animal) object);
    }
}
