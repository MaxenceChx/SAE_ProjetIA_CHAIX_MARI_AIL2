package Algorithmes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Clustering {
    public static void main(String[] args) throws IOException {
        // Lire l'image
        BufferedImage originalImage = ImageIO.read(new File("SAÉ/img/Planete 1.jpg"));

        // Définir la région d'intérêt (ROI)
        int roiX = 100; // Position X de la ROI
        int roiY = 100; // Position Y de la ROI
        int roiWidth = 200; // Largeur de la ROI
        int roiHeight = 200; // Hauteur de la ROI

        // Extraire la ROI
        BufferedImage roiImage = originalImage.getSubimage(roiX, roiY, roiWidth, roiHeight);

        // Convertir la ROI en tableau de caractéristiques
        double[][] data = imageToFeatureArray(roiImage);

        // Appliquer DBSCAN
        DBSCAN dbscan = new DBSCAN(10.0, 4);
        int[] clusters = dbscan.fit(data, 4);

        // Calculer la couleur moyenne de chaque cluster
        Map<Integer, double[]> clusterColors = new HashMap<>();
        Map<Integer, Integer> clusterCounts = new HashMap<>();

        for (int i = 0; i < clusters.length; i++) {
            int cluster = clusters[i];
            clusterColors.putIfAbsent(cluster, new double[]{0, 0, 0});
            clusterCounts.putIfAbsent(cluster, 0);

            double[] colorSum = clusterColors.get(cluster);
            colorSum[0] += data[i][0];
            colorSum[1] += data[i][1];
            colorSum[2] += data[i][2];

            clusterColors.put(cluster, colorSum);
            clusterCounts.put(cluster, clusterCounts.get(cluster) + 1);
        }

        for (Map.Entry<Integer, double[]> entry : clusterColors.entrySet()) {
            int cluster = entry.getKey();
            double[] colorSum = entry.getValue();
            int count = clusterCounts.get(cluster);

            if (count > 0) {
                colorSum[0] /= count;
                colorSum[1] /= count;
                colorSum[2] /= count;
            }
        }

        // Remplacer chaque pixel par la couleur moyenne de son cluster dans la ROI
        BufferedImage outputImage = new BufferedImage(roiImage.getWidth(), roiImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < roiImage.getHeight(); y++) {
            for (int x = 0; x < roiImage.getWidth(); x++) {
                int cluster = clusters[y * roiImage.getWidth() + x];
                double[] avgColor = clusterColors.get(cluster);
                int rgb = ((int) avgColor[0] << 16) | ((int) avgColor[1] << 8) | ((int) avgColor[2]);
                outputImage.setRGB(x, y, rgb);
            }
        }

        // Enregistrer l'image de sortie
        ImageIO.write(outputImage, "jpg", new File("SAÉ/img/Algorithmes/Planete 1_clustered.jpg"));
    }

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
}
