package Sciezka.game.sprite;

import Sciezka.util.ImageToArea;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * Klasa przechowująca informację o graficznej reprezentacji jakiegoś obiektu
 * na planszy w grze. Zawiera surowe dane o obrazku który ma wyświetlać oraz
 * niezbędne metody i dodatkowe informacje aby narysować ten obrazek na
 * obszarze Graphics2D.
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public class Sprite {

    /**
     * Obrazek w postaci klasy Image
     */
    private BufferedImage buf_image;

    /**
     * Kształt zawierający nieprzezroczyste piksele obrazka
     */
    private Area area;

    /**
     * Szerokość obrazka podana w pikselach
     */
    private int size;

    /**
     * Konstruktor klasy Sprite. Wymaga ścieżki do pliku z obrazkiem który
     * będzie reprezentował obiekt.
     *
     * @param imageFilePath nazwa bądź ścieżka do pliku z obrazkiem
     * @param size          rozmiar sprite'a
     */
    public Sprite(String imageFilePath, int size) {

        this.size = size;

        ImageIcon ii = new ImageIcon(imageFilePath);

        if (ii == null) {
            System.out.println("Could not load sprite at \"" + imageFilePath + "\"");
            return;
        }

        // Snippet przeskalowujący obrazek do wymiarów 'size' za pomocą wrysowania go w BufferedImage
        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bi_g2d = (Graphics2D) bi.createGraphics();
        bi_g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        bi_g2d.drawImage(ii.getImage(), 0, 0, size, size, null);

        this.buf_image = bi;

        area = ImageToArea.createFromBufferedImage(bi);
    }

    /**
     * Zamienia obiekt typu Image na obiekt typu BufferedImage
     *
     * @param img Obiekt Image do konwersji
     * @return Wynikowy BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /**
     * Getter do pola zawierającego obrazek
     *
     * @return Obrazek jako klasa Image
     */
    public BufferedImage getBufferedImage() {

        return buf_image;
    }

    /**
     * Getter do obszaru obrazka (obiekt typu Area)
     *
     * @return obiekt Area reprezentujący kształt w obrazku
     */
    public Area getArea() {

        return area;
    }

    /**
     * Metoda zwracająca szerokość sprite'a
     *
     * @return szerokość sprite'a
     */
    public int getWidth() {

        return size;
    }

    /**
     * Metoda zwracająca wysokość sprite'a
     *
     * @return wysokość sprite'a
     */
    public int getHeight() {

        return size;
    }

    /**
     * Metoda zwracająca wymiar sprite'a
     *
     * @return wymiar sprite'a
     */
    public int getSize() {

        return size;
    }
}
