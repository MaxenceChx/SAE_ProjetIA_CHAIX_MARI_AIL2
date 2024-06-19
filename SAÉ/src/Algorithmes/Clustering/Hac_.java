package Algorithmes.Clustering;

import java.util.ArrayList;
import java.util.List;

public class Hac_ {
    private double[][] data;
    private int nClusters;

    public Hac_(double[][] data, int nClusters) {
        this.data = data;
        this.nClusters = nClusters;
    }

    public int[] fit() {
        long startTime = System.currentTimeMillis();
        System.out.println("Initialisation des clusters");

        int N = data.length;
        int[] labels = new int[N];
        List<List<Integer>> clusters = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            List<Integer> cluster = new ArrayList<>();
            cluster.add(i);
            clusters.add(cluster);
            labels[i] = i;
        }
        System.out.println("Initialisation des clusters terminée");

        int iteration = 0;
        while (clusters.size() > nClusters) {
            double minDistance = Double.MAX_VALUE;
            int mergeIdx1 = -1;
            int mergeIdx2 = -1;

            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double distance = calculateDistance(clusters.get(i), clusters.get(j));
                    if (distance < minDistance) {
                        minDistance = distance;
                        mergeIdx1 = i;
                        mergeIdx2 = j;
                    }
                }
            }

            clusters.get(mergeIdx1).addAll(clusters.get(mergeIdx2));
            clusters.remove(mergeIdx2);

            for (int i : clusters.get(mergeIdx1)) {
                labels[i] = mergeIdx1;
            }

            iteration++;
            if (iteration % 10 == 0) {
                System.out.println("Itération : " + iteration + ", Clusters restants : " + clusters.size());
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (endTime - startTime) + " ms");

        return labels;
    }

    private double calculateDistance(List<Integer> cluster1, List<Integer> cluster2) {
        double sum = 0.0;
        for (int i : cluster1) {
            for (int j : cluster2) {
                sum += euclideanDistance(data[i], data[j]);
            }
        }
        return sum / (cluster1.size() * cluster2.size());
    }

    private double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }
}
