package Sciezka;

import java.util.Properties;

/**
 * Klasa zawierająca stałe operacyjne, udostępniająca je dla wszystkich
 * komponentów programu. Nazwy danych są identyczne z nazwami parametrów
 * konfiguracyjnych i mają typy właściwe dla ich wykorzystania.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Config {

    /**
     * Nazwa aplikacji
     */
    public static String appName;
    /**
     * Szerokość okna
     */
    public static int windowWidth;
    /**
     * Wysokość okna
     */
    public static int windowHeight;
    /**
     * Nazwy plików z definicjami poziomów
     */
    public static String[] filenameLevel;
    /**
     * Nazwa plikku z najlepszymi wynikami
     */
    public static String filenameHighscores;
    /**
     * Liczba dostępnych poziomów
     */
    public static int numberOfLevels;
    /**
     * Tekst na przycsiku Start
     */
    public static String menuStart;
    /**
     * Tekst na przycsiku wyboru trudności - łatwy
     */
    public static String menuStartEasy;
    /**
     * Tekst na przycsiku wyboru trudności - normalny
     */
    public static String menuStartMedium;
    /**
     * Tekst na przycsiku wyboru trudnosci - trudny
     */
    public static String menuStartHard;
    /**
     * Tekst na przycisku powórt do menu głównego
     */
    public static String menuStartBack;
    /**
     * Tekst na przecisku Najlepsze Wyniki
     */
    public static String menuHighscore;
    /**
     * Tekst na przycsiku powrót do menu głównego
     */
    public static String menuHighscoreBack;
    /**
     * Tekst na przycisku Zasady
     */
    public static String menuRules;
    /**
     * Tekst na przycisku Zakończ Grę
     */
    public static String menuExit;
    /**
     * Tekst na przycisku kontynuuj rozgrywkę
     */
    public static String continueButton;
    /**
     * Tekst na przycisku zakończ grę
     */
    public static String endGameButton;
    /**
     * Tekst w oknie dialogowym - koniec dostępnym poziomów
     */
    public static String dialogMessage;
    /**
     * Szybkość przewijania - poziom łatwy
     */
    public static float scrollSpeedEasy;
    /**
     * Szybkość przewijania - poziom normalny
     */
    public static float scrollSpeedNormal;
    /**
     * Szybkość przewijania - poziom trudny
     */
    public static float scrollSpeedHard;

    /**
     *  Sterowanie - góra
     */
    public static String controlsUp;
    /**
     *  Sterowanie - dół
     */
        public static String controlsDown;
    /**
     *  Sterowanie - lewo
     */
        public static String controlsLeft;
    /**
     *  Sterowanie - prawo
     */
        public static String controlsRight;
    /**
     * Max pędkośc pojazdu
     */
    public static float vehMaxSpeed;
    /**
     * Przyśpieszenie pojazdu
     */
    public static float vehAccel;

    /**
     * Szybkość hamowania
     */
    public static float vehDecel;
    /**Szybkość hamowania pojazdu
     *
     */
        public static float vehDecelCoast;
    /**
     * Szybkość obrotu pojazdu
     */
        public static float vehRotSpeed;
    /**
     * Prędkość przeciwnika
     */
        public static float enemySpeed;
    /**
     * Prędkość pocisku
     */
        public static float projectileSpeed;
    /**
     * Komplet punktów
     */
        public static int pointsComplete;
    /**
     * Max punkty za dobry czas
     */
        public static int pointsTime;
    /**
     * Punkty za zachowane życie
     */
        public static int pointsLives;
    /**
     * Początkowa liczba żyć
     */
        public static int lives;
    /**
     * Klarki na sekundę
     */
        public static int FPS;
    /**
     * Wysokość widocznej części mapy
     */
        public static int visibleMapHeight;
    /**
     * Szerokość widocznej części mapy
     */
        public static int visibleMapWidth;
    /**
     * Rozmiar pojedynczej komórki
     */
        public static int tileSize;
    /**
     * Rozmiar samochodu
     */
        public static int carSize;
    /**
     * Rozmiar przeciwnika
     */
        public static int enemySize;
    /**
     * Liczba punktów za zniszczenie przeciwnika
     */
        public static int enemyPoints;
    /**
     * Rozmiar pocisku
     */
        public static int projectileSize;
    /**
     * Rozmiar małego bonusu
     */
        public static int bonus1Size;
    /**
     * Punkty za zebranie małego bonusu
     */
        public static int bonus1Points;
    /**
     * Rozmiar średniego bonusu
     */
        public static int bonus2Size;
    /**
     * Punty za zebranie średniego bonusu
     */
        public static int bonus2Points;
    /**
     * Rozmiar dużego bonusu
     */
        public static int bonus3Size;
    /**
     * Punkty za zebranie dużego bonusu
     */
        public static int bonus3Points;
    /**
     * Rozmiar power-upa
     */
        public static int powerupSize;
    /**
     * Czas trwania power-upa
     */
        public static int powerupDuration;
    /**
     * Liczba wierszy najelpszych wyników
     */
        public static int highScoresRows;
    /**
     * Liczba kolumn najlepszych wyników
     */
        public static int highScoresColumns;
    /**
     * Wynik początkowy
     */
        public static int startScore;
    /**
     * Domyślny nick wyśiwetlany w polu, do którego wpisuje się nazwe gracza
     */
        public static String defaultNickname;
    /**
     *  Liczba pocisków
     */
        public static int numberOfBullets;
    /**
     * Punkty za przejście poziomu na łatwym poziomie trudności
     */
        public static int easyPoints;
    /**
     * Punkty za przejście poziomu na normalnym poziomie trudności
     */
        public static int normalPoints;
    /**
     * Punkty za przejście poziomu na trudnym poziomie trudności
     */
        public static int hardPoints;
    /**
     * Punkty za czas przejścia poziomu
     */
        public static int timePoints;
    /**
     * Odstęp czasowy pomiędzy kolejnymi odjęciami punktów za czas
     */
        public static int interval;
    /**
     * Punkty odejmowane co odstęp czasowy
     */
        public static int decrease;

    /**
     * Metoda parsująca dane konfiguracyjne, gdy aplikacja działa w trybie offline, a dane są wczytane z lokalnego pliku konfiguracyjnego
     * @param config obiekt Properties przechowujący dane konfiguracyjne, które należy sparsować
     */
    public static void readConstants(Properties config) {

        appName = config.getProperty("appName");
        windowWidth = Integer.parseInt(config.getProperty("windowWidth"));
        windowHeight = Integer.parseInt(config.getProperty("windowHeight"));
        filenameLevel = config.getProperty("filenameLevel").split(" ");
        filenameHighscores = config.getProperty("filenameHighscores");
        numberOfLevels = filenameLevel.length;
        menuStart = config.getProperty("menuStart");
        menuStartEasy = config.getProperty("menuStartEasy");
        menuStartMedium = config.getProperty("menuStartMedium");
        menuStartHard = config.getProperty("menuStartHard");
        menuStartBack = config.getProperty("menuStartBack");
        menuHighscore = config.getProperty("menuHighscore");
        menuHighscoreBack = config.getProperty("menuHighscoreBack");
        menuRules = config.getProperty("menuOptions");
        menuExit = config.getProperty("menuExit");
        continueButton = config.getProperty("continueButton");
        endGameButton = config.getProperty("endGameButton");
        dialogMessage = config.getProperty("dialogMessage");
        scrollSpeedEasy = Float.parseFloat(config.getProperty("scrollSpeedEasy"));
        scrollSpeedNormal = Float.parseFloat(config.getProperty("scrollSpeedNormal"));
        scrollSpeedHard = Float.parseFloat(config.getProperty("scrollSpeedHard"));
        vehMaxSpeed = Float.parseFloat(config.getProperty("vehMaxSpeed"));
        vehAccel = Float.parseFloat(config.getProperty("vehAccel"));
        vehDecel = Float.parseFloat(config.getProperty("vehDecel"));
        vehDecelCoast = Float.parseFloat(config.getProperty("vehDecelCoast"));
        vehRotSpeed = Float.parseFloat(config.getProperty("vehRotSpeed"));
        enemySpeed = Float.parseFloat(config.getProperty("enemySpeed"));
        projectileSpeed = Float.parseFloat(config.getProperty("projectileSpeed"));
        pointsComplete = Integer.parseInt(config.getProperty("pointsComplete"));
        pointsTime = Integer.parseInt(config.getProperty("pointsTime"));
        pointsLives = Integer.parseInt(config.getProperty("pointsLives"));
        lives = Integer.parseInt(config.getProperty("lives"));
        FPS = Integer.parseInt(config.getProperty("FPS"));
        controlsUp = config.getProperty("controlsUp");
        controlsDown = config.getProperty("controlsDown");
        controlsLeft = config.getProperty("controlsLeft");
        controlsRight = config.getProperty("controlsRight");
        visibleMapHeight = Integer.parseInt(config.getProperty("visibleMapHeight"));
        visibleMapWidth = Integer.parseInt(config.getProperty("visibleMapWidth"));
        tileSize = Integer.parseInt(config.getProperty("tileSize"));
        carSize = Integer.parseInt(config.getProperty("carSize"));
        enemySize = Integer.parseInt(config.getProperty("enemySize"));
        enemyPoints = Integer.parseInt(config.getProperty("enemyPoints"));
        projectileSize = Integer.parseInt(config.getProperty("projectileSize"));
        bonus1Size = Integer.parseInt(config.getProperty("bonus1Size"));
        bonus1Points = Integer.parseInt(config.getProperty("bonus1Points"));
        bonus2Size = Integer.parseInt(config.getProperty("bonus2Size"));
        bonus2Points = Integer.parseInt(config.getProperty("bonus2Points"));
        bonus3Size = Integer.parseInt(config.getProperty("bonus3Size"));
        bonus3Points = Integer.parseInt(config.getProperty("bonus3Points"));
        powerupSize = Integer.parseInt(config.getProperty("powerupSize"));
        powerupDuration = Integer.parseInt(config.getProperty("powerupDuration"));
        highScoresRows = Integer.parseInt(config.getProperty("highScoresRows"));
        highScoresColumns = Integer.parseInt(config.getProperty("highScoresColumns"));
        startScore = Integer.parseInt(config.getProperty("startScore"));
        defaultNickname = config.getProperty("defaultNickname");
        numberOfBullets = Integer.parseInt(config.getProperty("numberOfBullets"));
        easyPoints = Integer.parseInt(config.getProperty("easyPoints"));
        normalPoints = Integer.parseInt(config.getProperty("normalPoints"));
        hardPoints = Integer.parseInt(config.getProperty("hardPoints"));
        timePoints = Integer.parseInt(config.getProperty("timePoints"));
        interval = Integer.parseInt(config.getProperty("interval"));
        decrease = Integer.parseInt(config.getProperty("decrease"));

    }


    /**
     * Metoda parsująca dane konfiguracyjne, gdy aplikacja działa w trybie online, a dane są pobrane z serwera
     * @param config obiekt Properties przechowujący dane konfiguracyjne, które należy sparsować
     */
    public static void readConstantsOnline(Properties config) {

        appName = config.getProperty("appName");
        windowWidth = Integer.parseInt(config.getProperty("windowWidth"));
        windowHeight = Integer.parseInt(config.getProperty("windowHeight"));
        menuStart = config.getProperty("menuStart");
        menuStartEasy = config.getProperty("menuStartEasy");
        menuStartMedium = config.getProperty("menuStartMedium");
        menuStartHard = config.getProperty("menuStartHard");
        menuStartBack = config.getProperty("menuStartBack");
        menuHighscore = config.getProperty("menuHighscore");
        menuHighscoreBack = config.getProperty("menuHighscoreBack");
        menuRules = config.getProperty("menuOptions");
        menuExit = config.getProperty("menuExit");
        continueButton = config.getProperty("continueButton");
        endGameButton = config.getProperty("endGameButton");
        dialogMessage = config.getProperty("dialogMessage");
        scrollSpeedEasy = Float.parseFloat(config.getProperty("scrollSpeedEasy"));
        scrollSpeedNormal = Float.parseFloat(config.getProperty("scrollSpeedNormal"));
        scrollSpeedHard = Float.parseFloat(config.getProperty("scrollSpeedHard"));
        vehMaxSpeed = Float.parseFloat(config.getProperty("vehMaxSpeed"));
        vehAccel = Float.parseFloat(config.getProperty("vehAccel"));
        vehDecel = Float.parseFloat(config.getProperty("vehDecel"));
        vehDecelCoast = Float.parseFloat(config.getProperty("vehDecelCoast"));
        vehRotSpeed = Float.parseFloat(config.getProperty("vehRotSpeed"));
        enemySpeed = Float.parseFloat(config.getProperty("enemySpeed"));
        projectileSpeed = Float.parseFloat(config.getProperty("projectileSpeed"));
        pointsComplete = Integer.parseInt(config.getProperty("pointsComplete"));
        pointsTime = Integer.parseInt(config.getProperty("pointsTime"));
        pointsLives = Integer.parseInt(config.getProperty("pointsLives"));
        lives = Integer.parseInt(config.getProperty("lives"));
        FPS = Integer.parseInt(config.getProperty("FPS"));
        controlsUp = config.getProperty("controlsUp");
        controlsDown = config.getProperty("controlsDown");
        controlsLeft = config.getProperty("controlsLeft");
        controlsRight = config.getProperty("controlsRight");
        visibleMapHeight = Integer.parseInt(config.getProperty("visibleMapHeight"));
        visibleMapWidth = Integer.parseInt(config.getProperty("visibleMapWidth"));
        tileSize = Integer.parseInt(config.getProperty("tileSize"));
        carSize = Integer.parseInt(config.getProperty("carSize"));
        enemySize = Integer.parseInt(config.getProperty("enemySize"));
        enemyPoints = Integer.parseInt(config.getProperty("enemyPoints"));
        projectileSize = Integer.parseInt(config.getProperty("projectileSize"));
        bonus1Size = Integer.parseInt(config.getProperty("bonus1Size"));
        bonus1Points = Integer.parseInt(config.getProperty("bonus1Points"));
        bonus2Size = Integer.parseInt(config.getProperty("bonus2Size"));
        bonus2Points = Integer.parseInt(config.getProperty("bonus2Points"));
        bonus3Size = Integer.parseInt(config.getProperty("bonus3Size"));
        bonus3Points = Integer.parseInt(config.getProperty("bonus3Points"));
        powerupSize = Integer.parseInt(config.getProperty("powerupSize"));
        powerupDuration = Integer.parseInt(config.getProperty("powerupDuration"));
        highScoresRows = Integer.parseInt(config.getProperty("highScoresRows"));
        highScoresColumns = Integer.parseInt(config.getProperty("highScoresColumns"));
        startScore = Integer.parseInt(config.getProperty("startScore"));
        defaultNickname = config.getProperty("defaultNickname");
        numberOfBullets = Integer.parseInt(config.getProperty("numberOfBullets"));
        easyPoints = Integer.parseInt(config.getProperty("easyPoints"));
        normalPoints = Integer.parseInt(config.getProperty("normalPoints"));
        hardPoints = Integer.parseInt(config.getProperty("hardPoints"));
        timePoints = Integer.parseInt(config.getProperty("timePoints"));
        interval = Integer.parseInt(config.getProperty("interval"));
        decrease = Integer.parseInt(config.getProperty("decrease"));

    }


}
