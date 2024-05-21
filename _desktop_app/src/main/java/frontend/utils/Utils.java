package frontend.utils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import backend.utils.EspLogger;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.util.tinyfd.TinyFileDialogs.*;

/**
 * Utils
 *
 * @author Santiago Barreiro
 */
public class Utils {

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
        String path = TinyFileDialogs.tinyfd_saveFileDialog("", defaultFile, null, fileType);
        File file = null;
        if (path != null && !path.isEmpty()) {
            file = new File(path);
            try {
                if (file.createNewFile()) EspLogger.log(backend.utils.Utils.class,
                        "Overwriting file '" + file.getAbsolutePath() + "'");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
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
        String result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultFile, null, type, true);
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
        String result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath, null, filter, false);
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
        String result = TinyFileDialogs.tinyfd_selectFolderDialog(title, defaultPath);
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return null;
    }

    public static GLFWImage.Buffer loadGLFWImage(String path) {
        GLFWImage.Buffer imageBuffer = null;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()) {

            IntBuffer comp = stack.mallocInt(1);
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            buffer = STBImage.stbi_load(new File(path).getAbsolutePath(), width, height, comp, 4);
            if (buffer != null) {

                GLFWImage image = GLFWImage.malloc();
                imageBuffer = GLFWImage.malloc(1);
                image.set(width.get(), height.get(), buffer);
                imageBuffer.put(0, image);
            }
        }

        return imageBuffer;
    }
}
