package stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DigitalClock extends JFrame {
    private JLabel timeLabel;
    private JLabel dateLabel;
    
    public DigitalClock() {
        setTitle("디지털 시계");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // 시간과 날짜 레이블을 담을 패널 생성
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());
        
        // 시간을 표시할 레이블 생성
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.RED);
        
        // 날짜를 표시할 레이블 생성
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(timeLabel, BorderLayout.CENTER);
        panel.add(dateLabel, BorderLayout.SOUTH);
        add(panel);
        
        // 1초마다 시간 갱신하는 타이머 설정
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
        
        setVisible(true);
    }
    
    private void updateTime() {
        // 현재 시간과 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        
        // 시간 형식 지정
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormatter.format(calendar.getTime());
        timeLabel.setText(time);
        
        // 날짜 형식 지정
        Locale locale=new Locale("en","US");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy",locale);
        String date = dateFormatter.format(calendar.getTime());
        dateLabel.setText(date);
    }
    
    public static void main(String[] args) {
        new DigitalClock();
    }
}