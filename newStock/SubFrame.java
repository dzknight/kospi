package newStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubFrame extends JFrame {

    private JTable table;
    private JTextField targetTextField;

    public SubFrame(JTextField targetTextField) {
        super("서브 프레임"); // Sub Frame -> 서브 프레임
        this.targetTextField = targetTextField;

        // 테이블을 위한 샘플 데이터
        String[] columnNames = {"컬럼 1", "컬럼 2"}; // Column 1, Column 2 -> 컬럼 1, 컬럼 2
        Object[][] data = {
                {"데이터 1", "데이터 2"}, // Data 1, Data 2 -> 데이터 1, 데이터 2
                {"데이터 3", "데이터 4"}, // Data 3, Data 4 -> 데이터 3, 데이터 4
                {"데이터 5", "데이터 6"}  // Data 5, Data 6 -> 데이터 5, 데이터 6
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        // 테이블에 더블 클릭 리스너 추가
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();

                    // 선택된 셀의 값을 가져옴
                    Object value = table.getValueAt(row, column);

                    // 메인 창의 대상 텍스트 필드에 값을 설정
                    targetTextField.setText(value.toString());

                    // 데이터 전송 후 서브 프레임을 닫을 수도 있습니다.
                    dispose();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 메인 프레임과 텍스트 필드 생성
            JFrame mainFrame = new JFrame("메인 프레임"); // Main Frame -> 메인 프레임
            JTextField mainTextField = new JTextField(20);

            // 텍스트 필드를 전달하여 서브 프레임 생성
            SubFrame subFrame = new SubFrame(mainTextField);

            // 서브 프레임을 여는 버튼 추가
            JButton openSubFrameButton = new JButton("서브 프레임 열기"); // Open Sub Frame -> 서브 프레임 열기
            openSubFrameButton.addActionListener(e -> subFrame.setVisible(true));

            JPanel panel = new JPanel();
            panel.add(new JLabel("테이블에서 가져온 값:")); // Value from Table -> 테이블에서 가져온 값
            panel.add(mainTextField);
            panel.add(openSubFrameButton);

            mainFrame.add(panel);

            mainFrame.setSize(600, 200);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}