import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewStatic {
    public static void main(String[] args) {
        List<ReviewData> data = readCSV("리뷰평점.csv");

        JFrame frame = new JFrame("리뷰관련 통계");
        frame.setSize(1600, 600); // Set a wider width
        frame.setLayout(new GridLayout(1, 2));

        ReviewRatingPanel barChartPanel = new ReviewRatingPanel(data);
        ReviewCountPanel pieChartPanel = new ReviewCountPanel(data);

        frame.add(barChartPanel);
        frame.add(pieChartPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static List<ReviewData> readCSV(String filePath) {
        List<ReviewData> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) { // Ensure all fields are present
                    String restaurant = values[0].trim();
                    String menu = values[1].trim();
                    double rating = Double.parseDouble(values[2].trim());
                    int reviewCount = Integer.parseInt(values[3].trim());
                    data.add(new ReviewData(restaurant, menu, rating, reviewCount));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    static class ReviewData {
        String restaurant;
        String menu;
        double rating;
        int reviewCount; // Adding review count field

        ReviewData(String restaurant, String menu, double rating, int reviewCount) {
            this.restaurant = restaurant;
            this.menu = menu;
            this.rating = rating;
            this.reviewCount = reviewCount;
        }
    }

    static class ReviewRatingPanel extends JPanel {
        Map<String, Double> reviewRatings;

        ReviewRatingPanel(List<ReviewData> data) {
            this.reviewRatings = data.stream()
                    .collect(Collectors.groupingBy(d -> d.menu, Collectors.averagingDouble(d -> d.rating)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            String[] menus = {"레몬티", "아메리카노", "자몽에이드", "레몬에이드", "초콜릿라떼", "딸기우유", "카모마일티", "말차라떼", "자스민티"};
            double[] ratings = new double[menus.length];

            for (int i = 0; i < menus.length; i++) {
                ratings[i] = reviewRatings.getOrDefault(menus[i], 0.0);
            }

            int width = getWidth();
            int height = getHeight();
            int barWidth = width / menus.length;
            int maxBarHeight = height - 30;
            double maxValue = Arrays.stream(ratings).max().orElse(1.0);

            // Set font and title
            Font font = new Font("NPS font", Font.PLAIN, 20);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("리뷰평점 통계", 20, 30); // Title above the bar chart

            // Set font for chart labels
            font = new Font("NPS font", Font.PLAIN, 15);
            g.setFont(font);

            g.setColor(Color.ORANGE);
            for (int i = 0; i < menus.length; i++) {
                int barHeight = (int) ((ratings[i] / maxValue) * maxBarHeight);
                int x = i * barWidth;
                int y = height - barHeight - 20;
                g.fillRect(x, y, barWidth - 10, barHeight);
                g.drawString(menus[i], x + (barWidth / 2) - 5, height - 5);
            }
        }
    }

    static class ReviewCountPanel extends JPanel {
        Map<String, Integer> reviewCounts;
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.LIGHT_GRAY};

        ReviewCountPanel(List<ReviewData> data) {
            this.reviewCounts = data.stream()
                    .collect(Collectors.groupingBy(d -> d.menu, Collectors.summingInt(d -> d.reviewCount)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            String[] menus = {"레몬티", "아메리카노", "자몽에이드", "레몬에이드", "초콜릿라떼", "딸기우유", "카모마일티", "말차라떼", "자스민티"};
            int[] counts = new int[menus.length];

            for (int i = 0; i < menus.length; i++) {
                counts[i] = reviewCounts.getOrDefault(menus[i], 0);
            }

            int width = getWidth();
            int height = getHeight();
            int pieSize = Math.min(width, height) - 50;
            int cx = width / 2 - pieSize / 2;
            int cy = height / 2 - pieSize / 2;
            int radius = pieSize / 2;
            int total = Arrays.stream(counts).sum();

            // Set font and title
            Font font = new Font("NPS font", Font.PLAIN, 20);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("리뷰개수 통계", 20, 30); // Title above the pie chart

            // Set font for chart labels
            font = new Font("NPS font", Font.PLAIN, 15);
            g.setFont(font);

            double startAngle = 0.0;

            for (int i = 0; i < menus.length; i++) {
                if (counts[i] > 0) {
                    double arcAngle = 360.0 * counts[i] / total;
                    g.setColor(colors[i % colors.length]);
                    g.fillArc(cx, cy, pieSize, pieSize, (int) startAngle, (int) arcAngle);
                    g.drawString(menus[i], cx + pieSize + 20, cy + i * 20);
                    startAngle += arcAngle;
                }
            }
        }
    }
}
