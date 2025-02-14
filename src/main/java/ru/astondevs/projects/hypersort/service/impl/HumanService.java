package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Human;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.projects.hypersort.service.Comparators.AgeHumanComparator;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.projects.hypersort.service.io.DataReader;

import java.io.IOException;


public class HumanService extends AbstractService<Human> {
    @Override
    public void generateRandomObjects(int count) {
        for (int i = 0; i <= count; i++) {
            container.add(
                    new Human.Builder()
                            .setAge(random.nextInt(100))
                            .setGender(randomString(new String[]{"мужчина", "женщина"}))
                            .setLastName(capitalizeString(randomString(8)))
                            .build()
            );
        }
    }

    @Override
    public void readObjects(String pathFile, int limitCountObjects) throws IOException {
        container = DataReader.readFromFile(pathFile, Human.class, limitCountObjects);
    }

    @Override
    public ObjectList<Human> getObjects(boolean isSorted) {
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
            case SortMethod.BY_INT_FIELD -> sort(new AgeHumanComparator());
        }
    }

    @Override
    public Human getObject(int objectIndex) {
        return sortedContainer.get(objectIndex);
    }

    @Override
    public void addObject(CollectionObject item) {
        container.add((Human) item);
    }

    @Override
    public int searchObject(CollectionObject object) {
        if (sortedContainer.isEmpty()) {
            sortObjects(SortMethod.DEFAULT);
        }

        return sortedContainer.indexOf((Human) object);
    }
}
