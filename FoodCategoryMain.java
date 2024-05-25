import javax.swing.SwingUtilities;

public class FoodCategoryMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodCategory frame = new FoodCategory();
            frame.setVisible(true);
        });
    }
}


