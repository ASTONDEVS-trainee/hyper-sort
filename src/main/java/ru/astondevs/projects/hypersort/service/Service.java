package ru.astondevs.projects.hypersort.service;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.service.impl.*;
import ru.astondevs.utils.collections.ObjectList;

import java.io.IOException;


public interface Service {
    static Service createService(ServiceName type) {
        return switch (type) {
            case ServiceName.ANIMAL -> new AnimalService();
            case ServiceName.BARREL -> new BarrelService();
            case ServiceName.HUMAN -> new HumanService();
        };
    }

    void generateRandomObjects(int count);
    void readObjects(String pathFile, int limitCountObjects) throws IOException;
    void writeObjects(String pathFile, boolean isSorted, boolean append) throws IOException;
    ObjectList<? extends CollectionObject> getObjects(boolean isSorted);
    CollectionObject getObject(int objectIndex);
    void addObject(CollectionObject object);
    void sortObjects(SortMethod method);
    int searchObject(CollectionObject object);
    void clear();
}
