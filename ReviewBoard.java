import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ReviewBoard extends JFrame {
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

    public ReviewBoard() {
        setTitle("리뷰 보기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);

        reviews = loadReviews(); // 리뷰 데이터 로드

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // 버튼 패널
        buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton viewAllButton = new JButton("전체보기");
        JButton viewByMenuButton = new JButton("메뉴별");
        JButton viewByFeatureButton = new JButton("특징별");
        Font font = new Font("나눔고딕", Font.BOLD, 30);

        viewAllButton.setBorder(BorderFactory.createEtchedBorder());
        viewByMenuButton.setBorder(BorderFactory.createEtchedBorder());
        viewByFeatureButton.setBorder(BorderFactory.createEtchedBorder());

        viewAllButton.setFont(font);
        viewByMenuButton.setFont(font);
        viewByFeatureButton.setFont(font);

        viewAllButton.setForeground(Color.WHITE);
        viewByMenuButton.setForeground(Color.WHITE);
        viewByFeatureButton.setForeground(Color.WHITE);

        viewAllButton.setBackground(Color.GRAY);
        viewByMenuButton.setBackground(Color.GRAY);
        viewByFeatureButton.setBackground(Color.GRAY);


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

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // 내용을 표시할 패널
        contentPanel = new JPanel();
//        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setLayout(new BorderLayout());

        // 스크롤 패널 생성
        contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);

        // 정렬 콤보박스 생성
        JComboBox<String> sortComboBox = createComboBox(new String[]{"최신순", "오래된순", "별점 높은 순", "별점 낮은 순"});
        sortComboPanel = new JPanel(new GridLayout(2, 1));
        sortComboPanel.add(createPanelWithLabel(" 정렬 선택 ", sortComboBox));


        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortReviews(sortComboBox.getSelectedItem().toString());
            }
        });

        // 메뉴 콤보박스

        menuComboBox = createComboBox(new String[]{"짜장면", "짬뽕", "볶음밥"});
        menuComboPanel = new JPanel(new GridLayout(2, 1)); // 한 줄에 메뉴 선택만 표시
        menuComboPanel.add(createPanelWithLabel(" 메뉴 선택 ", menuComboBox));

        // 메뉴 선택 리스너 추가
        menuComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByMenu(menuComboBox.getSelectedItem().toString());
            }
        });

        // 특징 콤보박스
        featureComboBox = createComboBox(new String[]{"위생", "맛", "서비스"});
        featureComboPanel = new JPanel(new GridLayout(2, 1)); // 한 줄에 특징 선택만 표시
        featureComboPanel.add(createPanelWithLabel(" 특징 선택 ", featureComboBox));

        // 특징 선택 리스너 추가
        featureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByFeature(featureComboBox.getSelectedItem().toString());
            }
        });

        setVisible(true);
    }

    // 텍스트 파일에서 리뷰 데이터 가져오기
    private ArrayList<Review> loadReviews() {
        ArrayList<Review> loadedReviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("reviews.txt"))) {
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

    // 리뷰 전체보기 페이지
    private void showAllReviews() {
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

    // 최신순, 오래된순, 별점 높은 순, 별점 낮은 순으로 리뷰 정렬
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
        filterByMenu(menuComboBox.getSelectedItem().toString()); 
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showFeaturePanel() {
        contentPanel.removeAll();
        filterByFeature(featureComboBox.getSelectedItem().toString()); 
        contentPanel.revalidate();
        contentPanel.repaint();
    }
  
    // 메뉴별 정렬
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

    // 특징별 정렬
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
        Font font = new Font("나눔고딕", Font.PLAIN, 20);
        comboBox.setFont(font);
        return comboBox;
    }

    private JPanel createPanelWithLabel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        Font font = new Font("나눔고딕", Font.BOLD, 20);
        label.setFont(font);
        label.setForeground(Color.GRAY);
        panel.add(label, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }
  
    // 사용자 리뷰 패널
    private JPanel createUserReviewPanel(Review review) {
        JPanel panel = new JPanel();
        Font font = new Font("나눔고딕", Font.PLAIN, 20);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.setBackground(Color.WHITE);

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

  
    public static void main(String[] args) {
        new ReviewBoard();
    }

    // 내부 클래스: 리뷰 데이터
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
}