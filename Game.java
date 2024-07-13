import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel {
    GameManager gm;
    GameBoard gb;
    GameFrame gf;
    JPanel scorePanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JPanel timerPanel = new JPanel();
    JTextField player = new JTextField("player", 20);
    JButton startbtn = new JButton("start game");
    JLabel attempts = new JLabel();
    JLabel matches = new JLabel();
    JLabel myHighestScore = new JLabel();
    JLabel highestScore = new JLabel();
    JLabel modeLabel = new JLabel();
    JLabel time;
    JPanel gameOverPanel = new JPanel();
    JLabel endGame = Label.endGame();
    JLabel newScore = Label.newScore();
    String mode;
    int nbcards;
    public Game(GameFrame gf, String mode, String theme) {
        this.gf = gf;
        this.mode = mode;
        modeLabel.setText(mode+" Mode");
        if(mode.equals("Easy"))
            nbcards = 3;
        else if(mode.equals("Normal"))
            nbcards = 4;
        else
            nbcards = 5;
        this.gm = new GameManager(this);
        this.gb = new GameBoard(theme, nbcards, gm);

        time = Label.timer((1+nbcards)*10);
        setLayout(new BorderLayout(50, 50));
        startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClickSound();
                resetGame();
                gm.startGame(player.getText());
            }
        });
        scorePanel.add(Label.stats(" attempts   "));
        scorePanel.add(attempts);
        scorePanel.add(Label.stats("   matched pairs   "));
        scorePanel.add(matches);
        scorePanel.add(Label.stats("   my highest score   "));
        scorePanel.add(myHighestScore);
        scorePanel.add(Label.stats("   highest score   "));
        scorePanel.add(highestScore);
        player.setMinimumSize(new Dimension(200,50));
        buttonsPanel.add(player);
        buttonsPanel.add(Box.createHorizontalStrut(100));
        buttonsPanel.add(startbtn);
        timerPanel.add(modeLabel);
        timerPanel.add(Box.createVerticalStrut(30));
        timerPanel.add(new JLabel("timer :  "));
        timerPanel.add(time);
        timerPanel.setPreferredSize(new Dimension(150,300));

        gameOverPanel.add(endGame);
        gameOverPanel.add(newScore);
        add(gb, "Center");
        add(scorePanel, "North");
        add(buttonsPanel, "South");
        add(timerPanel, "West");
    }
    public void resetGame() {
        if(gm.gameStarted) {
            gm.endGame();
        }
        gb.reset();
        add(gb, "Center");
        gb.setVisible(true);
        gameOverPanel.setVisible(false);
    }
    public void showEndGameMessage(String msg1, String msg2) {
        endGame.setText(msg1);
        newScore.setText(msg2);
        gb.setVisible(false);
        add(gameOverPanel, "Center");
        gameOverPanel.setVisible(true);
    }
}
