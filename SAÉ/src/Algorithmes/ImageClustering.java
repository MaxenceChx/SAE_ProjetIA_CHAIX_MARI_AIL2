package Algorithmes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageClustering {

    public static double[][] imageToFeatureArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] featureArray = new double[width * height][3];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                featureArray[index][0] = red;
                featureArray[index][1] = green;
                featureArray[index][2] = blue;
                index++;
            }
        }
        return featureArray;
    }

    public static BufferedImage createClusteredImage(BufferedImage image, int[] labels, int nClusters) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage clusteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Tableaux pour stocker la somme des couleurs et le nombre de pixels dans chaque cluster
        int[] clusterSizes = new int[nClusters];
        int[][] clusterSums = new int[nClusters][3]; // [0] -> sumRed, [1] -> sumGreen, [2] -> sumBlue

        // Calculer la somme des couleurs et le nombre de pixels pour chaque cluster
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (index < labels.length) {
                    int clusterId = labels[index];
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    clusterSizes[clusterId]++;
                    clusterSums[clusterId][0] += red;
                    clusterSums[clusterId][1] += green;
                    clusterSums[clusterId][2] += blue;
                }
                index++;
            }
        }

        // Calculer la couleur moyenne pour chaque cluster et l'appliquer à l'image clusterisée
        for (int i = 0; i < nClusters; i++) {
            if (clusterSizes[i] > 0) {
                int avgRed = clusterSums[i][0] / clusterSizes[i];
                int avgGreen = clusterSums[i][1] / clusterSizes[i];
                int avgBlue = clusterSums[i][2] / clusterSizes[i];
                int clusterColor = (avgRed << 16) | (avgGreen << 8) | avgBlue;

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (labels[y * width + x] == i) {
                            clusteredImage.setRGB(x, y, clusterColor);
                        }
                    }
                }
            }
        }

        return clusteredImage;
    }

    public static void main(String[] args) {
        try {
            // Charger l'image
            File file = new File("img/Pretraitement/Planete 1_flouteeGaussien7_3.jpg");
            BufferedImage image = ImageIO.read(file);

            // Conversion de l'image en tableau de caractéristiques
            double[][] featureArray = imageToFeatureArray(image);

            // Appliquer l'algorithme de clustering KMeans
            int nClusters = 6; // nombre de clusters souhaités
            AlgoClustering algorithm = new KMeansClustering();
            int[] labels = algorithm.clustering(featureArray, nClusters);

            // Créer une nouvelle image à partir des clusters avec couleurs moyennes
            BufferedImage clusteredImage = createClusteredImage(image, labels, nClusters);

            // Sauvegarder l'image clusterisée
            File outputfile = new File("SAÉ/img/Algorithmes/KMeans_6.jpg");
            ImageIO.write(clusteredImage, "jpg", outputfile);

            System.out.println("Image clusterisée sauvegardée avec succès!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}