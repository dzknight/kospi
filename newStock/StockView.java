package newStock;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import stock.View;



public class StockView extends JPanel implements DataTransfer{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    private JTable stockTable;
    //private JButton enterButton;

    private DataTransfer dataTransfer;
    private int currentPage=1;
    private int rowPerPage=20;

	public StockView(DataTransfer dataTransfer) {
    	//this.dataTransfer=dataTransfer;
		this.dataTransfer=dataTransfer;
        setLayout(new BorderLayout());
        //패널 사이즈는 stockdialog에서 설정
        // JTable 초기화
        stockTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(stockTable);
        //

        add(tableScrollPane, BorderLayout.CENTER);
        //add(buttonPanel, BorderLayout.SOUTH);
        InputMap inputMap=stockTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap=stockTable.getActionMap();
        //엔터키 이벤트
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "sendData");
        actionMap.put("sendData", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendSelectedData();
				//System.out.println("test");
			}
		});
        stockTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					JPopupMenu popup = new JPopupMenu("오른쪽 버튼 메뉴");
					JMenuItem item1 = new JMenuItem("엑셀로 저장");
					item1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							 String filePath = "stockList.xls"; // 저장 경로 및 파일명 설정
							JOptionPane.showMessageDialog(getRootPane(), "엑셀파일이 저장되었습니다: "+filePath);
							try {
								exportTableToExcel(stockTable, filePath);
								System.out.println("Excel 파일이 생성되었습니다: " + filePath);
							} catch(IOException ex) {
								 System.err.println("Excel 파일 생성 실패: " + ex.getMessage());
							}

						}
					});
					popup.add(item1);

					JMenuItem item2 = new JMenuItem("선택하기");
					item2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							//JOptionPane.showMessageDialog(getRootPane(), "값 전달키 클릭됨");
							int row=stockTable.getSelectedRow();
		        			int column=stockTable.getSelectedColumn();
		        			Object value=(String) stockTable.getValueAt(row, column);
		        			//JOptionPane.showMessageDialog(stockTable, value);
		        			View.stock_code.setText(value.toString());

						}
					});
					popup.add(item2);

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

        //stockJList.addMouseListener(new MyMouseListener());
        stockTable.setDefaultEditor(Object.class, null);
        stockTable.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount()==2) {
        			int row=stockTable.getSelectedRow();
        			int column=stockTable.getSelectedColumn();

        			Object value=stockTable.getValueAt(row, column);
        			JOptionPane.showMessageDialog(stockTable, value);
        			// 메인 창의 대상 텍스트 필드에 값을 설정
                    //targetTextField.setText(value.toString());
        		}
        	}
		});
    }

    // 선택된 데이터 전송 메서드

    private void sendSelectedData() {
        int row = stockTable.getSelectedRow();
        int column = stockTable.getSelectedColumn();

        if (row >= 0 && column >= 0) {
            String selectedData = stockTable.getValueAt(row, column).toString();
            String columnName = stockTable.getColumnName(column);

            if (dataTransfer != null) {
                dataTransfer.transferData(selectedData);
                JOptionPane.showMessageDialog(StockView.this,
                    columnName + " 컬럼의 데이터가 전송됨: " + selectedData);
            }


        }
    }

    public void setTableModel(javax.swing.table.DefaultTableModel model) {
        stockTable.setModel(model);
    }

	@Override
	public void transferData(String data) {
		// TODO Auto-generated method stub

	}
	//테이블을 엑셀로 변환하는 메서드
    public static void exportTableToExcel(JTable table, String filePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // 헤더 쓰기
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            headerRow.createCell(i).setCellValue(table.getColumnName(i));
        }

        // 데이터 쓰기
        for (int i = 0; i < table.getRowCount(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                if (value != null) {
                    dataRow.createCell(j).setCellValue(value.toString());
                } else {
                    dataRow.createCell(j).setCellValue("");
                }
            }
        }

        // 파일에 쓰기
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
    //팝업해서 엑셀테이블에 저장하는 메서드
    public static void addMouseListenerToTable(JTable table) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem exportItem = new JMenuItem("Save as XLS");
        popupMenu.add(exportItem);

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "table_data.xls"; // 저장 경로 및 파일명 설정
                try {
                    exportTableToExcel(table, filePath);
                    System.out.println("Excel 파일이 생성되었습니다: " + filePath);
                } catch (IOException ex) {
                    System.err.println("Excel 파일 생성 실패: " + ex.getMessage());
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
    }

}