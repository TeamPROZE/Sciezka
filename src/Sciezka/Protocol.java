package Sciezka;

/**
 * Klasa zawirająca stałe potrzebne do pracy protokołu sieciowego.
 * Poszczególne pola to identyfikatory poleceń sieciowych wysyłanych
 * przez klienta albo serwer. Identyfikatory muszą być jednoznaczne
 * między klientem a serwerem.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Protocol {

    public static final String GETCONFIG = "GET_CONFIG";
    public static final String GETHIGHSCORES = "GET_HIGHSCORES";
    public static final String GETLEVELS = "GET_LEVELS";
    public static final String GETLEVEL = "GET_LEVEL";
    public static final String GAMESCORE = "GAME_SCORE";
    public static final String PING = "PING";
    public static final String CONFIG = "CONFIG";
    public static final String HIGHSCORES = "HIGHSCORES";
    public static final String LEVELS = "LEVELS";
    public static final String LEVEL = "LEVEL";
    public static final String GAMESCOREACCEPTED = "GAME_SCORE_ACCEPTED";
    public static final String GAMESCOREREJECTED = "GAME_SCORE_REJECTED";
    public static final String ERROR = "ERROR";
}
