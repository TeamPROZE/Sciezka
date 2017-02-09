package Sciezka.game;

import Sciezka.Config;
import Sciezka.game.entity.Projectile;
import Sciezka.game.entity.enemy.Enemy;
import Sciezka.game.entity.item.Item;
import Sciezka.game.entity.item.ItemBonus;
import Sciezka.game.entity.item.ItemPowerup;
import Sciezka.game.entity.tile.Tile;
import Sciezka.game.entity.tile.TileBlocked;
import Sciezka.game.entity.tile.TileFinish;
import Sciezka.game.entity.tile.TileFloor;
import Sciezka.util.ReadLevelData;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentującą planszę do wyświetlenia.
 * <p>
 * Zawiera macierz obiektów typu Tile, które są elementami budującymi planszę.
 * Zawiera liste przeciwników oraz liste znajdziek
 *
 * @author Kacper Bloch
 * @version 1.0
 */
public class Level {

    /**
     * Tablica przechowujaca pola mapy
     */
    private Tile[][] tileMatrix;

    /**
     * Wektor przechowujący znajdźki
     */
    private List<Item> items;

    /**
     * Wektor przechowujący przeciwników
     */
    private List<Enemy> enemies;

    /**
     * Wektor przechowujący lecące pociski
     */
    private List<Projectile> projectiles;

    /**
     * Szerokosc mapy podana w liczbie kafelków
     */
    private int mapTileWidth;

    /**
     * Wysokosc mapy podana w liczbie kafelków
     */
    private int mapTileHeight;

    /**
     * Współrzędna X kafelka startowego
     */
    private int startTileX;

    /**
     * Współrzędna Y kafelka startowego
     */
    private int startTileY;

    /**
     * Rozmiar kafelka podstawowego (planszy)
     */
    private int tileSize = Config.tileSize;

    /**
     * Konstruktor przyjmuje ścieżkę do pliku z zapisaną planszą
     * i zamienia go na kolekcję kolekcji zawierającaych liczby oznaczające
     * elementy planszy. Następnie przekazuje ten obiekt do metody init().
     *
     * @param file ścieżka do pliku z planszą
     */
    public Level(String file) {

        ReadLevelData readLevelData = new ReadLevelData();
        try {
            List<List<Integer>> array;
            array = readLevelData.getMatrix(file);
            init(array);
        } catch (IOException e) {
            System.out.println("Exception while loading tileMatrix: " + e);
        }
    }

    /**
     * Konstruktor przyjmuje gotowy obiekt zawierający dynamicznie stworzoną
     * macierz z zapisem planszy do zainicjalizowania. Przekazuje tę macierz
     * do metody init().
     *
     * @param array dynamiczna macierz z zapisem planszy według zasad
     */
    public Level(List<List<Integer>> array) {

        init(array);
    }

    /**
     * Metoda tworząca mape na podstawie wcześniej wczytanych liczb
     * 0 - TileFloor
     * 1 - TileBlocked
     * 2 - ItemBonus 1
     * 3 - ItemBonus 2
     * 4 - ItemBonus 3
     * 5 - ItemPowerup
     * 9 - pole startowe gracza
     *
     * @param array Lista wczytanych liczb
     */
    private void init(List<List<Integer>> array) {
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();

        mapTileHeight = array.size();
        mapTileWidth = array.get(0).size();

        tileMatrix = new Tile[mapTileHeight][mapTileWidth];

        for (int m = 0; m < mapTileHeight; m++) {
            List<Integer> line = array.get(m);

            for (int n = 0; n < mapTileWidth; n++) {

                int num = line.get(n);
                Tile newTile;

                switch (num) {
                    case 1:
                        newTile = new TileBlocked(n, m);
                        break;
                    case 2:
                        newTile = new TileFloor(n, m);
                        items.add(new ItemBonus(n, m, ItemBonus.BonusLevel.LEVEL1));
                        break;
                    case 3:
                        newTile = new TileFloor(n, m);
                        items.add(new ItemBonus(n, m, ItemBonus.BonusLevel.LEVEL2));
                        break;
                    case 4:
                        newTile = new TileFloor(n, m);
                        items.add(new ItemBonus(n, m, ItemBonus.BonusLevel.LEVEL3));
                        break;
                    case 5:
                        newTile = new TileFloor(n, m);
                        items.add(new ItemPowerup(n, m));
                        break;
                    case 6:
                        newTile = new TileFinish(n, m);
                        break;
                    case 7:
                        newTile = new TileFloor(n, m);
                        enemies.add(new Enemy(n, m, Enemy.MoveDirection.DOWN));
                        break;
                    case 8:
                        newTile = new TileFloor(n, m);
                        enemies.add(new Enemy(n, m, Enemy.MoveDirection.RIGHT));
                        break;
                    case 9:
                        startTileX = n;
                        startTileY = m;
                        newTile = new TileFloor(n, m);
                        break;

                    case 0:
                    default:
                        newTile = new TileFloor(n, m);
                }
                tileMatrix[m][n] = newTile;
            }
        }
    }

    /**
     * Metoda rysująca mape oraz znajdźki na ekranie
     *
     * @param g2d       Obiekt klasy Graphics2D rysujacy mapę na ekranie
     * @param width     Szerokość ekranu gry
     * @param height    Wysokość ekranu gry
     * @param observer  Obserwator Graphics2D
     */
    public void draw(Graphics2D g2d, int width, int height, ImageObserver observer) {

        for (int row = 0; row < mapTileHeight; row++) {
            for (int column = 0; column < mapTileWidth; column++) {

                tileMatrix[row][column].draw(g2d, width, height, observer);
            }
        }

        for (Item item : items) {
            item.draw(g2d, width, height, observer);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g2d, width, height, observer);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(g2d, width, height, observer);
        }
    }

    /**
     * Aktualizuje położenie dynamicznych obiektów
     */
    void update() {

        for (Enemy e : enemies) {
            e.updateEnemy();
        }

        for (Projectile projectile : projectiles) {
            projectile.updateBullet();
        }
    }

    /**
     * Metoda zwraca wspólrzędną porządkową X kafelka startowego
     *
     * @return wspólrzędna X komórki
     */
    public int getStartTileX() {
        return startTileX;
    }

    /**
     * Metoda zwraca wspólrzędną porządkową Y kafelka startowego
     *
     * @return wspólrzędna Y komórki
     */
    public int getStartTileY() {
        return startTileY;
    }

    /**
     * Metoda zwraca porządkową szerokość mapy
     *
     * @return szerokość mapy
     */
    int getMapTileWidth() {
        return mapTileWidth;
    }

    /**
     * Metoda zwraca porządkową wysokość mapy
     *
     * @return wysokość mapy
     */
    int getMapTileHeight() {
        return mapTileHeight;
    }

    /**
     * Metoda zwraca wspólrzędną X kafelka startowego
     *
     * @return wspólrzędna X komórki
     */
    int getStartX() {
        return startTileX * tileSize;
    }

    /**
     * Metoda zwraca wspólrzędną Y kafelka startowego
     *
     * @return wspólrzędna Y komórki
     */
    int getStartY() {
        return startTileY * tileSize;
    }

    /**
     * Metoda zwraca szerokość mapy
     *
     * @return szerokość mapy
     */
    int getMapWidth() {
        return mapTileWidth * tileSize;
    }

    /**
     * Metoda zwraca wysokość mapy
     *
     * @return wysokość mapy
     */
    int getMapHeight() {
        return mapTileHeight * tileSize;
    }

    /**
     * Getter do kafelka o indeksowych współrzędnych x i y
     *
     * @param x współrzędna x
     * @param y współrzędna y
     * @return kafelek pod podanymi współrzędnymi
     * @throws ArrayIndexOutOfBoundsException jeżeli nie istnieje kafelek o podanych współrzędnych
     */
    Tile getTile(int x, int y) throws ArrayIndexOutOfBoundsException {

        return tileMatrix[y][x];
    }

    /**
     * Getter do przedmiotów-znajdziek na planszy
     *
     * @return lista zawierająca Itemy
     */
    List<Item> getItems() {

        return items;
    }

    /**
     * Getter do przeciwników poruszających się na planszy
     *
     * @return lista zawierająca przeciwników (Enemy)
     */
    List<Enemy> getEnemies() {

        return enemies;
    }

    /**
     * Getter do wygenerowanych pocisków na planszy
     *
     * @return List&lt;&gt; zawierający pociski Projectile
     */
    List<Projectile> getProjectiles() {

        return projectiles;
    }

    /**
     * Dodaje pocisk (instancję klasy Projectile) do wektora
     * zawierającego pociski
     *
     * @param projectile instancja pocisku
     */
    void addProjectile(Projectile projectile) {

        projectiles.add(projectile);
    }

}
