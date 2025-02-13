package ru.astondevs.projects.hypersort.service;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.service.impl.AnimalService;
import ru.astondevs.projects.hypersort.service.impl.BarrelService;
import ru.astondevs.projects.hypersort.service.impl.HumanService;
import ru.astondevs.utils.collections.ObjectList;


public interface Service {
    static Service createService(ServiceName type) {
        return switch (type) {
            case ServiceName.ANIMAL -> new AnimalService();
            case ServiceName.BARREL -> new BarrelService();
            case ServiceName.HUMAN -> new HumanService();
        };
    }

    void generateRandomObjects(int count);
    void readObjectsFrom(String pathFile, int limitCountObjects);
    void writeObjectsTo(String pathFile);
    void writeSortedObjectsTo(String pathFile);
    ObjectList<? extends CollectionObject> getObjects();
    ObjectList<? extends CollectionObject> getSortedObjects();
    CollectionObject getObject(int objectIndex);
    void addObject(CollectionObject object);
    void sortObjects(SortMethod method);
    int searchObject(CollectionObject object);
    void clear();
}
