import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class AdminTPS extends JFrame {
    JPanel leftPanel, topRightPanel, bottomRightPanel;

    public AdminTPS(String msg) {
        super(msg);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Create panels for the 3 sections
        leftPanel = new JPanel(new BorderLayout());
        topRightPanel = new JPanel();
        bottomRightPanel = new JPanel();

        leftPanel.setBorder(BorderFactory.createTitledBorder("식당 화면"));
        topRightPanel.setBorder(BorderFactory.createTitledBorder("정보 등록 화면"));
        bottomRightPanel.setBorder(BorderFactory.createTitledBorder("리뷰 보기 화면"));

        // Add panels to the frame with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;  // spans 2 rows
        add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;  // spans 1 row
        add(topRightPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(bottomRightPanel, gbc);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }
}