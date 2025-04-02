package newStock;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;

	public class TableToExcel {

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

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            // 예제 데이터
	            Object[][] data = {
	                    {"John", "Doe", "john.doe@example.com"},
	                    {"Jane", "Smith", "jane.smith@example.com"},
	                    {"Alice", "Wonderland", "alice@wonderland.com"}
	            };
	            String[] columnNames = {"First Name", "Last Name", "Email"};

	            // JTable 생성
	            JTable table = new JTable(data, columnNames);

	            // 마우스 리스너 추가
	            addMouseListenerToTable(table);

	            // JFrame 생성 및 JTable 추가
	            JFrame frame = new JFrame("JTable to Excel Example");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.add(new JScrollPane(table));
	            frame.setSize(600, 400);
	            frame.setVisible(true);
	        });
	    }
	}