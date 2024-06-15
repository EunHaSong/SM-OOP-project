import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import java.nio.file.*;
import java.util.List;

class AdminInfo extends JPanel {

    public AdminInfo(String msg) {
        // 화면 크기 초기 설정
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 2;
        int frameHeight = screenSize.height;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frameWidth / 2, frameHeight / 2)); 
        setBackground(Color.WHITE);

        Font customFont = new Font("NPS font", Font.PLAIN, 12); // Fallback to default font if loading fails

        // 이미지를 표시할 라벨
        JLabel imageLabel = new JLabel("+ 음식사진을 업로드하세요", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setFont(customFont);
        add(imageLabel, BorderLayout.NORTH);

        // 파일 첨부 및 검색을 위한 Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(Color.WHITE);

        // 파일 첨부 버튼
        JButton attachButton = new JButton("이미지 첨부");
        attachButton.setFont(customFont);
        attachButton.setForeground(Color.WHITE);
        addHoverEffect(attachButton); // 마우스 호버 효과 추가
        attachButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filename = selectedFile.getName();
                String imagePath = selectedFile.getPath();
                imageLabel.setText("선택한 이미지: " + filename);
                try {
                    Image image = ImageIO.read(new File(imagePath));
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JLabel attachLabel = new JLabel("음식사진 첨부");
        attachLabel.setFont(customFont);
        controlPanel.add(attachLabel);
        controlPanel.add(attachButton);

        // 검색을 위한 TextField와 버튼
        JTextField searchField = new JTextField(30);
        searchField.setFont(customFont);
        JButton searchButton = new JButton("검색");
        searchButton.setFont(customFont);
        JLabel searchLabel = new JLabel("식당검색");
        searchLabel.setFont(customFont);
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);


        add(controlPanel, BorderLayout.CENTER);

        // 입력 필드를 포함한 Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBackground(Color.WHITE);

        JLabel menuLabel = new JLabel("메뉴", SwingConstants.CENTER);
        menuLabel.setFont(customFont);
        JTextField menuTextField = new JTextField();
        menuTextField.setFont(customFont);
        inputPanel.add(menuLabel);
        inputPanel.add(menuTextField);

        JLabel priceLabel = new JLabel("가격", SwingConstants.CENTER);
        priceLabel.setFont(customFont);
        JTextField priceTextField = new JTextField();
        priceTextField.setFont(customFont);
        inputPanel.add(priceLabel);
        inputPanel.add(priceTextField);

        JLabel discountLabel = new JLabel("할인 정보", SwingConstants.CENTER);
        discountLabel.setFont(customFont);
        JTextField discountTextField = new JTextField();
        discountTextField.setFont(customFont);
        inputPanel.add(discountLabel);
        inputPanel.add(discountTextField);

        JButton registerButton = new JButton("등록하기");
        registerButton.setFont(customFont);
        registerButton.setForeground(Color.WHITE);
        addHoverEffect(registerButton); // 마우스 호버 효과 추가
        inputPanel.add(new JLabel()); // 빈 공간
        inputPanel.add(registerButton);

        registerButton.addActionListener(e -> {
            String menu = menuTextField.getText();
            String price = priceTextField.getText();
            String discount = discountTextField.getText();

            if (!menu.isEmpty() && !price.isEmpty() && !discount.isEmpty()) {
                try {
                    Path path = Paths.get("\"C:\\yeons\\java\\final\\src\\관리자용정보등록.csv\"");
                    boolean fileExists = Files.exists(path);

                    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                        if (!fileExists) {
                            writer.write("메뉴,가격,할인 정보");
                            writer.newLine();
                        }
                        writer.write(String.join(",", menu, price, discount));
                        writer.newLine();
                    }

                    JFrame newFrame = new JFrame("완료");
                    newFrame.setSize(400, 300);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newFrame.getContentPane().setBackground(Color.WHITE); 
                    JLabel completionLabel = new JLabel("메뉴와 할인정보가 등록되었습니다.", SwingConstants.CENTER);
                    completionLabel.setFont(customFont); // Set custom font for the label
                    newFrame.add(completionLabel);
                    newFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        add(inputPanel, BorderLayout.SOUTH);
    }

    // 버튼에 마우스 호버 효과 및 둥근 테두리 추가
    private void addHoverEffect(JButton button) {
        // 마우스 호버 효과 추가
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.ORANGE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 140, 0));
            }
        });
        // 둥근 테두리 추가
        Border roundedBorder = BorderFactory.createLineBorder(new Color(255, 140, 0), 4, true);
        button.setBorder(roundedBorder);

        // 글자색과 글꼴 설정
        button.setFont(new Font("NPS font", Font.PLAIN, 12));
        button.setForeground(Color.BLACK); // 검은색으로 설정
    }
}
