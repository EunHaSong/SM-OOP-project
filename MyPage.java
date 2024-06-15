import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPage extends JFrame {
    // 화면 크기 가져오기
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    // 화면 너비의 절반 계산
    int halfScreenWidth = screenSize.width / 2;
    int screenHeight = screenSize.height;

    // 다크 오렌지 색상 설정
    Color darkOrangeColor = new Color(255, 140, 0); // RGB(255, 140, 0)

    public MyPage(String username) {
        // 프레임 위치를 화면 왼쪽으로 설정
        setLocation(0, 0);

        // 프레임 크기 설정
        setSize(halfScreenWidth, screenHeight);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 제목 라벨 생성
        JLabel titleLabel = new JLabel("<html>마이페이지<br>" + username + "님</html>");
        titleLabel.setPreferredSize(new Dimension(halfScreenWidth, 100)); // 라벨의 크기 설정
        titleLabel.setFont(new Font("NPS font", Font.BOLD, 30)); // 폰트 변경
        titleLabel.setForeground(darkOrangeColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // 왼쪽 여백 추가

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
        browseButton.addActionListener(new BrowseButtonListener()); // 액션 리스너 추가
        browseButton.setBorderPainted(false); // 버튼 테두리 숨기기
        browseButton.setContentAreaFilled(false); // 버튼 배경 숨기기
        addHoverEffect(browseButton); // 마우스 호버 효과 추가


        // 제목 라벨과 "전체" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(browseButton, BorderLayout.EAST);
        titlePanel.setBackground(Color.WHITE);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가

        // 버튼을 위한 패널 생성
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER); // 버튼 패널을 프레임의 중앙에 추가

        // 각 카테고리 버튼 생성
        String[] buttons = {"내 가게 정보 보기", "메뉴 추가하기", "내 가게 통계정보 보기"};

        // 각 카테고리 버튼 생성 및 추가
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(80, 20, 80, 20); // 각 버튼의 상하좌우 여백 설정
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < buttons.length; i++) {
            gbc.gridx = i;
            JButton button = createButton(buttons[i], i + 1);
            buttonPanel.add(button, gbc);
        }

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

    // 버튼 생성 메서드
    public JButton createButton(String buttonName, int buttonIndex) {
        String imagePath = "image/button" + buttonIndex + ".png";
        ImageIcon buttonIcon = new ImageIcon(imagePath);

        Image img = buttonIcon.getImage(); // ImageIcon에서 이미지 크기 조정
        Image updatedImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon updatedIcon = new ImageIcon(updatedImg);

        // 버튼 생성
        JButton button = new JButton(buttonName, updatedIcon);
        button.setPreferredSize(new Dimension(halfScreenWidth / 4, screenHeight / 3)); // 높이 조정
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("NPS font", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        addHoverEffect(button);

        // 버튼 클릭 이벤트 처리 (새 창 열기)
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 새 창 열기 코드 작성
                switch (buttonName) {
                    case "내 가게 정보 보기":
                        new MyStoreInfo();
                        break;
                    case "메뉴 추가하기":
                        new CreateAdminPanel();
                        break;
                    case "내 가게 통계정보 보기":
                        new ViewStatistics();
                        break;
                }
            }
        });

        return button;
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
        MyPage mp = new MyPage("포돈"); // 가게 이름을 넣어서 ___님이 제목 라벨에 출력되게 함
    }
}
