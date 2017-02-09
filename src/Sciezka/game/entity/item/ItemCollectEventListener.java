package Sciezka.game.entity.item;

import java.util.EventListener;

/**
 * Interfejs słuchacza Eventów związanych z zebraniem znajdźki.
 * Okresla, że wymagana jest obsługa zdarzenia w zależności od
 * typu znajdźki
 *
 * @author Kamil Dzierżanowski
 * @author Kacper Bloch
 * @version 1.0
 */
public interface ItemCollectEventListener extends EventListener {

    /**
     * Wirtualna metoda obsługująca otrzymane zdarzenie zebrania
     * znajdźki.
     *
     * @param e otrzymane zdarzenie
     */
    void powerupCollected(ItemCollectEvent e);
}
