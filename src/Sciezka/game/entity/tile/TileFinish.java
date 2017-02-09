package Sciezka.game.entity.tile;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Element planszy typu meta.
 * <p>
 * Zderzenie z nim powoduje pomyślne ukończenie poziomu gry.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class TileFinish extends Tile {

    /**
     * Konstruktor przyjmuje indeksowe współrzędne i tworzy się jako kafelek.
     * Wywoływany jest nadrzędny konstruktor, do którego dodawana jest konkretna
     * reprezentacja graficzna.
     *
     * @param tileX współrzędna indeksowa x
     * @param tileY współrzędna indeksowa y
     */
    public TileFinish(int tileX, int tileY) {

        super(tileX, tileY, new Sprite(GameConfig.filenameFinishSprite, Config.tileSize));

        type = Type.FINISH;
    }

}
