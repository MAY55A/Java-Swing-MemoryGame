import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sounds {

    public static void playSound(String event) {
        File soundFile = new File("sounds\\"+event+".wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void playClickSound() {
        playSound("buttonClick");
    }
    public static void playRadioClickSound() {
        playSound("pick");
    }
    public static void playMatchingCardSound() {
        playSound("plus");
    }
    public static void playFlipSound() {
        playSound("flipCard");
    }
    public static void playGameOverSound() {
        playSound("GameOver");
    }

    public static void playNewScoreSound() {
        playSound("newHighScore");
    }
    public static void playWinSound() {
        playSound("win");
    }
    static class timerSound {
        File soundFile = new File("sounds\\timer.wav");
        Clip clip;

        public timerSound() {
            try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public void play() {
                clip.setFramePosition(0);
                clip.start();
        }
        public void stop() {
            clip.stop();
        }
    }
}
