package stock;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExcPanel extends JPanel {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel usdKrwRateLabel;
    private JLabel jpyKrwRateLabel;
    private JLabel lastUpdateLabel;
    private Timer refreshTimer;
    private JButton refreshButton;

    public ExcPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("환율 정보"));
        setPreferredSize(new Dimension(300, 230));


        // 정보 표시 패널
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        infoPanel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 원달러 환율 패널
        JPanel usdPanel = createInfoPanel("원달러 환율", Color.BLUE);
        usdPanel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        usdKrwRateLabel = (JLabel) usdPanel.getComponent(0);

        // 엔화 환율 패널
        JPanel jpyPanel = createInfoPanel("엔화 환율", new Color(0, 128, 0));
        jpyPanel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        jpyKrwRateLabel = (JLabel) jpyPanel.getComponent(0);

        infoPanel.add(usdPanel);
        infoPanel.add(jpyPanel);

        // 하단 패널 (업데이트 시간 및 새로고침 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout());

        lastUpdateLabel = new JLabel("마지막 업데이트: ");
        lastUpdateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
        lastUpdateLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        refreshButton = new JButton("새로고침");
        refreshButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        refreshButton.addActionListener(e -> fetchExchangeRates());

        bottomPanel.add(lastUpdateLabel, BorderLayout.WEST);
        bottomPanel.add(refreshButton, BorderLayout.EAST);

        // 메인 패널에 추가
        add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 초기 데이터 로드
        fetchExchangeRates();

        // 30분마다 자동 갱신
        refreshTimer = new Timer(1800000, e -> fetchExchangeRates());
        refreshTimer.start();
    }

    private JPanel createInfoPanel(String title, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JLabel valueLabel = new JLabel("로딩 중...", SwingConstants.CENTER);
        valueLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        valueLabel.setForeground(color);

        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private void fetchExchangeRates() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private String usdRate = "로딩 실패";
            private String jpyRate = "로딩 실패";

            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // USD/KRW 환율 가져오기
                    usdRate = fetchExchangeRate("USD");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    // JPY/KRW 환율 가져오기
                    jpyRate = fetchExchangeRate2("JPY");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                usdKrwRateLabel.setText("1 USD = " + usdRate + " KRW");
                jpyKrwRateLabel.setText("1 JPY = " + jpyRate + " KRW");
                lastUpdateLabel.setText("마지막 업데이트: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };

        worker.execute();
    }

    private String fetchExchangeRate(String currency) {
        try {
            // 실제 API 호출 코드
        	/* url 셋팅하고 파서셋팅하고, doc 셋팅하는 부분 */
    		/* 매매 동향의 경우 URL을 수정해야한다. */
    		final String URL = "https://finance.naver.com/"; // <-- 가져올 url
    		final String pharser = ".article2";
    		Document doc = Jsoup.connect(URL).get(); // <-- 해당부분에서 doc에 url의 HTML 문서가 모두 들어가게 된다.
    		/* 셋팅 부분 끝 */
    		/* 구분자를 통해 데이터들을 잘게 쪼갠다. */
    		Elements header = doc.select(pharser).select("table");
    		//System.out.println(i + 1 + ". 번째줄" + header.get(i).select("tbody").text());
    		System.out.println("원달러환율: "+header.get(0).select("tbody").text().split(" ")[1]);
    		System.out.println("엔화환율: "+header.get(0).select("tbody").text().split(" ")[6]);
    		System.out.println("휘발유가격: "+header.get(3).select("tbody").text().split(" ")[9]);
    		System.out.println("고급휘발유가격: "+header.get(3).select("tbody").text().split(" ")[13]);
    		//System.out.println(header.get(0).select("tbody").text());
    		String dollor=header.get(0).select("tbody").text().split(" ")[1];
    		String enn=header.get(0).select("tbody").text().split(" ")[6];

            return dollor;

            // 테스트용 임시 데이터
            // return currency.equals("USD") ? "1,354.50" : "9.2345";
        } catch (Exception e) {
            e.printStackTrace();
            return "데이터 없음";
        }
    }
    private String fetchExchangeRate2(String currency) {
        try {
            // 실제 API 호출 코드
        	/* url 셋팅하고 파서셋팅하고, doc 셋팅하는 부분 */
    		/* 매매 동향의 경우 URL을 수정해야한다. */
    		final String URL = "https://finance.naver.com/"; // <-- 가져올 url
    		final String pharser = ".article2";
    		Document doc = Jsoup.connect(URL).get(); // <-- 해당부분에서 doc에 url의 HTML 문서가 모두 들어가게 된다.
    		/* 셋팅 부분 끝 */
    		/* 구분자를 통해 데이터들을 잘게 쪼갠다. */
    		Elements header = doc.select(pharser).select("table");
    		//System.out.println(i + 1 + ". 번째줄" + header.get(i).select("tbody").text());
    		System.out.println("원달러환율: "+header.get(0).select("tbody").text().split(" ")[1]);
    		System.out.println("엔화환율: "+header.get(0).select("tbody").text().split(" ")[6]);
    		System.out.println("휘발유가격: "+header.get(3).select("tbody").text().split(" ")[9]);
    		System.out.println("고급휘발유가격: "+header.get(3).select("tbody").text().split(" ")[13]);
    		//System.out.println(header.get(0).select("tbody").text());

    		String enn=header.get(0).select("tbody").text().split(" ")[6];

            return enn;

            // 테스트용 임시 데이터
            // return currency.equals("USD") ? "1,354.50" : "9.2345";
        } catch (Exception e) {
            e.printStackTrace();
            return "데이터 없음";
        }
    }

    // 패널을 독립적인 창으로 표시
    public static void showInFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JFrame frame = new JFrame("환율 정보");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ExcPanel());
        frame.pack();
        frame.setLocation(980,0);
        frame.setVisible(true);
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				SwingUtilities.updateComponentTreeUI(frame);
				break;
			}
		}
    }

    // 테스트용 메인 메서드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            try {
				showInFrame();
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
}