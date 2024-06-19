package Algorithmes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainHac {
    public static void main(String[] args) {
        try {
            System.out.println("try");
            // Charger l'image prétraitée (floutée)
            File file = new File("img/Pretraitement/Planete 1_flouteeGaussien7_3.jpg");
            BufferedImage image = ImageIO.read(file);
            System.out.println("Image chargée");

            // Conversion de l'image en tableau de caractéristiques
            int width = image.getWidth();
            int height = image.getHeight();
            double[][] featureArray = new double[width * height][3];
            System.out.println("Conversion de l'image en tableau de caractéristiques");

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
            System.out.println("Tableau de caractéristiques créé");

            // Appliquer l'algorithme de clustering HAC
            int nClusters = 6; // nombre de clusters souhaités
            AlgoClustering algorithm = new HacClustering_();
            int[] labels = algorithm.clustering(featureArray, nClusters);
            System.out.println("Clustering effectué");

            // Debugging: Vérifier les étiquettes des clusters
            System.out.println("Étiquettes des clusters: " + Arrays.toString(labels));

            // Créer une nouvelle image à partir des clusters avec couleurs moyennes
            BufferedImage clusteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            System.out.println("Création de l'image clusterisée");

            // Tableaux pour stocker la somme des couleurs et le nombre de pixels dans chaque cluster
            int[] clusterSizes = new int[nClusters];
            int[][] clusterSums = new int[nClusters][3]; // [0] -> sumRed, [1] -> sumGreen, [2] -> sumBlue

            // Calculer la somme des couleurs et le nombre de pixels pour chaque cluster
            index = 0;
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
            System.out.println("Sommes des couleurs calculées");

            // Debugging: Vérifier les tailles et les sommes des clusters
            System.out.println("Tailles des clusters: " + Arrays.toString(clusterSizes));
            System.out.println("Sommes des clusters: " + Arrays.deepToString(clusterSums));

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
            System.out.println("Couleurs moyennes appliquées");

            // Sauvegarder l'image clusterisée
            String outputFilePath = "img/Pretraitement/Planete1_flouteeGaussien7_3.jpg/HAC_6.jpg";
            File outputfile = new File(outputFilePath);

            // Créez les répertoires s'ils n'existent pas
            outputfile.getParentFile().mkdirs();

            // Sauvegarder l'image
            if (ImageIO.write(clusteredImage, "jpg", outputfile)) {
                System.out.println("Image clusterisée sauvegardée avec succès dans " + outputFilePath);
            } else {
                System.out.println("Échec de la sauvegarde de l'image clusterisée.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("try6");
    }
}
