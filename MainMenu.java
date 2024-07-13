import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {
    GameFrame gf;
    JPanel buttons = new JPanel(new GridBagLayout());
    JLabel title = new JLabel("MEMORY CARDS");
    JButton startbtn = new JButton("start new game");
    JButton settingsbtn = new JButton("settings");
    JButton scoresbtn = new JButton("scores");

    JButton exitbtn = new JButton("exit");
    public MainMenu(GameFrame gf) {
        this.gf = gf;
        setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.gridy++;
        buttons.add(startbtn, gbc);
        gbc.gridy++;
        buttons.add(settingsbtn, gbc);
        gbc.gridy++;
        buttons.add(scoresbtn, gbc);
        gbc.gridy++;
        buttons.add(exitbtn, gbc);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Styles.gameTitle);
        title.setBorder(BorderFactory.createEmptyBorder(150,100,0,100));
        add(title, "North");
        add(buttons, "Center");

        startbtn.addActionListener(this);
        settingsbtn.addActionListener(this);
        scoresbtn.addActionListener(this);
        exitbtn.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        Sounds.playClickSound();
        if(src == startbtn)
            gf.cl.show(gf.getContentPane(),"game");
        else if(src == settingsbtn)
            gf.cl.show(gf.getContentPane(),"settings");
        else if(src == scoresbtn)
            gf.cl.show(gf.getContentPane(),"scores");
        else
            gf.dispose();
    }
}
