import java.io.File;
import java.io.IOException;

public class CreateDir {
    public static File createDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }

    public static void main(String[] args) {
        String directoryPath = "example_directory";

        try {
            File directory = CreateDir.createDirectory(directoryPath);
            System.out.println("Directory created: " + directory.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

}
