package ComparaisonsCouleurs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {

    public static Color[] cluster(BufferedImage img, int k) {
        int width = img.getWidth();
        int height = img.getHeight();
        List<int[]> pixels = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                Color c = new Color(color);
                pixels.add(new int[]{c.getRed(), c.getGreen(), c.getBlue()});
            }
        }

        int[][] data = pixels.toArray(new int[0][]);
        int[][] centroids = initializeCentroids(data, k);

        boolean convergence = false;
        while (!convergence) {
            int[][] newCentroids = new int[k][3];
            int[] counts = new int[k];

            for (int[] pixel : data) {
                int nearestIndex = getNearestCentroid(pixel, centroids);
                for (int i = 0; i < 3; i++) {
                    newCentroids[nearestIndex][i] += pixel[i];
                }
                counts[nearestIndex]++;
            }

            convergence = true;
            for (int i = 0; i < k; i++) {
                if (counts[i] == 0) continue;
                for (int j = 0; j < 3; j++) {
                    newCentroids[i][j] /= counts[i];
                }
                if (!convergence || !isEqual(centroids[i], newCentroids[i])) {
                    convergence = false;
                }
                centroids[i] = newCentroids[i];
            }
        }

        Color[] palette = new Color[k];
        for (int i = 0; i < k; i++) {
            palette[i] = new Color(centroids[i][0], centroids[i][1], centroids[i][2]);
        }
        return palette;
    }

    private static int[][] initializeCentroids(int[][] data, int k) {
        Random random = new Random();
        int[][] centroids = new int[k][3];
        for (int i = 0; i < k; i++) {
            centroids[i] = data[random.nextInt(data.length)];
        }
        return centroids;
    }

    private static int getNearestCentroid(int[] pixel, int[][] centroids) {
        double minDistance = Double.MAX_VALUE;
        int nearestIndex = -1;
        for (int i = 0; i < centroids.length; i++) {
            double distance = getDistance(pixel, centroids[i]);
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    private static double getDistance(int[] p1, int[] p2) {
        int r = p1[0] - p2[0];
        int g = p1[1] - p2[1];
        int b = p1[2] - p2[2];
        return r * r + g * g + b * b;
    }

    private static boolean isEqual(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
}
