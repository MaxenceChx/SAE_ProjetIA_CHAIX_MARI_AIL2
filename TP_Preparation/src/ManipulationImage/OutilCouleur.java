package ManipulationImage;

public class OutilCouleur {
    public static int[] getTabColor(int c) {
        int[] tab = new int[3];
        tab[0] = (c & 0xFF0000) >> 16;
        tab[1] = (c & 0xFF00) >> 8;
        tab[2] = c & 0xFF;

        return tab;
    }

    public static int getBW(int[] tab) {
        int g = (tab[0] + tab[1] + tab[2]) / 3;
        return (g << 16) + (g << 8) + g;
    }
}
