import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameBoard extends JPanel{
    GameManager gm;
    String theme;
    int cols;
    int rows = 4;
    public GameBoard(String theme, int cols, GameManager gm) {
        this.theme = theme;
        this.gm = gm;
        this.cols = cols;
        setLayout(new GridLayout(rows, cols, 10, 10));
        initialize(cols);
    }
    public void initialize(int cols) {
            for(int n : generate(cols*2)) {
                Card card = new Card(theme, (short) n);
                card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(gm.gameStarted)
                        gm.selectCard(card);
                }
            });
            add(card);
        }
    }
    public void reset() {
        removeAll();
        initialize(cols);
        revalidate();
        repaint();
    }
    private List<Integer> generate(int n) {
        List<Integer> l = new ArrayList<>(n);
        for(int i=1; i<=n; i++) {
            l.add(i);
            l.add(i);
        }
        Collections.shuffle(l);
        return l;
    }
}
