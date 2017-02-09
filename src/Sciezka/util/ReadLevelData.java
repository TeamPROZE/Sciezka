package Sciezka.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja wczytywania mapy.
 * <p>
 * Posiada metodę getMatrix przyjmującą nazwę pliku <code>.txt</code>, którego
 * zawartość to macierz cyfr odpowiadająca rozdzajom kafelków na planszy.
 * <p>
 * Zwraca listę wierszy, każdy będący listą liczb oznaczających rodzaj kafelka.
 *
 * @author Waked
 * @version 1.0
 */
public class ReadLevelData {

    /**
     * Metoda tworzy wektor wektorów zawierający liczby całkkowite od 0 do 9.
     * Wczytuje plik z podanej ścieżki i znak po znaku generuje obiekt wyjściowy.
     *
     * @param textFileName ścieżka dostępu do pliku <code>.txt</code> z planszą
     * @return kolekcja kolekcji (klasy List) zawierająca liczby całkowite
     * @throws IOException jeżeli nie uda się wczytać pliku z planszą
     */
    public List<List<Integer>> getMatrix(String textFileName) throws IOException {

        List<List<Integer>> result = new ArrayList<>();
        LineNumberReader lnr;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(textFileName)) {
            lnr = new LineNumberReader(new InputStreamReader(inputStream));

            String lineStr;

            while ((lineStr = lnr.readLine()) != null) {
                List<Integer> lineArr = new ArrayList<>();
                for (char c : lineStr.toCharArray()) {
                    lineArr.add(Integer.parseInt(Character.toString(c)));
                }
                result.add(lineArr);
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
