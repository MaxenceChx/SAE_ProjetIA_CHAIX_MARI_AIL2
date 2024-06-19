package Algorithmes;

public interface AlgoClustering {
    /**
     * Applique l'algorithme de clustering sur les données fournies.
     *
     * @param data Un tableau 2D de taille (nombre d'objets, nombre de caractéristiques)
     *             qui contient la description des objets à classifier.
     * @param nbClusters Le nombre de clusters souhaités.
     * @return Un tableau 1D de taille (nombre d'objets) qui contient un numéro de cluster pour chaque objet.
     */
    int[] clustering(double[][] data, int nbClusters);
}
