package stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//모델 클래스 - 주식 데이터 저장
public class Model extends BaseModel {
	private String code;
    private String name;
    private String currentPrice;
    private String highPrice;
    private String lowPrice;
    private String tradingVolume;
    private String changeRate;
    private String comparedToYesterday;
    private String type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getTradingVolume() {
		return tradingVolume;
	}
	public void setTradingVolume(String tradingVolume) {
		this.tradingVolume = tradingVolume;
	}
	public String getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}
	public String getComparedToYesterday() {
		return comparedToYesterday;
	}
	public void setComparedToYesterday(String comparedToYesterday) {
		this.comparedToYesterday = comparedToYesterday;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/*
	public  JScrollPane findAll() throws SQLException {
		List<Model> models=new ArrayList<>();
		
		PreparedStatement pstmt=null;
		ResultSet rs=pstmt.executeQuery("select * from kosdaq_stocks");
		DefaultTableModel model=new DefaultTableModel();
		JTable table =new JTable(model);
		
		ResultSetMetaData metaData=rs.getMetaData();
		int columnCount=metaData.getColumnCount();
		for(int i=1;i<=columnCount;i++) {
			model.addColumn(metaData.getColumnName(i));
		}
		
		while(rs.next()) {
			Object[] row=new Object[columnCount];
			for(int i=1;i<=columnCount;i++) {
				row[i-1]=rs.getObject(i);
			}
			model.addRow(row);
		}
		JScrollPane scrollPane=new JScrollPane(table);
		return scrollPane; 
		
	}*/
	public boolean saveToDb() {
		// TODO Auto-generated method stub
		
		   try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:system", "hr", "hr");
			String sql = "INSERT INTO ASTOCK (s_id, s_name, DEUNGRAKRATE, JUGA, GOGA, ZEOGA, GEORAERYANG, VSYESTERDAY, STYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
	        pstmt.setString(1, code);
	        pstmt.setString(2, name);
	        pstmt.setString(3, changeRate);
	        pstmt.setString(4, currentPrice);
	        pstmt.setString(5, highPrice);  // 수정: 인덱스 6에서 5로 변경
	        pstmt.setString(6, lowPrice);   // 
	        pstmt.setString(7, tradingVolume);
	        pstmt.setString(8, comparedToYesterday); // 
	        pstmt.setString(9, null);
			int result =pstmt.executeUpdate();
			con.close();
			System.out.println("HELLO");
			return result>0;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
           
           // 데이터베이스 연결
		
	}

}
