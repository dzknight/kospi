package stock;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.eclipse.swt.graphics.Image;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChartPanel extends JPanel {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel imageLabel;
    private ImageIcon[] images;
    private int currentIndex = 0;

    public ChartPanel() {


        setSize(250, 250);
        setLayout(new BorderLayout());

        // 이미지 배열 초기화 (실제 경로로 변경 필요)
        java.awt.Image image=null;
        java.awt.Image image2=null;
        java.awt.Image image3=null;
        try {
			URL url1 = new URL("https://ssl.pstatic.net/imgstock/chart3/day/KOSPI.png?sidcode=1743069066499");
			URL url2 = new URL("https://ssl.pstatic.net/imgstock/chart3/day90/KOSPI.png?sidcode=1743069066499");
			URL url3 = new URL("https://ssl.pstatic.net/imgstock/chart3/day1095/KOSPI.png?sidcode=1743059946441");
			try {
				image=ImageIO.read(url1);//일간 코스피차트이미지
				image2=ImageIO.read(url2);//3개월간 차트이미지
				image3=ImageIO.read(url3);//3년간 차트이미지
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        images = new ImageIcon[]{
        	new ImageIcon(image),
            new ImageIcon(image2),
            new ImageIcon(image3)
        };

        // 이미지를 표시할 JLabel 생성
        imageLabel = new JLabel(images[currentIndex]);
        add(imageLabel, BorderLayout.CENTER);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton prevButton = new JButton("이전");
        JButton nextButton = new JButton("다음");

        // 버튼에 액션 리스너 추가
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPreviousImage();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNextImage();
            }
        });

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        imageLabel.setIcon(images[currentIndex]);
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % images.length;
        imageLabel.setIcon(images[currentIndex]);
    }



    // 테스트용 메인 메서드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
            	showInFrame();
            	new ChartPanel().setVisible(true);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }

    // 패널을 독립적인 창으로 표시
    public static void showInFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JFrame frame = new JFrame("코스피 챠트 보기");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel());
        frame.pack();
        frame.setLocation(1000,530);
        frame.setVisible(true);
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				SwingUtilities.updateComponentTreeUI(frame);
				break;
			}
		}
    }
}