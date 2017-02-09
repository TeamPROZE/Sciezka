package Sciezka;

import java.util.Properties;

/**
 * Klasa zawierająca dane konfiguracyjne, które muszą zostać wczytane od razu po starcie aplikacji.
 * Zawiera parametry komunikacji sieciowej i ścieżki spritów.
 * Created by Kacper on 2017-01-30.
 */
public class GameConfig {

    /**
     * IP Serwera
     */
    public static String connectIP;
    /**
     * Port wykorzystywany do komunikacji
     */
    public static int connectPort;
    /**
     * Limit casu połąćzennia
     */
    public static int connectTimeout;

    /**
     * Nazwa pliku ze spritem - pojazd
     */
    public static String filenameCarSprite;
    /**
     * Nazwa pliku ze spritem - przeciwnik
     */
    public static String filenameEnemySprite;
    /**
     * Nazwa pliku ze spritem - pocisk
     */
    public static String filenameProjectileSprite;
    /**
     * Nazwa pliku ze spritem - ściana
     */
    public static String filenameWallSprite;
    /**
     * Nazwa pliku ze spritem - ścieżka
     */
    public static String filenamePathSprite;
    /**
     * Nazwa pliku ze spritem - meta
     */
    public static String filenameFinishSprite;
    /**
     * Nazwa pliku ze spritem - bonus1
     */
    public static String filenameBonus1Sprite;
    /**
     * Nazwa pliku ze spritem - bonus2
     */
    public static String filenameBonus2Sprite;
    /**
     * Nazwa pliku ze spritem - bonus3
     */
    public static String filenameBonus3Sprite;
    /**
     * Nazwa pliku ze spritem - power-up
     */
    public static String filenamePowerupSprite;


    /**
     * Metoda wczytująca i parsująca dane
     *
     * @param config kolekcja Properties z danymi
     */
    public static void readConstants(Properties config) {

        connectIP = config.getProperty("connectIP");
        connectPort = Integer.parseInt(config.getProperty("connectPort"));
        connectTimeout = Integer.parseInt(config.getProperty("connectTimeout"));
        filenameCarSprite = config.getProperty("filenameCarSprite");
        filenameEnemySprite = config.getProperty("filenameEnemySprite");
        filenameProjectileSprite = config.getProperty("filenameProjectileSprite");
        filenameWallSprite = config.getProperty("filenameWallSprite");
        filenamePathSprite = config.getProperty("filenamePathSprite");
        filenameFinishSprite = config.getProperty("filenameFinishSprite");
        filenameBonus1Sprite = config.getProperty("filenameBonus1Sprite");
        filenameBonus2Sprite = config.getProperty("filenameBonus2Sprite");
        filenameBonus3Sprite = config.getProperty("filenameBonus3Sprite");
        filenamePowerupSprite = config.getProperty("filenamePowerupSprite");


    }
}
