import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ReviewPagePanel2 extends JPanel implements ActionListener {

    private JTextField titleField;
    private JTextArea contentArea;
    private JComboBox<String> menuComboBox;
    private JComboBox<Integer> ratingComboBox;
    private JComboBox<String> featureComboBox;
    private JSpinner dateSpinner;
    private ArrayList<ImageIcon> images;
    private JPanel imagePanel;
    private JButton addImageButton;

    private String[] menuItems = {"아메리카노", "카페 라떼", "카푸치노", "에스프레소", "초콜릿 케이크"};
    private String[] featureItems = {"위생", "맛", "서비스"};
    private Font defaultFont = new Font("국민연금체", Font.PLAIN, 13);
    private Color myOrange = new Color(255, 140, 0);
    private Font defaultFont_Bold = new Font("국민연금체", Font.BOLD, 30);

    public ReviewPagePanel2() {
        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // 화면 너비의 절반 계산
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;


        setBounds(screenSize.width / 2, screenSize.height / 2, width, height);
        setLayout(new BorderLayout(10, 10)); // 패널 간 여백 설정
        setBackground(Color.WHITE);

        // 전체 컨테이너 패널 설정
        JPanel containerPanel = new JPanel();
        containerPanel.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        containerPanel.setLayout(new BorderLayout(10, 10)); // 패널 간 여백 설정
        containerPanel.setBackground(Color.WHITE);

        JPanel StorenamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align components to the right
        StorenamePanel.setBackground(myOrange);

        JLabel storenameLable = new JLabel("상점이름입니당");
        storenameLable.setBackground(myOrange);
        storenameLable.setForeground(Color.WHITE);
        storenameLable.setLayout(new BorderLayout(30, 15));
        storenameLable.setFont(new Font("국민연금체", Font.PLAIN, 20));
        StorenamePanel.add(storenameLable);

        // 상단 패널 설정 (메뉴 선택, 제목 입력, 별점 및 날짜 선택)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 5행 1열의 그리드 레이아웃 사용
        topPanel.setBackground(Color.WHITE);

        // 메뉴 선택 패널 설정
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout(5, 10)); // 패널 간 여백 설정
        menuPanel.setBackground(Color.WHITE);
        JLabel menuLabel = new JLabel("메뉴:");
        menuLabel.setFont(defaultFont);

        menuComboBox = new JComboBox<>(menuItems);
        menuComboBox.setFont(defaultFont);
        menuPanel.add(menuLabel, BorderLayout.WEST);
        menuPanel.add(menuComboBox, BorderLayout.CENTER);

        // 특징 선택 패널 설정
        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        featurePanel.setBackground(Color.WHITE);
        JLabel featureLabel = new JLabel("특징:");
        featureLabel.setFont(defaultFont);

        featureComboBox = new JComboBox<>(featureItems);
        featureComboBox.setFont(defaultFont);
        featurePanel.add(featureLabel, BorderLayout.WEST);
        featurePanel.add(featureComboBox, BorderLayout.CENTER);

        // 별점 선택 패널 설정
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        ratingPanel.setBackground(Color.WHITE);
        JLabel ratingLabel = new JLabel("별점:");
        ratingLabel.setFont(defaultFont);

        Integer[] ratings = {1, 2, 3, 4, 5};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setFont(defaultFont);
        ratingPanel.add(ratingLabel, BorderLayout.WEST);
        ratingPanel.add(ratingComboBox, BorderLayout.CENTER);

        // 날짜 선택 패널 설정
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        datePanel.setBackground(Color.WHITE);
        JLabel dateLabel = new JLabel("날짜:");
        dateLabel.setFont(defaultFont);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setFont(defaultFont);
        dateSpinner.setValue(new Date()); // 현재 날짜로 설정

        datePanel.add(dateLabel, BorderLayout.WEST);
        datePanel.add(dateSpinner, BorderLayout.CENTER);

        topPanel.add(menuPanel);
        topPanel.add(featurePanel);
        topPanel.add(ratingPanel);
        topPanel.add(datePanel);

        // 중앙 패널 설정 (내용 입력)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        contentPanel.setBackground(Color.WHITE);
        JLabel contentLabel = new JLabel("내용:");
        contentLabel.setFont(defaultFont);

        contentArea = new JTextArea(0, 25);
        contentArea.setFont(defaultFont);
        contentArea.setLineWrap(true); // 자동 줄 바꿈 활성화
        JScrollPane scrollPane = new JScrollPane(contentArea); // 스크롤 기능 추가
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 수직 스크롤 항상 표시
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // 이미지 패널 설정
        images = new ArrayList<>();
        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // 패널 간 여백 설정
        imagePanel.setBackground(Color.WHITE);
        addImageButton = createAddImageButton();
        imagePanel.add(addImageButton);


        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 플로우 레이아웃 사용

        // 제출 버튼 설정
        JButton submitButton = new JButton("작성 완료");
        submitButton.addActionListener(this); // ActionListener 등록
        submitButton.setFont(defaultFont);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(myOrange);
        buttonPanel.add(submitButton);

        // 초기화 버튼 설정
        JButton cancelButton = new JButton("초기화");
        cancelButton.addActionListener(this); // ActionListener 등록
        cancelButton.setFont(defaultFont);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(myOrange);
        buttonPanel.add(cancelButton);

        // 상점의 리뷰 특징 패널과 리뷰 내용 작성 패널을 가로 정렬해둔 패널
        JPanel hapPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        hapPanel.setBackground(Color.WHITE);
        hapPanel.add(topPanel);
        hapPanel.add(contentPanel);

        // 상점의 리뷰 특징 패널과 리뷰 내용 작성 패널을 가로 정렬해둔 패널
        JPanel hap2Panel = new JPanel(new GridLayout(2, 1, 0, 0));
        hap2Panel.setBackground(Color.WHITE);
        hap2Panel.add(imagePanel);
        hap2Panel.add(buttonPanel);

        // 스크롤 패널 설정
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BorderLayout(0, 0)); // 패널 간 여백 설정
        scrollablePanel.setBackground(Color.WHITE);

        scrollablePanel.add(StorenamePanel, BorderLayout.NORTH);
        scrollablePanel.add(hapPanel, BorderLayout.CENTER);
        scrollablePanel.add(hap2Panel, BorderLayout.SOUTH);

        JScrollPane mainScrollPane = new JScrollPane(scrollablePanel);
        //mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 수직 스크롤 항상 표시

        // 전체 컨테이너 패널에 스크롤 패널 및 버튼 패널 추가
        containerPanel.add(mainScrollPane, BorderLayout.NORTH);

        // 패널에 전체 컨테이너 패널 추가
        add(containerPanel);

        setVisible(true); // 패널 표시
    }

    private JButton createAddImageButton() {
        JButton button = new JButton("+");
        button.setFont(defaultFont);
        button.setPreferredSize(new Dimension(150, 150));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (images.size() < 3) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("이미지를 선택하세요");
                    int result = fileChooser.showOpenDialog(ReviewPagePanel2.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().getPath());
                        Image image = icon.getImage();
                        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        images.add(scaledIcon);
                        updateImagePanel();
                    }
                } else {
                    JOptionPane.showMessageDialog(ReviewPagePanel2.this, "이미지는 최대 3개까지 추가할 수 있습니다.", "제한 초과", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return button;
    }

    private void updateImagePanel() {
        imagePanel.removeAll();
        for (ImageIcon icon : images) {
            JLabel imageLabel = new JLabel(icon);
            imagePanel.add(imageLabel);
        }
        if (images.size() < 3) {
            addImageButton = createAddImageButton();
            imagePanel.add(addImageButton);
        }
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("작성 완료")) {
            String selectedMenu = (String) menuComboBox.getSelectedItem();
            String selectedFeature = (String) featureComboBox.getSelectedItem();
            String title = titleField.getText();
            String content = contentArea.getText();
            int rating = (int) ratingComboBox.getSelectedItem();
            Date date = (Date) dateSpinner.getValue();

            StringBuilder review = new StringBuilder();
            review.append("메뉴: ").append(selectedMenu).append("\n");
            review.append("특징: ").append(selectedFeature).append("\n");
            review.append("제목: ").append(title).append("\n");
            review.append("내용: ").append(content).append("\n");
            review.append("별점: ").append(rating).append("\n");
            review.append("날짜: ").append(date.toString()).append("\n");
            review.append("이미지 파일 이름: ").append(images.size()).append("\n");

            JOptionPane.showMessageDialog(this, review.toString(), "리뷰 내용", JOptionPane.INFORMATION_MESSAGE);

            // Save to CSV
            saveReviewToCSV(selectedMenu, selectedFeature, title, content, rating, date, images.size());
        } else if (e.getActionCommand().equals("취소")) {
            int result = JOptionPane.showConfirmDialog(this, "작성을 취소하시겠습니까?", "취소 확인", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                clearForm();
            }
        }
    }

    private void saveReviewToCSV(String menu, String feature, String title, String content, int rating, Date date, int imageCount) {
        String csvFile = "reviews.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(menu).append(",");
            writer.append(feature).append(",");
            writer.append(title).append(",");
            writer.append(content).append(",");
            writer.append(String.valueOf(rating)).append(",");
            writer.append(date.toString()).append(",");
            writer.append(String.valueOf(imageCount)).append("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        titleField.setText("");
        contentArea.setText("");
        menuComboBox.setSelectedIndex(0);
        ratingComboBox.setSelectedIndex(0);
        dateSpinner.setValue(new Date());
        images.clear();
        updateImagePanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("리뷰 작성 페이지");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(600, 600));
            frame.setResizable(false);

            ReviewPagePanel reviewPanel = new ReviewPagePanel();
            frame.add(reviewPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}