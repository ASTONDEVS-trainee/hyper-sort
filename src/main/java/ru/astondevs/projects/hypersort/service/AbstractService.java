package ru.astondevs.projects.hypersort.service;

import ru.astondevs.utils.collections.ObjectList;
import ru.astondevs.projects.hypersort.service.io.DataWriter;

import java.io.IOException;
import java.util.Comparator;
import java.util.Random;


public abstract class AbstractService<T extends Comparable<T>> implements Service {
    protected final Random random = new Random();
    protected ObjectList<T> container = new ObjectList<>();
    protected ObjectList<T> sortedContainer = new ObjectList<>();

    @Override
    public void writeObjects(String pathFile, boolean isSorted, boolean append) throws IOException {
        ObjectList<T> objects;

        if (isSorted) {
            if (sortedContainer.isEmpty()) {
                sortObjects(SortMethod.DEFAULT);
            }
            objects = sortedContainer;
        } else {
            objects = container;
        }

        DataWriter.write(objects, pathFile, append);
    }

    @Override
    public void clear() {
        container.clear();
        sortedContainer.clear();
    }

    protected void sort() {
        sortedContainer.addAll(container.sort());
    }

    protected void sort(Comparator<T> comparator) {
        sortedContainer.addAll(container.sort(comparator));
    }

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
}
