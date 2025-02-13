package ru.astondevs.projects.hypersort.service;

import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.utils.io.DataReader;
// import ru.astondevs.utils.io.DataReader;
// import ru.astondevs.utils.io.DataWriter;

import java.util.Random;


public abstract class AbstractService<T extends Comparable<T>> implements Service {
    protected final Random random = new Random();
    protected ObjectList<T> container = new ObjectList<>();
    protected ObjectList<T> sortedContainer = new ObjectList<>();
    protected SortMethod sortMethod = SortMethod.DEFAULT;

    protected String randomString(int length) {
        String source = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i <= length; i++) {
            int index = random.nextInt(source.length());
            buffer.append(source.charAt(index));
        }

        return buffer.toString();
    }

    protected String randomString(String[] items) {
        return items[random.nextInt(items.length)];
    }

    protected String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    protected boolean randomBoolean() {
        return random.nextInt(2) == 1;
    }

    @Override
    public void writeObjectsTo(String pathFile) {
        // DataWriter.write(container, pathFile);
    }

    @Override
    public void writeSortedObjectsTo(String pathFile) {
        // DataWriter.write(sortedContainer, pathFile);
    }

    @Override
    public void sortObjects(SortMethod method) {
        sortedContainer.addAll(container.sort());
    }

    @Override
    public void clear() {
        container.clear();
        sortedContainer.clear();
    }
}
