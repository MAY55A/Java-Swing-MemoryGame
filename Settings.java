import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings extends JPanel {
    GameFrame gf;
    JLabel title = Label.title("Settings");
    ButtonGroup radioGroup1 = new ButtonGroup();
    ButtonGroup radioGroup2 = new ButtonGroup();
    JPanel radios = new JPanel();
    JPanel buttons = new JPanel();
    BoxLayout bl = new BoxLayout(radios, BoxLayout.Y_AXIS);
    JRadioButton theme1 = new JRadioButton("Lord Of The Rings", true);
    JRadioButton theme2 = new JRadioButton("Dark Souls");
    JRadioButton theme3 = new JRadioButton("Elden Ring");
    JRadioButton level1 = new JRadioButton("Easy", true);
    JRadioButton level2 = new JRadioButton("Normal");
    JRadioButton level3 = new JRadioButton("Hard");
    JLabel board = new JLabel("3x4 board");
    JLabel timer = new JLabel("30 seconds timer");
    JButton apply = new JButton("Apply Changes");
    JButton cancel = new JButton("Cancel");
    ImageIcon soundIcon = resizeIcon("icons\\volume.png", 50, 50);
    ImageIcon noSoundIcon = resizeIcon("icons\\mute.png", 50, 50);
    JLabel sound = new JLabel(soundIcon);
    File musicFile;
    Clip clip;
    boolean musicOn = true;
    int nbcards = 3;
    String[] modes = {"Easy", "Normal", "Hard"};
    String theme = "LOTR";
    ButtonModel selectedDiff;
    ButtonModel selectedTheme;
    public Settings(GameFrame gf) {
        this.gf = gf;
        gf.game = new Game(gf, modes[nbcards-3], theme);
        setLayout(new BorderLayout(50, 50));
        radios.setLayout(bl);
        selectedDiff = level1.getModel();
        selectedTheme = theme1.getModel();
        playMusic();
        ActionListener diffListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();
                if(src == level1)
                    nbcards = 3;
                else if(src == level2)
                    nbcards = 4;
                else
                    nbcards = 5;
                timer.setText((nbcards+1)*10+" seconds timer");
                board.setText(nbcards+"x4 board");
                Sounds.playRadioClickSound();
            }
        };
        ActionListener themeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();
                Sounds.playRadioClickSound();
                if(src == theme3)
                    theme = "elden ring";
                else if(src == theme2)
                    theme = "dark souls";
                else
                    theme = "LOTR";
            }
        };
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();
                Sounds.playClickSound();
                if(src == apply) {
                    applyChanges();
                } else {
                    radioGroup1.setSelected(selectedDiff, true);
                    radioGroup2.setSelected(selectedTheme, true);
                }
                gf.cl.show(gf.getContentPane(),"main menu");
            }
        };

        radioGroup1.add(level1);
        radioGroup1.add(level2);
        radioGroup1.add(level3);
        radioGroup2.add(theme1);
        radioGroup2.add(theme2);
        radioGroup2.add(theme3);

        level1.addActionListener(diffListener);
        level2.addActionListener(diffListener);
        level3.addActionListener(diffListener);
        theme1.addActionListener(themeListener);
        theme2.addActionListener(themeListener);
        theme3.addActionListener(themeListener);
        apply.addActionListener(buttonListener);
        cancel.addActionListener(buttonListener);

        sound.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(musicOn) {
                    sound.setIcon(noSoundIcon);
                    clip.stop();
                } else {
                    sound.setIcon(soundIcon);
                    clip.start();
                }
                musicOn = !musicOn;
                Sounds.playRadioClickSound();
            }
        });

        radios.setBorder(BorderFactory.createEmptyBorder(50,300,50,100));
        radios.add(Label.settings("Mode :"));
        radios.add(level1);
        radios.add(level2);
        radios.add(level3);
        radios.add(Box.createVerticalStrut(50));
        radios.add(board);
        radios.add(timer);
        radios.add(Box.createVerticalStrut(50));
        radios.add(Label.settings("Theme :"));
        radios.add(theme1);
        radios.add(theme2);
        radios.add(theme3);
        radios.add(Box.createVerticalStrut(50));
        radios.add(sound);

        buttons.add(apply);
        buttons.add(cancel);

        add(title, "North");
        add(radios, "Center");
        add(buttons, "South");

    }
    public void applyChanges() {
        ButtonModel oldDiff = selectedDiff;
        ButtonModel oldTheme = selectedTheme;
        selectedDiff = radioGroup1.getSelection();
        selectedTheme = radioGroup2.getSelection();
        if (oldTheme != selectedTheme || oldDiff != selectedDiff) {
            if (oldTheme != selectedTheme)
                playMusic();
            gf.getContentPane().remove(gf.game);
            gf.game = new Game(gf, modes[nbcards - 3], theme);
            gf.getContentPane().add("game", gf.game);
            revalidate();
            repaint();
        }
    }
    private static ImageIcon resizeIcon(String filePath, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    void playMusic() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
        }
        musicFile = new File("soundtracks\\"+theme+".wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
