import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FirstTimeMyPage extends JPanel {
    // 화면 크기 가져오기
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    // 화면 너비의 절반 계산
    int halfScreenWidth = screenSize.width / 2;
    int screenHeight = screenSize.height;

    // 다크 오렌지 색상 설정
    Color darkOrangeColor = new Color(255, 140, 0); // RGB(255, 140, 0)

    public FirstTimeMyPage(String username) {
        // 패널 크기 설정
        setPreferredSize(new Dimension(halfScreenWidth, screenHeight));

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 제목 라벨 생성
        JLabel titleLabel = new JLabel("<html>마이페이지<br>안녕하세요 " + username + "님</html>");
        titleLabel.setPreferredSize(new Dimension(halfScreenWidth, 100)); // 라벨의 크기 설정
        titleLabel.setFont(new Font("NPS font", Font.BOLD, 30)); // 폰트 변경
        titleLabel.setForeground(darkOrangeColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // 왼쪽 마진 추가

        // 다른 가게 둘러보기 버튼
        ImageIcon searchIcon = new ImageIcon("image/search.png");
        Image searchImg = searchIcon.getImage();
        Image updatedSearchImg = searchImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon updatedSearchIcon = new ImageIcon(updatedSearchImg);

        JButton browseButton = new JButton("다른 가게 둘러보기", updatedSearchIcon);
        browseButton.setPreferredSize(new Dimension(250, 80));
        browseButton.setFont(new Font("NPS font", Font.PLAIN, 20));
        browseButton.setHorizontalTextPosition(SwingConstants.RIGHT); // 텍스트 위치 설정
        browseButton.setVerticalTextPosition(SwingConstants.CENTER); // 텍스트 위치 설정
        browseButton.addActionListener(new FirstTimeMyPage.BrowseButtonListener()); // 액션 리스너 추가
        browseButton.setBorderPainted(false); // 버튼 테두리 숨기기
        browseButton.setContentAreaFilled(false); // 버튼 배경 숨기기
        addHoverEffect(browseButton); // 마우스 호버 효과 추가

        // browseButton 버튼에 ActionListener 추가 (식당 카테고리로 이동)
        browseButton.addActionListener(e -> {
            // FoodCategory 생성 및 보이기
            JFrame mainFrame = new JFrame("관리자 화면");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);
            FoodCategory foodCate = new FoodCategory();
            mainFrame.add(foodCate, BorderLayout.CENTER);
            mainFrame.setVisible(true);

            // 현재 패널이 포함된 프레임 닫기
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        // 식당 정보 등록 버튼
        JButton addButton = new JButton("식당 정보 등록하기");
        addButton.setPreferredSize(new Dimension(halfScreenWidth - 400, 200)); // 버튼 크기 변경
        addButton.setFont(new Font("NPS font", Font.PLAIN, 30));
        addButton.addActionListener(new AddButtonListener()); // 액션 리스너 추가
        addButton.setBorderPainted(true); // 버튼 테두리 숨기기
        addHoverEffect(addButton); // 마우스 호버 효과 추가

        // 식당 정보 등록 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // 위치 조정을 위해 레이아웃을 null로 설정
        buttonPanel.add(addButton);
        addButton.setBounds(200, screenHeight / 6, halfScreenWidth - 400, 150); // 버튼 위치와 크기 설정 (y 좌표 변경)
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER); // 버튼 패널을 프레임의 중앙에 추가

        // 이미지 패널 생성
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 20, 20)); // 1행 2열, 가로 간격 20, 세로 간격 20
        imagePanel.setBackground(Color.WHITE);

        // 이미지 추가
        ImageIcon image1 = new ImageIcon("image/character_line02_10.png");
        Image img1 = image1.getImage().getScaledInstance(200, 320, Image.SCALE_SMOOTH); // 이미지 크기 조절
        ImageIcon scaledImage1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledImage1);
        label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 이미지 여백 설정

        ImageIcon image2 = new ImageIcon("image/character_line02_04.png");
        Image img2 = image2.getImage().getScaledInstance(200, 320, Image.SCALE_SMOOTH); // 이미지 크기 조절
        ImageIcon scaledImage2 = new ImageIcon(img2);
        JLabel label2 = new JLabel(scaledImage2);
        label2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 이미지 여백 설정

        imagePanel.add(label1);
        imagePanel.add(label2);

        add(imagePanel, BorderLayout.SOUTH); // 이미지 패널을 프레임의 하단에 추가

        // 제목 라벨과 "전체" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(browseButton, BorderLayout.EAST);
        titlePanel.setBackground(Color.WHITE);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가
    }

    // 식당 정보 등록 버튼 액션 리스너
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 식당 정보 등록 액션 추가
        }
    }

    // 다른 가게 둘러보기 버튼 액션 리스너 -> 음식 카테고리창 열기
    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // FoodCategory 창을 모달리스로 열기
            SwingUtilities.invokeLater(() -> {
                JFrame foodCategoryFrame = new JFrame("Food Category");
                foodCategoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
                foodCategoryFrame.add(new FoodCategory());
                foodCategoryFrame.pack();
                foodCategoryFrame.setVisible(true); // FoodCategoryFrame 인스턴스 생성 및 표시
            });
        }
    }

    // 마우스 호버 효과 추가
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(darkOrangeColor); // 마우스가 버튼에 들어가면 글자색 변경
                button.setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK); // 마우스가 버튼에서 나가면 글자색 원래대로
                button.setBorderPainted(false);
            }
        });

        // 둥근 테두리 추가 (DarkOrange 색상 사용)
        Border roundedBorder = BorderFactory.createLineBorder(darkOrangeColor, 4, true);
        button.setBorder(roundedBorder);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("First Time My Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.add(new FirstTimeMyPage("포36거리"));
        frame.pack();
        frame.setVisible(true);
    }
}
