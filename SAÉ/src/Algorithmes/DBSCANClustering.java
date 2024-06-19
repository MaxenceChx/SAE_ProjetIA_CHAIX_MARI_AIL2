package Algorithmes;

import Algorithmes.Clustering.DBSCAN;

public class DBSCANClustering implements AlgoClustering{
    @Override
    public int[] clustering(double[][] data, int nbClusters) {
        DBSCAN dbscan = new DBSCAN(0.5, 5);
        return dbscan.fit(data, nbClusters);
    }
}
