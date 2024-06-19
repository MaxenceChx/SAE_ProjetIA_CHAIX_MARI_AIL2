package Algorithmes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DBSCAN implements AlgoClustering {
    private double eps;
    private int minPts;

    public DBSCAN(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    @Override
    public int[] fit(double[][] data, int nClusters) {
        int n = data.length;
        int[] labels = new int[n];
        Arrays.fill(labels, -1); // initialiser tous les labels à -1 (non assignés)

        int clusterId = 0;

        for (int i = 0; i < n; i++) {
            if (labels[i] != -1) {
                continue; // déjà traité
            }

            List<Integer> neighbors = regionQuery(data, i);

            if (neighbors.size() < minPts) {
                labels[i] = 0; // point de bruit
            } else {
                clusterId++;
                expandCluster(data, labels, i, neighbors, clusterId);
            }
        }

        return labels;
    }

    private void expandCluster(double[][] data, int[] labels, int point, List<Integer> neighbors, int clusterId) {
        labels[point] = clusterId;

        int index = 0;
        while (index < neighbors.size()) {
            int currentPoint = neighbors.get(index);

            if (labels[currentPoint] == 0) {
                labels[currentPoint] = clusterId; // changer le point de bruit en un point de cluster
            }

            if (labels[currentPoint] == -1) {
                labels[currentPoint] = clusterId;
                List<Integer> currentNeighbors = regionQuery(data, currentPoint);
                if (currentNeighbors.size() >= minPts) {
                    neighbors.addAll(currentNeighbors);
                }
            }

            index++;
        }
    }

    private List<Integer> regionQuery(double[][] data, int point) {
        List<Integer> neighbors = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            if (i != point && distance(data[point], data[i]) <= eps) {
                neighbors.add(i);
            }
        }

        return neighbors;
    }

    private double distance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}