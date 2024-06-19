package Algorithmes.ComparaisonsCouleurs;

import java.awt.*;

public class NormeRedmean implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        int[] rgb1 = {c1.getRed(), c1.getGreen(), c1.getBlue()};
        int[] rgb2 = {c2.getRed(), c2.getGreen(), c2.getBlue()};

        double rmean = (double) (rgb1[0] + rgb2[0]) / 2;
        double deltaR = rgb1[0] - rgb2[0];
        double deltaG = rgb1[1] - rgb2[1];
        double deltaB = rgb1[2] - rgb2[2];

        return Math.sqrt((2 + rmean / 256) * Math.pow(deltaR, 2) + 4 * Math.pow(deltaG, 2) + (2 + (255 - rmean) / 256)) * Math.pow(deltaB, 2);
    }
}
