package stock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Controller  implements MouseListener {
	private  String juga;
	private  String DungRakrate;
	private  String siga;
	private  String goga;
	private  String zeoga;
	private  String georaeryang;
	private  String stype;
	private  String vsyesterday;
	private  String name;
	private  String code;
	private  String inputCode;
	
	private View view;
	private Model model;
	
	public Controller(Model model,View view2) {
		this.model= model ;
		//this.model= model != null ? model : new Model();
		//널이면 새 모델 생성
		//this.view=view != null ? view : new View(null);
		this.view=view2;
		//널이면 새 뷰 생성
		//콘트롤러를 모델의 리스너로 등록
		//saveStock(new SaveBtnListener());
		//this.view.addSaveListener(new SaveBtnListener());
		
		//initEventListeners();
	}
	/*
	public static Controller createController() {
		Model model=new Model();
		View view =new View();
		return new Controller(model, view);
	}
*/
	private void initEventListeners() {
		// TODO Auto-generated method stub
		//view.getSaveBtn().addActionListener(e-> saveStock());
		
		
	}
	class SaveBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.setCurrentPrice(view.getJuga());
			model.setChangeRate(view.getDungRakrate());
			model.setName(view.getName());
			model.setHighPrice(view.getGoga());
			model.setLowPrice(view.getJuga());
			model.setTradingVolume(view.getGeorea_val());
			model.setComparedToYesterday(view.getTypeComp());
			model.setType(view.getTypeComp());
			
			
			try {
				boolean success=model.saveToDb();
				if(success) {
					view.showMessage("성공적으로 저장됨");
				} else {
					view.showMessage("데이터 저장에 실패했습니다");
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				view.showMessage("데이터베이스오류");
			}
		}
		
	}
	//주식정보 저장 메서드
	public void saveStock() {
		if (view != null) {
	        // View에서 데이터 가져오기
	        String name = view.getStock_name().getText();
	        String code = view.getStock_code().getText();
	        String juga = view.getCur_value().getText();
	        String high = view.getHigh_val().getText();
	        String low = view.getLow_val().getText();
	        String dungrak = view.getDungrak().getText();
	        String georae = view.getGeorae().getText();
	        String type = view.getCompareY().getText();
	        
	        // Model에 데이터 설정
	        model.setName(name);
	        model.setCode(code);
	        model.setCurrentPrice(juga);
	        model.setHighPrice(high);
	        model.setLowPrice(low);
	        model.setTradingVolume(georae);
	        model.setChangeRate(dungrak);
	        model.setType(type);
	        
	        // 데이터베이스에 저장
	        boolean success = model.saveToDb();
	        if (success) {
	            view.showMessage("성공적으로 저장되었습니다.");
	        } else {
	            view.showMessage("저장에 실패했습니다.");
	        }
	    } else {
	        System.out.println("View가 초기화되지 않았습니다.");
	    }
	}

	 //String inputCode="005930";
	 //종목명
	 //종목코드
	 public void test() {
		 String URL = "https://finance.naver.com/item/main.nhn?code="+getInputCode();
		 Document doc;
		
		 try {
			 doc = Jsoup.connect(URL).get();
			 Elements elem = doc.select(".date");
			 String[] str = elem.text().split(" ");
			 
			 Elements todaylist =doc.select(".new_totalinfo dl>dd");
			 name= todaylist.get(1).text().split(" ")[1];
			 code= todaylist.get(2).text().split(" ")[1];
			 setJuga(todaylist.get(3).text().split(" ")[1]);
			 setDungRakrate(todaylist.get(3).text().split(" ")[6]);
			 siga =  todaylist.get(5).text().split(" ")[1];
			 setGoga(todaylist.get(6).text().split(" ")[1]);
			 setZeoga(todaylist.get(8).text().split(" ")[1]);
			 setGeoraeryang(todaylist.get(10).text().split(" ")[1]);
			 
			 setStype(todaylist.get(3).text().split(" ")[3]); //상한가,상승,보합,하한가,하락 구분
			 
			 vsyesterday = todaylist.get(3).text().split(" ")[4];
			
			 
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 System.out.println(code+"이름"+name);
		 System.out.println("코드:"+code);
		 System.out.println("이름:"+name);
		 System.out.println("주가:"+getJuga());
		 System.out.println("등락률:"+getDungRakrate());
		 System.out.println("시가:"+siga);
		 System.out.println("고가:"+getGoga());
		 System.out.println("저가:"+getZeoga());
		 System.out.println("거래량:"+getGeoraeryang());
		 System.out.println("타입:"+getStype());
		 System.out.println("전일대비:"+vsyesterday);
		 
	}
	public String getInputCode() {
		return inputCode;
	}
	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}
	public String getDungRakrate() {
		return DungRakrate;
	}
	public void setDungRakrate(String dungRakrate) {
		DungRakrate = dungRakrate;
	}
	public String getGeoraeryang() {
		return georaeryang;
	}
	public void setGeoraeryang(String georaeryang) {
		this.georaeryang = georaeryang;
	}
	public String getJuga() {
		return juga;
	}
	public void setJuga(String juga) {
		this.juga = juga;
	}
	public String getGoga() {
		return goga;
	}
	public void setGoga(String goga) {
		this.goga = goga;
	}
	public String getZeoga() {
		return zeoga;
	}
	public void setZeoga(String zeoga) {
		this.zeoga = zeoga;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}