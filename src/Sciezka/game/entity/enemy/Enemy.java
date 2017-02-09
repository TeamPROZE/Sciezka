package Sciezka.game.entity.enemy;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.entity.Entity;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.ImageObserver;

import static Sciezka.game.entity.enemy.Enemy.MoveDirection.*;

/**
 * Klasa przeciwnika na planszy, czyli ruchomej przeszkody powodującej
 * uszkodzenie pojazdu gracza. Przemieszcza się w osi pionowej albo poziomej
 * między ścianami planszy, tj. jeżeli ją napotka, zmienia zwrot na przeciwny.
 * Porusza się ze stałą prędkością.
 */
public class Enemy extends Entity {

    /**
     * Prędkość animacji [kl/s]
     */
    protected int FPS;

    /**
     * Prędkość poruszania się przeciwnika
     */
    protected double speed;

    /**
     * Kierunek poruszania się przeciwnika
     */
    private MoveDirection moveDirection;

    /**
     * Konstruktor obiektu reprezentującego  przeciwnika
     *
     * @param startX        współrzędna x przeciwnika
     * @param startY        współrzędna y przeciwnika
     * @param movedirection kierunek poruszania się przeciwnika
     */
    public Enemy(double startX, double startY, MoveDirection movedirection) {

        super(startX * Config.tileSize, startY * Config.tileSize, new Sprite(GameConfig.filenameEnemySprite, Config.enemySize));

        // Inicjalizacja stałych
        this.moveDirection = movedirection;
        this.FPS = Config.FPS;
        this.speed = Config.enemySpeed;
    }

    /**
     * Metoda zwracająca obszar zajmowany w przestrzeni przez pojazd. Uwzględnia obrót pojazdu.
     *
     * @return obiekt typu Area reprezentujący obszar zajęty przez pojazd
     */
    public Area getEntityArea() {

        Shape tempShape;
        AffineTransform at = AffineTransform.getRotateInstance(0, sprite.getWidth() / 2, sprite.getHeight() / 2);
        tempShape = at.createTransformedShape(sprite.getArea());
        at = AffineTransform.getTranslateInstance(x, y);
        return new Area(at.createTransformedShape(tempShape));
    }

    /**
     * Metoda aktualizująca położenie przeciwnika
     */
    public void updateEnemy() {
        switch (moveDirection) {
            case RIGHT:
                x = x + speed / FPS;
                break;
            case LEFT:
                x = x - speed / FPS;
                break;
            case UP:
                y = y - speed / FPS;
                break;
            case DOWN:
                y = y + speed / FPS;
                break;
            default:
                break;
        }
    }

    /**
     * Metoda zmieniająca kierunek poruszania się przeciwnika
     */
    public void toggleMoveDirection() {
        switch (moveDirection) {
            case RIGHT:
                moveDirection = LEFT;
                break;
            case LEFT:
                moveDirection = RIGHT;
                break;
            case UP:
                moveDirection = DOWN;
                break;
            case DOWN:
                moveDirection = UP;
                break;
            default:
        }
    }

    /**
     * Typ określający kierunek poruszania się przeciwnika
     * <p>
     * UP       - kierunek w górę
     * DOWN     - kierunek w dół
     * LEFT     - kierunek w lewo
     * RIGHT    - kierunek w prawo
     */
    public enum MoveDirection {
        UP, DOWN, LEFT, RIGHT
    }

}
