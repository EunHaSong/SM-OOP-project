import javax.swing.*;

public class FoodCategoryMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserTPS mainFrame = new UserTPS("사용자 화면");
            mainFrame.setVisible(true);

            FoodCategory foodCate = new FoodCategory();
            mainFrame.getLeftPanel().add(foodCate);

        });
    }
}



