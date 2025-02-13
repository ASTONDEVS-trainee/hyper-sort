import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.*;
import java.util.List;
import static java.nio.file.StandardOpenOption.*;

public final class DataWriter {

    private static Object objectList;
    private static String pathFile;
    private static Boolean append = true;
    private String field;

    private DataWriter() {
    }

    private DataWriter(String pathFile, Boolean append) {
        this.pathFile = pathFile;
        this.append = append;
    }

    public static void write(List objectList, String pathFile) throws IOException {

        System.out.println("=".repeat(80) + '\n' +
                getClassName() + '\n' +
                "=".repeat(80));

        for (Object i : objectList) {
            System.out.println("-".repeat(80));

            Files.write(Path.of(pathFile), i.toString().getBytes(),
                    StandardOpenOption.APPEND,
                    CREATE,
                    WRITE);
        }
    }

    private static String getClassName() {
        return MethodHandles.lookup().lookupClass().getName();
    }
}


