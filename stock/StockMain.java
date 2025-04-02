package stock;

import java.util.EventListener;

import javax.swing.JFrame;




public class StockMain extends View implements EventListener {

	public StockMain(JFrame parentFrame) {
		super(parentFrame);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new View(getParentFrame());
		View.main(args);
	}

}
