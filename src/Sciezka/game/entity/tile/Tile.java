package Sciezka.game.entity.tile;

import Sciezka.Config;
import Sciezka.game.entity.Entity;
import Sciezka.game.sprite.Sprite;

/**
 * Klasa abstrakcyjna stanowiąca podstawę do klas będących
 * elementami planszy.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public abstract class Tile extends Entity {

    /**
     * Zmienna określająca typ pola
     * <p>
     * ALLOWED - można się po nim przemieszczać
     * BLOCKED - nie można się po nim przemieszczać
     */
    protected Type type;

    /**
     * Konstruktor przyjmuje indeksowe współrzędne kafelka i tworzy go
     * jako istotę na planszy w oparciu o zdefiniowane w konfiguracji
     * jego rozmiar i jego reprezentację graficzną
     * @param tileX współrzędna indeksowa x
     * @param tileY współrzędna indeksowa y
     * @param sprite reprezentacja graficzna kafelka
     */
    public Tile(int tileX, int tileY, Sprite sprite) {

        super(tileX * Config.tileSize, tileY * Config.tileSize, sprite);
    }

    /**
     * Getter do typu kafelka
     *
     * @return typ kafelka
     */
    public Type getType() {

        return type;
    }

    /**
     * Typ określający, czy dany element planszy stanowi przeszkodę, czy nie
     * <p>
     * ALLOWED - można się po nim przemieszczać
     * BLOCKED - nie można się po nim przemieszczać
     */
    public enum Type {
        ALLOWED, BLOCKED, FINISH
    }

}
