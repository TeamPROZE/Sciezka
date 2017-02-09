package Sciezka.game;

import Sciezka.Config;
import Sciezka.Player;
import Sciezka.game.entity.Car;
import Sciezka.game.entity.Entity;
import Sciezka.game.entity.Projectile;
import Sciezka.game.entity.enemy.Enemy;
import Sciezka.game.entity.item.*;
import Sciezka.game.entity.tile.Tile;
import Sciezka.game.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Klasa opisująca panel Swing, w ktrórym odbywa się rysowanie grafiki w grze.
 * Obsługuje ona jednocześnie cześć logiki gry związaną z połączeniem innych
 * cześci w jedno. Ponieważ implementuje Runnable, posiada obsługę wątku, dzięki
 * czemu decyduje kiedy aplikacja ma wykonywać działanie.
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    /**
     * Warstwy menu pochodzące z instancji klasy MainWindow, która wywołuje GamePanel
     */
    private JPanel cards;

    /**
     * Teoretyczna liczba klatek na sekundę animacji
     */
    private int FPS;

    /**
     * Współrzędna górnej krawędzi widocznej części mapy
     */
    private float y1;

    /**
     * Współrzędna dolnej krawędzi widocznej części mapy
     */
    private float y2;

    /**
     * Szybkość przesuwania się mapy
     */
    private float scrollSpeed;

    /**
     * Poziom trudności
     */
    private String dif;

    /**
     * Główny wątek gry, uruchamiany wraz z otrzymaniem przez klasę
     * powiadomienia notify
     */
    private Thread thread;

    /**
     * Powtarzający się licznik obsługujący główną pętlę gry.
     * Tworzony jest wraz z uruchomieniem wątku tej klasy (run)
     * i otrzymuje anonimowy obiekt ActionListener, w którym
     * zawarte są polecenia do wykonania przy każdej iteracji
     * pętli gry.
     */
    private Timer timer;

    /**
     * Licznik zmniejszający bonus punktowy za czas
     */
    private Timer timeCounter;

    /**
     * Zmienna okreslajaca czy watek dziala
     */
    private boolean isRunning;

    /**
     * Określa czy panel jest aktywny (ma być rysowany)
     */
    private boolean inactive;

    /**
     * Określa czy gra jest zatrzymana i kolejne klatki mają nie być renderowane
     */
    private boolean paused;

    /**
     * Stan klawisza skręcania w lewo
     */
    private KeyState keyStateLeft;

    /**
     * Stan klawisza skręcania w prawo
     */
    private KeyState keyStateRight;

    /**
     * Stan klawisza przyśpieszania
     */
    private KeyState keyStateAccelerate;

    /**
     * Stan klawisza hamowania
     */
    private KeyState keyStateBrake;

    /**
     * Obiekt klasy BufferedReader przechowujący informacje o obrazie do wyświetlenia na ekraniu
     */
    private BufferedImage image;

    /**
     * Obiekt klasy Graphics2D rysujący mapę na ekranie
     */
    private Graphics2D g2d;

    /**
     * Pole tekstowe wyświetlające informacje podsumowujące rozgrywke
     */
    private JTextArea summaryTextArea;

    /**
     * Listener słuchający zdarzeń dotyczących zebrania znajdźki
     */
    private ItemCollectionHandler itemCollectionHandler;

    /**
     * Obiekt wyswietlanej mapy
     */
    private Level level;

    /**
     * Obiekt reprezentujacy pojazd uzytkownika
     */
    private Car car;

    /**
     * Liczba punktów za czas
     */
    private int timePoints;

    /**
     * Obiekt reprezentujący gracza i wynik jego rozgrywki
     */
    private Player player;

    /**
     * Określa czy rozgrywka została zakończona
     */
    private boolean gameOver;


    /**
     * Konstruktor przyjmuje String oznaczający poziom trudności gry do rozpoczęćia,
     * obiekt Level zawierający wszystkie informacje o planszy do uruchomienia,
     * obiekt JPanel służący do przełączania warstw interfejsu i obiekt Player
     * będący reprezentacją gracza (z nazwą i liczbą punktów oraz szans)
     *
     * @param difficulty Poziom trudności ("Easy", "Normal", "Hard")
     * @param level      Dane planszy w postaci klasy Level
     * @param cards      Panel głowny layoutu CardLayout
     * @param summary    Pole tekstowe wyświetlające podsumowanie rozgrywki
     * @param player     Obiekt reprezentujący gracza w obecnej rozgrywce
     */
    public GamePanel(String difficulty, Level level, JPanel cards, JTextArea summary, Player player) {

        super();

        this.level = level;
        this.FPS = Config.FPS;
        this.keyStateLeft = KeyState.UP;
        this.keyStateRight = KeyState.UP;
        this.keyStateAccelerate = KeyState.UP;
        this.keyStateBrake = KeyState.UP;
        this.cards = cards;
        this.player = player;
        this.gameOver = false;
        this.timePoints = Config.timePoints;
        this.summaryTextArea = summary;

        init(difficulty);
    }

    /**
     * Konstruktor podrzędny, przyjmuje nazwę gracza na podstawie której tworzy
     * nową instancję gracza (Player) i przekazuje ją wraz z pozostałymi parametrami
     * do konstruktora nadrzędnego.
     *
     * @param difficulty Poziom trudności ("Easy", "Normal", "Hard")
     * @param level      Dane planszy w postaci klasy Level
     * @param cards      Panel głowny layoutu CardLayout
     * @param summary    Pole tekstowe wyświetlające podsumowanie rozgrywki
     * @param nick       Nick gracza
     * @see #GamePanel(String, Level, JPanel, JTextArea, Player)
     */
    public GamePanel(String difficulty, Level level, JPanel cards, JTextArea summary, String nick) {

        this(difficulty, level, cards, summary, new Player(nick, Config.startScore, Config.lives));
    }

    /**
     * Inicjalizuje poziom gry na podstawie podanej trudności i konfiguracji
     *
     * @param difficulty Określa poziom trudności ("Easy", "Normal", "Hard")
     */
    private void init(String difficulty) {

        dif = difficulty;

        switch (difficulty) {
            case "Easy":
                this.scrollSpeed = Config.scrollSpeedEasy;
                break;
            case "Hard":
                this.scrollSpeed = Config.scrollSpeedHard;
                break;
            case "Normal":
            default:
                this.scrollSpeed = Config.scrollSpeedNormal;
        }

        addKeyListener(this);
        /*
         * Tworzy eventListener do znajdziek i przypisuje go każdej znajdźce w Level
         */
        this.itemCollectionHandler = new ItemCollectionHandler();
        for (Item item : level.getItems()) {
            item.addPowerupEventListener(itemCollectionHandler);
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        this.requestFocus();
    }

    /**
     * Nadpisanie metody klasy Component addNotify
     * Rozpoczyna prace wątku odpowiedzialnego za rysowanie
     */
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Implementacja metody run() z nadrzędnego interfejsu Runnable. Tworzy BufferedImage
     * służący za główny ekran gry, na którym rysowane są wszystkie inne grafiki.
     * Tworzy też instancję obiektu Car i przypisuje do niego obecnego gracza.
     * Następnie tworzy instancję powtarzającego się Timera o określonym kroku
     * (w zależności od FPSów), gdzie z każdą iteracją wywoływane są metody
     * aktualizowania i rysowania ekranu gry.
     */
    public void run() {

        isRunning = true;
        paused = true;
        inactive = false;

        image = new BufferedImage(level.getMapTileWidth() * Config.tileSize, level.getMapTileHeight() * Config.tileSize, BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) image.getGraphics();

        setStartVisibleArea();
        car = new Car(level.getStartX(), level.getStartY(), player);

        timer = new Timer(1000 / FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isRunning) {
                    requestFocus();

                    if (!inactive) {
                        render();
                        draw();
                    }

                    if (!paused) {
                        update();
                    }

                    if (gameOver) {

                    }
                }
            }
        });
        timer.start();

        timeCounter = new Timer(Config.interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!paused){
                timePoints = timePoints - Config.decrease;

                    if(timePoints < 0){
                        timePoints = 0;
                        timeCounter.stop();
                    }
                }
            }

        });

        timeCounter.start();
    }

    /**
     * Metoda sprawdzająca kolizję między dynamicznymi obiektami na planszy
     * oraz między nimi a ścianami. Przyjmuje obiekt, do którego wpisuje
     * informacje o uszkodzeniu pojazdu lub zakończeniu rozgrywki.
     */
    private void detectCollisions(Map<String, Boolean> status) {

        int nearestX = (int) Math.round(car.getX() / Config.tileSize);
        int nearestY = (int) Math.round(car.getY() / Config.tileSize);

        //Sprawdza czy pojazd dotyka ściany
        for (Tile tile : tilesAroundPoint(nearestX, nearestY)) {
            if (Entity.areCollided(car, tile)) {
                switch (tile.getType()) {
                    case BLOCKED:
                        status.replace("carCrashed", true);
                        break;
                    case FINISH:
                        status.replace("levelPassed", true);
                        break;
                    default:
                }
            }
        }

        //Sprawdza czy pojazd jest poza granicami mapy
        if (car.getY() > y2 - Config.carSize * 3 / 4) {
            status.replace("carCrashed", true);
        }

        //Sprawdza czy pojazd uderzył w przeciwnika
        for (Enemy enemy : level.getEnemies()) {

            int enemyX = (int) Math.round(enemy.getX() / Config.tileSize);
            int enemyY = (int) Math.round(enemy.getY() / Config.tileSize);

            for (Tile tile : tilesAroundPoint(enemyX, enemyY)) {
                if (tile.getType() == Tile.Type.BLOCKED && Entity.areCollided(enemy, tile)) {
                    enemy.toggleMoveDirection();
                }
            }

            if (Entity.areCollided(car, enemy)) {
                status.replace("carCrashed", true);
            }
        }

        //Sprawdza czy pojazd dotyka znajdźki
        for (Iterator<Item> iter = level.getItems().iterator(); iter.hasNext(); ) {
            Item item = iter.next();
            if (Entity.areCollided(car, item)) {
                item.fireItemCollectEvent();
                iter.remove();
            }
        }

        //Sprawdza czy pocisk uderzył ściany, jeśli tak znika
        for (Iterator<Projectile> iter = level.getProjectiles().iterator(); iter.hasNext(); ) {
            Projectile projectile = iter.next();

            boolean removeProjectile = false;

            int bulletX = (int) Math.round(projectile.getX() / Config.tileSize);
            int bulletY = (int) Math.round(projectile.getY() / Config.tileSize);

            for (Tile tile : tilesAroundPoint(bulletX, bulletY)) {
                if (tile.getType() == Tile.Type.BLOCKED && Entity.areCollided(projectile, tile)) {
                    removeProjectile = true;
                }
            }

            for (Iterator<Enemy> iterEnemy = level.getEnemies().iterator(); iterEnemy.hasNext(); ) {
                Enemy enemy = iterEnemy.next();

                if (Entity.areCollided(projectile, enemy)) {
                    removeProjectile = true;
                    iterEnemy.remove();
                    player.addScore(Config.enemyPoints);
                }
            }

            if (removeProjectile) iter.remove();
        }

    }

    /**
     * Metoda zajmujaca sie odswiezeniem stanu mapy
     */
    private void update() {

        updateVisibleMapArea();
        car.updateCar();
        level.update();

        Map<String, Boolean> status = new HashMap<>();
        status.put("carCrashed", false);
        status.put("levelPassed", false);
        status.put("gameOver", false);

        detectCollisions(status);

        if (status.get("carCrashed")) {
            player.decreaseLives();
            if (player.getLives() >= 0) {
                setStartVisibleArea();
                car.moveTo(level.getStartX(), level.getStartY());
                car.reset();
                togglePause();
            } else {
                System.out.println("Koniec gry");
                timer.stop();
                status.replace("gameOver", true);
                gameOver = true;
            }
        }

        if (status.get("levelPassed")) {
            System.out.println("Poziom ukończony");

            switch (dif){
                case "Easy":
                    this.getPlayer().addScore(Config.easyPoints);
                    break;
                case "Normal":
                    this.getPlayer().addScore(Config.normalPoints);
                    break;
                case "Hard":
                    this.getPlayer().addScore(Config.hardPoints);
                    break;
                default:
                    break;
            }
            this.getPlayer().addScore(timePoints);
            this.getPlayer().addScore(getPlayer().getLives() * Config.pointsLives);
            this.player.levelPassed();
            timer.stop();
            updateSummary();
            //Wyświetlenie panelu zakończenia poziomu
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, MainWindow.LEVELPASSEDPANEL);
            cardLayout.show(cards, MainWindow.LEVELPASSEDPANEL);
        }

        if (status.get("gameOver")) {
            System.out.println("Koniec gry");

            timer.stop();
            updateSummary();
            //Wyświetlenie panelu zakończenia poziomu
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, MainWindow.GAMEOVERPANEL);
        }


        g2d = (Graphics2D) image.getGraphics();
    }

    /**
     * Metoda zajmujaca sie renderowaniem mapy
     */
    private void render() {
        level.draw(g2d, this.getWidth(), this.getHeight(), this);
        car.draw(g2d, this.getWidth(), this.getHeight(), this);
        drawInfo(g2d);
    }

    /**
     * Przygotowuje podsumowanie rozgrywki do wyświetlenia
     */

    private String prepareSummary() {

        if(gameOver){
            return  "Koniec gry\n\n" + "Podsumowanie rozgrywki:\n\n" + "Liczba punktów: \t" + getPlayer().getScore() +"\nUkończone poziomy: \t" + player.getNumberOfPassedLevels() + "\n\n\n";

        }
        else{
        return  "Podsumowanie rozgrywki:\n\n" + "Liczba punktów: \t" + getPlayer().getScore() + "\nLiczba żyć: \t\t" + getPlayer().getLives() +"\nUkończone poziomy: \t" + player.getNumberOfPassedLevels() + "\n\n\n";
    }}

    /**
     * Aktualizuje podsumowanie rozgrywki
     */
    private void updateSummary(){

        summaryTextArea.setText(prepareSummary());
    }


    /**
     * Metoda ustawiająca początkowy fragment mapy do wyświetlenia
     */
    private void setStartVisibleArea() {
        y1 = (level.getMapTileHeight() - Config.visibleMapHeight) * Config.tileSize;
        y2 = level.getMapTileHeight() * Config.tileSize;
    }

    /**
     * Metoda aktualizująca fragment mapy do wyświetlenia
     * Szybkość przewijania jest zależna od poziomu trudności i jest odczytywana z pliku konfiguraycjnego
     * Gdy pojazd osiągnie 1/4 wysokości okna, prędkości przewijania wynosi tyle co prędkość samochodu
     */
    private void updateVisibleMapArea() {

        float scrollSpeed;

        if (car.getY() < y1 + (y2 - y1) / 4) {
            scrollSpeed = (float) (car.getSpeed() * Math.cos(car.getAngle()));
        } else scrollSpeed = this.scrollSpeed;

        if (y1 > 0) {
            y1 = y1 - scrollSpeed / FPS;
            y2 = y2 - scrollSpeed / FPS;
        }
    }

    /**
     * Metoda zajmujaca sie rysowaniem mapy na ekranie
     */
    public void draw() {
        Graphics g2d = getGraphics();

        g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), 0, Math.round(y1), level.getMapTileWidth() * Config.tileSize, Math.round(y2), null);
        g2d.dispose();

        this.requestFocus();
    }

    /**
     * Metoda rysująca na ekranie informacje o trwającej rozgrywce
     * Takie jak: nick, punkty, liczba żyć
     *
     * @param g2d obiekt odpowiedzialny za rysowanie na ekranie
     */
    public void drawInfo(Graphics2D g2d) {

        //Zmiana czcionki
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g2d.setColor(Color.black);
        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0 + 10, (int) y1 + 5, 150, 100);

        g2d.setColor(Color.white);
        g2d.drawString("Gracz: " + player.getNick(), 0 + 20, y1 + 25);
        g2d.drawString("Wynik: " + player.getScore(), 0 + 20, y1 + 50);
        g2d.drawString("Życia: " + player.getLives(), 0 + 20, y1 + 75);
        g2d.drawString("Bonus:" + timePoints, 0 + 20, y1 + 100);
    }

    /**
     * Przełącza stan zapauzowania (nieobliczania kolejnych klatek).
     */
    public void togglePause() {
        paused = !paused;
    }

    /**
     * Przełącza stan nieaktywności (nierysowania).
     */
    public void toggleInactive() {
        inactive = !inactive;
    }

    /**
     * Getter do obiektu reprezentującego gracza.
     *
     * @return obiekt reprezentujący gracza
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Metoda dziedziczona z KeyListener, nie wykonuje żadnych działań.
     *
     * @param e zdarzenie z klawiatury
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metoda dziedziczona z KeyListener, przyjmuje zdarzenie z klawiatury
     * związane z naciśnięciem klawisza, z którego wydobywa informacje
     * o naciśniętym klawiszu. Na podstawie tego wywołuje odpowiednie metody
     * służące za sterowanie pojazdem albo wywołanie odpowiedniej opcji interfejsu.
     *
     * @param e zdarzenie z klawiatury
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_LEFT:
                keyStateLeft = KeyState.DOWN;

                if (keyStateRight == KeyState.UP) {
                    car.setRotDirection(Car.RotDirection.LEFT);
                }

                // System.out.println("Left pressed");
                break;

            case KeyEvent.VK_RIGHT:
                keyStateRight = KeyState.DOWN;

                if (keyStateLeft == KeyState.UP) {
                    car.setRotDirection(Car.RotDirection.RIGHT);
                }

                // System.out.println("Right pressed");
                break;

            case KeyEvent.VK_UP:

                keyStateAccelerate = KeyState.DOWN;

                if (keyStateBrake == KeyState.UP) {
                    car.setAccelState(Car.AccelState.ACCEL);
                }

                if (paused) {
                    togglePause();
                }

                // System.out.println("Up pressed");
                break;

            case KeyEvent.VK_DOWN:
                keyStateBrake = KeyState.DOWN;

                if (keyStateAccelerate == KeyState.UP) {
                    car.setAccelState(Car.AccelState.BRAKE);
                }

                // System.out.println("Down pressed");
                break;

            case KeyEvent.VK_ESCAPE:

                if (!paused) {
                    togglePause();
                }

                if (!inactive) {
                    toggleInactive();
                }

                //Wyświetlenie menu
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, MainWindow.PAUSEPANEL);

                break;

            case KeyEvent.VK_P:

                if (!paused) {
                    togglePause();
                }

                break;
            case KeyEvent.VK_SPACE:
                level.addProjectile(car.createProjectile());
                break;

            default:
                break;
        }

    }

    /**
     * Metoda dziedziczona z KeyListener. Przyjmuje zdarzenie z klawiatury
     * związane z puszczeniem klawisza. W zależności od tego, który klawisz
     * został puszczony, cofa zmiany wywołane naciśnięciem go albo wywołuje
     * metody analogiczne do tych, które zostały wywołane w przypadku naciśnięcia.
     *
     * @param e zdarzenie z klawiatury
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_LEFT:
                keyStateLeft = KeyState.UP;

                if (keyStateRight == KeyState.DOWN) {
                    car.setRotDirection(Car.RotDirection.RIGHT);
                } else {
                    car.setRotDirection(Car.RotDirection.NONE);
                }

                // System.out.println("Left released");
                break;

            case KeyEvent.VK_RIGHT:
                keyStateRight = KeyState.UP;

                if (keyStateLeft == KeyState.DOWN) {
                    car.setRotDirection(Car.RotDirection.LEFT);
                } else {
                    car.setRotDirection(Car.RotDirection.NONE);
                }

                // System.out.println("Right released");
                break;

            case KeyEvent.VK_UP:
                keyStateAccelerate = KeyState.UP;

                if (keyStateBrake == KeyState.DOWN) {
                    car.setAccelState(Car.AccelState.BRAKE);
                } else {
                    car.setAccelState(Car.AccelState.NONE);
                }

                // System.out.println("Up released");
                break;

            case KeyEvent.VK_DOWN:
                keyStateBrake = KeyState.UP;

                if (keyStateAccelerate == KeyState.DOWN) {
                    car.setAccelState(Car.AccelState.ACCEL);
                } else {
                    car.setAccelState(Car.AccelState.NONE);
                }

                // System.out.println("Down released");
                break;

            default:
                break;
        }

    }

    /**
     * Metoda przyjmuje współrzędne indeksowe na planszy (zgodnie z rozmieszczeniem
     * kafelków) jednego kafelka i zwraca go oraz 8 kafelków wokół niego. Służy na przykład
     * do uzyskania kafelków, z którymi może wejść w interakcję jakiś obiekt dynamiczny.
     *
     * @param px współrzędna indeksowa x
     * @param py współrzędna indeksowa y
     * @return listę kafelków
     */
    private java.util.List<Tile> tilesAroundPoint(int px, int py) {
        java.util.List<Tile> list = new ArrayList<>();

        for (int x = px - 1; x <= px + 1; x++) {
            for (int y = py - 1; y <= py + 1; y++) {
                try {
                    list.add(level.getTile(x, y));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Nie istnieje kafelek (" + x + ", " + y + ")");
                }
            }
        }

        return list;
    }

    /**
     * Typ określający stan klawisza
     * Służy do opisu informacji o klawiszach sterowania
     * UP - przycisk niewciśnięty
     * DOWN - przycisk wciśnięty
     */
    private enum KeyState {
        UP, DOWN
    }

    /**
     * EventListener nasłuchujący Eventów związanych z zebraniem znajdźki
     * (item collection) przez gracza na planszy. Eventy odpalane są przez
     * znajdźki, metoda sprawdza rodzaj znajdźki poprzez źródło Eventa
     * i aplikuje stosowne bonusy.
     * <p>
     * W przypadku bonusu Powerup, tworzony jest Timer, po którego zakończeniu
     * wzmocnienie jest anulowane.
     */
    private class ItemCollectionHandler implements ItemCollectEventListener {

        public void powerupCollected(ItemCollectEvent ice) {

            switch (ice.getItemType()) {
                case BONUS:
                    ItemBonus itembonus = (ItemBonus) ice.getSource();
                    switch (itembonus.getBonusLevel()) {
                        case LEVEL1:
                            player.addScore(Config.bonus1Points);
                            break;
                        case LEVEL2:
                            player.addScore(Config.bonus2Points);
                            break;
                        case LEVEL3:
                            player.addScore(Config.bonus3Points);
                            break;
                        default:
                            break;
                    }
                    break;
                case POWERUP:
                    ItemPowerup itempowerup = (ItemPowerup) ice.getSource();
                    /*
                     * Ustawia prędkość maksymalną pojazdu na półtora raza większą
                     * niż podstawowa i uruchamia Timer, który po czasie określonym
                     * w pliku konfiguracyjnym wykona TimerTask przywracający
                     * pojazdowi domyślną prędkość
                     */
                    car.setMaxSpeed(Config.vehMaxSpeed * 1.5);
                    car.setRotSpeed(Config.vehRotSpeed * 1.5);
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    car.setMaxSpeed(Config.vehMaxSpeed);
                                    car.setRotSpeed(Config.vehRotSpeed);
                                }
                            },
                            itempowerup.getPowerupDuration()
                    );
                    break;
                default:
                    break;
            }
        }
    }

}

