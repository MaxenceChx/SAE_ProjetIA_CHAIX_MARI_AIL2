package ComparaisonsCouleurs;

import java.awt.*;

public class Palette {
    final static Color[] COLORS = {
        new Color(128, 0, 0),    // maroon
        new Color(139, 0, 0),    // dark red
        new Color(165, 42, 42),  // brown
        new Color(178, 34, 34),  // firebrick
        new Color(220, 20, 60),  // crimson
        new Color(255, 0, 0),    // red
        new Color(255, 99, 71),  // tomato
        new Color(255, 127, 80), // coral
        new Color(205, 92, 92),  // indian red
        new Color(240, 128, 128),// light coral
        new Color(233, 150, 122),// dark salmon
        new Color(250, 128, 114),// salmon
        new Color(255, 160, 122),// light salmon
        new Color(255, 69, 0),   // orange red
        new Color(255, 140, 0),  // dark orange
        new Color(255, 165, 0),  // orange
        new Color(255, 215, 0),  // gold
        new Color(184, 134, 11), // dark golden rod
        new Color(218, 165, 32), // golden rod
        new Color(238, 232, 170),// pale golden rod
        new Color(189, 183, 107),// dark khaki
        new Color(240, 230, 140),// khaki
        new Color(128, 128, 0),  // olive
        new Color(255, 255, 0),  // yellow
        new Color(154, 205, 50), // yellow green
        new Color(85, 107, 47),  // dark olive green
        new Color(107, 142, 35), // olive drab
        new Color(124, 252, 0),  // lawn green
        new Color(127, 255, 0),  // chartreuse
        new Color(173, 255, 47), // green yellow
        new Color(0, 100, 0),    // dark green
        new Color(0, 128, 0),    // green
        new Color(34, 139, 34),  // forest green
        new Color(0, 255, 0),    // lime
        new Color(50, 205, 50),  // lime green
        new Color(144, 238, 144),// light green
        new Color(152, 251, 152),// pale green
        new Color(143, 188, 143),// dark sea green
        new Color(0, 250, 154),  // medium spring green
        new Color(0, 255, 127),  // spring green
        new Color(46, 139, 87),  // sea green
        new Color(102, 205, 170),// medium aqua marine
        new Color(60, 179, 113), // medium sea green
        new Color(32, 178, 170), // light sea green
        new Color(47, 79, 79),   // dark slate gray
        new Color(0, 128, 128),  // teal
        new Color(0, 139, 139),  // dark cyan
        new Color(0, 255, 255),  // aqua
        new Color(0, 255, 255),  // cyan
        new Color(224, 255, 255),// light cyan
        new Color(0, 206, 209),  // dark turquoise
        new Color(64, 224, 208), // turquoise
        new Color(72, 209, 204), // medium turquoise
        new Color(175, 238, 238),// pale turquoise
        new Color(127, 255, 212),// aqua marine
        new Color(176, 224, 230),// powder blue
        new Color(95, 158, 160), // cadet blue
        new Color(70, 130, 180), // steel blue
        new Color(100, 149, 237),// corn flower blue
        new Color(0, 191, 255),  // deep sky blue
        new Color(30, 144, 255), // dodger blue
        new Color(173, 216, 230),// light blue
        new Color(135, 206, 235),// sky blue
        new Color(135, 206, 250),// light sky blue
        new Color(25, 25, 112),  // midnight blue
        new Color(0, 0, 128),    // navy
        new Color(0, 0, 139),    // dark blue
        new Color(0, 0, 205),    // medium blue
        new Color(0, 0, 255),    // blue
        new Color(65, 105, 225), // royal blue
        new Color(138, 43, 226), // blue violet
        new Color(75, 0, 130),   // indigo
        new Color(72, 61, 139),  // dark slate blue
        new Color(106, 90, 205), // slate blue
        new Color(123, 104, 238),// medium slate blue
        new Color(147, 112, 219),// medium purple
        new Color(139, 0, 139),  // dark magenta
        new Color(148, 0, 211),  // dark violet
        new Color(153, 50, 204), // dark orchid
        new Color(186, 85, 211), // medium orchid
        new Color(128, 0, 128),  // purple
        new Color(216, 191, 216),// thistle
        new Color(221, 160, 221),// plum
        new Color(238, 130, 238),// violet
        new Color(255, 0, 255),  // magenta / fuchsia
        new Color(218, 112, 214),// orchid
        new Color(199, 21, 133), // medium violet red
        new Color(219, 112, 147),// pale violet red
        new Color(255, 20, 147), // deep pink
        new Color(255, 105, 180),// hot pink
        new Color(255, 182, 193),// light pink
        new Color(255, 192, 203),// pink
        new Color(250, 235, 215),// antique white
        new Color(245, 245, 220),// beige
        new Color(255, 228, 196),// bisque
        new Color(255, 235, 205),// blanched almond
        new Color(245, 222, 179),// wheat
        new Color(255, 248, 220),// corn silk
        new Color(255, 250, 205),// lemon chiffon
        new Color(250, 250, 210),// light golden rod yellow
        new Color(255, 255, 224),// light yellow
        new Color(139, 69, 19),  // saddle brown
        new Color(160, 82, 45),  // sienna
        new Color(210, 105, 30), // chocolate
        new Color(205, 133, 63), // peru
        new Color(244, 164, 96), // sandy brown
        new Color(222, 184, 135),// burly wood
        new Color(210, 180, 140),// tan
        new Color(188, 143, 143),// rosy brown
        new Color(255, 228, 181),// moccasin
        new Color(255, 222, 173),// navajo white
        new Color(255, 218, 185),// peach puff
        new Color(255, 228, 225),// misty rose
        new Color(255, 240, 245),// lavender blush
        new Color(250, 240, 230),// linen
        new Color(253, 245, 230),// old lace
        new Color(255, 239, 213),// papaya whip
        new Color(255, 245, 238),// sea shell
        new Color(245, 255, 250),// mint cream
        new Color(112, 128, 144),// slate gray
        new Color(119, 136, 153),// light slate gray
        new Color(176, 196, 222),// light steel blue
        new Color(230, 230, 250),// lavender
        new Color(255, 250, 240),// floral white
        new Color(240, 248, 255),// alice blue
        new Color(248, 248, 255),// ghost white
        new Color(240, 255, 240),// honeydew
        new Color(255, 255, 240),// ivory
        new Color(240, 255, 255),// azure
        new Color(255, 250, 250),// snow
        new Color(0, 0, 0),      // black
        new Color(105, 105, 105),// dim gray / dim grey
        new Color(128, 128, 128),// gray / grey
        new Color(169, 169, 169),// dark gray / dark grey
        new Color(192, 192, 192),// silver
        new Color(211, 211, 211),// light gray / light grey
        new Color(220, 220, 220),// gainsboro
        new Color(245, 245, 245),// white smoke
        new Color(255, 255, 255) // white
    };


    public static Color getNearestColor(Color color, NormeCouleurs distanceCouleur) {
        double minDistance = Double.MAX_VALUE;
        Color nearest = null;

        for (Color c : COLORS) {
            double distance = distanceCouleur.distanceCouleur(color, c);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = c;
            }
        }

        return nearest;
    }

    private static int getDistance(Color c1, Color c2) {
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        return r * r + g * g + b * b;
    }
}
