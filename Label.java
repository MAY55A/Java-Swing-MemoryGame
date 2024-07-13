import javax.swing.*;
import java.awt.*;

public class Label {
    static JLabel stats(String str) {
        JLabel label = new JLabel(str);
        label.setForeground(Color.darkGray);
        label.setFont(Styles.label);
        return label;
    }
    static JLabel endGame() {
        JLabel label = new JLabel();
        label.setFont(Styles.gameEnd);
        label.setPreferredSize(new Dimension(1000,300));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    static JLabel newScore() {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(1000,20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    static JLabel timer(int time) {
        JLabel label = new JLabel(String.valueOf(time));
        label.setForeground(Color.black);
        label.setFont(Styles.timer);
        return label;
    }
    static JLabel settings(String str) {
        JLabel label = new JLabel(str);
        label.setForeground(Color.green);
        label.setFont(Styles.label);
        return label;
    }
    static JLabel title(String str) {
        JLabel label = new JLabel(str);
        label.setFont(Styles.title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        return label;
    }
}
