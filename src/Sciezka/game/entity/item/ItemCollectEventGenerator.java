package Sciezka.game.entity.item;

/**
 * Interfejs generatora zdarzeń związanych z zebraniem znajdźki.
 * Klasa implementująca musi posiadać metodę dodającą słuchacza, który
 * otrzyma rzucony Event.
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
interface ItemCollectEventGenerator {

    /**
     * Wirtualna metoda, która obsługuje dodanie słuchacza do generatora.
     *
     * @param listener instancja słuchacza Eventów
     */
    void addPowerupEventListener(ItemCollectEventListener listener);
}
