import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;

public class AdminMain {
    public static void main(String[] args) {
        // ThreePaneSplit 창 생성
        AdminTPS mainFrame = new AdminTPS("관리자 화면");

        // RS 패널 생성
        AdminRS rsPanel = new AdminRS();
        AdminInfo infoPanel = new AdminInfo(null);
        
        // 메인프레임 패널 추가
        mainFrame.getLeftPanel().add(rsPanel, BorderLayout.CENTER);
        //mainFrame.gettopRightPanel().add()
        mainFrame.getbottomRightPanel().add(infoPanel);
        mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainFrame.setVisible(true);
    }
}
