package Sciezka.util;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * Utility class which contains a method to convert a raster BufferedImage
 * into an Area (for example for collision detection purposes) using GeneralPath
 * pixel-by-pixel drawing. It returns a shape outlining every not fully transparent
 * pixel in an image.
 *
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class ImageToArea {

    /**
     * Utility method that takes a buffered image and scans it pixel by pixel,
     * creating a GeneralPath along pixels that have full transparency, that is
     * their first byte is all zeros in ARGB (<code>0x00</code>). Then it converts
     * that path into an Area object, does a negative conversion and returns final
     * shape.
     * <p>
     * Inspired by a <a href="http://stackoverflow.com/questions/7218309/smoothing-a-jagged-path">Stack Overflow thread</a>
     *
     * @param bi BufferedImage object with ARGB color scheme to convert into Area
     * @return Area object of shape in the input BufferedImage
     */
    public static final Area createFromBufferedImage(BufferedImage bi) {

        GeneralPath gp = new GeneralPath();
        Boolean pass = true;

        gp.moveTo(0.0, 0.0);

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                if ((bi.getRGB(x, y) >>> 24) > 0) { // Check if a pixel isn't fully transparent (first byte is all zeros)
                    if (pass) {
                        gp.moveTo(x, y);
                    }
                    gp.lineTo(x, y);
                    gp.lineTo(x, y + 1);
                    gp.lineTo(x + 1, y + 1);
                    gp.lineTo(x + 1, y);
                    gp.lineTo(x, y);
                    pass = false;
                } else {
                    pass = true;
                }
            }
            pass = true;
        }

        gp.closePath();
        Area area = new Area(gp);

        return area;
    }
}
