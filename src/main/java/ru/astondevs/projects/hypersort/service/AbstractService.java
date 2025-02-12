package ru.astondevs.projects.hypersort.service;

import ru.astondevs.utils.collections.ObjectList;
// import ru.astondevs.utils.io.DataReader;
// import ru.astondevs.utils.io.DataWriter;

import java.util.Random;


public abstract class AbstractService<T extends Comparable<T>> implements Service {
    protected final Random random = new Random();
    protected ObjectList<T> container = new ObjectList<>();
    protected ObjectList<T> sortedContainer;
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
    public void readObjectsFrom(String pathFile) {
        // TODO: ждём реализацию DataReader
        // container = DataReader.read(pathFile);
    }

    @Override
    public void writeObjectsTo(String pathFile) {
        // TODO: ждём реализацию DataWriter
        // DataWriter.write(container, pathFile);
    }

    @Override
    public void writeSortedObjectsTo(String pathFile) {
        // DataWriter.write(sortedContainer, pathFile);
    }

    @Override
    public SortMethod getSortMethod() {
        return sortMethod;
    }

    @Override
    public void setSortMethod(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public void sortObjects() {
        // TODO: ждем метод ArrayList.sort()
        // sortedContainer = container.sort(sortMethod);
    }
}
