package Sciezka.game.entity.item;

import java.util.EventObject;

/**
 * Klasa zdarzenia rzucanego przez znajdźki podczas ich zebrania
 * przez gracza. Oprócz standardowej informacji o obiekcie który
 * rzucił zdarzenie, posiada również informację o typie znajdźki.
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public class ItemCollectEvent extends EventObject {

    /**
     * Informacja o typie znajdźki, która rzuciła Event
     */
    private ItemType itemtype;

    /**
     * Konstruktor przyjmuje znajdźkę która go rzuca i jej rodzaj.
     *
     * @param item obiekt znajdźki
     * @param itemtype typ znajdźki
     */
    public ItemCollectEvent(Item item, ItemType itemtype) {

        super(item);
        this.itemtype = itemtype;
    }

    /**
     * Metoda zwraca typ znajdźki, która rzuciła Event.
     *
     * @return typ znajdźki
     */
    public ItemType getItemType() {

        return itemtype;
    }

    /**
     * Typ enumeracyjny rozróżniający rodzaje znajdxiek.
     */
    public enum ItemType {
        BONUS, POWERUP
    }
}
