import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodCategory extends JFrame {
    public FoodCategory() {
        // 화면 크기 가져오기
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // 화면 너비의 절반 계산
        int halfScreenWidth = screenSize.width / 2;
        int screenHeight = screenSize.height;

        // 프레임 크기를 화면 너비의 절반과 전체 화면 높이로 설정
        setSize(halfScreenWidth, screenHeight);

        // 프레임 위치를 화면 왼쪽으로 설정
        setLocation(0, 0);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 제목 라벨 생성
        JLabel titleLabel = new JLabel("음식 종류별 카테고리");
        titleLabel.setPreferredSize(new Dimension(halfScreenWidth, 100)); // 라벨의 크기 설정
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // 왼쪽 마진 추가

        // "기타" 버튼 생성
        JButton etcButton = new JButton("기타");
        etcButton.setPreferredSize(new Dimension(100, 100));
        etcButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        etcButton.addActionListener(new CategoryButtonListener());
        etcButton.setBorderPainted(false); // 버튼 테두리 숨기기
        etcButton.setContentAreaFilled(false); // 버튼 배경 숨기기
        addHoverEffect(etcButton); // 마우스 호버 효과 및 둥근 테두리 추가

        // 제목 라벨과 "기타" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(etcButton, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가

        // 버튼을 위한 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4, 5, 8)); // 2행 4열, 5px 가로 간격, 8px 세로 간격
        add(buttonPanel, BorderLayout.CENTER); // 버튼 패널을 프레임의 중앙에 추가

        // 각 카테고리 버튼 생성
        String[] categories = {
                "한식", "일식", "중식", "아시안",
                "양식", "샐러드&요거트", "카페&디저트", "술집"
        };

        // 각 카테고리 버튼에 아이콘 생성
        for (int i = 0; i < categories.length; i++) {
            String category = categories[i];
            if (!category.isEmpty()) {
                ImageIcon categoryIcon = new ImageIcon("image/" + (i + 1) + ".png");

                // ImageIcon에서 이미지 크기 조정
                Image img = categoryIcon.getImage(); // ImageIcon은 이미지 크기 조정이 안되어서 Image로 추출한 뒤 수정한다.
                Image updatedImg = img.getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                ImageIcon updatedIcon = new ImageIcon(updatedImg);

                // 버튼 생성
                JButton button = new JButton(category, updatedIcon);
                button.setPreferredSize(new Dimension(halfScreenWidth / 4, (screenHeight - 100) / 2)); // 버튼 크기 설정
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setFont(new Font("Helvetica", Font.BOLD, 16)); // 폰트 설정
                button.addActionListener(new CategoryButtonListener());
                button.setBorderPainted(false);   // 버튼 테두리 숨기기
                button.setContentAreaFilled(false); // 버튼 배경 숨기기
                addHoverEffect(button); // 마우스 호버 효과 및 둥근 테두리 추가
                buttonPanel.add(button);
            } else {
                buttonPanel.add(new JLabel("")); // 빈 셀 추가
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 닫기 설정

        // 포커스를 다른 컴포넌트로 옮겨 기본 포커스를 제거
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                getRootPane().requestFocusInWindow();
            }
        });
    }

    // 카테고리 버튼의 액션 리스너
    private class CategoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String category = source.getText();
            new RestaurantList(category); // 다음 프레임 열기
        }
    }

    // 버튼에 마우스 호버 효과 및 둥근 테두리 추가
    private void addHoverEffect(JButton button) {
        // 마우스 호버 효과 추가
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorderPainted(true); // 마우스가 버튼에 들어가면 테두리 그리기
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorderPainted(false); // 마우스가 버튼에서 나가면 테두리 숨기기
            }
        });

        // 둥근 테두리 추가
        Border roundedBorder = BorderFactory.createLineBorder(Color.CYAN, 4, true);
        button.setBorder(roundedBorder);
    }
}
