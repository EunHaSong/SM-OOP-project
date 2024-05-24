// 관리자용 - 정보 등록화면 (메뉴 등록, 가격 변동 등록, 할인 정보 등록) -> 오른쪽 아래
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class App extends JFrame {

    public App(String msg) {
        super(msg);
        setLayout(new BorderLayout()); // 전체 레이아웃을 BorderLayout으로 설정
        
        // 이미지를 표시할 라벨
        JLabel imageLabel = new JLabel("+ 음식사진을 업로드하세요", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        add(imageLabel, BorderLayout.NORTH);

        // 파일 첨부 및 검색을 위한 Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 왼쪽 정렬된 FlowLayout

        // 파일 첨부 버튼
        JButton attachButton = new JButton("이미지 첨부");
        attachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 파일 선택 다이얼로그 열기
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filename = selectedFile.getName();
                    String imagePath = selectedFile.getPath();
                    imageLabel.setText("선택한 이미지: " + filename);
                    try {
                        // 선택한 이미지 파일을 읽어와서 화면에 표시
                        Image image = ImageIO.read(new File(imagePath));
                        ImageIcon icon = new ImageIcon(image);
                        imageLabel.setIcon(icon);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        controlPanel.add(new JLabel("음식사진 첨부"));
        controlPanel.add(attachButton);

        // 검색을 위한 TextField와 버튼
        JTextField searchField = new JTextField(30);
        JButton searchButton = new JButton("검색");
        controlPanel.add(new JLabel("식당검색"));
        controlPanel.add(searchField); // TextField를 controlPanel에 추가
        controlPanel.add(searchButton); // 검색 버튼을 controlPanel에 추가

        // controlPanel을 Frame에 추가
        add(controlPanel, BorderLayout.CENTER); // control Panel을 중앙에 추가

        // 입력 필드를 포함한 Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2)); // 4행 2열의 GridLayout 설정

        // 메뉴 입력 관련 UI 생성
        JLabel menuLabel = new JLabel("메뉴", SwingConstants.CENTER);
        JTextField menuTextField = new JTextField();
        menuTextField.setPreferredSize(new Dimension(20, 30));
        inputPanel.add(menuLabel);
        inputPanel.add(menuTextField);

        // 가격 입력 관련 UI 생성
        JLabel priceLabel = new JLabel("가격", SwingConstants.CENTER);
        JTextField priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(20, 30));
        inputPanel.add(priceLabel);
        inputPanel.add(priceTextField);

        // 할인 정보 입력 관련 UI 생성
        JLabel discountLabel = new JLabel("할인 정보", SwingConstants.CENTER);
        JTextField discountTextField = new JTextField();
        discountTextField.setPreferredSize(new Dimension(20, 50));
        inputPanel.add(discountLabel);
        inputPanel.add(discountTextField);

        // 등록 버튼
        JButton registerButton = new JButton("등록하기");
        inputPanel.add(new JLabel()); // 공간 확보를 위한 빈 라벨 추가
        inputPanel.add(registerButton);

        // inputPanel을 Frame에 추가
        add(inputPanel, BorderLayout.SOUTH); // 하단 Panel을 Frame에 추가

         // 등록 버튼을 눌렀을 때 새로운 창을 여는 액션 리스너 추가
         registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 새로운 JFrame 생성
                JFrame newFrame = new JFrame(" 완료 ");
                newFrame.setSize(400, 300);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setVisible(true);

                // 새 창에 추가할 내용 설정 (예: 레이블 추가)
                JLabel newLabel = new JLabel(" 메뉴와 할인정보가 등록되었습니다. ", SwingConstants.CENTER);
                newFrame.add(newLabel);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack(); // Frame 크기 자동 조정
        setVisible(true);
    }

    public static void main(String args[]) {
        new App("관리자용 - 정보등록");
    }
}


