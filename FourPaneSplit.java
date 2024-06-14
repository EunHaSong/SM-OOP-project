
import javax.swing.*;
import java.awt.*;

public class FourPaneSplit extends JFrame {

    public FourPaneSplit() {
        setTitle("Four Pane Split");
        setLayout(new GridLayout(2, 2));

        // 다른 패널들 추가 (상단, 좌측, 우측 등)
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setBackground(Color.white);
        add(topLeftPanel);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setBackground(Color.white);
        add(topRightPanel);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.white);
        add(bottomLeftPanel);

        // Admin panel for the bottom right section
        JPanel bottomRightPanel = new CreateAdminPanel("관리자용 - 정보등록");
        bottomRightPanel.setBackground(Color.white);
        add(bottomRightPanel);

        // Set the frame to be maximized
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Set the frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FourPaneSplit();
        });
    }
}
    