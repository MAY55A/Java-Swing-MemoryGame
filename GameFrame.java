import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    Game game;
    Scores scores;
    CardLayout cl = new CardLayout();
    JPanel contentPanel = new JPanel(cl);
    JMenuBar mb = new JMenuBar();
    JMenu mn = new JMenu("Game");
    JMenuItem item1 = new JMenuItem("Main menu");
    JMenuItem item2 = new JMenuItem("Exit");

    public GameFrame() {
        UIManager.put("Label.font",Styles.normal);
        UIManager.put("Button.font",Styles.normal);
        UIManager.put("RadioButton.font",Styles.normal);
        UIManager.put("TextField.font",Styles.normal);

        scores = new Scores();

        contentPanel.add("main menu", new MainMenu(this));
        contentPanel.add("settings", new Settings(this));
        contentPanel.add("scores", scores);
        contentPanel.add("game", game);
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(contentPanel,"main menu");
                game.resetGame();
            }
        });
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mn.add(item1);
        mn.add(item2);
        mb.add(mn);

        setJMenuBar(mb);
        setContentPane(contentPanel);
        setTitle("Memory Cards");
        setSize(920, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
