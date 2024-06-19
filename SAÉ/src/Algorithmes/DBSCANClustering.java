package Algorithmes;

import Algorithmes.Clustering.DBSCAN;

public class DBSCANClustering implements AlgoClustering {

    @Override
    public int[] clustering(double[][] data, int nClusters) {
        // Paramètres spécifiques à DBSCAN
        double eps = 0.5; // Exemple : rayon epsilon
        int minPts = 5; // Exemple : nombre minimum de points dans un voisinage pour former un cluster

        DBSCAN dbscan = new DBSCAN(data, eps, minPts);
        dbscan.fit(); // Exécute DBSCAN pour obtenir les clusters

        // Obtient les étiquettes de cluster attribuées par DBSCAN
        int[] labels = dbscan.getLabels();

        // Comme DBSCAN ne prédéfinit pas le nombre de clusters, nClusters n'est pas utilisé ici
        return labels;
    }
}

