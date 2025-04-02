package newStock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class StockModel {
    private Connection conn;
    private DefaultTableModel tableModel;
    private List<String> stockList;

    public StockModel() {
        // 테이블 모델 초기화
        tableModel = new DefaultTableModel();
        tableModel.addColumn("코드");
        tableModel.addColumn("종목명");

        // 주식 목록 초기화
        stockList = new ArrayList<>();

        // DB 연결
        connectToDatabase();

        // 데이터 로드
        loadDataFromDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:system", "hr", "hr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromDatabase() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT distinct STOCK_CODE, STOCK_NAME FROM KOSDAQ_STOCK order by stock_name");

            // 기존 데이터 초기화
            tableModel.setRowCount(0);
            stockList.clear();

            // 결과셋에서 데이터 추출
            while (rs.next()) {
                String code = rs.getString("STOCK_CODE");
                String name = rs.getString("STOCK_NAME");

                // 테이블 모델에 행 추가
                tableModel.addRow(new Object[]{code, name});

                // JList용 데이터 추가
                stockList.add(name + " (" + code + ")");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public List<String> getStockList() {
        return stockList;
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}