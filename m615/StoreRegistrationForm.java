import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StoreRegistrationForm extends JPanel implements ActionListener {

    private JLabel nameLabel, emailLabel, storeNameLabel, passwordLabel, confirmPasswordLabel;
    private JTextField nameTextField, emailTextField, storeNameTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton, resetButton;

    private Font defaultFont = new Font("국민연금체", Font.PLAIN, 15);
    private Color myOrange = new Color(255, 140, 0);

    private static final String CSV_FILE = "store_data.csv";
    private static final String[] CSV_HEADERS = {"이름", "이메일", "상점 이름", "비밀번호", "식당 번호"};

    public StoreRegistrationForm() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 폼 패널 설정
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 컴포넌트 간의 여백 설정

        // 이름 입력 필드 설정
        nameLabel = new JLabel("이름");
        nameLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        nameTextField.setFont(defaultFont);
        nameTextField.setPreferredSize(new Dimension(300, nameTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameTextField, gbc);

        // 이메일 입력 필드 설정
        emailLabel = new JLabel("이메일");
        emailLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(emailLabel, gbc);

        emailTextField = new JTextField();
        emailTextField.setFont(defaultFont);
        emailTextField.setPreferredSize(new Dimension(300, emailTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailTextField, gbc);

        // 상점 이름 입력 필드 설정
        storeNameLabel = new JLabel("상점 이름");
        storeNameLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(storeNameLabel, gbc);

        storeNameTextField = new JTextField();
        storeNameTextField.setFont(defaultFont);
        storeNameTextField.setPreferredSize(new Dimension(300, storeNameTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(storeNameTextField, gbc);

        // 비밀번호 입력 필드 설정
        passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(defaultFont);
        passwordField.setPreferredSize(new Dimension(300, passwordField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // 비밀번호 확인 입력 필드 설정
        confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(defaultFont);
        confirmPasswordField.setPreferredSize(new Dimension(300, confirmPasswordField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 플로우 레이아웃 사용

        // 제출 버튼 설정
        submitButton = new JButton("제출");
        submitButton.addActionListener(this); // ActionListener 등록
        submitButton.setFont(defaultFont);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(myOrange);
        buttonPanel.add(submitButton);

        // 초기화 버튼 설정
        resetButton = new JButton("초기화");
        resetButton.addActionListener(e -> {
            nameTextField.setText("");
            emailTextField.setText("");
            storeNameTextField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        });
        resetButton.setFont(defaultFont);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(myOrange);
        buttonPanel.add(resetButton);

        // 컨테이너에 패널 추가
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true); // 패널 표시
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) { // 제출 버튼 클릭 시
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String storeName = storeNameTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if fields are empty
            StringBuilder emptyFields = new StringBuilder();
            if (name.isEmpty()) emptyFields.append("이름 ");
            if (email.isEmpty()) emptyFields.append("이메일 ");
            if (storeName.isEmpty()) emptyFields.append("상점 이름 ");
            if (password.isEmpty()) emptyFields.append("비밀번호 ");
            if (confirmPassword.isEmpty()) emptyFields.append("비밀번호 확인 ");

            if (emptyFields.length() > 0) {
                JOptionPane.showMessageDialog(this, emptyFields.toString() + "을(를) 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            } else {
                // Check if store name already exists in CSV
                Map<String, String> storeData = readStoreData();
                if (storeData.containsKey(storeName)) {
                    String restaurantNumber = storeData.get(storeName);
                    JOptionPane.showMessageDialog(this, "식당 번호는 " + restaurantNumber + "입니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String restaurantNumber = generateRestaurantNumber();
                    appendToCsv(name, email, storeName, password, restaurantNumber);
                    JOptionPane.showMessageDialog(this, "식당 번호는 " + restaurantNumber + "입니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private Map<String, String> readStoreData() {
        Map<String, String> storeData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            // Skip header line
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String storeName = parts[2]; // Assuming store name is at index 2
                    String restaurantNumber = parts[4]; // Assuming restaurant number is at index 4
                    storeData.put(storeName, restaurantNumber);
                }
            }
        } catch (IOException e) {
            // 파일이 없는 경우, 그냥 빈 데이터 반환
        }
        return storeData;
    }

    private void appendToCsv(String name, String email, String storeName, String password, String restaurantNumber) {
        boolean fileExists = new File(CSV_FILE).exists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            if (!fileExists) {
                // 파일이 존재하지 않는 경우에만 헤더 추가
                writer.write(String.join(",", CSV_HEADERS));
                writer.newLine();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(",").append(email).append(",").append(storeName).append(",").append(password).append(",").append(restaurantNumber);
            writer.write(sb.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateRestaurantNumber() {
        Random random = new Random();
        // 계속 이어지는 부분입니다.

        // 랜덤한 천 자리의 식당 번호 생성
        int restaurantNumber = random.nextInt(900) + 100; // 100부터 999까지의 랜덤한 숫자 생성
        return String.valueOf(restaurantNumber);
    }

    public static void main(String[] args) {
        // Swing GUI를 생성하는 코드
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Store Registration Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(500, 300));
            frame.setContentPane(new StoreRegistrationForm());
            frame.pack();
            frame.setLocationRelativeTo(null); // 화면 중앙에 표시
            frame.setVisible(true);
        });
    }
}

