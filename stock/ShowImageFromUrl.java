package stock;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowImageFromUrl {
    public static void main(String[] args) {
        Image image = null;
        try {
            URL url = new URL("https://ssl.pstatic.net/imgstock/chart3/day1095/KOSPI.png?sidcode=1743059946441");
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        JLabel lblimage = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(lblimage, BorderLayout.CENTER);
        frame.setSize(300, 400);
        frame.setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(lblimage);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
