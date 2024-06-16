import javax.swing.*;

public class FirstTimeMyPageMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminTPS mainFrame = new AdminTPS("관리자 화면");
            mainFrame.setVisible(true);

            FirstTimeMyPage mypage = new FirstTimeMyPage("마이페이지");
            mainFrame.getLeftPanel().add(mypage);
            
        });
    }
}