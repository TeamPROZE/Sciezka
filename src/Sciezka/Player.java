package Sciezka;

/**
 * Klasa reprezentująca gracza.
 * Zawiera informacje o: nicku, wyniku, pozostałych życiach.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Player extends Score {

    /**
     * Informacja o pozostałych życiach (szansach)
     */
    private int lives;

    /**
     * Liczba ukończonych poziomów
     */
    private int numberOfPassedLevels;

    /**
     * Konstruktor obiektu Player
     *
     * @param nick  Nick gracza
     * @param score Liczba zdobytych punktów
     * @param lives Liczba pozostałych graczowi żyć
     */
    public Player(String nick, int score, int lives) {
        super(nick, score);
        this.lives = lives;
        this.numberOfPassedLevels = 0;
    }

    /**
     * Getter do liczby żyć gracza
     *
     * @return liczba żyć
     */
    public int getLives() {

        return lives;
    }

    /**
     * Metoda zmieniająca liczbe pozostałych graczowi żyć
     *
     * @param lives nowa liczba żyć do ustawienia
     */
    public void setLives(int lives) {

        this.lives = lives;
    }

    /**
     * Metoda zmniejszająca liczbę żyć o 1
     */
    public void decreaseLives() {

        lives--;
    }

    /**
     * Metoda umożliwia dodanie liczby punktów do obecnej dla gracza.
     *
     * @param score liczba punktów do dodania
     */
    public void addScore(int score) {

        setScore(getScore() + score);
    }

    /**
     * Getter do liczby ukończonych poziomów
     * @return liczba ukończonych poziomów
     */
    public int getNumberOfPassedLevels(){
            return numberOfPassedLevels;
    }

    /**
     * Zwiększa liczbe ukończonych poziomów o 1
     */
    public void levelPassed(){
        numberOfPassedLevels++;
    }

}
