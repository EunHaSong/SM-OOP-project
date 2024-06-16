import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UserRegistrationForm2 extends JPanel implements ActionListener {
    private JLabel nameLabel, emailLabel, nicknameLabel, passwordLabel, confirmPasswordLabel, imageLabel;
    private JTextField nameTextField, emailTextField, nicknameTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton, resetButton;
    private Font defaultFont = new Font("국민연금체", Font.PLAIN, 15);
    private Color myOrange = new Color(255, 140, 0);

    public UserRegistrationForm2() {

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

        // 닉네임 입력 필드 설정
        nicknameLabel = new JLabel("닉네임");
        nicknameLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nicknameLabel, gbc);

        nicknameTextField = new JTextField();
        nicknameTextField.setFont(defaultFont);
        nicknameTextField.setPreferredSize(new Dimension(300, nicknameTextField.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nicknameTextField, gbc);

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
        passwordField.setEchoChar('*'); // 입력된 문자 대신 *로 표시
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
        confirmPasswordField.setEchoChar('*'); // 입력된 문자 대신 *로 표시
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        // 이미지 패널 설정
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);

        imageLabel = new JLabel("[ 학생증 이미지를 넣어주세요 ]");
        imageLabel.setFont(new Font("국민연금체", Font.BOLD, 20));
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150));
        imagePanel.add(imageLabel);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 플로우 레이아웃 사용

        // 제출 버튼 설정
        submitButton = new JButton("제출");
        submitButton.addActionListener(this); // ActionListener 등록

        // 초기화 버튼 설정
        resetButton = new JButton("초기화");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // 이미지 선택 버튼 설정
        JButton selectImageButton = new JButton("이미지 선택");
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage();
            }
        });

        // 확인 버튼 설정
        JButton confirmButton = new JButton("인증 확인");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmImage();
            }
        });

        // 각 버튼 레이아웃
        submitButton.setFont(defaultFont);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(myOrange);

        resetButton.setFont(defaultFont);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(myOrange);

        selectImageButton.setFont(defaultFont);
        selectImageButton.setForeground(Color.WHITE);
        selectImageButton.setBackground(myOrange);

        confirmButton.setFont(defaultFont);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(myOrange);

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(selectImageButton);
        buttonPanel.add(confirmButton);

        // 세로로 정렬하기 위한 패널 설정
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(imagePanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 컨테이너에 패널 추가
        add(formPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true); // 패널 표시
    }

    // 각 버튼 클릭시 액션 리스너
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
                // 이름, 이메일, 닉네임, 비밀번호 저장
                String[] userData = {name, email, nickname, password};
                boolean isSaved = saveUserData(userData);

                // 정상적으로 저장된 경우에만 로그인 화면으로 전환
                if (isSaved) {
                    // 로그인 창 생성 및 보이기
                    AllTPS mainFrame = new AllTPS();
                    LoginPanel loginPanel = new LoginPanel();
                    mainFrame.getLeftPanel().add(loginPanel, BorderLayout.CENTER);

                    // 현재 프레임 닫기
                    SwingUtilities.getWindowAncestor(this).dispose();
                }
            }
        } else if (e.getSource() == resetButton) { // 초기화 버튼 클릭 시
            resetForm();
        }
    }

    private void resetForm() {
        nameTextField.setText("");
        nicknameTextField.setText("");
        emailTextField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");

        // 이미지 초기화
        imageLabel.setIcon(null);
        imageLabel.setText("이미지 없음");

        // 이미지 패널의 여백 다시 설정
        JPanel imagePanel = (JPanel) imageLabel.getParent();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150));
    }

    private void selectImage() {
        // 이미지 파일을 선택하기 위한 JFileChooser 생성
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("이미지를 선택하세요");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // 이미지 파일을 선택하면 이미지 라벨에 이미지를 표시
            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            imageLabel.setIcon(icon);
            imageLabel.setText(""); // 이미지 라벨의 텍스트 제거

            // 이미지 크기 조정
            int panelWidth = imageLabel.getParent().getWidth(); // 패널의 너비
            int panelHeight = imageLabel.getParent().getHeight(); // 패널의 높이

            // 이미지 크기를 패널의 세로 크기에 맞춰 조정
            Image image = icon.getImage();
            int imgWidth = image.getWidth(imageLabel); // 이미지의 원본 너비
            int imgHeight = image.getHeight(imageLabel); // 이미지의 원본 높이

            double scaleFactor = (double) panelHeight / imgHeight; // 세로에 맞추기 위한 비율 계산
            int scaledWidth = (int) (imgWidth * scaleFactor); // 패널의 높이에 맞게 조정된 너비
            int scaledHeight = panelHeight; // 패널의 높이에 맞게 조정된 높이

            Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));

            // 이미지 패널의 여백 설정 제거
            JPanel imagePanel = (JPanel) imageLabel.getParent();
            imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        }
    }

    private void confirmImage() {
        // 이미지가 선택되었는지 확인
        if (imageLabel.getIcon() != null) {
            // 이미지가 선택된 경우에는 팝업 메시지로 인증 성공을 알림
            JOptionPane.showMessageDialog(this, "숙명여대 학생임이 증명되었습니다", "인증 성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // 이미지가 선택되지 않은 경우에는 에러 메시지 출력
            JOptionPane.showMessageDialog(this, "인증을 위해, 이미지를 선택하세요", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean saveUserData(String[] userData) {
        String name = userData[0];
        String email = userData[1];
        String nickname = userData[2];
        String password = userData[3];

        // 파일 경로 설정
        String filePath = "user_data.csv";

        // 파일이 존재하지 않으면 헤더를 추가하고 데이터를 저장
        if (!new File(filePath).exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                // 첫 줄에 헤더 추가
                writer.println("이름,이메일,닉네임,비밀번호");
                // 데이터 추가
                writer.println(name + "," + email + "," + nickname + "," + password);
                JOptionPane.showMessageDialog(this, "사용자 데이터가 성공적으로 저장되었습니다.", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
                return true; // 성공적으로 저장된 경우
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false; // 저장 실패
            }
        } else {
            // 파일이 이미 존재하는 경우, 닉네임 중복 확인 후 데이터 추가
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                 PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                boolean nicknameExists = false;
                String line;

                // 파일을 한 줄씩 읽어서 닉네임이 이미 존재하는지 확인
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].equals(nickname)) {
                        nicknameExists = true;
                        break;
                    }
                }

                if (nicknameExists) {
                    JOptionPane.showMessageDialog(this, "이미 존재하는 닉네임입니다. 다른 닉네임을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return false; // 닉네임 중복으로 저장 실패
                } else {
                    // 닉네임이 존재하지 않으면 데이터 추가
                    writer.println(name + "," + email + "," + nickname + "," + password);
                    JOptionPane.showMessageDialog(this, "사용자 데이터가 성공적으로 저장되었습니다.", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
                    return true; // 성공적으로 저장된 경우
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false; // 저장 실패
            }
        }
    }

    public void deletePanel() {
        jajajajaj frame = new jajajajaj();
        // 새로운 패널 생성
        LoginPanel loginPanel = new LoginPanel();
        // changeLeftPanel 메서드 호출
        frame.changeLeftPanel(loginPanel);
    }

    public void replacePanel() {
        jajajajaj frame = new jajajajaj(); // jajajajaj 객체 생성
        // 새로운 패널 생성
        LoginPanel loginPanel = new LoginPanel();
        // changeLeftPanel 메서드 호출
        frame.replaceLeftPanelContent(loginPanel);
    }

    public void changePPPP() {
        JPanel newPanel = new LoginPanel(); // 새로운 패널 생성
        jajajajaj.getInstance().changePPPP(newPanel); // jajajajaj 클래스의 static 메서드 호출
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("사용자 등록 폼");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 600);
                frame.setContentPane(new UserRegistrationForm2());
                frame.setVisible(true);
            }
        });
    }
}

