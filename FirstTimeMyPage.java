import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FirstTimeMyPage extends JFrame {
    // 화면 크기 가져오기
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    // 화면 너비의 절반 계산
    int halfScreenWidth = screenSize.width / 2;
    int screenHeight = screenSize.height;

    // 다크 오렌지 색상 설정
    Color darkOrangeColor = new Color(255, 140, 0); // RGB(255, 140, 0)

    public FirstTimeMyPage(String username) {
        // 프레임 위치를 화면 왼쪽으로 설정
        setLocation(0, 0);

        // 프레임 크기 설정
        setSize(halfScreenWidth, screenHeight);

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

        // 식당 정보 등록 버튼
        JButton addButton = new JButton("식당 정보 등록하기");
        addButton.setPreferredSize(new Dimension(halfScreenWidth-400,200)); // 버튼 크기 변경
        addButton.setFont(new Font("NPS font", Font.PLAIN, 20));
        addButton.addActionListener(new AddButtonListener()); // 액션 리스너 추가
        addButton.setBorderPainted(false); // 버튼 테두리 숨기기
        addHoverEffect(addButton); // 마우스 호버 효과 추가

        // 식당 정보 등록 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // 위치 조정을 위해 레이아웃을 null로 설정
        buttonPanel.add(addButton);
        addButton.setBounds(200, screenHeight / 4, halfScreenWidth - 400, 200); // 버튼 위치와 크기 설정
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel); // 버튼을 패널에 추가

        // 제목 라벨과 "전체" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(browseButton, BorderLayout.EAST);
        titlePanel.setBackground(Color.WHITE);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가

        setVisible(true); // 프레임 표시
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 닫기 설정

        // 포커스를 다른 컴포넌트로 옮겨 기본 포커스를 제거
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                getRootPane().requestFocusInWindow();
            }
        });
    }

    // 식당 정보 등록 버튼 액션 리스너
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    // 다른 가게 둘러보기 버튼 액션 리스너 -> 음식 카테고리창 열기
    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // FoodCategory 창을 모달리스로 열기
            SwingUtilities.invokeLater(() -> {
                FoodCategory foodCategoryFrame = new FoodCategory();
                foodCategoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
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
        FirstTimeMyPage myPage = new FirstTimeMyPage("포36거리");
    }
}
