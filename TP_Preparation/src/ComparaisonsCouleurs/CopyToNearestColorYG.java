package ComparaisonsCouleurs;

import ManipulationImage.OutilCouleur;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopyToNearestColorYG {
    public static void main(String[] args) {
        Color[] couleurs = new Color [2];
        couleurs[0] = Color.GREEN;
        couleurs[1] = Color.YELLOW ;

        try {
            BufferedImage img = ImageIO.read(new File("img/image.jpeg"));

            int width = img.getWidth();
            int height = img.getHeight();

            BufferedImage imgCopy = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = img.getRGB(x, y);

                    int[] rgb = OutilCouleur.getTabColor(color);
                    double distWithY = (Math.pow((rgb[0] - couleurs[1].getRed()), 2) + Math.pow((rgb[1] - couleurs[1].getGreen()), 2) + Math.pow((rgb[2] - couleurs[1].getBlue()), 2));
                    double distWithG = (Math.pow((rgb[0] - couleurs[0].getRed()), 2) + Math.pow((rgb[1] - couleurs[0].getGreen()), 2) + Math.pow((rgb[2] - couleurs[0].getBlue()), 2));

                    int nearest = distWithY < distWithG ? couleurs[1].getRGB() : couleurs[0].getRGB();

                    imgCopy.setRGB(x, y, nearest);
                }
            }

            ImageIO.write(imgCopy, "jpg", new File("img/image_copy_nearest_yg.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
