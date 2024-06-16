import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewStatic extends JFrame {
    public ReviewStatic() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        List<ReviewData> data = readCSV("리뷰평점.csv");
        setLayout(new BorderLayout());

        JPanel chartPanel = new JPanel(new GridLayout(1, 2));

        ReviewRatingPanel barChartPanel = new ReviewRatingPanel(data);
        ReviewCountPanel pieChartPanel = new ReviewCountPanel(data);

        chartPanel.add(barChartPanel);
        chartPanel.add(pieChartPanel);

        add(chartPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static List<ReviewData> readCSV(String filePath) {
        List<ReviewData> data = new ArrayList<>();
        Map<String, Color> colorMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String restaurant = values[0].trim();
                    String menu = values[1].trim();
                    double rating = Double.parseDouble(values[2].trim());
                    int reviewCount = Integer.parseInt(values[3].trim());

                    if (reviewCount > 0) {
                        Color color;
                        if (!colorMap.containsKey(menu)) {
                            color = generateUniqueColor(colorMap.values());
                            colorMap.put(menu, color);
                        } else {
                            color = colorMap.get(menu);
                        }
                        data.add(new ReviewData(restaurant, menu, rating, reviewCount, color));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static Color generateUniqueColor(Collection<Color> usedColors) {
        Random random = new Random();
        Color color;
        do {
            color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } while (isBlackOrWhite(color) || isSimilar(color, usedColors));
        return color;
    }

    private static boolean isSimilar(Color color, Collection<Color> colors) {
        for (Color c : colors) {
            if (colorDistance(color, c) < 100) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlackOrWhite(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return (r > 180 && g > 180 && b > 180) || (r < 60 && g < 60 && b < 60);
    }

    private static double colorDistance(Color c1, Color c2) {
        return Math.sqrt(Math.pow(c1.getRed() - c2.getRed(), 2) +
                Math.pow(c1.getGreen() - c2.getGreen(), 2) +
                Math.pow(c1.getBlue() - c2.getBlue(), 2));
    }


    public static void main(String[] args) {
        new ReviewStatic();
    }

    static class ReviewData {
        String restaurant;
        String menu;
        double rating;
        int reviewCount;
        Color color;

        ReviewData(String restaurant, String menu, double rating, int reviewCount, Color color) {
            this.restaurant = restaurant;
            this.menu = menu;
            this.rating = rating;
            this.reviewCount = reviewCount;
            this.color = color;
        }
    }

    static class ReviewRatingPanel extends JPanel {
        List<ReviewData> sortedData;

        ReviewRatingPanel(List<ReviewData> data) {
            this.sortedData = data.stream()
                    .sorted(Comparator.comparingDouble(d -> -d.rating))
                    .collect(Collectors.toList());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / sortedData.size() / 2;
            int maxBarHeight = height - 100;

            Font font = new Font("NPS font", Font.PLAIN, 25);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("리뷰 평점 통계", width / 2 - g.getFontMetrics().stringWidth("리뷰 평점 통계") / 2, 30);

            font = new Font("NPS font", Font.PLAIN, 13);
            g.setFont(font);

            for (int i = 0; i < sortedData.size(); i++) {
                ReviewData d = sortedData.get(i);
                double rating = d.rating;
                int barHeight = (int) ((rating / 5) * maxBarHeight);
                int x = (i * barWidth * 2) + (barWidth / 2);
                int y = height - barHeight - 50;
                g.setColor(d.color);
                g.fillRect(x, y, barWidth, barHeight);
                g.setColor(Color.BLACK);
                g.drawString(d.menu, x + (barWidth / 2) - g.getFontMetrics().stringWidth(d.menu) / 2, height - 20);
                g.drawString(String.format("%.1f", rating), x + (barWidth / 2) - g.getFontMetrics().stringWidth(String.format("%.1f", rating)) / 2, y - 10);
            }
        }
    }

    static class ReviewCountPanel extends JPanel {
        List<ReviewData> data;

        ReviewCountPanel(List<ReviewData> data) {
            this.data = data.stream()
                    .sorted(Comparator.comparingInt(d -> -d.reviewCount)) // Sort by review count in descending order
                    .collect(Collectors.toList());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int pieSize = Math.min(width, height - 150) - 150;  // Reduced pie size for more space
            int cx = width / 2 - pieSize / 2;
            int cy = height / 2 - pieSize / 2 - 75;  // Adjust vertical position
            int total = data.stream().mapToInt(d -> d.reviewCount).sum();

            Font font = new Font("NPS font", Font.PLAIN, 25);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("리뷰 개수 통계", width / 2 - g.getFontMetrics().stringWidth("리뷰 개수 통계") / 2, 30);

            double startAngle = 0.0;
            for (ReviewData d : data) {
                double percentage = (double) d.reviewCount / total;
                if (percentage > 0) {
                    double arcAngle = 360.0 * percentage;
                    g.setColor(d.color);
                    g.fillArc(cx, cy, pieSize, pieSize, (int) startAngle, (int) arcAngle);
                    startAngle += arcAngle;  // Continue from the last angle
                }
            }

            // Draw the legend in two columns if necessary
            int legendX1 = 50;
            int legendX2 = width / 2 + 50;  // Second column starts at the middle of the frame
            int legendY = height - 150;
            int columnSwitch = (data.size() + 1) / 2;  // Midpoint for switching columns
            int itemCount = 0;

            font = new Font("NPS font", Font.PLAIN, 15);
            g.setFont(font);
            for (ReviewData d : data) {
                double percentage = (double) d.reviewCount / total;
                if (percentage > 0) {
                    int legendX = itemCount < columnSwitch ? legendX1 : legendX2;
                    g.setColor(d.color);
                    g.fillRect(legendX, legendY, 20, 20);
                    g.setColor(Color.BLACK);
                    g.drawRect(legendX, legendY, 20, 20);
                    String legendText = String.format("%s: %.1f%%", d.menu, percentage * 100);
                    g.drawString(legendText, legendX + 30, legendY + 15);
                    legendY += 30;
                    if (itemCount == columnSwitch - 1) {  // Reset Y for second column
                        legendY = height - 150;
                    }
                    itemCount++;
                }
            }
        }
    }

}