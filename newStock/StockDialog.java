package newStock;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StockDialog extends JDialog {
    private StockView stockView;
    
    public StockDialog(JFrame parent, StockModel model) {
        super(parent, "주식 정보", false); // true는 modal 다이얼로그로 설정
        
        // StockView 생성
        stockView = new StockView(stockView);
        
        // Controller 생성 및 설정
        StockController controller = new StockController(model, stockView);
        
        // 다이얼로그 설정
        setContentPane(stockView);
        //setBounds(1600, 1600, 200, 500);
        setLocation(785, 200);
        setSize(200, 480);
        //setLocationRelativeTo(parent);
        
        // 닫기 버튼 추가
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        // 기존 패널의 남쪽에 닫기 버튼 추가
        stockView.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public StockView getStockView() {
        return stockView;
    }
}