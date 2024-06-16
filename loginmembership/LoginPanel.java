import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField restaurantField;
    private JLabel restaurantLabel; // 식당번호 라벨을 관리하기 위한 전역 변수

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        Font font = new Font("국민연금체", Font.PLAIN, 27);
        Font font2 = new Font("국민연금체", Font.PLAIN, 30);

        // 버튼 패널 생성 (관리자, 사용자 버튼)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton loginAsAdmin = new JButton("식당관리자");
        JButton loginAsUser = new JButton("송이 사용자");
        loginAsAdmin.setFont(font2);
        loginAsUser.setFont(font2);
        loginAsAdmin.setBackground(new Color(255, 140, 0));
        loginAsUser.setBackground(new Color(255, 140, 0));
        loginAsAdmin.setForeground(Color.WHITE);
        loginAsUser.setForeground(Color.WHITE);
        buttonPanel.add(loginAsUser);
        buttonPanel.add(loginAsAdmin);
        buttonPanel.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.05;
        add(buttonPanel, gbc);

        // 로그인 패널 생성
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        innerGbc.insets = new Insets(30, 5, 10, 20);
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;

        addField("아이디", idField = new JTextField(30), loginPanel, innerGbc);
        addField("비밀번호", passwordField = new JPasswordField(30), loginPanel, innerGbc);
        restaurantLabel = addField("식당번호", restaurantField = new JTextField(30), loginPanel, innerGbc); // 라벨 추가 및 반환

        restaurantField.setVisible(false); // 기본적으로 숨김
        restaurantLabel.setVisible(false); // 라벨도 숨김

        gbc.gridy = 1;
        gbc.weighty = 0.6;
        add(loginPanel, gbc);

        // 로그인 버튼 패널
        JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 40));
        JButton loginButton = new JButton("로그인");
        JButton backToRegister = new JButton("회원가입");

        loginButton.setFont(font);
        backToRegister.setFont(font);
        loginButton.setBackground(new Color(255, 140, 0));
        backToRegister.setBackground(new Color(255, 140, 0));
        loginButton.setForeground(Color.WHITE);
        backToRegister.setForeground(Color.WHITE);
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(backToRegister);

        gbc.gridy = 2;
        gbc.weighty = 0.01;
        add(loginButtonPanel, gbc);

        loginAsAdmin.addActionListener(e -> {
            restaurantField.setVisible(true);
            restaurantLabel.setVisible(true); // 라벨 표시
        });

        loginAsUser.addActionListener(e -> {
            restaurantField.setVisible(false);
            restaurantLabel.setVisible(false); // 라벨 숨김
        });

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            if (restaurantField.isVisible()) {
                String restaurant = restaurantField.getText();
                if (checkAdminLogin(id, password, restaurant)) {
                    JOptionPane.showMessageDialog(null, "관리자 로그인 성공!");
                    idField.setText("");
                    passwordField.setText("");
                    restaurantField.setText("");

                    //changePPPP_store();
                    // 회원가입 창 생성 및 보이기
                    AllTPS mainFrame = new AllTPS();
                    MyPage myPage = new MyPage(null);
                    mainFrame.getLeftPanel().add(myPage, BorderLayout.CENTER);

                    // 현재 프레임 닫기
                    SwingUtilities.getWindowAncestor(this).dispose();


                } else {
                    JOptionPane.showMessageDialog(null, "잘못된 아이디, 비밀번호 또는 식당번호!");
                }
            } else {
                if (checkUserLogin(id, password)) {
                    JOptionPane.showMessageDialog(null, "사용자 로그인 성공!");
                    idField.setText("");
                    passwordField.setText("");

                    // 회원가입 창 생성 및 보이기
                    AllTPS mainFrame = new AllTPS();
                    FoodCategory foodCategory = new FoodCategory();
                    mainFrame.getLeftPanel().add(foodCategory, BorderLayout.CENTER);

                    // 현재 프레임 닫기
                    SwingUtilities.getWindowAncestor(this).dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "잘못된 아이디 또는 비밀번호!");
                }
            }
        });

        backToRegister.addActionListener(e -> {
            // 회원가입 창 생성 및 보이기
            AllTPS mainFrame = new AllTPS();
            MembershipRegistrationForm membership = new MembershipRegistrationForm();
            mainFrame.getLeftPanel().add(membership, BorderLayout.CENTER);

            // 현재 프레임 닫기
            SwingUtilities.getWindowAncestor(this).dispose();;
        });

    }

    private JLabel addField(String label, JTextField field, JPanel panel, GridBagConstraints gbc) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("국민연금체", Font.PLAIN, 25));
        panel.add(jLabel, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        return jLabel; // 라벨을 반환하여 외부에서 사용할 수 있도록 함
    }

    private boolean checkUserLogin(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("user_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[1].equals(id) && parts[3].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkAdminLogin(String id, String password, String restaurantNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader("store_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[1].equals(id) && parts[3].equals(password) && parts[4].equals(restaurantNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deletePanel() {
        jajajajaj frame = new jajajajaj();
        // 새로운 패널 생성
        MembershipRegistrationForm membershipPanel = new MembershipRegistrationForm();
        // changeLeftPanel 메서드 호출
        frame.changeLeftPanel(membershipPanel);
    }

    public void changePPPP_user() {
        JPanel newPanel = new FoodCategory(); // 새로운 패널 생성
        jajajajaj.getInstance().changePPPP(newPanel); // jajajajaj 클래스의 static 메서드 호출
    }

    public void changePPPP_store() {
        JPanel newPanel = new UserRS(); // 새로운 패널 생성
        jajajajaj.getInstance().changePPPP(newPanel); // jajajajaj 클래스의 static 메서드 호출
    }

}