import javax.swing.*;
import java.awt.*;


public class DataSort extends JFrame {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int halfScreenWidth = screenSize.width / 2;
    int screenWidth = screenSize.width / 5;
    int screenHeight = screenSize.height / 6;

    public DataSort() {
        setSize(screenWidth, screenHeight);
        setLocation(halfScreenWidth - screenWidth, 0);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("정렬");
        label.setFont(new Font("NPS font", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // 위쪽 여백 추가

        add(label);

        JButton b1 = new JButton("평점순");
        b1.setFont(new Font("NPS font", Font.PLAIN, 15));
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(b1);

        JButton b2 = new JButton("리뷰 많은 순");
        b2.setFont(new Font("NPS font", Font.PLAIN, 15));
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(b2);

        setVisible(true);

    }
}
