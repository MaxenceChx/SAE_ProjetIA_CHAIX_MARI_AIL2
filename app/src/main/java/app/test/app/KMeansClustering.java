package app.test.app;

public class KMeansClustering implements AlgoClustering {

    @Override
    public int[] clustering(double[][] data, int nClusters) {
        KMeans kmeans = new KMeans(data, nClusters);
        return kmeans.fit();
    }
}


