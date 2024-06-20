package app.test.app;

import java.awt.image.BufferedImage;

public class FlouGaussien implements Flou {
    private final int taille;
    private final double sigma;

    public FlouGaussien(int taille, double sigma) {
        this.taille = taille;
        this.sigma = sigma;
    }

    @Override
    public BufferedImage appliquer(BufferedImage image) {
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        BufferedImage imageFloutee = new BufferedImage(largeur, hauteur, image.getType());

        int demiTaille = taille / 2;
        double[][] noyau = creerNoyauGaussien(taille, sigma);

        for (int y = demiTaille; y < hauteur - demiTaille; y++) {
            for (int x = demiTaille; x < largeur - demiTaille; x++) {
                double sommeRouge = 0, sommeVert = 0, sommeBleu = 0;

                for (int dy = -demiTaille; dy <= demiTaille; dy++) {
                    for (int dx = -demiTaille; dx <= demiTaille; dx++) {
                        int pixel = image.getRGB(x + dx, y + dy);
                        double coefficient = noyau[dy + demiTaille][dx + demiTaille];
                        sommeRouge += ((pixel >> 16) & 0xff) * coefficient;
                        sommeVert += ((pixel >> 8) & 0xff) * coefficient;
                        sommeBleu += (pixel & 0xff) * coefficient;
                    }
                }

                int nouvelleCouleur = ((int) sommeRouge << 16) | ((int) sommeVert << 8) | (int) sommeBleu;
                imageFloutee.setRGB(x, y, nouvelleCouleur);
            }
        }

        return imageFloutee;
    }

    private double[][] creerNoyauGaussien(int taille, double sigma) {
        int demiTaille = taille / 2;
        double[][] noyau = new double[taille][taille];
        double somme = 0;

        for (int y = -demiTaille; y <= demiTaille; y++) {
            for (int x = -demiTaille; x <= demiTaille; x++) {
                noyau[y + demiTaille][x + demiTaille] = (1 / (2 * Math.PI * sigma * sigma)) * Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
                somme += noyau[y + demiTaille][x + demiTaille];
            }
        }

        for (int y = 0; y < taille; y++) {
            for (int x = 0; x < taille; x++) {
                noyau[y][x] /= somme;
            }
        }

        return noyau;
    }
}