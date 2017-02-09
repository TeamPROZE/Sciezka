package Sciezka.game.entity;

import Sciezka.Config;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.ImageObserver;

/**
 * Klasa reprezentująca dynamiczny element na planszy gry.
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public abstract class Entity {

    /**
     * Współrzędna x (pozioma) położenia istoty w przestrzeni.
     */
    protected double x;

    /**
     * Współrzędna y (pionowa) położenia istoty w przestrzeni.
     */
    protected double y;

    /**
     * Obiekt zawierający dane o graficznej i przestrzennej
     * reprezentacji istoty.
     */
    protected Sprite sprite;

    /**
     * Obiekt będący obszarem zajętym przez istotę, z uwzględnieniem
     * jej położenia.
     */
    protected Area area;

    /**
     * Konstruktor istoty. Wymaga spółrzędnych, w których początkowo
     * będzie się ona znajdować.
     *
     * @param x         współrzędna x istoty
     * @param y         współrzędna y istoty
     * @param sprite    graficzna reprezentacja istoty na planszy
     */
    public Entity(double x, double y, Sprite sprite) {

        this.x = x;
        this.y = y;
        this.sprite = sprite;
        init();
    }

    /**
     * Metoda sprawdzająca, czy dwie podane istoty są ze sobą zderzone.
     * <p>
     * Dokonuje odjęcia pola drugiej figury od pierwszej i sprawdza, czy
     * uzyskana w ten sposób figura jest tożsama z pierwszą - zwraca wtedy
     * false. W przeciwnym wypadku zwraca true.
     *
     * @param ent1 pierwsza z istot do porównania
     * @param ent2 druga z istot do porównania
     * @return true jeżeli istoty są zderzone, false jeżeli nie
     */
    public static boolean areCollided(Entity ent1, Entity ent2) {

        Area inter = new Area(ent1.getEntityArea());
        inter.intersect(ent2.getEntityArea());
        return !inter.isEmpty();
    }

    /**
     * Medota inicjująca nietrywialne parametry obiektu po jego konstrukcji
     */
    private void init() {

        x += (Config.tileSize - sprite.getSize()) / 2;
        y += (Config.tileSize - sprite.getSize()) / 2;
        Area area = sprite.getArea();
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        this.area = new Area(at.createTransformedShape(area));
    }

    /**
     * Metoda rysująca istotę na ekranie gry
     *
     * @param g2d           Obiekt klasy Graphics2D rysujący pojazd na ekranie
     * @param screenWidth   Szerokość ekranu gry
     * @param screenHeight  Wysokość ekranu gry
     * @param observer      Obserwator Graphics2D
     */
    public void draw(Graphics2D g2d, int screenWidth, int screenHeight, ImageObserver observer) {

        g2d.drawImage(sprite.getBufferedImage(), (int) x, (int) y, observer);
    }

    /**
     * Getter do współrzędnej X
     *
     * @return współrzędna x kafelka
     */
    public double getX() {

        return x;
    }

    /**
     * Getter do współrzędnej Y
     *
     * @return współrzędna y kafelka
     */
    public double getY() {

        return y;
    }

    /**
     * Metoda zwracająca obszar w przestrzeni zajmowany przez istotę.
     *
     * @return obiekt typu Area reprezentujący obszar
     */
    public Area getEntityArea() {

        return area;
    }

}