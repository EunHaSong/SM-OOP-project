
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.*;

public class UserMain {
    public static void main(String[] args) {
        // ThreePaneSplit 창 생성
        UserTPS mainFrame = new UserTPS("사용자 화면");

        // 패널 생성
        UserRS rsPanel = new UserRS();
        ReviewPage rPage = new ReviewPage();
        ReviewBoard2 rBoard2 = new ReviewBoard2();
        
        // 메인프레임 패널 추가
        mainFrame.getLeftPanel().add(rsPanel);
        mainFrame.getTopRightPanel().add(rBoard2);
        mainFrame.getBottomRightPanel().add(rPage);
        mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainFrame.setVisible(true);
    }
}
