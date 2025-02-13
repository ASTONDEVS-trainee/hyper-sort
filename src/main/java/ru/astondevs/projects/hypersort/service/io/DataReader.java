package ru.astondevs.projects.hypersort.service.io;

import ru.astondevs.projects.hypersort.model.Animal;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.model.Human;
import ru.astondevs.utils.collections.ObjectList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DataReader {
    public static <T extends Comparable<T>> ObjectList<T> readFromFile(String fileName,
                                                                       Class<T> myClass,
                                                                       int limitObjects) throws IOException {

        ObjectList<T> objects = new ObjectList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Object builder = createBuilder(myClass.getSimpleName());

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("-")) {
                    if (objects.size() < limitObjects) {
                        objects.add(buildObject(builder));
                    } else {
                        return objects;
                    }
                    builder = createBuilder(myClass.getSimpleName());
                } else {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        updateBuilder(builder, parts[0].trim(), parts[1].trim());
                    }
                }
            }

            objects.add(buildObject(builder));
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении файла: " + e.getMessage());
        }

        return objects;
    }

    private static Object createBuilder(String type) {
        return switch (type) {
            case "Human" -> new Human.Builder();
            case "Barrel" -> new Barrel.Builder();
            case "Animal" -> new Animal.Builder();
            default -> throw new IllegalArgumentException("Неизвестный тип: " + type);
        };
    }

    private static void updateBuilder(Object builder, String key, String value) {
        if (builder instanceof Human.Builder humanBuilder) {
            switch (key) {
                case "пол" -> humanBuilder.setGender(value);
                case "возраст" -> humanBuilder.setAge(Integer.parseInt(value));
                case "фамилия" -> humanBuilder.setLastName(value);
            }
        } else if (builder instanceof Barrel.Builder barrelBuilder) {
            switch (key) {
                case "объём" -> barrelBuilder.setVolume(Integer.parseInt(value));
                case "хранимый материал" -> barrelBuilder.setStoredMaterial(value);
                case "материал" -> barrelBuilder.setMaterial(value);
            }
        } else if (builder instanceof Animal.Builder animalBuilder) {
            switch (key) {
                case "вид" -> animalBuilder.setSpecies(value);
                case "цвет глаз" -> animalBuilder.setEyeColor(value);
                case "имеет шерсть" -> animalBuilder.setHasFur(Boolean.parseBoolean(value));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T buildObject(Object builder) {
        if (builder instanceof Human.Builder) {
            return (T) ((Human.Builder) builder).build();
        } else if (builder instanceof Barrel.Builder) {
            return (T) ((Barrel.Builder) builder).build();
        } else if (builder instanceof Animal.Builder) {
            return (T) ((Animal.Builder) builder).build();
        }
        throw new IllegalArgumentException("Неизвестный тип builder'а");
    }
}