package Sciezka.game.entity;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Reprezentuje pocisk wystrzeliwany przez pojazd. Po uderzeniu w przeciwnika niszczy go, a samemu znika.
 * Po uderzeniu w ściane znika.
 * Created by Kacper on 2017-01-27.
 */
public class Projectile extends EntityDynamic {

    /**
     * Prędkość animacji [kl/s]
     */
    private int FPS;

    /**
     * Prędkość poruszania się pocisku
     */
    private double speed;

    /**
     * Pole zawierające obrazek po transformacji obrotu.
     */
    private BufferedImage rotatedImage;

    /**
     * Konstruktor obiektu reprezentującego  przeciwnika
     *
     * @param startX    współrzędna x przeciwnika
     * @param startY    współrzędna y przeciwnika
     * @param angle     kąt pod jakim ma lecieć pocisk
     */
    public Projectile(double startX, double startY, double angle) {

        super(startX, startY, new Sprite(GameConfig.filenameProjectileSprite, Config.projectileSize));

        // Inicjalizacja stałych
        this.FPS = Config.FPS;
        this.speed = Config.projectileSpeed;
        this.angle = angle;

        updateTransform();

    }

    /**
     * Metoda aktualizuje położenie pocisku na planszy zgodnie z ustalonymi
     * prędkościami.
     */
    public void updateBullet() {

        x = x - (speed * Math.sin(angle)) / FPS;
        y = y - (speed * Math.cos(angle)) / FPS;
    }

}


