package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Human;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.utils.io.DataReader;

import java.io.IOException;


public class HumanService extends AbstractService<Human> {
    @Override
    public void generateRandomObjects(int count) {
        for (int i = 0; i <= count; i++) {
            container.add(
                    new Human.Builder()
                            .setAge(random.nextInt(100))
                            .setGender(randomString(new String[]{"male", "female"}))
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
