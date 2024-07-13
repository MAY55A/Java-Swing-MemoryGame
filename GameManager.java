import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager {
    Game game;
    Scores.ManipScores scores;
    boolean gameStarted = false;
    boolean selectionEnabled = true;
    Timer timer;
    Sounds.timerSound timerSound = new Sounds.timerSound();
    int timeLeft;
    int matches = 0;
    int attempts = 0;
    int score = 100;
    Card lastSelected;
    public GameManager(Game game) {
        this.game = game;
        this.timeLeft = (game.nbcards+1) * 10;
    }

    private void initializeTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTimeLeft(timeLeft - 1);
                score--;
                if(timeLeft < 10)
                    game.time.setForeground(Color.RED);
                if(timeLeft == 0) {
                    timer.stop();
                    timerSound.stop();
                    Sounds.playGameOverSound();
                    game.endGame.setForeground(Color.RED);
                    game.showEndGameMessage("GAME OVER", "You Lost");
                }
            }
        });
    }
    public void startGame(String player) {
        initializeTimer();
        scores = new Scores.ManipScores(player, game.mode);
        game.myHighestScore.setText(String.valueOf(scores.myHighestScore));
        game.highestScore.setText(String.valueOf(scores.highestScore));
        lastSelected = null;
        gameStarted = true;
        timerSound.play();
        timer.start();
        game.startbtn.setText("new game");
    }
    public void endGame() {
        gameStarted = false;
        timer.stop();
        timerSound.stop();
        setTimeLeft((1+game.nbcards)*10);
        setAttempts(0);
        setMatches(0);
        score = 100;
        game.time.setForeground(Color.BLACK);
        game.startbtn.setText("start game");
    }
    public void selectCard(Card card) {
        if(!card.isFlipped() && selectionEnabled) {
            setAttempts(attempts + 1);
            score--;
            card.setFlipped(true);
            Sounds.playFlipSound();
            if (lastSelected != null) {
                if (card.equals(lastSelected)) {
                    Sounds.playMatchingCardSound();
                    setMatches(matches + 1);
                    lastSelected = null;
                    if(matches == game.nbcards*2) {
                        timer.stop();
                        timerSound.stop();
                        Sounds.playWinSound();
                        String msg1 = "YOU WON";
                        String msg2 = "score : " + score;
                        game.endGame.setForeground(Color.orange);
                        if(score > scores.myHighestScore) {
                            Sounds.playNewScoreSound();
                            scores.updateScores(scores.player + "," + score + "," + attempts + "," + timeLeft);
                            game.gf.scores.loadScores();
                            if (score > scores.highestScore)
                                msg2 = "New highest score : " + score;
                            else
                                msg2 = "Your new highest score : " + score;
                        }
                        game.showEndGameMessage(msg1, msg2);
                    }
                } else {
                    selectionEnabled = false;
                    Timer timer = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Sounds.playFlipSound();
                            card.setFlipped(false);
                            lastSelected.setFlipped(false);
                            lastSelected = null;
                            selectionEnabled = true;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            } else {
                lastSelected = card;
            }
        }
    }
    public void setMatches(int sc) {
        this.matches = sc;
        game.matches.setText(getMatches());
    }
    public void setAttempts(int n) {
        this.attempts = n;
        game.attempts.setText(getAttempts());
    }
    public void setTimeLeft(int t) {
        this.timeLeft = t;
        game.time.setText(getTimeLeft());
    }
    public String getMatches() {
        return String.valueOf(matches);
    }

    public String getAttempts() {
        return String.valueOf(attempts);
    }

    public String getTimeLeft() {
        return String.valueOf(timeLeft);
    }
}
