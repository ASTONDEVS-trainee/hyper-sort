package ru.astondevs.utils.io;
import java.util.*;


    public final class DataWriter {

        private static byte[] textBytes;
        private static Integer index;
        private static String path;
        private static Boolean append = true;

        private DataOutput (String path, Boolean append) {
            this.path = path;
            this.append = append;
        }

        public static byte[] getTextBytes() {
            return textBytes;
        }
    }
