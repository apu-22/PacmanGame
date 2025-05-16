package Pac_Man;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {

    public static Image load(String path) {
        java.net.URL imageUrl = ImageLoader.class.getResource(path);
        if (imageUrl == null) {
            System.out.println("Image not found: " + path);
            return null;
        }
        return new ImageIcon(imageUrl).getImage();
    }
}
