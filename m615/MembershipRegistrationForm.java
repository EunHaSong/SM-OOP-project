import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MembershipRegistrationForm extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Color myOrange = new Color(255, 140, 0);

    public MembershipRegistrationForm() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 140, 0));

        // 타이틀 패널 설정
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        titlePanel.setBackground(myOrange);

        // 타이틀 버튼 설정
        JButton userTitleButton = new JButton("송이 사용자");
        JButton storeTitleButton = new JButton("식당관리자");

        Font defaultFont_Bold = new Font("국민연금체", Font.BOLD, 30);
        userTitleButton.setFont(defaultFont_Bold);
        storeTitleButton.setFont(defaultFont_Bold);

        userTitleButton.setForeground(Color.WHITE);
        storeTitleButton.setForeground(Color.WHITE);

        userTitleButton.setBackground(myOrange);
        storeTitleButton.setBackground(myOrange);

        userTitleButton.setBorder(BorderFactory.createEtchedBorder());
        storeTitleButton.setBorder(BorderFactory.createEtchedBorder());

        titlePanel.add(userTitleButton);
        titlePanel.add(storeTitleButton);

        // 화면을 전환할 패널 만들기
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // 사용자 폼 패널 추가
        UserRegistrationForm userForm = new UserRegistrationForm();
        contentPanel.add(userForm, "UserForm");

        // 상점 폼 패널 추가
        StoreRegistrationForm storeForm = new StoreRegistrationForm();
        contentPanel.add(storeForm, "StoreForm");

        // 버튼 클릭 시 카드 레이아웃 전환
        userTitleButton.addActionListener(e -> cardLayout.show(contentPanel, "UserForm"));
        storeTitleButton.addActionListener(e -> cardLayout.show(contentPanel, "StoreForm"));

        // 컨테이너에 패널 추가
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Membership Registration Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MembershipRegistrationForm());
            frame.pack();
            frame.setLocationRelativeTo(null); // 화면 중앙에 창 띄우기
            frame.setVisible(true);
        });
    }
}