import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginScreen extends JFrame {
    private JPanel loginPanel;
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField restaurantField;

    public LoginScreen() {
        setTitle("로그인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setResizable(false);

        // 전체 레이아웃 설정
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;


        // 로그인 패널 생성
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        Font font = new Font("나눔고딕", Font.BOLD, 18);

        // 아이디 입력 필드
        JLabel idLabel = new JLabel("아이디");
        gbc.insets = new Insets(20, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        idLabel.setFont(font);
        loginPanel.add(idLabel, gbc);

        idField = new JTextField(19);
        gbc.insets = new Insets(20, 20, 10, 20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(idField, gbc);

        // 비밀번호 입력 필드
        JLabel pwdLabel = new JLabel("비밀번호");
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pwdLabel.setFont(font);
        loginPanel.add(pwdLabel, gbc);

        passwordField = new JPasswordField(19);
        gbc.insets = new Insets(5, 20, 10, 20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        // 식당번호 입력 필드 (관리자 전용)
        JLabel restaurantLabel = new JLabel("식당번호");
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        restaurantLabel.setFont(font);
        loginPanel.add(restaurantLabel, gbc);

        restaurantField = new JTextField(19);
        gbc.insets = new Insets(5, 20, 10, 20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(restaurantField, gbc);

        // 초기에는 식당번호 필드 숨김
        restaurantLabel.setVisible(false);
        restaurantField.setVisible(false);

        // 라디오 버튼 (관리자, 사용자)
        JRadioButton loginAsAdmin = new JRadioButton("관리자");
        JRadioButton loginAsUser = new JRadioButton("사용자");
        loginAsUser.setSelected(true); // 기본값으로 사용자 선택
        loginAsAdmin.setFont(font);
        loginAsUser.setFont(font);
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(loginAsAdmin);
        userTypeGroup.add(loginAsUser);

        // 라디오 버튼 선택 시 동작
        loginAsAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 관리자 라디오 버튼 선택 시, 식당번호 입력 필드를 표시
                restaurantLabel.setVisible(true);
                restaurantField.setVisible(true);
                revalidate();
                repaint();
            }
        });

        loginAsUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 라디오 버튼 선택 시, 식당번호 입력 필드를 숨김
                restaurantLabel.setVisible(false);
                restaurantField.setVisible(false);
                revalidate();
                repaint();
            }
        });

        // 로그인 버튼
        JButton loginButton = new JButton("로그인");
        loginButton.setBorder(BorderFactory.createEtchedBorder());
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(font);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 로그인 버튼 클릭 시 동작
                String id = idField.getText();
                String password = new String(passwordField.getPassword());
                String restaurant = restaurantField.getText();

                // 아이디와 비밀번호가 일치하는지 확인
                boolean loggedIn = checkLogin(id, password);
                if (loggedIn) {
                    JOptionPane.showMessageDialog(null, "로그인 성공!");
                
                } else {
                    JOptionPane.showMessageDialog(null, "잘못된 아이디 또는 비밀번호!");
                }
            }
        });

        // 패널에 추가
        gbc.insets = new Insets(20, 20, 5, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(loginAsAdmin, gbc);

        gbc.insets = new Insets(5, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(loginAsUser, gbc);

        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(loginPanel, gbc);

        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.ipadx = 260;
        add(loginButton, gbc);

        setVisible(true);
    }

    // 아이디와 비밀번호를 확인하는 메서드
    private boolean checkLogin(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(id) && parts[1].equals(password)) {
                    return true; // 아이디와 비밀번호가 일치하는 경우
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // 아이디와 비밀번호가 일치하지 않는 경우
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
