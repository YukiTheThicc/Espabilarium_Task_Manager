package frontend.ui;

import backend.utils.EspLogger;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * Image
 *
 * @author Santiago Barreiro
 */
public class Image {

    // ATTRIBUTES
    private int id;
    private int width;
    private int height;
    private String path;
    private boolean loaded;

    // CONSTRUCTORS
    public Image() {
        this.id = -1;
        this.width = -1;
        this.height = -1;
        this.loaded = false;
    }

    public Image(int width, int height) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isLoaded() {
        return loaded;
    }

    // METHODS
    public void load(String path) {

        this.path = path;
        this.id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        // Texture Parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(path, x, y, channels, 0);

        // Create texture according to the image channels if loaded correctly
        if (image != null) {
            this.width = x.get(0);
            this.height = y.get(0);
            if (channels.get(0) == 4) {
                // RGBA Image
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x.get(0), y.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
                this.loaded = true;
            } else if (channels.get(0) == 3) {
                // RGB Image
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, x.get(0), y.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
                this.loaded = true;
            } else {
                    EspLogger.log(Image.class, "Failed to load image, unexpected number of channels", EspLogger.Level.ERROR);
            }
            stbi_image_free(image);
        } else {
            EspLogger.log(Image.class, "Failed to load image from '" + path + "'", EspLogger.Level.ERROR);
        }
    }
}
