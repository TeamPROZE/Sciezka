package Sciezka.game.entity.tile;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

/**
 * Element planszy odpowiadający za przejezdny teren
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class TileFloor extends Tile {

    /**
     * Konstruktor przyjmuje indeksowe współrzędne i tworzy się jako kafelek.
     * Wywoływany jest nadrzędny konstruktor, do którego dodawana jest konkretna
     * reprezentacja graficzna.
     *
     * @param tileX współrzędna indeksowa x
     * @param tileY współrzędna indeksowa y
     */
    public TileFloor(int tileX, int tileY) {

        super(tileX, tileY, new Sprite(GameConfig.filenamePathSprite, Config.tileSize));

        type = Type.ALLOWED;
    }

}
