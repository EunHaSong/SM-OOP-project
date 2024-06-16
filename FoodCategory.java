import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FoodCategory extends JPanel {

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
        titleLabel.setFont(new Font("NPS font", Font.BOLD, 30)); // 폰트 변경
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // 왼쪽 마진 추가

        // "전체" 버튼 생성
        JButton etcButton = new JButton("전체");
        etcButton.setPreferredSize(new Dimension(100, 100));
        etcButton.setFont(new Font("NPS font", Font.BOLD, 20)); // 폰트 변경
        etcButton.addActionListener(new CategoryButtonListener());
        etcButton.setBorderPainted(false); // 버튼 테두리 숨기기
        etcButton.setContentAreaFilled(false); // 버튼 배경 숨기기
        addHoverEffect(etcButton); // 마우스 호버 효과 및 둥근 테두리 추가

        // 제목 라벨과 "전체" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(etcButton, BorderLayout.EAST);
        titlePanel.setBackground(Color.WHITE);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가

        // 버튼을 위한 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4, 5, 8)); // 2행 4열, 5px 가로 간격, 8px 세로 간격
        buttonPanel.setBackground(Color.WHITE);
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
                ImageIcon categoryIcon = new ImageIcon("C:\\yeons\\java\\final\\images\\" + (i + 1) + ".png");

                // ImageIcon에서 이미지 크기 조정
                Image img = categoryIcon.getImage(); // ImageIcon은 이미지 크기 조정이 안되어서 Image로 추출한 뒤 수정한다.
                Image updatedImg = img.getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                ImageIcon updatedIcon = new ImageIcon(updatedImg);

                // 버튼 생성
                JButton button = new JButton(category, updatedIcon);
                button.setPreferredSize(new Dimension(halfScreenWidth / 4, (screenHeight - 100) / 2));
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                // 폰트 변경 - "국민연금체" 폰트 사용
                button.setFont(new Font("NPS font", Font.BOLD, 20));
                button.addActionListener(new CategoryButtonListener());
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                addHoverEffect(button);

                // 버튼 클릭 시 이벤트 (식당 리스트로 이동)
                button.addActionListener(e -> {
                    // 카테고리 선택 시 해당 카테고리의 RestaurantList를 생성하여 화면에 표시
                    JPanel parentPanel = (JPanel) this.getParent();
                    parentPanel.removeAll();
                    parentPanel.add(new RestaurantList(category));
                    parentPanel.revalidate();
                    parentPanel.repaint();
                });

                buttonPanel.add(button);
            }else {
                buttonPanel.add(new JLabel("")); // 빈 셀 추가
            }
        }
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

        // 둥근 테두리 추가 (DarkOrange 색상 사용)
        Color darkOrangeColor = new Color(255, 140, 0); // RGB(255, 140, 0)
        Border roundedBorder = BorderFactory.createLineBorder(darkOrangeColor, 4, true);
        button.setBorder(roundedBorder);

    }

    public void setDefaultCloseOperation(int disposeOnClose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDefaultCloseOperation'");
    }
}
