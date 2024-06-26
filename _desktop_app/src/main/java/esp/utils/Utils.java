package esp.utils;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.util.tinyfd.TinyFileDialogs.*;

public class Utils {

    private static SimpleDateFormat sdf;

    // METHODS
    public static void init() {
        Utils.sdf = new SimpleDateFormat("hh:mm:ss.SSS");
        EspLogger.log(Utils.class, "Initializing Diamond utilities...");
    }

    /**
     * Returns the current time formatted
     * @return Current time formatted
     */
    public static String getTime() {
        return Utils.sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * Gets the system os name
     * @return OS name
     */
    public static String getOS() {
        return System.getProperty("os.name");
    }

    /**
     * Opens native OS to save a file. Returns null when the file has not been selected (cancelled or failed)
     * @return String with the path for the new file or null.
     */
    public static File saveFile() {
        return saveFile("");
    }

    public static File saveFile(String defaultFile) {
        return saveFile(defaultFile, "");
    }

    /**
     * Opens native OS to save a file. Returns null when the file has not been selected (cancelled or failed)
     * @return String with the path for the new file or null.
     */
    public static File saveFile(String defaultFile, String fileType) {
        String path = tinyfd_saveFileDialog("", defaultFile, null, fileType);
        File file = null;
        if (path != null && !path.isEmpty()) {
            file = new File(path);
            try {
                if (file.createNewFile()) EspLogger.log(Utils.class,
                        "Overwriting file '" + file.getAbsolutePath() + "'");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    /**
     * Saves into an array of lines into a file. Overrides all data from said file.
     * @param file File in which the data is going to be stored
     * @param lines Array of Strings representing the lines to write
     */
    public static void saveToFile(File file, String[] lines) {
        if (file != null && file.exists() && file.isFile()) {
            if (lines != null && lines.length > 0) {
                try {
                    Files.write(file.toPath(), Arrays.asList(lines), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    EspLogger.log(Utils.class, "Failed save to file: '" + file.getAbsolutePath() + "'", EspLoggerLevel.ERROR);
                }
            } else {
                EspLogger.log(Utils.class, "Null or empty lines", EspLoggerLevel.ERROR);
            }
        } else {
            EspLogger.log(Utils.class, "Invalid file", EspLoggerLevel.ERROR);
        }
    }

    /**
     * Reads all bytes from a file.
     * @param file File to read the bytes from
     * @return Not null array of bytes
     */
    public static byte[] readAllBytes(File file) {
        byte[] data = new byte[0];
        if (file.exists()) {
            try {
                data = Files.readAllBytes(Paths.get(file.getPath()));
            } catch (IOException e) {
                EspLogger.log(Utils.class, "Failed to load data from file '" + file.getPath() + "'", EspLoggerLevel.ERROR);
            }
        }
        return data;
    }

    /**
     * Opens native OS multiple file selector.
     * @return Array of strings containing the paths of the selected files.
     */
    public static String[] selectFiles() {
        return selectFiles("", "", "");
    }

    public static String[] selectFiles(String title) {
        return selectFiles(title, "", "");
    }

    public static String[] selectFiles(String title, String defaultFile) {
        return selectFiles(title, defaultFile, "");
    }

    public static String[] selectFiles(String title, String defaultFile, String type) {
        String result = tinyfd_openFileDialog(title, defaultFile, null, type, true);
        if (result != null && !result.isEmpty()) {
            return result.split("\\|");
        }
        return null;
    }

    public static String selectFile() {
        return selectFile("", "", "");
    }

    public static String selectFile(String title) {
        return selectFile(title, "", "");
    }

    public static String selectFile(String title, String defaultPath) {
        return selectFile(title, defaultPath, "");
    }

    /**
     * Opens a single file selection dialog
     * @return String containing the path of the selected file.
     */
    public static String selectFile(String title, String defaultPath, String filter) {
        String result = tinyfd_openFileDialog(title, defaultPath, null, filter, false);
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return null;
    }

    /**
     * Opens a single directory selection dialog
     * @param title Title for the dialog window
     * @param defaultPath Default path from which the file dialog is going to open
     * @return String containing the path of the selected file.
     */
    public static String selectDirectory(String title, String defaultPath) {
        String result = tinyfd_selectFolderDialog(title, defaultPath);
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return null;
    }

    /**
     * Looks for all files within the specified directory.
     * @param directory Directory in which files are going to be searched.
     * @return List of retrieved files.
     */
    public static ArrayList<File> getFilesInDir(String directory) {
        return getFilesInDir(directory, "");
    }

    /**
     * Looks for all files with the given extension within the specified directory.
     * @param directory Directory in which files are going to be searched.
     * @param extension Extension of the files to retrieve (without dot).
     * @return ArrayList of retrieved files.
     */
    public static ArrayList<File> getFilesInDir(String directory, String extension) {
        File dir = new File(directory);
        ArrayList<File> retrievedFiles = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileExtension = file.getPath().substring(file.getPath().lastIndexOf(".") + 1);
                    if (extension == null || extension.isEmpty()) {
                        retrievedFiles.add(file);
                    } else if (extension.equals(fileExtension)) {
                        retrievedFiles.add(file);
                    }
                }
            }
        }

        return retrievedFiles;
    }

    /**
     * Gets all folders within the specified directory;
     * @param directory Directory from which to look
     * @return List if found folders
     */
    public static ArrayList<File> getFoldersInDir(String directory) {
        File dir = new File(directory);
        ArrayList<File> retrievedFiles = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    retrievedFiles.add(file);
                }
            }
        }

        return retrievedFiles;
    }

    public static GLFWImage.Buffer loadGLFWImage(String path) {
        GLFWImage.Buffer imageBuffer = null;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()) {

            IntBuffer comp = stack.mallocInt(1);
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            buffer = stbi_load(new File(path).getAbsolutePath(), width, height, comp, 4);
            if (buffer != null) {

                GLFWImage image = GLFWImage.malloc();
                imageBuffer = GLFWImage.malloc(1);
                image.set(width.get(), height.get(), buffer);
                imageBuffer.put(0, image);
            }
        }

        return imageBuffer;
    }

    /**
     * Gets the values of an Enum type
     * @param enumType Enum which values are to be retrieved
     * @return Array of names for the enum
     * @param <T> Enum class
     */
    public static <T extends Enum<T>> String[] getEnumValues(Class<T> enumType) {
        String[] enumValues = new String[enumType.getEnumConstants().length];
        int i = 0;
        for (T enumIntegerValue : enumType.getEnumConstants()) {
            enumValues[i] = enumIntegerValue.name();
            i++;
        }
        return enumValues;
    }

    /**
     * Gets the index of a String inside a String array
     * @param str String which index with the index that wants to be known
     * @param arr Array of Strings in which the index of str wants to be known
     * @return The index, returns -1 if is not found within the array
     */
    public static int indexOf(String str, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (str.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }
}
