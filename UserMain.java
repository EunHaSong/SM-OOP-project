import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UserMain {
    public static void main(String[] args) {
        // ThreePaneSplit 창 생성
        UserTPS mainFrame = new UserTPS("사용자 화면");

        // RS 패널 생성
        UserRS rsPanel = new UserRS();

        // RS 패널을 leftPanel에 추가
        mainFrame.getLeftPanel().add(rsPanel, BorderLayout.CENTER);

        mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainFrame.setVisible(true);
    }
}
