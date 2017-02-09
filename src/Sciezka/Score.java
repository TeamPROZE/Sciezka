package Sciezka;

/**
 * Klasa zawierająca informację o wyniku jednej rozgrywki.
 * Posiada pole o nazwie gracza i o liczbie uzyskanych przez niego punktów.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Score {

    /**
     * Nazwa gracza
     */
    private String nick;

    /**
     * Liczba punktów po zakończeniu rozgrywki
     */
    private int score;

    /**
     * Konstruktor przyjmuje ciąg (String) zawierający nazwę gracza i
     * liczbę punktów. Wpisuje te wartości w pola obiektu.
     *
     * @param nick  nazwa gracza
     * @param score liczba punktów gracza
     */
    public Score(String nick, int score) {

        this.nick = nick;
        this.score = score;
    }

    /**
     * Konstruktor przyjmuje jedynie nazwę gracza. Wywołuje konstruktor
     * wyższego rzędu zakładając, że liczba punktów wynosi zero.
     *
     * @param nick nazwa gracza
     * @see #Score(String, int)
     */
    public Score(String nick) {

        this(nick, 0);
    }

    /**
     * Metoda zwracająca nazwę gracza.
     *
     * @return nazwa gracza
     */
    public String getNick() {

        return nick;
    }

    /**
     * Metoda zwracająca liczbę punktów.
     *
     * @return liczba punktów
     */
    public int getScore() {

        return score;
    }

    /**
     * Metoda służąca do ustawienia konkretnej liczby punktów w obiekcie.
     *
     * @param score nowa liczba punktów
     */
    public void setScore(int score) {

        this.score = score;
    }
}
