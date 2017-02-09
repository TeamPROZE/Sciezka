package Sciezka.game.entity.item;

import Sciezka.Config;
import Sciezka.GameConfig;
import Sciezka.game.sprite.Sprite;

/**
 * Element planszy typu bonus
 * <p>
 * Zebranie go jest premiowane punktami
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class ItemBonus extends Item {

    /**
     * Poziom nagrody za zebranie znajdźki
     */
    private BonusLevel bonusLevel;

    /**
     * Konstruktor obiektu Item. Umieszcza obiekt we współrzędnych
     * podanych jako parametry.
     *
     * @param x             współrzędna indeksowa x
     * @param y             współrzędna indeksowa y
     * @param bonusLevel    poziom wynagrodzenia gracza
     */
    public ItemBonus(int x, int y, BonusLevel bonusLevel) {

        /*
         * NIE PYTAJ NAWET XDD
         */
        super(x, y, ((bonusLevel == BonusLevel.LEVEL1) ? new Sprite(GameConfig.filenameBonus1Sprite, Config.bonus1Size) :
                ((bonusLevel == BonusLevel.LEVEL2) ? new Sprite(GameConfig.filenameBonus2Sprite, Config.bonus2Size) :
                        new Sprite(GameConfig.filenameBonus3Sprite, Config.bonus3Size))));

        this.bonusLevel = bonusLevel;
        this.type = ItemCollectEvent.ItemType.BONUS;
    }

    /**
     * Metoda zwracająca stopień wynagrodzenia gracza punktami.
     * Dopuszczalne wartości to LEVEL1, LEVEL2 i LEVEL3. Zdefiniowane
     * są w typie enumeracyjnym wewnątrz klasy.
     *
     * @return stopień nagrody w postaci wartości typu enumeracyjnego
     */
    public BonusLevel getBonusLevel() {

        return bonusLevel;
    }

    /**
     * Typ określający poziom nagrody za zebranie przedmiotu
     */
    public enum BonusLevel {
        LEVEL1, LEVEL2, LEVEL3
    }

}
