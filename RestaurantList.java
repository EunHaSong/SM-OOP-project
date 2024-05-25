import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RestaurantList extends JFrame {
    // 화면 크기 가져오기
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    // 화면 너비의 절반 계산
    int halfScreenWidth = screenSize.width / 2;
    int screenHeight = screenSize.height;

    private static final String IMAGE_FOLDER_PATH = "restaurant_images/"; // 이미지 폴더 경로
    private static final int IMAGE_WIDTH = 200; // 이미지 너비
    private static final int IMAGE_HEIGHT = 150; // 이미지 높이


    public RestaurantList(String category) {
        // 프레임 크기를 화면 너비의 절반과 전체 화면 높이로 설정
        setSize(halfScreenWidth, screenHeight);

        // 프레임 위치를 화면 왼쪽으로 설정
        setLocation(0, 0);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 제목 라벨 생성
        JLabel label = new JLabel(category);
        label.setPreferredSize(new Dimension(halfScreenWidth, 60)); // 라벨의 크기 설정
        label.setFont(new Font("Helvetica", Font.BOLD, 30));
        label.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // 제목의 왼쪽에 여백을 준다

        // "정렬" 버튼 생성
        JButton etcButton = new JButton("정렬");
        etcButton.setPreferredSize(new Dimension(60, 30));
        etcButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        etcButton.addActionListener(new SortButtonListener());
        etcButton.setBorderPainted(false); // 버튼 테두리 숨기기
        etcButton.setContentAreaFilled(false); // 버튼 배경 숨기기
        addHoverEffect(etcButton); // 마우스 호버 효과 및 둥근 테두리 추가

        // 제목 라벨과 "정렬" 버튼을 포함할 패널 생성
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(label, BorderLayout.CENTER);
        titlePanel.add(etcButton, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH); // 제목 패널을 프레임의 북쪽에 추가

        // 식당 정보를 출력할 패널 생성
        JPanel restaurantPanel = new JPanel();
        restaurantPanel.setLayout(new BoxLayout(restaurantPanel, BoxLayout.Y_AXIS));

        // 텍스트 파일에서 식당 정보 읽기
        List<Restaurant> restaurants = readRestaurantData("DataSortedByRating.txt");

        // 식당별 버튼과 사진 생성
        for (Restaurant restaurant : restaurants) {
            String starRating = getStarRating(restaurant.rating);
            String buttonText = String.format("<html><b style='font-size:16px;'>%s</b><br>" +
                            "%s (%.1f)&nbsp;&nbsp;&nbsp;리뷰수: %d<br>" +
                            "주메뉴: %s<br>" +
                            "주소: %s<br>" +
                            "영업시간: %s<br>" +
                            "브레이크타임: %s&nbsp;&nbsp;&nbsp;&nbsp;휴무일: %s</html>",
                    restaurant.name, starRating, restaurant.rating, restaurant.reviewCount,
                    restaurant.mainDish, restaurant.address, restaurant.hours,
                    restaurant.breakTime, restaurant.holidays);

            JButton button = new JButton();
            button.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // 텍스트 라벨 추가
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;

            JLabel textLabel = new JLabel(buttonText);
            textLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            button.add(textLabel, gbc);

            // 식당 이미지 추가
            String imageFilePath = IMAGE_FOLDER_PATH + restaurant.name + ".jpeg";
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFilePath).getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.NORTHEAST;
            gbc.insets = new Insets(2, 2, 2, 0); // 여백 추가
            button.add(imageLabel, gbc);

            button.setPreferredSize(new Dimension(halfScreenWidth, (screenHeight - 70) / 4)); // 버튼 크기 조정
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            addHoverEffect(button); // 망스 호버 효과 및 둥근 테두리 추가

            restaurantPanel.add(button); // 패널에 버튼 추가
        }

        // 스크롤 기능
        JScrollPane scrollPane = new JScrollPane(restaurantPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private List<Restaurant> readRestaurantData(String filePath) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (int i = 0; i < lines.size(); i += 8) {
                String name = lines.get(i).trim();
                double rating = Double.parseDouble(lines.get(i + 1).trim());
                int reviewCount = Integer.parseInt(lines.get(i + 2).trim());
                String mainDish = lines.get(i + 3).trim();
                String address = lines.get(i + 4).trim();
                String hours = lines.get(i + 5).trim();
                String breakTime = lines.get(i + 6).trim().isEmpty() ? "없음" : lines.get(i + 6).trim();
                String holidays = lines.get(i + 7).trim().isEmpty() ? "없음" : lines.get(i + 7).trim();

                restaurants.add(new Restaurant(name, rating, reviewCount, mainDish, address, hours, breakTime, holidays));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 포커스를 다른 컴포넌트로 옮겨 기본 포커스를 제거
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                getRootPane().requestFocusInWindow();
            }
        });

        return restaurants;

    }


    // 평점을 주황색 별표로 변환하는 메서드
    private String getStarRating(double rating) {
        int fullStars = (int) rating;
        boolean halfStar = (rating - fullStars) >= 0.5;
        StringBuilder starRating = new StringBuilder();

        // 채워진 별 추가
        for (int i = 0; i < fullStars; i++) {
            starRating.append("<span style='color:orange;'>★</span>"); // HTML 사용하여 스타일 지정
        }

        // 소수점 단위 -> 반 별 추가 (있는 경우)
        if (halfStar) {
            starRating.append("<span style='color:orange;'>☆</span>");
            fullStars++; // 반 별을 추가했으므로 전체 별 개수 증가
        }

        // 빈 별 추가
        for (int i = fullStars; i < 5; i++) {
            starRating.append("<span style='color:orange; border: 1px solid orange;'>☆</span>");
        }

        return starRating.toString();
    }


    // 식당 정보를 저장할 클래스
    static class Restaurant {
        String name;
        double rating;
        int reviewCount;
        String mainDish;
        String address;
        String hours;
        String breakTime;
        String holidays;

        public Restaurant(String name, double rating, int reviewCount, String mainDish, String address, String hours, String breakTime, String holidays) {
            this.name = name;
            this.rating = rating;
            this.reviewCount = reviewCount;
            this.mainDish = mainDish;
            this.address = address;
            this.hours = hours;
            this.breakTime = breakTime;
            this.holidays = holidays;
        }
    }

    // 정렬 버튼의 액션 리스너
    private class SortButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String category = source.getText();
            new DataSort(); // 정렬창 열기
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
