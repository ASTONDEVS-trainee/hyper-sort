package ru.astondevs.utils.io;
import java.util.*;

public final class DataWriter {

    private static List objectList;
    private static String pathFile;
    private static Boolean append = true;

    private DataWriter() {
    }

    private DataWriter(String pathFile, Boolean append) {
        this.pathFile = pathFile;
        this.append = append;
    }

    public static void writer(List objectList, String pathFile) {
        for (Object i : objectList) {
            i.toString();
            //нужно ещё записать
        }
    }

    @Override
    public String toString() {
        System.out.println("============================================" + '\'' +
                        Object.class.getClass() + '\'' +
                        "============================================" + '\'' +
                        Object.class.getDeclaredFields() + ": "
                //цикл для полей?
                //+ вывести для них значение?
        );
    }
}


