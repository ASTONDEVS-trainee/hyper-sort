import java.lang.reflect.Field;
import java.util.List;

public final class DataWriter {

    private static List objectList;
    private static String pathFile;
    private static Boolean append = true;
    private String field;

    private DataWriter() {
    }

    private DataWriter(String pathFile, Boolean append) {
        this.pathFile = pathFile;
        this.append = append;
    }

    public static void writer(List objectList, String pathFile) {
        for (Object i : objectList) {
            i.toString();
            //+ записать
        }
    }

    @Override
    public String toString() {
        return "============================================" + '\'' +
                Object.class.getClass() + '\'' +
                "============================================" + '\'' +
//                Object.class.getDeclaredFields() + ": " +  "\n" +
        //цикл для полей?
        //+ вывести для них значение?
        for(Field field : Object.class.getDeclaredFields()) {
            System.out.println(field.getName() + ": " + field.set(Object value))}; + '\'' +
                "---------------------------------------------";
    }
}


