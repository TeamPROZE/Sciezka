package Sciezka.game.entity;

import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Wariant wirtualnej klasy Entity służący do reprezentacji istot na planszy,
 * które są dynamiczne (przemieszczają się i mogą znikać).
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public abstract class EntityDynamic extends Entity {

    /**
     * Kąt obrotu dynamicznego obiektu.
     */
    protected double angle;

    /**
     * Pole zawierające obrazek po transformacji obrotu.
     */
    protected BufferedImage rotatedImage;

    /**
     * Pole zawierające kształt po transformacji obrotu.
     */
    protected Area rotatedArea;

    /**
     * Konstruktor jest tożsamy z konstruktorem klasy nadrzędnej,
     * toteż natychmiast wywoływany jest konstruktor tejże klasy.
     *
     * @param x współrzędna x istoty na planszy
     * @param y współrzędna x istoty na planszy
     * @param sprite obiekt-reprezentacja graficzna na planszy
     */
    public EntityDynamic(double x, double y, Sprite sprite) {

        super(x, y, sprite);
        this.angle = 0.0;
    }

    /**
     * Metoda zwracająca obszar (kształt) zajmowany przez obiekt.
     * Informacja ta służy między innymi do detekcji kolizji z innymi obiektami
     * na planszy.
     *
     * @return obszar zajmowany przez obiekt
     */
    public Area getEntityArea() {

        Shape tempShape;
        AffineTransform at = AffineTransform.getRotateInstance(-angle, sprite.getWidth() / 2, sprite.getHeight() / 2);
        tempShape = at.createTransformedShape(sprite.getArea());
        at = AffineTransform.getTranslateInstance(x, y);
        return new Area(at.createTransformedShape(tempShape));
    }

    /**
     * Obraca obrazek i obszar ze Sprite'a i wpisuje je do odpowiednich pól
     */
    protected void updateTransform() {

        AffineTransform at_image = AffineTransform.getRotateInstance(-angle, sprite.getWidth() / 2, sprite.getHeight() / 2);
        AffineTransformOp aop_image = new AffineTransformOp(at_image, AffineTransformOp.TYPE_BILINEAR);
        rotatedImage = aop_image.filter(sprite.getBufferedImage(), null);
    }

    /**
     * Metoda rysująca pojazd na ekranie
     *
     * @param g2d          Obiekt klasy Graphics2D rysujący pojazd na ekranie
     * @param screenWidth  Szerokość ekranu gry
     * @param screenHeight Wysokość ekranu gry
     */
    public void draw(Graphics2D g2d, int screenWidth, int screenHeight, ImageObserver observer) {

        g2d.drawImage(rotatedImage, (int) x, (int) y, observer);
    }

    /**
     * Getter do kąta obrotu istoty
     *
     * @return chwilowy kąt obrotu pojazdu
     */
    public double getAngle() {

        return angle;
    }

    /**
     * Metoda zmieniająca kąt obrotu istoty
     *
     * @param rot kąt obrotu istoty
     */
    public void setAngle(double rot) {

        this.angle = rot;
    }
}
