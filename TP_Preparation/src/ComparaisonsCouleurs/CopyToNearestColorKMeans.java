package ComparaisonsCouleurs;

import ManipulationImage.OutilCouleur;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CopyToNearestColorKMeans {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("img/image.jpeg"));

            int width = img.getWidth();
            int height = img.getHeight();

            // Generate the color palette using KMeans
            int k = 500; // Number of clusters/colors
            Color[] palette = PaletteKMeans.generatePalette(img, k);

            BufferedImage imgCopy = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = img.getRGB(x, y);

                    int[] rgb = OutilCouleur.getTabColor(color);
                    Color nearest = PaletteKMeans.getNearestColor(new Color(rgb[0], rgb[1], rgb[2]), new NormeRedmean(), palette);

                    imgCopy.setRGB(x, y, nearest.getRGB());
                }
            }

            ImageIO.write(imgCopy, "jpg", new File("img/image_copy_nearest_kmeans.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
