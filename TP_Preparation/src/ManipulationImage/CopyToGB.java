package ManipulationImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopyToGB {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("img/image.jpeg"));

            int width = img.getWidth();
            int height = img.getHeight();

            BufferedImage imgCopy = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = img.getRGB(x, y);
                    int cBG = color & 0x00FFFF;

                    imgCopy.setRGB(x, y, cBG);
                }
            }

            ImageIO.write(imgCopy, "jpg", new File("img/image_copy_gb.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
