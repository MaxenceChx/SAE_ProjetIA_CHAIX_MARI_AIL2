package ComparaisonsCouleurs;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaletteKMeans {

    public static Color[] generatePalette(BufferedImage img, int k) {
        return KMeans.cluster(img, k);
    }

    public static Color getNearestColor(Color color, NormeCouleurs distanceCouleur, Color[] palette) {
        double minDistance = Double.MAX_VALUE;
        Color nearest = null;

        for (Color c : palette) {
            double distance = distanceCouleur.distanceCouleur(color, c);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = c;
            }
        }

        return nearest;
    }

    private static int getDistance(Color c1, Color c2) {
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        return r * r + g * g + b * b;
    }
}