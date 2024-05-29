package ManipulationImage;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Copy {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("img/image.jpeg"));
            ImageIO.write(img, "jpg", new File("img/image_copy.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
