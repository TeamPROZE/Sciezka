package Sciezka.game.entity.item;

import Sciezka.Config;
import Sciezka.game.entity.Entity;
import Sciezka.game.sprite.Sprite;

/**
 * Klasa wirtualna będąca generalną reprezentacją znajdźki.
 * Dziedziczy po klasie Entity, dodając parametr collected świadczący o tym,
 * czy została ona już zebrana, oraz wirtualną metodę applyBonus służącą do
 * zaaplikowania bonusu do obiektu Car który potencjalnie może się z nim zetknąć.
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public abstract class Item extends Entity implements ItemCollectEventGenerator {

    /**
     * Słuchacz, do którego wysłany zostanie Event w razie zebrania znajdźki
     */
    protected ItemCollectEventListener listener;

    /**
     * Informacja o typie znajdźki dla słuchacza
     */
    protected ItemCollectEvent.ItemType type;

    /**
     * Konstruktor znajdźki. Wymaga podania współrzędnych x i y w macierzy kafelków
     * kafelka, na którym umieszczona zostanie ta znajdźka. Ponadto wymaga zaopatrzenia w
     * reprezentacyjny obrazek w postaci złożonego obiektu Sprite.
     *
     * @param x      współrzędna indeksowa x kafelka macierzystego
     * @param y      współrzędna indeksowa y kafelka macierzystego
     * @param sprite obiekt Sprite reprezentujący znajdźkę na planszy
     */
    public Item(int x, int y, Sprite sprite) {

        super(x * Config.tileSize, y * Config.tileSize, sprite);
    }

    /**
     * Metoda ustawia słuchacza, do którego zostanie wysłany Event w razie
     * zebrania znajdźki.
     *
     * @param listener instancja śłuchacza
     */
    public void addPowerupEventListener(ItemCollectEventListener listener) {

        this.listener = listener;
    }

    /**
     * Rzuca event dla listenera o zebraniu tej znajdźki, podając typ - POWERUP
     */
    public void fireItemCollectEvent() {

        listener.powerupCollected(new ItemCollectEvent(this, type));
    }

}
