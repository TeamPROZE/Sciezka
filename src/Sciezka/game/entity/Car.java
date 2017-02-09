package Sciezka.game.entity;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.Player;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Reprezentacja gracza na planszy
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Car extends EntityDynamic {

    /**
     * Referencja do obiektu Player, w którym przechowywane są informacje o wyniku
     */
    private Player player;

    /**
     * Określa kierunek obrotu pojazdu w najbliższym renderowaniu
     */
    private RotDirection rotDirection;

    /**
     * Określa stan przyśpieszania w następnym renderowaniu
     */
    private AccelState accelState;

    /**
     * Pole definiujące czy pojazd jest ruchomy czy nie
     */
    private boolean controllable;

    /**
     * Maksymalna prędkość pojazdu
     */
    private double maxSpeed;

    /**
     * Prędkość obrotu pojazdu
     */
    private double rotSpeed;

    /**
     * Chwilowa prędkość pojazdu.
     */
    private double speed;

    /**
     * Prędkość animacji [kl/s]
     */
    private int FPS;

    /**
     * Konstruktor obiektu reprezentującego pojazd gracza.
     * <p>
     * Przyjmuje współrzędne na których inicjalnie zostanie umieszczony pojazd.
     *
     * @param startX Współrzędna początkowa X pojazdu
     * @param startY Współrzędna początkowa Y pojazdu
     * @param player Obiekt reprezentujący gracza podczas sesji gry
     */
    public Car(double startX, double startY, Player player) {

        super(startX, startY, new Sprite(GameConfig.filenameCarSprite, Config.carSize));

        // Inicjalizacja stałych
        this.FPS = Config.FPS;
        this.maxSpeed = Config.vehMaxSpeed;
        this.rotSpeed = Config.vehRotSpeed;
        // Inicjalizacja zmiennych
        initCar();
    }

    /**
     * Ustawia podstawowe wartości pól pojazdu, takie jak zerowe prędkości początkowe
     */
    private void initCar() {

        this.controllable = true;
        this.angle = 0.0;
        this.speed = 0.0;
        this.accelState = AccelState.NONE;
        this.rotDirection = RotDirection.NONE;
        updateTransform();
    }



    /**
     * Ustawia stan kierunku obrotu pojazdu
     *
     * @param rotDirection kierunek obrotu pojazdu, zgodnie z wewnętrznym typem
     */
    public void setRotDirection(RotDirection rotDirection) {

        this.rotDirection = rotDirection;
    }

    /**
     * Ustawia stan zmiany prędkości pojazdu
     *
     * @param accelState kierunek zmiany prędkości pojazdu, zgodnie z wewnętrznym typem
     */
    public void setAccelState(AccelState accelState) {

        this.accelState = accelState;
    }

    /**
     * Aktualizuje połozenie i obrót pojazdu na podstawie posiadanych danych
     */
    public void updateCar() {

        Boolean hasBeenRotated = false;

        switch (rotDirection) {
            case LEFT:
                angle += Math.toRadians(rotSpeed / FPS);
                hasBeenRotated = true;
                break;
            case RIGHT:
                angle -= Math.toRadians(rotSpeed / FPS);
                hasBeenRotated = true;
                break;
            case NONE:
            default:
        }

        switch (accelState) {
            case ACCEL:
                if (speed < maxSpeed) {
                    speed += (int) ((Config.vehAccel * (1 + 2 * (maxSpeed - speed) / maxSpeed)) / FPS);
                } else if (speed >= maxSpeed) {
                    speed = maxSpeed;
                }
                break;
            case BRAKE:
                if (speed > 0) {
                    speed -= (int) (Config.vehDecel / FPS);
                }
                if (speed <= 0) {
                    speed = 0;
                }
                break;
            case NONE:
            default:
                if (speed > 0) {
                    speed -= (int) (Config.vehDecelCoast / FPS);
                }
                if (speed <= 0) {
                    speed = 0;
                }
        }

        x = x - (speed * Math.sin(angle)) / FPS;
        y = y - (speed * Math.cos(angle)) / FPS;

        updateTransform();
    }

    /**
     * Zmienia stan kontroli pojazdu (ruchomy/nieruchomy)
     */
    public void toggleControllable() {

        controllable = !controllable;
    }

    /**
     * getter do prędkości pojazdu
     *
     * @return prędkość pojazdu
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Ustawia maksymalną prędkość pojazdu na podaną w parametrze.
     *
     * @param maxSpeed nowa maksymalna prędkość pojazdu
     */
    public void setMaxSpeed(double maxSpeed) {

        this.maxSpeed = maxSpeed;
    }

    /**
     * Ustawia prędkość obrotu pojazdu na podaną w parametrze.
     *
     * @param rotSpeed nowa prędkość obrotu
     */
    public void setRotSpeed(double rotSpeed) {

        this.rotSpeed = rotSpeed;
    }

    /**
     * Metoda zmieniająca położenie samochodu
     *
     * @param x współrzędna X
     * @param y współrzędna Y
     */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Przywraca pojazd do stanu początkowego
     */
    public void reset() {

        this.initCar();
    }

    /**
     * Metoda tworząca wystrzeliwany przez pojazd pocisk
     *
     * @return nowa instancja Projectile
     */
    public Projectile createProjectile() {

        return new Projectile(this.getX(), this.getY(), this.angle);
    }

    /**
     * Typ określający kierunek obrotu
     * <p>
     * LEFT     - obrót przeciwnie do ruchu wskazówek zegara
     * RIGHT    - obrót zgodnie z ruchem wskazówek zegara
     * NONE     - brak obrotu
     */
    public enum RotDirection {
        LEFT, RIGHT, NONE
    }

    /**
     * Typ określający zmianę prędkości pojazdu
     * <p>
     * ACCEL    - samochód przyspiesza
     * BRAKE    - samochód hamuje
     * NONE     - brak zmiany prędkości
     */
    public enum AccelState {
        ACCEL, BRAKE, NONE
    }
}
