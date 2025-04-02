package stock;

import java.awt.Scrollbar;

import com.formdev.flatlaf.*;

import newStock.StockDialog;
import newStock.StockModel;

import org.eclipse.swt.dnd.Transfer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import java.io.IOException;
import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Label;
import java.awt.PopupMenu;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.ScrollPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;

import javax.imageio.ImageIO;

public class View extends JFrame implements  DataTransfer {
	private static final long serialVersionUID = 1L;
	// private JButton btnRefresh;
	// 현재시간 라벨
	private JLabel timeLabel;
	private JLabel dateLabel;
	public Label curTime = new Label();
	// 시간 날짜 라벨
	// 개별 종목 텍스트필드
	private static String name;
	private static JTextField stock_name;
	public static JTextField stock_code;
	public static String code;
	private static String dungRakrate;
	private static JTextField dungrak;
	private static String juga;
	private static JTextField cur_value;
	private static String geoga;
	private static JTextField low_val;
	private static String goga;
	private static JTextField high_val;
	private static String georea_val;
	private static JTextField georae;
	private static JTextField compareY;
	private static String typeComp;
	JButton btnNewButton_1 = new JButton("환율조회");

	/**
	 * 실행메인메서드
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {// 프레임 만들기
					View frame = new View(getParentFrame());
					DigitalClock nwFrame1 = new DigitalClock();
					// Model model=new Model();
					stock.Controller trans = new stock.Controller(null, null);
					nwFrame1.setVisible(true);
					frame.setVisible(true);
					for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							SwingUtilities.updateComponentTreeUI(frame);
							break;
						}
					}
					// 기본 조회 삼성전자 고정
					/*
					 * 코드 순서에 따라 디스플레이 안나옴
					 */
					// 주식코드의 스트링텍스트 가져오기
					trans.setInputCode(stock_code.getText());
					// stock_code.getText();
					trans.test();
					dungRakrate = trans.getDungRakrate();
					// dungrak.setText(dungRakrate);

					georea_val = trans.getGeoraeryang();
					georae.setText(georea_val);

					name = trans.getName();
					stock_name.setText(name);

					juga = trans.getJuga();
					cur_value.setText(juga);

					goga = trans.getGoga();
					high_val.setText(goga);

					geoga = trans.getZeoga();
					low_val.setText(geoga);

					// code=trans.code;
					code = stock_code.getText();
					stock_code.setText(code);
					typeComp = trans.getStype();
					compareY.setText(typeComp);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void DigitalClock() {
		// 미국시간
		setTitle("디지털 시계");
		setSize(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		btnNewButton_1.setBounds(259, 39, 97, 23);
		// 시간과 날짜 레이블을 담을 패널 생성
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(new BorderLayout());

		// 시간을 표시할 레이블 생성
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 60));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(Color.RED);

		// 날짜를 표시할 레이블 생성
		dateLabel = new JLabel();
		dateLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(timeLabel, BorderLayout.CENTER);
		panel.add(dateLabel, BorderLayout.SOUTH);
		getContentPane().add(panel);

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

	/**
	 * Create the frame.
	 */
	private static JFrame parentFrame;

	private JLabel panel_icon = new JLabel();

	public View(JFrame parentFrame) {
		this.setParentFrame(parentFrame);
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					JPopupMenu popup = new JPopupMenu("오른쪽 버튼 메뉴");
					JMenuItem item1 = new JMenuItem("엑셀");
					item1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(rootPane, "엑셀파일이 저장되었습니다");
							}
					});
					popup.add(item1);
					JMenuItem item2 = new JMenuItem("아이템2");
					item2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(rootPane, "아이템2 클릭됨");
						}
					});
					popup.add(item2);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		getContentPane().setBackground(new Color(204, 255, 255));
		setTitle("KOSPI");
		setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 14));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(160, 200, 640, 480);
		setResizable(false);
		getContentPane().setLayout(null);
		stock_name = new JTextField();
		stock_name.setBounds(90, 83, 116, 30);
		stock_name.setForeground(new Color(0, 0, 0));
		stock_name.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		// stock_name.setText(null);
		Image image=null;
		 try {
			URL url = new URL("https://ssl.pstatic.net/imgstock/chart3/day1095/KOSPI.png?sidcode=1743059946441");
			image=ImageIO.read(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		getContentPane().add(stock_name);
		stock_name.setColumns(10);
		JButton btnCode = new JButton("코드 조회하기");
		btnCode.setBounds(109, 4, 141, 40);
		btnCode.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		getContentPane().add(btnCode);
		btnCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StockModel model = new StockModel();
				StockDialog dialog = new StockDialog(parentFrame, model);
				dialog.setVisible(true);
			}
		});

		JButton btnNewButton = new JButton("조회");
		btnNewButton.setBounds(218, 53, 97, 30);
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Model model=new Model();
				stock.Controller trans = new stock.Controller(model,View.this);//
				trans.setInputCode(stock_code.getText());
				// stock_code.getText();
				trans.test();
				dungRakrate = trans.getDungRakrate();/*----------------------*/
				dungrak.setText(dungRakrate);

				georea_val = trans.getGeoraeryang();
				georae.setText(georea_val);
				name = trans.getName();
				stock_name.setText(name);
				juga = trans.getJuga();
				cur_value.setText(juga);
				goga = trans.getGoga();
				high_val.setText(goga);
				geoga = trans.getZeoga();
				low_val.setText(geoga);
				// code=trans.code;
				code = stock_code.getText();
				stock_code.setText(code);
				typeComp = trans.getStype();
				compareY.setText(typeComp);
			}

		});
		// https://finance.yahoo.com/
		// https://finance.naver.com/item/main.naver?code=022100

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(0, 0, 97, 26);
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "나스닥", "코스닥" }));
		comboBox.setSelectedIndex(1);
		getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("종목");
		lblNewLabel.setBounds(33, 90, 57, 15);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.white);
		lblNewLabel.setBackground(Color.WHITE);
		getContentPane().add(lblNewLabel);

		// 새로고침 버튼 생성
		JButton btnRefresh = new JButton("새로고침");
		btnRefresh.setBounds(527, 4, 97, 40);
		btnRefresh.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		getContentPane().add(btnRefresh);

		// 새로 고침 버튼 누를시 리프레시 액션 추가
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTime();
			}
		});

		BufferedImage mypicture = null;
		try {
			mypicture = ImageIO.read(new File("E:\\3757.jpg"));
			int width = mypicture.getWidth();
			int height = mypicture.getHeight();
			System.out.println(width + "|" + height);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selected = (String) comboBox.getSelectedItem();
				openWebsite(selected);
			}

			private void openWebsite(String selected) {
				// TODO Auto-generated method stub
				try {
					Desktop desktop = Desktop.getDesktop();
					switch (selected) {
					case "나스닥":
						desktop.browse(new URI("https://finance.yahoo.com/quote/%5EIXIC/"));
						break;
					case "코스닥":
						desktop.browse(new URI("https://finance.naver.com/sise/sise_index.naver?code=KPI100"));
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Label label = new Label("현재시간");
		label.setBounds(276, 10, 69, 23);
		label.setBackground(new Color(255, 255, 255));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setForeground(Color.black);
		getContentPane().add(label);
		curTime.setBounds(361, 10, 132, 23);
		curTime.setBackground(new Color(255, 255, 255));
		getContentPane().add(curTime);
		try {
			// ImageIcon icon1=new ImageIcon("E:\\250311산출물\\참고자료\\mug.gif");
			JLabel lblNewLabel_2 = new JLabel("현재가");
			lblNewLabel_2.setBounds(33, 114, 45, 31);
			getContentPane().add(lblNewLabel_2);
			lblNewLabel_2.setForeground(Color.white);
			lblNewLabel_2.setFont(new Font("한컴산뜻돋움", Font.BOLD, 16));

			JLabel lblNewLabel_2_1 = new JLabel("고가");
			lblNewLabel_2_1.setBounds(33, 146, 45, 31);
			lblNewLabel_2_1.setForeground(Color.white);
			getContentPane().add(lblNewLabel_2_1);
			lblNewLabel_2_1.setFont(new Font("맑은 고딕", Font.BOLD, 16));

			JLabel lblNewLabel_3 = new JLabel("저가");
			lblNewLabel_3.setBounds(33, 182, 57, 21);
			getContentPane().add(lblNewLabel_3);
			lblNewLabel_3.setForeground(Color.white);
			lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 16));

			cur_value = new JTextField();
			cur_value.setBounds(90, 115, 116, 30);
			cur_value.setForeground(Color.BLACK);
			cur_value.setEditable(false);
			getContentPane().add(cur_value);
			cur_value.setText(null);
			cur_value.setColumns(10);

			high_val = new JTextField();
			high_val.setBounds(90, 147, 116, 30);
			high_val.setForeground(Color.BLACK);
			getContentPane().add(high_val);
			high_val.setColumns(10);

			low_val = new JTextField();
			low_val.setBounds(90, 179, 116, 30);
			getContentPane().add(low_val);
			low_val.setColumns(10);

			JLabel lblNewLabel_3_1 = new JLabel("거래량");
			lblNewLabel_3_1.setBounds(33, 215, 57, 21);
			getContentPane().add(lblNewLabel_3_1);
			lblNewLabel_3_1.setForeground(Color.white);
			lblNewLabel_3_1.setFont(new Font("한컴 고딕", Font.BOLD, 16));

			georae = new JTextField();
			georae.setBounds(90, 211, 116, 30);
			getContentPane().add(georae);
			georae.setColumns(10);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(403, 58, 123, 133);
			getContentPane().add(scrollPane);

			JList list = new JList();
			list.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			scrollPane.setViewportView(list);
			list.setModel(new AbstractListModel() {
				String[] values = new String[] { "삼성전자", "주성", "포스코홀딩스", "엘지생활건강", "엘앤에프", "엔씨소프트", "네이버", "카카오",
						"카카오페이", "주성", "신세계" };

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});
			list.setSelectedIndex(0);
			list.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					if(!e.getValueIsAdjusting()) {
						int selectedIndex=list.getSelectedIndex();
						String selectedValue=(String) list.getSelectedValue();
						System.out.println("선택된 인덱스: " + selectedIndex);
						System.out.println("선택된 값: " + selectedValue);
					}
				}

			});

			list.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        int index = list.locationToIndex(e.getPoint());
			        if (index >= 0) {
			            String selectedStock = (String) list.getModel().getElementAt(index);
			            String stockCode = getStockCodeForName(selectedStock);
			            stock_code.setText(stockCode);

			            // Controller를 통해 주식 정보 가져오기
			            Model model = new Model();
			            Controller controller = new Controller(model, View.this);
			            controller.setInputCode(stockCode);
			            controller.test();

			            // UI 업데이트
			            updateStockInfo(controller);
			        }
			    }
			});


			JLabel lblNewLabel_3_1_1 = new JLabel("등락율");
			lblNewLabel_3_1_1.setBounds(33, 247, 57, 21);
			getContentPane().add(lblNewLabel_3_1_1);
			lblNewLabel_3_1_1.setForeground(Color.white);
			lblNewLabel_3_1_1.setFont(new Font("한컴 고딕", Font.BOLD, 16));

			dungrak = new JTextField();
			dungrak.setBounds(90, 243, 116, 30);
			dungrak.setColumns(10);
			getContentPane().add(dungrak);

			JLabel lblNewLabel_4 = new JLabel("코드");
			lblNewLabel_4.setBounds(33, 57, 37, 15);
			lblNewLabel_4.setForeground(Color.white);
			lblNewLabel_4.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			lblNewLabel_4.setBackground(Color.WHITE);
			getContentPane().add(lblNewLabel_4);

			stock_code = new JTextField("005930");
			stock_code.setBounds(90, 51, 116, 30);
			stock_code.setForeground(Color.RED);
			stock_code.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			stock_code.setColumns(10);
			getContentPane().add(stock_code);

			JLabel compare = new JLabel("어제대비");
			compare.setBounds(33, 279, 57, 21);
			getContentPane().add(compare);
			compare.setForeground(Color.white);
			compare.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));

			compareY = new JTextField();
			compareY.setBounds(90, 275, 116, 30);
			compareY.setColumns(10);
			getContentPane().add(compareY);

			JButton btnNewButton_1 = new JButton("환율조회");
			btnNewButton_1.setBounds(218, 125, 97, 30);
			btnNewButton_1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			getContentPane().add(btnNewButton_1);

			JButton button = new JButton("기름시세");
			button.setBounds(218, 160, 97, 30);
			button.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			btnNewButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        // 독립적인 창으로 환율 정보 표시
			        try {
						ExcPanel.showInFrame();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			});
			button.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        // 독립적인 창으로 환율 정보 표시
			        try {
						OilPanel.showInFrame();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			});
			getContentPane().add(button);

			JButton btnChart = new JButton("챠트보기");
			btnChart.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			btnChart.setBounds(218, 195, 97, 30);
			getContentPane().add(btnChart);
			btnChart.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        // 독립적인 창으로 환율 정보 표시
			        try {
						ChartPanel.showInFrame();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			});
			panel_icon = new JLabel(new ImageIcon(mypicture));
			panel_icon.setText("chart");
			panel_icon.setBounds(0, 0, 624, 441);
			panel_icon.setForeground(new Color(0, 0, 0));
			getContentPane().add(panel_icon);



			// 라벨에 현재 시간 제이프레임 띄울때 시간 디스플레이
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		updateTime();
	}

	// 라벨을 현재시간으로 갱신하는 메서드
	private void updateTime() {
		// TODO Auto-generated method stub
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm;ss"));
		curTime.setText(currentTime);
	}

//팝업 이벤트

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});
	}
	// UI 업데이트 메서드
	private void updateStockInfo(Controller controller) {
		stock_code.setText(controller.getInputCode());
	    dungrak.setText(controller.getDungRakrate());
	    georae.setText(controller.getGeoraeryang());
	    stock_name.setText(controller.getName());
	    cur_value.setText(controller.getJuga());
	    high_val.setText(controller.getGoga());
	    low_val.setText(controller.getZeoga());
	    compareY.setText(controller.getStype());

	}
	public static JFrame getParentFrame() {
		return parentFrame;
	}

	private String getStockCodeForName(String stockName) {
	    // 종목명에 따른 종목코드 매핑
	    switch(stockName) {
	        case "삼성전자": return "005930";
	        case "네이버": return "035420";
	        case "카카오": return "035720";
	        case "엘지생활건강": return "051900";
	        case "카카오페이": return "377300";
	        case "엘앤에프": return "066970";
	        case "주성": return "036930";
	        case "포스코홀딩스": return "005490";
	        case "엔씨소프트": return "036570";
	        case "신세계": return "004170";
	        // 기타 종목 추가
	        default: return "005930"; // 기본값
	    }
	}

	private void fetchStockInfo(String stockCode) {
	    try {
	        String URL = "https://finance.naver.com/item/main.nhn?code=" + stockCode;
	        Document doc = Jsoup.connect(URL).get();

	        Elements todaylist = doc.select(".new_totalinfo dl>dd");
	        String name = todaylist.get(1).text().split(" ")[1];
	        String juga = todaylist.get(3).text().split(" ")[1];
	        String dungRakrate = todaylist.get(3).text().split(" ")[6];
	        String goga = todaylist.get(6).text().split(" ")[1];
	        String zeoga = todaylist.get(8).text().split(" ")[1];
	        String georaeryang = todaylist.get(10).text().split(" ")[1];
	        String stype = todaylist.get(3).text().split(" ")[3];

	        // UI 업데이트
	        stock_name.setText(name);
	        cur_value.setText(juga);
	        dungrak.setText(dungRakrate);
	        high_val.setText(goga);
	        low_val.setText(zeoga);
	        georae.setText(georaeryang);
	        compareY.setText(stype);

	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "주식 정보를 가져오는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public static void setParentFrame(JFrame parentFrame) {
		View.parentFrame = parentFrame;
	}

	class MyMouseListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getClickCount() == 2) {
				int r = (int) (Math.random() * 256);
				int g = (int) (Math.random() * 256);
				int b = (int) (Math.random() * 256);

				JPanel p = (JPanel) e.getSource();
				// 마우스가 클릭된 패널의 객체를 알아냄
				p.setBackground(new Color(r, b, g));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			// la.setText(e.getX()+"|"+e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			// la.setText(e.getX()+"|"+e.getY());
		}
	}
	//스톡뷰에서 데이터 넘겨받는
	public void setStockData(String id) {
		stock_code.setText(id);
	}

	@Override
	public void transferData(String data) {
		// TODO Auto-generated method stub
		stock_code.setText(data);
	}
/*
	public void addSaveListener(ActionListener listener) {
		btnSave.addActionListener(listener);
	}
*/
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(JLabel dateLabel) {
		this.dateLabel = dateLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static JTextField getStock_name() {
		return stock_name;
	}

	public static void setStock_name(JTextField stock_name) {
		View.stock_name = stock_name;
	}

	public static JTextField getStock_code() {
		return stock_code;
	}

	public static void setStock_code(JTextField stock_code) {
		View.stock_code = stock_code;
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		View.code = code;
	}

	public static String getDungRakrate() {
		return dungRakrate;
	}

	public static void setDungRakrate(String dungRakrate) {
		View.dungRakrate = dungRakrate;
	}

	public static JTextField getDungrak() {
		return dungrak;
	}

	public static void setDungrak(JTextField dungrak) {
		View.dungrak = dungrak;
	}

	public static String getJuga() {
		return juga;
	}

	public static void setJuga(String juga) {
		View.juga = juga;
	}

	public static JTextField getCur_value() {
		return cur_value;
	}

	public static void setCur_value(JTextField cur_value) {
		View.cur_value = cur_value;
	}

	public static String getGeoga() {
		return geoga;
	}

	public static void setGeoga(String geoga) {
		View.geoga = geoga;
	}

	public static JTextField getLow_val() {
		return low_val;
	}

	public static void setLow_val(JTextField low_val) {
		View.low_val = low_val;
	}

	public static String getGoga() {
		return goga;
	}

	public static void setGoga(String goga) {
		View.goga = goga;
	}

	public static JTextField getHigh_val() {
		return high_val;
	}

	public static void setHigh_val(JTextField high_val) {
		View.high_val = high_val;
	}

	public static String getGeorea_val() {
		return georea_val;
	}

	public static void setGeorea_val(String georea_val) {
		View.georea_val = georea_val;
	}

	public static JTextField getGeorae() {
		return georae;
	}

	public static void setGeorae(JTextField georae) {
		View.georae = georae;
	}

	public static JTextField getCompareY() {
		return compareY;
	}

	public static void setCompareY(JTextField compareY) {
		View.compareY = compareY;
	}

	public static String getTypeComp() {
		return typeComp;
	}

	public static void setTypeComp(String typeComp) {
		View.typeComp = typeComp;
	}

	public JLabel getPanel_icon() {
		return panel_icon;
	}

	public void setPanel_icon(JLabel panel_icon) {
		this.panel_icon = panel_icon;
	}
}
