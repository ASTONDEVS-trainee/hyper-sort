package ru.astondevs.projects.hypersort.service.io;

import ru.astondevs.utils.collections.ObjectList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public final class DataWriter {
        private static final String CLASS_LINE = "=".repeat(80);
        private static final String INSTANCE_LINE = "-".repeat(80);

        private DataWriter() {}

        public static <T extends Comparable<T>> void write(ObjectList <T> objectList,
                                                           String pathFile,
                                                           Boolean append) throws IOException {

            Path path = Path.of(pathFile);

            String className = switch (objectList.get(0).getClass().getSimpleName()) {
                case "Animal" -> "Животное";
                case "Barrel" -> "Бочка";
                case "Human" -> "Человек";

                default -> throw new RuntimeException("Unknown class name");
            };

            String classHeader = String.format(
                    "%s\n%s\n%s\n",
                    CLASS_LINE,
                    className,
                    CLASS_LINE
            );

            Files.write(
                    path,
                    classHeader.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING
            );

            for (int i = 0; i <= objectList.size() - 1; i++) {
                String instanceData = String.format(
                        i == 0 ? "%s" : INSTANCE_LINE + "\n%s",
                        objectList.get(i).toString()
                );

                Files.write(
                        path,
                        instanceData.getBytes(),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND
                );
            }
        }
    }
