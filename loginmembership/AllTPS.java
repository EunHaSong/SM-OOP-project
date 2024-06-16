import javax.swing.*;
import java.awt.*;

class AllTPS extends JFrame {
    JPanel leftPanel, topRightPanel, bottomRightPanel;

    public AllTPS() {
        super("송직한 리뷰");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Left Panel
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(getWidth() / 2, getHeight())); // 왼쪽 패널 크기 설정
        leftPanel.setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST); // 왼쪽 패널을 프레임의 서쪽에 추가

        // Right Panels
        topRightPanel = new JPanel(new BorderLayout());
        bottomRightPanel = new JPanel(new BorderLayout());

        // Split pane for the right side (top and bottom)
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topRightPanel, bottomRightPanel);
        rightSplitPane.setResizeWeight(0.5); // Equally split the space
        rightSplitPane.setDividerSize(0); // Divider size set to 0

        // Split pane for the entire frame (left and right)
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightSplitPane);
        mainSplitPane.setResizeWeight(0.5); // Equally split the space
        mainSplitPane.setDividerSize(0); // Divider size set to 0

        add(mainSplitPane);

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public JPanel getTopRightPanel() {
        return topRightPanel;
    }

    public JPanel getBottomRightPanel() { return bottomRightPanel;}
}