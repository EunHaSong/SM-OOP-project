import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ReviewBoard2 extends JPanel {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JScrollPane contentScrollPane;
    private JPanel contentPanel;
    private ArrayList<Review> reviews;

    JPanel sortComboPanel;
    JPanel menuComboPanel;
    JComboBox<String> menuComboBox;
    JPanel featureComboPanel;
    JComboBox<String> featureComboBox;

    public ReviewBoard2() {
        setLayout(new BorderLayout());

        reviews = loadReviews(); // 리뷰 데이터 로드

        // 버튼 패널
        buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton viewAllButton = new JButton("전체보기");
        JButton viewByMenuButton = new JButton("메뉴별");
        JButton viewByFeatureButton = new JButton("특징별");
        Font font = new Font("국민연금체", Font.PLAIN, 30);

        viewAllButton.setBorder(BorderFactory.createEtchedBorder());
        viewByMenuButton.setBorder(BorderFactory.createEtchedBorder());
        viewByFeatureButton.setBorder(BorderFactory.createEtchedBorder());

        viewAllButton.setFont(font);
        viewByMenuButton.setFont(font);
        viewByFeatureButton.setFont(font);

        viewAllButton.setForeground(Color.WHITE);
        viewByMenuButton.setForeground(Color.WHITE);
        viewByFeatureButton.setForeground(Color.WHITE);

        viewAllButton.setBackground(new Color(255, 140, 0));
        viewByMenuButton.setBackground(new Color(255, 140, 0));
        viewByFeatureButton.setBackground(new Color(255, 140, 0));

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllReviews();
            }
        });

        viewByMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuPanel();
            }
        });

        viewByFeatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFeaturePanel();
            }
        });

        buttonPanel.add(viewAllButton);
        buttonPanel.add(viewByMenuButton);
        buttonPanel.add(viewByFeatureButton);

        add(buttonPanel, BorderLayout.NORTH);

        // 내용을 표시할 패널
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // 스크롤 패널 생성
        contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(contentScrollPane, BorderLayout.CENTER);

        // 정렬 콤보박스 생성
        JComboBox<String> sortComboBox = createComboBox(new String[]{"최신순", "오래된순", "별점 높은 순", "별점 낮은 순"});
        sortComboPanel = new JPanel(new GridLayout(1, 1));
        sortComboPanel.add(createPanelWithLabel(" 정렬 선택 ", sortComboBox));

        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortReviews(sortComboBox.getSelectedItem().toString());
            }
        });

        // menu 콤보박스
        menuComboBox = createComboBox(new String[]{"짜장면", "짬뽕", "볶음밥"});
        menuComboPanel = new JPanel(new GridLayout(1, 1)); // 한 줄에 메뉴 선택만 표시
        menuComboPanel.add(createPanelWithLabel(" 메뉴 선택 ", menuComboBox));

        // 메뉴 선택 리스너 추가
        menuComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByMenu(menuComboBox.getSelectedItem().toString());
            }
        });

        // feature 패널 추가
        featureComboBox = createComboBox(new String[]{"위생", "맛", "서비스"});
        featureComboPanel = new JPanel(new GridLayout(1, 1)); // 한 줄에 특징 선택만 표시
        featureComboPanel.add(createPanelWithLabel(" 특징 선택 ", featureComboBox));

        // feature 선택 리스너 추가
        featureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByFeature(featureComboBox.getSelectedItem().toString());
            }
        });

        setVisible(true);
    }

    private ArrayList<Review> loadReviews() {
        ArrayList<Review> loadedReviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\yeons\\java\\final\\src\\reviews.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    System.out.println("데이터 포맷 오류: " + line); // 데이터 포맷 오류 출력
                    continue; // 데이터 항목이 충분하지 않은 경우 이 줄을 건너뜀
                }
                Review review = new Review(data[0], data[1], data[2], data[3], data[4]);
                loadedReviews.add(review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedReviews;
    }

    private void showAllReviews() {
        contentPanel.removeAll();
        contentPanel.add(sortComboPanel, BorderLayout.NORTH);

        JPanel reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));
        reviewListPanel.setBackground(Color.WHITE);
        for (Review review : reviews) {
            JPanel reviewItemPanel = createUserReviewPanel(review);
            reviewListPanel.add(reviewItemPanel);
        }
        contentPanel.add(reviewListPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void sortReviews(String sortType) {
        switch (sortType) {
            case "최신순":
                reviews.sort(Comparator.comparing(Review::getDate).reversed());
                break;
            case "오래된순":
                reviews.sort(Comparator.comparing(Review::getDate));
                break;
            case "별점 높은 순":
                reviews.sort(Comparator.comparing(Review::getRating).reversed());
                break;
            case "별점 낮은 순":
                reviews.sort(Comparator.comparing(Review::getRating));
                break;
        }
        contentPanel.removeAll();
        contentPanel.add(sortComboPanel, BorderLayout.NORTH);

        JPanel reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));
        for (Review review : reviews) {
            JPanel reviewItemPanel = createUserReviewPanel(review);
            reviewListPanel.add(reviewItemPanel);
        }
        contentPanel.add(reviewListPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMenuPanel() {
        contentPanel.removeAll();
        filterByMenu(menuComboBox.getSelectedItem().toString()); // 초기 필터 적용
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showFeaturePanel() {
        contentPanel.removeAll();
        filterByFeature(featureComboBox.getSelectedItem().toString()); // 초기 필터 적용
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void filterByMenu(String menu) {
        ArrayList<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getMenu().contains(menu)) {
                filteredReviews.add(review);
            }
        }
        contentPanel.removeAll();
        contentPanel.add(menuComboPanel, BorderLayout.NORTH);
        JPanel reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));
        for (Review review : filteredReviews) {
            JPanel reviewItemPanel = createUserReviewPanel(review);
            reviewListPanel.add(reviewItemPanel);
        }
        contentPanel.add(reviewListPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void filterByFeature(String feature) {
        ArrayList<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getFeature().contains(feature)) {
                filteredReviews.add(review);
            }
        }
        contentPanel.removeAll();
        contentPanel.add(featureComboPanel, BorderLayout.NORTH);
        JPanel featureListPanel = new JPanel();
        featureListPanel.setLayout(new BoxLayout(featureListPanel, BoxLayout.Y_AXIS));
        for (Review review : filteredReviews) {
            JPanel reviewItemPanel = createUserReviewPanel(review);
            featureListPanel.add(reviewItemPanel);
        }
        contentPanel.add(featureListPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(Color.WHITE);
        Font font = new Font("국민연금체", Font.PLAIN, 20);
        comboBox.setFont(font);

        // 테두리 설정: 오렌지 색, 2픽셀 두께
        //Border border = BorderFactory.createLineBorder(new Color(255, 140, 0), 2);
        //comboBox.setBorder(border);

        return comboBox;
    }


    private JPanel createPanelWithLabel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        Font font = new Font("국민연금체", Font.PLAIN, 20);
        label.setFont(font);
        label.setForeground(Color.GRAY);
        panel.setBackground(Color.WHITE);
        panel.add(label, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUserReviewPanel(Review review) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        Font font = new Font("국민연금체", Font.PLAIN, 20);
        panel.setLayout(new GridLayout(5, 1));  // 5 rows, 1 column

        // Set a uniform border
        panel.setBorder(BorderFactory.createEtchedBorder());

        JLabel label1 = new JLabel("작성일: " + review.getDate());
        JLabel label2 = new JLabel("별점: " + review.getRating());
        JLabel label3 = new JLabel("특징: " + review.getFeature());
        JLabel label4 = new JLabel("메뉴: " + review.getMenu());
        JLabel label5 = new JLabel("내용: " + review.getContent());

        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        label4.setFont(font);
        label5.setFont(font);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);

        return panel;
    }


    /*public static void main(String[] args) {
        new ReviewBoard();
    } */

    // 내부 클래스: 리뷰 데이터 모델
    class Review {
        private String date;
        private String rating;
        private String feature;
        private String menu;
        private String content;

        public Review(String date, String rating, String feature, String menu, String content) {
            this.date = date;
            this.rating = rating;
            this.feature = feature;
            this.menu = menu;
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public String getRating() {
            return rating;
        }

        public String getFeature() {
            return feature;
        }

        public String getMenu() {
            return menu;
        }

        public String getContent() {
            return content;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
