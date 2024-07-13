import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scores extends JPanel {
    String file = "scores\\Easy.txt";
    List<String> scores;
    JPanel scoresPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel title = Label.title("Top Scores");
    JPanel modesPanel = new JPanel();
    BoxLayout bl = new BoxLayout(modesPanel, BoxLayout.Y_AXIS);
    ButtonGroup radioGroup = new ButtonGroup();
    JRadioButton mode1 = new JRadioButton("easy mode", true);
    JRadioButton mode2 = new JRadioButton("normal mode");
    JRadioButton mode3 = new JRadioButton("hard mode");
    public Scores() {
        setLayout(new BorderLayout(0,50));

        modesPanel.setLayout(bl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.insets = new Insets(5, 5, 5, 5); // Adjust cell padding
        gbc.weightx = 1.0;

        loadScores();

        ActionListener modeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();
                Sounds.playRadioClickSound();
                if(src == mode1)
                    file = "scores\\Easy.txt";
                else if(src == mode2)
                    file = "scores\\Normal.txt";
                else
                    file = "scores\\Hard.txt";
                loadScores();
            }
        };

        mode1.addActionListener(modeListener);
        mode2.addActionListener(modeListener);
        mode3.addActionListener(modeListener);

        radioGroup.add(mode1);
        radioGroup.add(mode2);
        radioGroup.add(mode3);

        modesPanel.add(mode1);
        modesPanel.add(mode2);
        modesPanel.add(mode3);
        modesPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,100));

        scoresPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(title, "North");
        add(modesPanel, "West");
        add(new JScrollPane(scoresPanel), "Center");
        add(Box.createVerticalStrut(100), "South");
        add(Box.createHorizontalStrut(50), "East");
    }
    void loadScores() {
        try {
            scores = Files.readAllLines(Paths.get(file));
            scoresPanel.removeAll();
            gbc.gridx = 0;
            gbc.gridy = 0;
            for (String header : "Player,Score,Attempts,Time left".split(",")) {
                JLabel headerLabel = new JLabel(header);
                headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                headerLabel.setForeground(Color.blue);
                headerLabel.setFont(Styles.header);
                scoresPanel.add(headerLabel, gbc);
                gbc.gridx++;
            }
            gbc.gridy++;
            for (String score : scores) {
                gbc.gridx = 0;
                for (String value : score.split(",")) {
                    JLabel valueLabel = new JLabel(value);
                    valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    scoresPanel.add(valueLabel, gbc);
                    gbc.gridx++;
                }
                gbc.gridy++;
            }
            revalidate();
            repaint();
        } catch (IOException e) {
            System.out.println("No such file !");
        }
    }
    public static class ManipScores {
        String file;
        String player;
        List<String> scores = new ArrayList<String>();
        int myHighestScore = 0;
        int highestScore = 0;
        Comparator<String> scoreComparator;
        public ManipScores(String player, String mode) {
            this.file = "scores\\" + mode + ".txt";
            this.player = player;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    scores.add(line);
                    if (line.startsWith(player + ","))
                        myHighestScore = Integer.parseInt(line.split(",")[1]);
                }
                if(!scores.isEmpty())
                    highestScore = Integer.parseInt(scores.getFirst().split(",")[1]);
                br.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            scoreComparator = new Comparator<String>() {
                @Override
                public int compare(String p1, String p2) {
                    try {
                        int score1 = Integer.parseInt(p1.split(",")[1]);
                        int score2 = Integer.parseInt(p2.split(",")[1]);
                        return score2 - score1;
                    } catch (Exception e) {
                        return 0;
                    }
                }
            };
        }

        void updateScores(String newLine) {
            boolean changed = false;
            for (int i = 0; i < scores.size() && !changed; i++)
                if (scores.get(i).startsWith(player + ",")) {
                    scores.set(i, newLine);
                    changed = true;
                }
            if(!changed)
                scores.add(newLine);
            scores.sort(scoreComparator);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(String.join("\n", scores));
                bw.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
