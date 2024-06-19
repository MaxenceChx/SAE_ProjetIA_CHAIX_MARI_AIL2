package Algorithmes;

import Algorithmes.Clustering.Hac_;

public class HacClustering_ implements AlgoClustering {

    @Override
    public int[] clustering(double[][] data, int nClusters) {
        System.out.println("Début du clustering HAC");
        Hac_ hac = new Hac_(data, nClusters);
        int[] result = hac.fit();
        System.out.println("Clustering HAC terminé");
        return result;
    }
}
