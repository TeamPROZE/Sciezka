package Sciezka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Oferuje funkcje sieciowe. Metody tej klasy pozwalają na nawiązanie połączenia z serwerem oraz swobodną komunikacje.
 * Umożliwa pobranie danych konfiguracyjnych, listy najlepszych wyników, definicji poziomów oraz przesłanie wyniku rozgrywki na serwer
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class Client {

    /**
     * Sprawdza czy połączenie do serwera pod adresem podanym w configu
     * jest możliwe.
     *
     * @return czy udało się połączyć z serwerem
     */
    public static boolean canConnect() {
        try {
            Socket socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(Protocol.PING);

            System.out.println(reader.readLine());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metoda wysyła żądanie GETHIGHSCORES do serwera, po czym odbiera liste najlepszych wynik
     *
     * @return Lista najlepszych wyników
     */
    public static List<Score> getHighscores() {

        Socket socket;
        List<Score> highScores = null;
        try {
            socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(Protocol.GETHIGHSCORES);

            String[] answer = reader.readLine().split("/");

            highScores = new ArrayList<>();

            for (int i = 1; i < answer.length; i++) {
                String[] score = answer[i].split("=");
                highScores.add(new Score(score[0], Integer.parseInt(score[1])));
            }

            System.out.println("Pobrano najlepsze wyniki");

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    /**
     * Metoda służąca do pobrania z serwera danych konfiguracyjnych do rozgrywki.
     */
    public static void getConfig() {

        Socket socket;
        try {
            socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(Protocol.GETCONFIG);

            String[] answer = reader.readLine().split(new String("#"));

                Properties property = new Properties();

                for (int i = 1; i < answer.length; i++) {
                    String[] str = answer[i].split(new String("="));
                    property.setProperty(str[0], str[1]);
                }

                Config.readConstantsOnline(property);


            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metoda wysyła żądanie GETLEVELS do serwera, po czym odbiera liczbę dostępnych poziomów
     */
    public static void getLevels() {

        Socket socket;
        try {
            socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(Protocol.GETLEVELS);

            String[] answer = reader.readLine().split(new String("/"));

                Config.numberOfLevels = Integer.parseInt(answer[1]);

                System.out.println("Liczba dostępnych poziomów: " + answer[1]);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metoda wysyła żądanie GETLEVEL/numer_poziomu do serwera, po czym odbiera definicje poziomu
     *
     * @param numberOfLevel numer poziomu
     * @return definicja poziomu
     */
    public static List<List<Integer>> getLevel(int numberOfLevel) {

        Socket socket;
        List<List<Integer>> result = null;
        try {
            socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(Protocol.GETLEVEL + "/" + numberOfLevel);

            String[] answer = reader.readLine().split("/");
            String lineStr;
            result = new ArrayList<>();

            for (int i = 1; i < answer.length; i++) {
                lineStr = answer[i];
                List<Integer> lineArr = new ArrayList<>();
                for (char c : lineStr.toCharArray()) {
                    lineArr.add(Integer.parseInt(Character.toString(c)));
                }
                result.add(lineArr);
            }

            System.out.println("Pobrane definicje poziomu " + numberOfLevel);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * Metoda wysyła wynik rozgrywki do serwera, po czym odbiera informację czy wynik został zaakceptowany.
     * Zwraca true gdy wynik zostanie zaakceptowany. False gdy odrzucony.
     *
     * @param name  nick gracza
     * @param score wynik punktowy
     * @return true - gdy wynik zaakceptowany, false - gdy wynik nie został zaakceptowany
     */
    public static boolean sendGameScore(String name, int score) {

        Socket socket;
        boolean result = false;
        try {
            socket = new Socket(GameConfig.connectIP, GameConfig.connectPort);
            socket.setSoTimeout(GameConfig.connectTimeout);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String str = Protocol.GAMESCORE + "/" + name + "=" + score;
            writer.println(str);

            String[] answer = reader.readLine().split("/");

            String command = answer[0];

            switch (command) {
                case Protocol.GAMESCOREACCEPTED:
                    result = true;
                    System.out.println("Wynik zostal zaakceptowany");
                    break;
                case Protocol.GAMESCOREREJECTED:
                    result = false;
                    System.out.println("Wynik zostal odrzucony");
                    break;
                default:
                    break;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
