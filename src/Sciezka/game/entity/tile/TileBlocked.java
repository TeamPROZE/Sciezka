package Sciezka.game.entity.tile;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Element planszy typu ściana.
 * <p>
 * Zderzenie z nim powoduje utratę punktu życia.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class TileBlocked extends Tile {

    /**
     * Konstruktor przyjmuje indeksowe współrzędne i tworzy się jako kafelek.
     * Wywoływany jest nadrzędny konstruktor, do którego dodawana jest konkretna
     * reprezentacja graficzna.
     *
     * @param tileX współrzędna indeksowa x
     * @param tileY współrzędna indeksowa y
     */
    public TileBlocked(int tileX, int tileY) {

        super(tileX, tileY, new Sprite(GameConfig.filenameWallSprite, Config.tileSize));

        type = Type.BLOCKED;
    }

}
