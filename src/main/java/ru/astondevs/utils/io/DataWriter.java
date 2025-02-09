package ru.astondevs.utils.io;
import java.util.*;


    public final class DataWriter {

        private ArrayList array;
        private Integer index;
        private String path;
        private Boolean append = true;


        private DataWriter (String path, Boolean append){
            this.path = path;
            this.append = append;

        }

        public static ArrayList writeArray (ArrayList array){
            writeArray(array);
            return array;
        }

        public static Integer writeIndex (Integer index){
            writeIndex(index);
            return index;
        }
    }

