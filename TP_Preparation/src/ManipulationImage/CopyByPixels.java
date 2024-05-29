package ManipulationImage;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopyByPixels {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("img/image.jpeg"));

            int width = img.getWidth();
            int height = img.getHeight();

            BufferedImage imgCopy = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x, y);
                    imgCopy.setRGB(x, y, rgb);
                }
            }

            ImageIO.write(imgCopy, "jpg", new File("img/image_copy_pixels.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
