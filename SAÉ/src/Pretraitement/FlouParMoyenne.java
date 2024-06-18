package Pretraitement;

import java.awt.image.BufferedImage;

public class FlouParMoyenne implements Flou {
    private final int taille;

    public FlouParMoyenne(int taille) {
        this.taille = taille;
    }

    @Override
    public BufferedImage appliquer(BufferedImage image) {
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        BufferedImage imageFloutee = new BufferedImage(largeur, hauteur, image.getType());

        int demiTaille = taille / 2;
        float coefficient = 1.0f / (taille * taille);

        for (int y = demiTaille; y < hauteur - demiTaille; y++) {
            for (int x = demiTaille; x < largeur - demiTaille; x++) {
                float sommeRouge = 0, sommeVert = 0, sommeBleu = 0;

                for (int dy = -demiTaille; dy <= demiTaille; dy++) {
                    for (int dx = -demiTaille; dx <= demiTaille; dx++) {
                        int pixel = image.getRGB(x + dx, y + dy);
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
}

