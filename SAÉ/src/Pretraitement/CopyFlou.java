package Pretraitement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopyFlou {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("img/Planete 1.jpg"));

            Flou flouParMoyenne = new FlouParMoyenne(3);
            Flou flouGaussien5 = new FlouGaussien(5, 1.0);
            Flou flouGaussien7 = new FlouGaussien(7, 1.0);
            Flou flouGaussien7_3 = new FlouGaussien(7, 3.0);

            BufferedImage imgFlouteeParMoyenne = flouParMoyenne.appliquer(img);
            BufferedImage imgFlouteeGaussien5 = flouGaussien5.appliquer(img);
            BufferedImage imgFlouteeGaussien7 = flouGaussien7.appliquer(img);
            BufferedImage imgFlouteeGaussien7_3 = flouGaussien7_3.appliquer(img);

            ImageIO.write(imgFlouteeParMoyenne, "jpg", new File("img/Pretraitement/Planete 1_flouteeParMoyenne.jpg"));
            ImageIO.write(imgFlouteeGaussien5, "jpg", new File("img/Pretraitement/Planete 1_flouteeGaussien5.jpg"));
            ImageIO.write(imgFlouteeGaussien7, "jpg", new File("img/Pretraitement/Planete 1_flouteeGaussien7.jpg"));
            ImageIO.write(imgFlouteeGaussien7_3, "jpg", new File("img/Pretraitement/Planete 1_flouteeGaussien7_3.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
