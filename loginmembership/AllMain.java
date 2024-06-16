import javax.swing.*;
import java.awt.*;

public class AllMain {
    public static void main(String[] args) {
        // ThreePaneSplit 창 생성
        AllTPS mainFrame = new AllTPS();

        // 패널 생성
        LoginPanel loginPanel = new LoginPanel();

        // 메인프레임 패널 추가
        mainFrame.getLeftPanel().add(loginPanel, BorderLayout.CENTER);
        mainFrame.getTopRightPanel().add(new JPanel());
        mainFrame.getBottomRightPanel().add(new JPanel());
        mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainFrame.setVisible(true);
    }
}
