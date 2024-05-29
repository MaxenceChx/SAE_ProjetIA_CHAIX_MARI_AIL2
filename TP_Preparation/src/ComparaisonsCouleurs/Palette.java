package ComparaisonsCouleurs;

import java.awt.*;

public class Palette {
    final static Color[] COLORS = {
            Color.BLACK,
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            new Color(192,192,192),
            new Color(128,128,128),
            new Color(128,0,0),
            new Color(128,128,0),
            new Color(0,128,0),
            new Color(128,0,128),
            new Color(0,128,128),
            new Color(0,0,128)
    };

    public static Color getNearestColor(Color color, NormeCouleurs distanceCouleur) {
        double minDistance = Double.MAX_VALUE;
        Color nearest = null;

        for (Color c : COLORS) {
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
