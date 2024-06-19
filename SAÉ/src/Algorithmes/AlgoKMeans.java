package Algorithmes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoKMeans implements AlgoClustering {
    // On a un attribut img de type BufferedImage
    private final BufferedImage img;
    // On a un attribut k de type entier
    private final int k = 50;

    /**
     * Constructeur de la classe AlgoKMeans
     * @param img
     */
    public AlgoKMeans(BufferedImage img) {
        this.img = img;
    }

    /**
     * Méthode clustering qui prend en paramètre un tableau de valeurs
     * @param data tableau de valeurs
     * @param nbClusters nombre de clusters
     * @return un tableau d'entiers affectant chaque objet à un cluster
     */
    @Override
    public int[] clustering(double[][] data, int nbClusters) {
        // Convertir les données en tableau d'entiers
        int[][] intData = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                intData[i][j] = (int) data[i][j];
            }
        }

        // Initialiser les centroïdes
        int[][] centroids = initializeCentroids(intData, nbClusters);

        boolean convergence = false;
        while (!convergence) {
            int[][] newCentroids = new int[nbClusters][3];
            int[] counts = new int[nbClusters];

            for (int[] pixel : intData) {
                int nearestIndex = getNearestCentroid(pixel, centroids);
                for (int i = 0; i < 3; i++) {
                    newCentroids[nearestIndex][i] += pixel[i];
                }
                counts[nearestIndex]++;
            }

            convergence = true;
            for (int i = 0; i < nbClusters; i++) {
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

        // Convertir les centroïdes en tableau d'entiers pour le retour
        int[] result = new int[nbClusters];
        for (int i = 0; i < nbClusters; i++) {
            result[i] = new Color(centroids[i][0], centroids[i][1], centroids[i][2]).getRGB();
        }

        return result;
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