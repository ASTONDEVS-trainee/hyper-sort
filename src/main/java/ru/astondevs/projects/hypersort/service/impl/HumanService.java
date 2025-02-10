package ru.astondevs.projects.hypersort.service.impl;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Human;
import ru.astondevs.projects.hypersort.service.AbstractService;
import ru.astondevs.utils.collections.ObjectList;


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
    public ObjectList<Human> getObjects() {
        return container;
    }

    @Override
    public ObjectList<Human> getSortedObjects() {
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
        if (sortedContainer == null) {
            sortObjects();
        }

        return sortedContainer.indexOf((Human) object);
    }
}
