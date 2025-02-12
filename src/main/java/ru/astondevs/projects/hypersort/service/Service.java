package ru.astondevs.projects.hypersort.service;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.service.impl.AnimalService;
import ru.astondevs.projects.hypersort.service.impl.BarrelService;
import ru.astondevs.projects.hypersort.service.impl.HumanService;
import ru.astondevs.utils.collections.ObjectList;


public interface Service {
    static Service createService(String type) {
        return switch (type) {
            case "Animal" -> new AnimalService();
            case "Barrel" -> new BarrelService();
            case "Human" -> new HumanService();
            default -> throw new RuntimeException("Unknown service name");
        };
    }

    void generateRandomObjects(int count);

    void readObjectsFrom(String pathFile);

    void writeObjectsTo(String pathFile);

    void writeSortedObjectsTo(String pathFile);

    ObjectList<? extends CollectionObject> getObjects();

    ObjectList<? extends CollectionObject> getSortedObjects();

    CollectionObject getObject(int objectIndex);

    void addObject(CollectionObject object);

    SortMethod getSortMethod();

    void setSortMethod(SortMethod sortMethod);

    void sortObjects();

    int searchObject(CollectionObject object);
}
