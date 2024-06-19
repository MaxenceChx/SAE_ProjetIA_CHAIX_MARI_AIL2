package Algorithmes.Clustering;

import java.util.ArrayList;
import java.util.List;

public class HAC {
    private double[][] data;
    private int[] labels;
    private List<Cluster> clusters;

    public HAC(double[][] data) {
        this.data = data;
        this.labels = new int[data.length];
        this.clusters = new ArrayList<>();

        // Initialisation : chaque point est un cluster individuel
        for (int i = 0; i < data.length; i++) {
            Cluster cluster = new Cluster(i);
            cluster.addPoint(i);
            clusters.add(cluster);
        }
    }

    public int[] clustering(int nClusters) {
        while (clusters.size() > nClusters) {
            // Trouver les clusters les plus proches
            int[] closestClusters = findClosestClusters();

            // Fusionner les deux clusters les plus proches
            mergeClusters(closestClusters[0], closestClusters[1]);
        }

        // Assigner les étiquettes de cluster finales aux points
        assignLabels();

        return labels;
    }

    private int[] findClosestClusters() {
        int[] closest = new int[]{-1, -1};
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster1 = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                Cluster cluster2 = clusters.get(j);
                double distance = cluster1.distanceTo(cluster2);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest[0] = i;
                    closest[1] = j;
                }
            }
        }

        return closest;
    }

    private void mergeClusters(int index1, int index2) {
        Cluster cluster1 = clusters.get(index1);
        Cluster cluster2 = clusters.get(index2);

        // Fusionner cluster2 dans cluster1
        cluster1.merge(cluster2);

        // Supprimer cluster2 de la liste des clusters
        clusters.remove(index2);
    }

    private void assignLabels() {
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            for (int pointIndex : cluster.getPoints()) {
                labels[pointIndex] = i;
            }
        }
    }

    private class Cluster {
        private List<Integer> points;

        public Cluster(int initialPoint) {
            this.points = new ArrayList<>();
        }

        public void addPoint(int pointIndex) {
            points.add(pointIndex);
        }

        public void merge(Cluster other) {
            points.addAll(other.points);
        }

        public List<Integer> getPoints() {
            return points;
        }

        public double distanceTo(Cluster other) {
            // Exemple de distance : distance moyenne entre les points des deux clusters
            double sumDistance = 0.0;
            for (int i : points) {
                for (int j : other.points) {
                    sumDistance += euclideanDistance(data[i], data[j]);
                }
            }
            return sumDistance / (points.size() * other.points.size());
        }

        private double euclideanDistance(double[] point1, double[] point2) {
            double sum = 0;
            for (int i = 0; i < point1.length; i++) {
                sum += Math.pow(point1[i] - point2[i], 2);
            }
            return Math.sqrt(sum);
        }
    }
}
