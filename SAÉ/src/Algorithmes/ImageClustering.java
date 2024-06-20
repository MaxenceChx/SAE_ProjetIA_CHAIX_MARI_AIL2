package Algorithmes;

import Algorithmes.ComparaisonsCouleurs.NormeCielab;
import Algorithmes.ComparaisonsCouleurs.NormeCouleurs;
import Pretraitement.Flou;
import Pretraitement.FlouGaussien;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageClustering {

    private static final int[][] BIOME_COLORS = {
        {71, 70, 61},  // Tundra
        {43, 50, 35},  // Taiga
        {59, 66, 43},  // Forêt tempérée
        {46, 64, 34},  // Forêt tropicale
        {84, 106, 70}, // Savane
        {104, 95, 82}, // Prairie
        {152, 140, 120}, // Désert
        {200, 200, 200}, // Glacier
        {49, 83, 100},  // Eau peu profonde
        {12, 31, 47},   // Eau profonde
    };

    private static final String[] BIOME_NAMES = {
        "Tundra",
        "Taiga",
        "Forêt tempérée",
        "Forêt tropicale",
        "Savane",
        "Prairie",
        "Désert",
        "Glacier",
        "Eau peu profonde",
        "Eau profonde"
    };

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

    private static int findClosestBiome(Color color, NormeCouleurs normeCouleurs) {
        double minDistance = Double.MAX_VALUE;
        int closestBiome = -1;

        for (int i = 0; i < BIOME_COLORS.length; i++) {
            Color biomeColor = new Color(BIOME_COLORS[i][0], BIOME_COLORS[i][1], BIOME_COLORS[i][2]);
            double distance = normeCouleurs.distanceCouleur(color, biomeColor);
            if (distance < minDistance) {
                minDistance = distance;
                closestBiome = i;
            }
        }
        return closestBiome;
    }

    public static BufferedImage createBrightenedImage(BufferedImage image, double percentage) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightenedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int newRed = (int)Math.round(red + percentage * (255 - red));
                int newGreen = (int)Math.round(green + percentage * (255 - green));
                int newBlue = (int)Math.round(blue + percentage * (255 - blue));

                int newRGB = (newRed << 16) | (newGreen << 8) | newBlue;
                brightenedImage.setRGB(x, y, newRGB);
            }
        }

        return brightenedImage;
    }

    public static void createBiomeImages(BufferedImage originalImage, BufferedImage brightenedImage, int[] labels, int nClusters, NormeCouleurs normeCouleurs) throws IOException {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int[] clusterSizes = new int[nClusters];
        int[][] clusterSums = new int[nClusters][3];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int clusterId = labels[index];
                int rgb = originalImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                clusterSizes[clusterId]++;
                clusterSums[clusterId][0] += red;
                clusterSums[clusterId][1] += green;
                clusterSums[clusterId][2] += blue;
                index++;
            }
        }

        int[] biomeAssignments = new int[nClusters];
        for (int i = 0; i < nClusters; i++) {
            if (clusterSizes[i] > 0) {
                int avgRed = clusterSums[i][0] / clusterSizes[i];
                int avgGreen = clusterSums[i][1] / clusterSizes[i];
                int avgBlue = clusterSums[i][2] / clusterSizes[i];
                Color avgColor = new Color(avgRed, avgGreen, avgBlue);
                biomeAssignments[i] = findClosestBiome(avgColor, normeCouleurs);
            }
        }

        boolean[] biomeTaken = new boolean[BIOME_COLORS.length];
        for (int i = 0; i < nClusters; i++) {
            if (clusterSizes[i] > 0) {
                int biomeId = biomeAssignments[i];
                if (biomeTaken[biomeId]) {
                    for (int j = i + 1; j < nClusters; j++) {
                        if (biomeAssignments[j] == biomeId && clusterSizes[j] > 0) {
                            clusterSizes[i] += clusterSizes[j];
                            for (int k = 0; k < 3; k++) {
                                clusterSums[i][k] += clusterSums[j][k];
                            }
                            clusterSizes[j] = 0;
                        }
                    }
                }
                biomeTaken[biomeId] = true;
            }
        }

        for (int i = 0; i < BIOME_COLORS.length; i++) {
            BufferedImage biomeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            boolean hasCluster = false;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int clusterId = labels[y * width + x];
                    if (clusterId >= 0 && clusterId < nClusters && biomeAssignments[clusterId] == i) {
                        int biomeColor = (BIOME_COLORS[i][0] << 16) | (BIOME_COLORS[i][1] << 8) | BIOME_COLORS[i][2];
                        biomeImage.setRGB(x, y, biomeColor);
                        hasCluster = true;
                    } else {
                        biomeImage.setRGB(x, y, brightenedImage.getRGB(x, y));
                    }
                }
            }

            if (!hasCluster) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        biomeImage.setRGB(x, y, brightenedImage.getRGB(x, y));
                    }
                }
            }

            String biomeName = BIOME_NAMES[i];
            File outputfile = new File("img/Algorithmes/Biomes/" + biomeName + ".jpg");
            ImageIO.write(biomeImage, "jpg", outputfile);
            System.out.println("Image de biome " + biomeName + " sauvegardée avec succès!");
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("img/Planete 1.jpg");
            BufferedImage image = ImageIO.read(file);

            Flou flouGaussien7_3 = new FlouGaussien(7, 3.0);
            BufferedImage flou = flouGaussien7_3.appliquer(image);

            double[][] featureArray = imageToFeatureArray(flou);

            int nClusters = 10;
            AlgoClustering algorithm = new KMeansClustering();
            int[] labels = algorithm.clustering(featureArray, nClusters);

            NormeCouleurs normeCouleurs = new NormeCielab();
            BufferedImage brightenedImage = createBrightenedImage(flou, 0.75);

            createBiomeImages(flou, brightenedImage, labels, nClusters, normeCouleurs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}