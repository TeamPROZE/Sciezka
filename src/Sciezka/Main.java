package Sciezka;

import Sciezka.game.MainWindow;
import Sciezka.util.ReadConfig;

import java.io.IOException;
import java.util.Properties;

/**
 * Gra Ścieżka.
 * <p>
 * Celem gry jest przedostanie się za pomocą pojazdu z jednego końca planszy na drugi,
 * nie zderzywszy się po drodze z żandą przeszkodą ani ścianą. Ponadto, obszar gry
 * stale się przesuwa, zmuszając gracza do dynamicznej gry.
 * <p>
 * Do dyspozycji gracza są bonusy w postaci przyspieszenia oraz działka, za pomocą
 * którego może on niszczyć przeszkody.
 * <p>
 * Podczas gry gracz zdobywa punkty, które sumowane są po skończeniu sesji grania.
 * Lista najlepszych wyników może zostać wyświelona w menu.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Main {

    /**
     * Flaga określająca czy gra odbywa się w trybie online czy offline
     */
    public static boolean online;

    /**
     * Wczytuje plik konfiguracyjny config.properties i tworzy
     * na jego podstawie instancję Menu głównego
     *
     * @param args argumenty przekazane do aplikacji
     * @see MainWindow główne okno aplikacji
     */
    public static void main(String[] args) {

        String configPath, gameConfigPath;

        if (args.length < 2) {
            configPath = "config.properties";
            gameConfigPath = "gameconfig.properties0";
        } else {
            configPath = args[0];
            gameConfigPath = args[1];
        }

        ReadConfig readConfig = new ReadConfig();
        final Properties config;

        try {
            config = readConfig.getProperties(gameConfigPath);
            GameConfig.readConstants(config);

            if (Client.canConnect()) {
                online = true;
            }

            if(online){
                Client.getConfig();
                Client.getLevels();
            }
            else{
                Properties prop = readConfig.getProperties(configPath);
                Config.readConstants(prop);
            }

            MainWindow mainWindow = new MainWindow();
            mainWindow.showWindow();
        } catch (IOException e) {
            System.out.println("Exception when loading properties: " + e);
        }
    }
}
