package Algorithmes.Clustering;

import java.util.ArrayList;
import java.util.List;

public class DBSCAN {
    private double[][] data;
    private double eps; // Rayon epsilon
    private int minPts; // Nombre minimum de points dans un voisinage pour former un cluster
    private int[] labels;
    private boolean[] visited;
    private List<List<Integer>> clusters;

    public DBSCAN(double[][] data, double eps, int minPts) {
        this.data = data;
        this.eps = eps;
        this.minPts = minPts;
        this.labels = new int[data.length];
        this.visited = new boolean[data.length];
        this.clusters = new ArrayList<>();
    }

    public List<List<Integer>> fit() {
        // Initialisation
        for (int i = 0; i < data.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                List<Integer> neighbors = regionQuery(i);
                if (neighbors.size() < minPts) {
                    labels[i] = -1; // Marquer comme bruit (noise)
                } else {
                    List<Integer> cluster = new ArrayList<>();
                    expandCluster(i, neighbors, cluster);
                    clusters.add(cluster);
                }
            }
        }

        return clusters;
    }

    private List<Integer> regionQuery(int dataIndex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (euclideanDistance(data[dataIndex], data[i]) <= eps) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    private void expandCluster(int dataIndex, List<Integer> neighbors, List<Integer> cluster) {
        labels[dataIndex] = clusters.size(); // Assigner le numéro de cluster actuel
        cluster.add(dataIndex); // Ajouter le point à ce cluster

        int index = 0;
        while (index < neighbors.size()) {
            int nextIndex = neighbors.get(index);
            if (!visited[nextIndex]) {
                visited[nextIndex] = true;
                List<Integer> nextNeighbors = regionQuery(nextIndex);
                if (nextNeighbors.size() >= minPts) {
                    neighbors.addAll(nextNeighbors);
                }
            }
            // Si le point n'a pas encore été affecté à un cluster
            if (labels[nextIndex] == 0) {
                labels[nextIndex] = clusters.size(); // Assigner le même numéro de cluster
                cluster.add(nextIndex); // Ajouter le point à ce cluster
            }
            index++;
        }
    }

    private double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public int[] getLabels() {
        return labels;
    }
}

