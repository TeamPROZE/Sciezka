package Sciezka.game;

import Sciezka.*;
import Sciezka.util.UtilHighScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Klasa okna głównego
 * <p>
 * Jedna jej instancja tworzona jest zawsze przy uruchomieniu programu
 *
 * @author Kacper Bloch
 * @author Kamil Dzierżanowski
 * @version 1.0
 */
public class MainWindow extends JFrame {

    /**
     * Identyfikator panelu menuPanel
     */
    final static String MENUPANEL = "menuPanel";
    /**
     * Identyfikator panelu hifhScoresPanel
     */
    final static String HIGHSCORESPANEL = "heighScoresPanel";
    /**
     * Identyfikator panelu startPanel
     */
    final static String STARTPANEL = "startPanel";
    /**
     * Identyfikator panelu gamePanel
     */
    final static String GAMEPANEL = "gamePanel";
    /**
     * Identyfikator panelu gamePanel
     */
    final static String PAUSEPANEL = "pausePanel";
    /**
     * Identyfikator panelu levelPassedPanel
     */
    final static String LEVELPASSEDPANEL = "levelPassedPanel";
    /**
     * Identyfikator panelu gameOverPanel
     */
    final static String GAMEOVERPANEL = "gameOver";

    /**
     * Panel głowny layoutu CardLayout
     */
    private JPanel cards;
    /**
     * Panel z zawartoscia menu glownego
     */
    private JPanel menuPanel;
    /**
     * Panel wyswietlajacy Liste Wynikow
     */
    private JPanel highScoresPanel;
    /**
     * Panel w ktorym wybieramy poziom trudnosci i podajemy nick gracza
     */
    private JPanel startPanel;
    /**
     * Pole tekstowe, do którego wprowadzana jest nazwa gracza
     */
    private JTextField nicknameTextField;
    /**
     * Panel wyświetlający animacje
     */
    private GamePanel gamePanel;
    /**
     * Panel menu Pauzy podczas trwania rozgrywki
     */
    private JPanel pausePanel;
    /**
     * Panel ekranu zakończenia poziomu
     */
    private JPanel levelPassedPanel;
    /**
     * Panel zakończenia gry
     */
    private JPanel gameOverPanel;

    /**
     * Identyfikator panelu rulesPANEL
     */
    final static String RULESPANEL = "rulesPanel";

    /**
     * Panel wyświetlający zasady gry
     */
    private JPanel rulesPanel;

    /**
     * Pole tekstowe wyswietlające liste najlepszych wyników
     */
    private JTextArea hsTextArea;

    /**
     * Pole tekstowe wyświetlające podsumowanie rozgrywki
     */
    private JTextArea summaryTextArea;

    /**
     * Lista najlepszych wyników
     * Przechowuje obiekty typu Score - reprezentujące najlepsze wyniki
     */
    private List<Score> highScoresList;

    /**
     * Określa, który poziom powinien zostać wyświetlony
     */
    private int currentLevelNumber;

    /**
     * Konstruktor Menu głównego
     * Inicjalizuje instancję poprzez init()
     */
    public MainWindow() {
        init();
    }

    /**
     * Tworzy okno główne i wszystkie panele menu
     */
    private void init() {

        this.setTitle(Config.appName);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Config.windowWidth, Config.windowHeight);
        this.currentLevelNumber = 1;

        if (Main.online) {
            highScoresList = Client.getHighscores();
        } else {
            highScoresList = UtilHighScores.readHighScores(Config.filenameHighscores);
        }

        // Wyśrodkuj na ekranie
        this.setLocationRelativeTo(null);

        cards = new JPanel(new CardLayout());
        createMenu();
        createStart();
        createHighScores();
        createMenuPause();
        createLevelPassedPanel();
        createRulesPanel();
        creategameOverPanel();


        cards.add(menuPanel, MENUPANEL);
        cards.add(highScoresPanel, HIGHSCORESPANEL);
        cards.add(startPanel, STARTPANEL);
        cards.add(pausePanel, PAUSEPANEL);
        cards.add(levelPassedPanel, LEVELPASSEDPANEL);
        cards.add(rulesPanel, RULESPANEL);
        cards.add(gameOverPanel, GAMEOVERPANEL);

        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, MENUPANEL);

        this.getContentPane().add(cards);
    }


    /**
     * Metoda tworzy panel wyświetlający zasady
     */
    private void createRulesPanel(){
        rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setHighlighter(null);

        try{
            BufferedReader reader = new BufferedReader(new FileReader("zasady.txt"));
            textArea.read(reader, null);
            reader.close();
        }
        catch (Exception e){

        }

        rulesPanel.add(textArea, BorderLayout.CENTER);

        JButton back = new JButton(Config.menuHighscoreBack);
        back.addActionListener(new BackButtonListener());
        rulesPanel.add(back, BorderLayout.SOUTH);

    }


    /**
     * Metoda przyjmuje numer poziomu, począwszy od 1, a następnie
     * próbuje wczytać ten poziom z serwera (jeżeli gra funkcjonuje w trybie
     * online) albo z pliku lokalnego. Następnie zwraca ten poziom.
     *
     * @param levelnumber numer poziomu (zaczynając od 1)
     * @return wczytany poziom
     */
    private Level acquireLevel(int levelnumber) {
        Level level;

        if (Main.online) {
            level = new Level(Client.getLevel(levelnumber));
            System.out.println("Map " + levelnumber + " loaded from server");
        } else {
            level = new Level(Config.filenameLevel[levelnumber - 1]);
        }

        return level;
    }

    /**
     * Wyświetla okno główne.
     */
    public void showWindow() {

        this.setVisible(true);
    }

    /**
     * Tworzy panel menu głównego.
     */
    private void createMenu() {

        menuPanel = new JPanel((new GridBagLayout()));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //Obwodka ekranu, czysto estetyczna

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        JButton startButton = new JButton(Config.menuStart);
        startButton.addActionListener(new StartButtonListener());
        menuPanel.add(startButton, c);

        c.gridy = 1;
        JButton highScoresButton = new JButton(Config.menuHighscore);
        highScoresButton.addActionListener(new HighscoresButtonListener());
        menuPanel.add(highScoresButton, c);


        c.gridy = 2;
        JButton rulesButton = new JButton(Config.menuRules);
        rulesButton.addActionListener(new RulesButtonListener());
        menuPanel.add(rulesButton, c);


        c.gridy = 3;
        JButton endButton = new JButton(Config.menuExit);
        endButton.addActionListener(new QuitButtonListener());
        menuPanel.add(endButton, c);

        c.gridy = 4;
        c.insets = new Insets(50, 10, 10, 10);
        JLabel connectionInfo = new JLabel();
        if(Main.online){
            connectionInfo.setText("Tryb: online");
            connectionInfo.setForeground(Color.GREEN);
        }
        else {
            connectionInfo.setText("Tryb: offline");
            connectionInfo.setForeground(Color.RED);
        }
        menuPanel.add(connectionInfo,c);

    }

    /**
     * Tworzy panel na koniec poziomu, w którym można wybrać,
     * czy grać dalej czy zakończyć rozgrywkę.
     */
    private void createLevelPassedPanel() {
        levelPassedPanel = new JPanel(new GridBagLayout());
        levelPassedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        c.gridy = 1;

        JButton continueButton = new JButton(Config.continueButton);
        continueButton.addActionListener(e -> {
            currentLevelNumber++;
            if (!(currentLevelNumber > Config.numberOfLevels)) {

                Level level = acquireLevel(currentLevelNumber);

                /*
                 * Określa poziom trudności. Może przyjąć wartości
                 * - Easy
                 * - Medium
                 * - Hard
                 */
                String difficulty = e.getActionCommand();

                gamePanel = new GamePanel(difficulty, level, getCards(), summaryTextArea, gamePanel.getPlayer());
                cards.add(gamePanel, GAMEPANEL);
                nicknameTextField.setText(Config.defaultNickname);
                nicknameTextField.setForeground(Color.black);

                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, GAMEPANEL);
            }
            else{
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, GAMEOVERPANEL);
                JOptionPane.showMessageDialog(null,Config.dialogMessage);
            }
        });

        levelPassedPanel.add(continueButton, c);

        c.gridy = 2;
        JButton endGameButton = new JButton(Config.endGameButton);
        endGameButton.addActionListener(new LeaveGameButtonListener());
        levelPassedPanel.add(endGameButton, c);

    }


    private void creategameOverPanel() {
        gameOverPanel = new JPanel(new GridBagLayout());
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        summaryTextArea = new JTextArea();

        summaryTextArea.setEditable(false);
        summaryTextArea.setHighlighter(null);
        summaryTextArea.setBackground(gameOverPanel.getBackground());

        gameOverPanel.add(summaryTextArea, c);


        c.gridy = 1;

        JButton endGameButton = new JButton(Config.endGameButton);
        endGameButton.addActionListener(new LeaveGameButtonListener());
        gameOverPanel.add(endGameButton, c);

    }

    /**
     * Tworzy panel wyników.
     */
    private void createHighScores() {

        highScoresPanel = new JPanel(new BorderLayout());
        highScoresPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //Obwodka ekranu, czysto estetyczna

        JPanel gbl = new JPanel(new GridBagLayout());
        highScoresPanel.add(gbl);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        hsTextArea = new JTextArea(Config.highScoresRows, Config.highScoresColumns);

        hsTextArea.setText(prepareHighScores());
        hsTextArea.setEditable(false);
        hsTextArea.setHighlighter(null);
        hsTextArea.setBackground(highScoresPanel.getBackground());
        gbl.add(hsTextArea, c);

        JButton backButton = new JButton(Config.menuHighscoreBack);
        backButton.addActionListener(new BackButtonListener());
        highScoresPanel.add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Metoda przygotowująca liste najlepszych wyników do wyświetlenia
     */
    private String prepareHighScores() {

        String text = "Najlepsze wyniki:\n";
        int i = 1;
        for (Score s : highScoresList) {
            text = text + "\n" + i + "." + "\t" + s.getNick() + "\t\t" + s.getScore() + "      ";
            i++;
        }

        return text;
    }

    /**
     * Aktualizuje wyświetlona wyniki
     */
    private void updateHighScores() {

        hsTextArea.setText(prepareHighScores());

    }



    /**
     * Tworzy Panel rozpoczęcia gry
     */
    private void createStart() {

        startPanel = new JPanel(new BorderLayout());
        startPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //Obwodka ekranu, czysto estetyczna

        JPanel buttons = new JPanel(new GridBagLayout());
        startPanel.add(buttons);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        c.insets = new Insets(10, 10, 10, 10);
        JLabel nickLabel = new JLabel(Config.defaultNickname);
        buttons.add(nickLabel, c);

        c.insets = new Insets(1, 10, 30, 10);
        c.gridy = 1;
        nicknameTextField = new JTextField(Config.defaultNickname, 20);
        buttons.add(nicknameTextField, c);

        c.insets = new Insets(5, 10, 10, 10);

        c.gridy = 2;
        JButton button1 = new JButton(Config.menuStartEasy);
        button1.addActionListener(new RunButtonListener());
        button1.setActionCommand("Easy");
        buttons.add(button1, c);

        c.gridy = 3;
        JButton button2 = new JButton(Config.menuStartMedium);
        button2.addActionListener(new RunButtonListener());
        button2.setActionCommand("Medium");
        buttons.add(button2, c);

        c.gridy = 4;
        JButton button3 = new JButton(Config.menuStartHard);
        button3.addActionListener(new RunButtonListener());
        button3.setActionCommand("Hard");
        buttons.add(button3, c);

        nicknameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nicknameTextField.getText().trim().equals(Config.defaultNickname)) {
                    nicknameTextField.setText("");
                    nicknameTextField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nicknameTextField.getText().trim().equals("")) {
                    nicknameTextField.setText(Config.defaultNickname);
                }
            }
        });

        JButton backButton = new JButton(Config.menuStartBack);
        backButton.addActionListener(new BackButtonListener());
        startPanel.add(backButton, BorderLayout.SOUTH);

    }

    /**
     * Tworzy panel menu pauzy rozgrywki
     */
    private void createMenuPause() {
        pausePanel = new JPanel(new GridBagLayout());
        pausePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //Obwodka ekranu, czysto estetyczna

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        JButton button1 = new JButton("Kontynuuj");
        button1.addActionListener(e -> {

            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, GAMEPANEL);

            gamePanel.toggleInactive();
        });

        pausePanel.add(button1, c);

        c.gridy = 1;
        JButton endGameButton = new JButton(Config.endGameButton);
        endGameButton.addActionListener(new LeaveGameButtonListener());
        pausePanel.add(endGameButton, c);

    }

    /**
     * Metoda zwraca panel główny layoutu CardLayout
     *
     * @return panel główny layoutu CardLayout
     */
    private JPanel getCards() {

        return cards;
    }

    /**
     * Słuchacz przycisku Start w menu głównym
     * <p>
     * Wyświetla Panel z wyborem poziomu trudności
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, STARTPANEL);
        }
    }

    /**
     * Sluchacz przecisku Najlepsze wyniki w menu glownym
     * <p>
     * Wyswietla panel z najlepszymi wynikami
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class HighscoresButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, HIGHSCORESPANEL);
        }
    }

    /**
     * Sluchacz przecisku Opcje w menu glownym
     * <p>
     * Wyswietla panel opcji
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class RulesButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, RULESPANEL);
        }
    }

    /**
     * Sluchacz przycisku Koniec w menu glownym
     * <p>
     * Konczy dzialanie gry
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class QuitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.exit(0);
        }
    }

    /**
     * Sluchacz przycisku Wroc do menu glownego w panelu highScoresPanel i startPanel
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, MENUPANEL);
            nicknameTextField.setText(Config.defaultNickname);
            nicknameTextField.setForeground(Color.black);
        }
    }

    /**
     * Sluchacz przycisku Rozpocznij gre w panelu startPanel
     * <p>
     * Wyswietla ekran gry i ja rozpoczyna
     *
     * @author Kacper Bloch
     * @author Kamil Dzierżanowski
     * @version 1.0
     */
    public class RunButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!nicknameTextField.getText().trim().equals(Config.defaultNickname) && !nicknameTextField.getText().trim().equals(Config.defaultNickname) && (!nicknameTextField.getText().contains("/") && !nicknameTextField.getText().contains("#"))) {

                Level level = acquireLevel(currentLevelNumber);

                /*
                 * Określa poziom trudności. Może przyjąć wartości "Easy", "Medium", "Hard"
                 */
                String difficulty = e.getActionCommand();

                gamePanel = new GamePanel(difficulty, level, getCards(), summaryTextArea, nicknameTextField.getText());
                cards.add(gamePanel, GAMEPANEL);
                nicknameTextField.setText(Config.defaultNickname);
                nicknameTextField.setForeground(Color.black);

                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, GAMEPANEL);

            } else {
                nicknameTextField.setForeground(Color.RED);
            }

        }
    }

    /**
     * Słuchacz przycisku wyjścia z gry podczas trwania rozgrywki.
     * Zajmuje się sprawdzeniem, czy wynik uzyskany w sesji przez gracza
     * nadaje się na listę najlepszych wyników na serwerze albo lokalnie
     * i podejmuje odpowiednie działanie. Aktualizuje lokalnie spis najlepszych
     * wyników w menu.
     *
     * @author Kamil Dzierżanowski
     * @author Kacper Bloch
     * @version 1.0
     */
    public class LeaveGameButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevelNumber = 1;

            Player player = gamePanel.getPlayer();
            List<Score> newHSList;

            if (Main.online) {
                boolean newhigh = Client.sendGameScore(player.getNick(), player.getScore());
                if (newhigh) {
                    newHSList = Client.getHighscores();
                    UtilHighScores.writeHighScores(newHSList, Config.filenameHighscores);
                    highScoresList = newHSList;
                }
            } else {
                newHSList = UtilHighScores.updateHighScores(highScoresList, player);
                UtilHighScores.writeHighScores(newHSList, Config.filenameHighscores);
                highScoresList = newHSList;
            }

            updateHighScores();
            CardLayout cardLayout = (CardLayout) cards.getLayout();
            cardLayout.show(cards, MainWindow.MENUPANEL);
        }
    }

}
