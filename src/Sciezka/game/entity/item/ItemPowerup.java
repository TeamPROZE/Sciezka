package Sciezka.game.entity.item;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

/**
 * Element planszy typu power-up
 * <p>
 * Zebranie go powoduje otrzymanie przez gracza bonusu
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class ItemPowerup extends Item {

    /**
     * Czas trwania wzmocnienia gracza [ms]
     * Pobierany jest z pliku konfiguracyjnego podczas inicjalizacji obiektu.
     */
    long powerupDuration;

    /**
     * Konstruktor znajdźki wzmacniającej gracza.
     * Wymaga współrzędne indeksowe Tile'a na planszy na którym zostanie
     * umieszczona. Pobiera z danych konfiguracyjnych czas trwania bonusowego
     * efektu.
     *
     * @param x współrzędna indeksowa x Tile'a macierzystego
     * @param y współrzędna indeksowa y Tile'a macierzystego
     */
    public ItemPowerup(int x, int y) {

        super(x, y, new Sprite(GameConfig.filenamePowerupSprite, Config.powerupSize));
        this.powerupDuration = Config.powerupDuration;
        this.type = ItemCollectEvent.ItemType.POWERUP;
    }

    /**
     * Metoda zwracająca czas w milisekundach trwania bonusowego efektu
     * po zebraniu znajdźki.
     *
     * @return czas w milisekundach
     */
    public long getPowerupDuration() {

        return powerupDuration;
    }

}
