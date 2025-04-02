package newStock;

public class StockApp {
    public static void main(String[] args) {
        StockModel model = new StockModel();
        StockView view = new StockView(null);
        StockController controller = new StockController(model, view);
        
        controller.start();
    }
}