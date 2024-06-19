package Algorithmes;

import Algorithmes.Clustering.HAC;

public class HACClustering implements AlgoClustering {

    @Override
    public int[] clustering(double[][] data, int nClusters) {
        HAC hac = new HAC(data);
        return hac.clustering(nClusters);
    }
}

