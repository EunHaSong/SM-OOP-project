import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MembershipRegistrationForm extends JFrame implements ActionListener {

    private Container container;
    private JLabel titleLabel, nameLabel, emailLabel, nicknameLabel, passwordLabel, confirmPasswordLabel, imageLabel;
    private JTextField nameTextField, emailTextField, nicknameTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton, resetButton;

    public MembershipRegistrationForm() {
        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;

        // 프레임 설정
        setTitle("회원가입 시작");
        setBounds(screenSize.width / 4, screenSize.height / 4, width, height); // 화면 크기의 1/4 위치와 크기 설정
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
        setResizable(false); // 크기 조절 금지

        // 컨테이너 설정
        container = getContentPane();
        container.setLayout(new BorderLayout(10, 10)); // BorderLayout 사용

        // 타이틀 라벨 설정
        titleLabel = new JLabel("[ 회원 가입 폼 ]");
        titleLabel.setFont(new Font("조선굴림체", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        container.add(titleLabel, BorderLayout.NORTH); // 상단에 타이틀 라벨 추가

        // 폼 패널 설정
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 컴포넌트 간의 여백 설정

        // 이름 입력 필드 설정
        nameLabel = new JLabel("이름");
        nameLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        nameTextField.setPreferredSize(new Dimension(200, nameTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameTextField, gbc);

        // 이메일 입력 필드 설정
        emailLabel = new JLabel("이메일");
        emailLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(emailLabel, gbc);

        emailTextField = new JTextField();
        emailTextField.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        emailTextField.setPreferredSize(new Dimension(200, emailTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailTextField, gbc);

        // 닉네임 입력 필드 설정
        nicknameLabel = new JLabel("닉네임");
        nicknameLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nicknameLabel, gbc);

        nicknameTextField = new JTextField();
        nicknameTextField.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        nicknameTextField.setPreferredSize(new Dimension(200, nicknameTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nicknameTextField, gbc);

        // 비밀번호 입력 필드 설정
        passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        passwordField.setPreferredSize(new Dimension(200, passwordField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // 비밀번호 확인 입력 필드 설정
        confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        confirmPasswordField.setPreferredSize(new Dimension(200, confirmPasswordField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        // 이미지 라벨 설정
        imageLabel = new JLabel("이미지 없음");
        imageLabel.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(imageLabel, gbc);

        container.add(formPanel, BorderLayout.CENTER); // 폼 패널을 중앙에 추가

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 플로우 레이아웃 사용

        // 제출 버튼 설정
        submitButton = new JButton("제출");
        submitButton.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        submitButton.addActionListener(this); // ActionListener 등록
        buttonPanel.add(submitButton);

        // 초기화 버튼 설정
        resetButton = new JButton("초기화");
        resetButton.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        resetButton.addActionListener(this); // ActionListener 등록
        buttonPanel.add(resetButton);

        // 이미지 선택 버튼 설정
        JButton selectImageButton = new JButton("이미지 선택");
        selectImageButton.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이미지 파일을 선택하기 위한 JFileChooser 생성
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("이미지를 선택하세요");
                int result = fileChooser.showOpenDialog(MembershipRegistrationForm.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // 이미지 파일을 선택하면 이미지 라벨에 이미지를 표시
                    ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                    imageLabel.setIcon(icon);
                    imageLabel.setText(""); // 이미지 라벨의 텍스트 제거

                    // 이미지 크기 조정
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(imageLabel.getWidth() * 2, imageLabel.getHeight() * 8, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        });
        buttonPanel.add(selectImageButton);

        // 확인 버튼 설정
        JButton confirmButton = new JButton("인증 확인");
        confirmButton.setFont(new Font("조선굴림체", Font.PLAIN, 15));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이미지가 선택되었는지 확인
                if (imageLabel.getIcon() != null) {
                    // 이미지가 선택된 경우에는 팝업 메시지로 인증 성공을 알림
                    JOptionPane.showMessageDialog(MembershipRegistrationForm.this, "숙명여대 학생임이 증명되었습니다", "인증 성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // 이미지가 선택되지 않은 경우에는 에러 메시지 출력
                    JOptionPane.showMessageDialog(MembershipRegistrationForm.this, "이미지를 선택하세요", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(confirmButton);

        container.add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널을 하단에 추가

        setVisible(true); // 프레임 표시
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) { // 제출 버튼 클릭 시
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String nickname = nicknameTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            StringBuilder emptyFields = new StringBuilder();
            if (name.isEmpty()) emptyFields.append("이름 ");
            if (nickname.isEmpty()) emptyFields.append("닉네임 ");
            if (email.isEmpty()) emptyFields.append("이메일 ");
            if (password.isEmpty()) emptyFields.append("비밀번호 ");
            if (confirmPassword.isEmpty()) emptyFields.append("비밀번호 확인 ");

            if (emptyFields.length() > 0) {
                JOptionPane.showMessageDialog(this, emptyFields.toString() + "을(를) 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "등록 성공.\n이름: " + name + "\n이메일: " + email + "\n닉네임: " + nickname, "성공", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == resetButton) { // 초기화 버튼 클릭 시
            nameTextField.setText("");
            nicknameTextField.setText("");
            emailTextField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            imageLabel.setIcon(null);
            imageLabel.setText("이미지 없음");
        }
    }

    public static void main(String[] args) {
        new MembershipRegistrationForm();
    }
}
