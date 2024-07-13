import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JPanel {
    private short value;
    private String theme;
    private ImageIcon frontImage;
    private ImageIcon backImage;
    private boolean isFlipped;
    public Card(String theme, short val) {
        this.theme = theme;
        this.value = val;
        this.backImage = getScaledImageIcon("cards/"+theme+"/0.jpg");
        this.frontImage = getScaledImageIcon("cards/"+theme+"/"+value+".jpg");
        setFlipped(false);
    }
    public void setFlipped(boolean val) {
        this.isFlipped = val;
        repaint();
    }
    public boolean isFlipped() {
        return isFlipped;
    }
    public boolean equals(Card c) {
        return this.value == c.value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image = isFlipped ? frontImage : backImage;
        g.drawImage(image.getImage(),0,0,120, 160, this);
    }
    private ImageIcon getScaledImageIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
