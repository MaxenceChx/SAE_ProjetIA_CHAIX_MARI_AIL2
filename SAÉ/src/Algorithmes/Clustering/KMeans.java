package Algorithmes.Clustering;

import java.util.Random;

public class KMeans {
    private double[][] data;
    private int nClusters;
    private double[][] centroids;
    private int[] labels;
    private Random random;

    public KMeans(double[][] data, int nClusters) {
        this.data = data;
        this.nClusters = nClusters;
        this.centroids = new double[nClusters][data[0].length];
        this.labels = new int[data.length];
        this.random = new Random();
    }

    public int[] fit() {
        initializeCentroids();
        boolean convergence = false;

        while (!convergence) {
            assignLabels();
            convergence = updateCentroids();
        }

        return labels;
    }

    private void initializeCentroids() {
        for (int i = 0; i < nClusters; i++) {
            centroids[i] = data[random.nextInt(data.length)];
        }
    }

    private void assignLabels() {
        for (int i = 0; i < data.length; i++) {
            labels[i] = nearestCentroid(data[i]);
        }
    }

    private int nearestCentroid(double[] point) {
        double minDist = Double.MAX_VALUE;
        int label = -1;

        for (int i = 0; i < nClusters; i++) {
            double dist = euclideanDistance(point, centroids[i]);
            if (dist < minDist) {
                minDist = dist;
                label = i;
            }
        }

        return label;
    }

    private boolean updateCentroids() {
        double[][] newCentroids = new double[nClusters][data[0].length];
        int[] counts = new int[nClusters];

        for (int i = 0; i < data.length; i++) {
            int label = labels[i];
            for (int j = 0; j < data[i].length; j++) {
                newCentroids[label][j] += data[i][j];
            }
            counts[label]++;
        }

        for (int i = 0; i < nClusters; i++) {
            for (int j = 0; j < data[0].length; j++) {
                newCentroids[i][j] /= counts[i];
            }
        }

        boolean convergence = true;
        for (int i = 0; i < nClusters; i++) {
            if (!java.util.Arrays.equals(centroids[i], newCentroids[i])) {
                convergence = false;
                break;
            }
        }

        centroids = newCentroids;
        return convergence;
    }

    private double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }
}