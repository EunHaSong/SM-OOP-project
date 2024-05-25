import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ReviewPage extends JFrame implements ActionListener {

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
    private Font defaultFont = new Font("조선굴림체", Font.PLAIN, 13);

    public ReviewPage() {
        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;

        // 프레임 설정
        setTitle("리뷰 작성 페이지");
        setBounds(screenSize.width / 4, screenSize.height / 4, width, height); // 화면 크기의 1/4 위치와 크기 설정
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
        setResizable(false); // 크기 조절 금지

        // 전체 컨테이너 패널 설정
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout(10, 10)); // 패널 간 여백 설정

        // 상단 패널 설정 (메뉴 선택, 제목 입력, 별점 및 날짜 선택)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5행 1열의 그리드 레이아웃 사용

        // 메뉴 선택 패널 설정
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        JLabel menuLabel = new JLabel("메뉴:");
        menuLabel.setFont(defaultFont);

        menuComboBox = new JComboBox<>(menuItems);
        menuComboBox.setFont(defaultFont);
        menuPanel.add(menuLabel, BorderLayout.WEST);
        menuPanel.add(menuComboBox, BorderLayout.CENTER);

        // 특징 선택 패널 설정
        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        JLabel featureLabel = new JLabel("특징:");
        featureLabel.setFont(defaultFont);

        featureComboBox = new JComboBox<>(featureItems);
        featureComboBox.setFont(defaultFont);
        featurePanel.add(featureLabel, BorderLayout.WEST);
        featurePanel.add(featureComboBox, BorderLayout.CENTER);

        // 제목 입력 패널 설정
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        JLabel titleLabel = new JLabel("제목:");
        titleLabel.setFont(defaultFont);

        titleField = new JTextField();
        titleField.setFont(defaultFont);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);

        // 별점 선택 패널 설정
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
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
        topPanel.add(titlePanel);
        topPanel.add(ratingPanel);
        topPanel.add(datePanel);

        // 중앙 패널 설정 (내용 입력)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5, 5)); // 패널 간 여백 설정
        JLabel contentLabel = new JLabel("내용:");
        contentLabel.setFont(defaultFont);

        contentArea = new JTextArea();
        contentArea.setFont(defaultFont);
        contentArea.setLineWrap(true); // 자동 줄 바꿈 활성화
        JScrollPane scrollPane = new JScrollPane(contentArea); // 스크롤 기능 추가
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 수직 스크롤 항상 표시
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // 이미지 패널 설정
        images = new ArrayList<>();
        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // 패널 간 여백 설정
        addImageButton = createAddImageButton();
        imagePanel.add(addImageButton);

        // 하단 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 버튼 간 여백 설정
        JButton submitButton = new JButton("작성 완료");
        submitButton.setFont(defaultFont);

        submitButton.addActionListener(this); // ActionListener 등록
        JButton cancelButton = new JButton("취소");
        cancelButton.setFont(defaultFont);

        cancelButton.addActionListener(this); // ActionListener 등록
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // 스크롤 패널 설정
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BorderLayout(10, 10)); // 패널 간 여백 설정
        scrollablePanel.add(topPanel, BorderLayout.NORTH);
        scrollablePanel.add(contentPanel, BorderLayout.CENTER);
        scrollablePanel.add(imagePanel, BorderLayout.SOUTH);

        JScrollPane mainScrollPane = new JScrollPane(scrollablePanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 수직 스크롤 항상 표시

        // 전체 컨테이너 패널에 스크롤 패널 및 버튼 패널 추가
        containerPanel.add(mainScrollPane, BorderLayout.CENTER);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 프레임에 전체 컨테이너 패널 추가
        add(containerPanel);

        setVisible(true); // 프레임 표시
    }

    private JButton createAddImageButton() {
        JButton button = new JButton("+");
        button.setFont(defaultFont);
        button.setPreferredSize(new Dimension(100, 100));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (images.size() < 3) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("이미지를 선택하세요");
                    int result = fileChooser.showOpenDialog(ReviewPage.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().getPath());
                        Image image = icon.getImage();
                        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        images.add(scaledIcon);
                        updateImagePanel();
                    }
                } else {
                    JOptionPane.showMessageDialog(ReviewPage.this, "이미지는 최대 3개까지 추가할 수 있습니다.", "제한 초과", JOptionPane.WARNING_MESSAGE);
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

            // 팝업 메시지로 리뷰 작성 완료 알림
            JOptionPane.showMessageDialog(this, selectedMenu + "에 대한 리뷰가 작성되었습니다.", "작성 완료", JOptionPane.INFORMATION_MESSAGE);

            // 여기서 리뷰 데이터를 저장하거나 다른 작업을 수행할 수 있습니다.
            System.out.println("메뉴: " + selectedMenu);
            System.out.println("특징: " + selectedFeature);
            System.out.println("제목: " + title);
            System.out.println("별점: " + rating);
            System.out.println("날짜: " + date);
            System.out.println("내용: " + content);
            System.out.println("이미지 개수: " + images.size());
            for (int i = 0; i < images.size(); i++) {
                System.out.println("이미지 " + (i + 1) + ": " + images.get(i));
            }

            // 작성이 완료되면 창을 닫습니다.
            dispose();
        } else if (e.getActionCommand().equals("취소")) {
            int confirm = JOptionPane.showConfirmDialog(this, "정말 취소 버튼을 누르시겠습니까?", "취소 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // 내용 초기화
                menuComboBox.setSelectedIndex(0);
                featureComboBox.setSelectedIndex(0);
                titleField.setText("");
                ratingComboBox.setSelectedIndex(0);
                dateSpinner.setValue(new Date());
                contentArea.setText("");
                images.clear();
                updateImagePanel();
            }
        }
    }

    public static void main(String[] args) {
        new ReviewPage();
    }
}

