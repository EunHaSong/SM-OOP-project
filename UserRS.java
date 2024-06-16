import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;

class UserRS extends JPanel{
    JPanel whole, profile, information, menu, item;
    JLabel name, sign, info, menulabel;
    JButton back2main, picture;

    public UserRS() {
        // 데이타베이스 끌고오기
        DB db = new DB();

        // 화면 크기 초기 설정
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 2;
        int frameHeight = screenSize.height;

        // 테두리 초기 설정
        Border b = BorderFactory.createLineBorder(new Color(255,140,0), 4);

        // 글자 초기 설정
        Font defaultFont = new Font("국민연금체", Font.PLAIN, 13);
 
        // 전체 패널 생성 (전체 스크롤이 가능하도록)
        whole = new JPanel();
        whole.setLayout(new BoxLayout(whole, BoxLayout.Y_AXIS)); // 레이아웃 설정
        whole.setBackground(Color.WHITE); // 배경색 설정(흰색)

        // 위 패널 생성
        profile = new JPanel();
        profile.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // 레이아웃 및 간격 설정
        profile.setPreferredSize(new Dimension(frameWidth, (int) (200 * 2 / 3.0))); // 크기 설정
        profile.setBackground(Color.WHITE); // 배경색 설정(흰색)

        // 버튼 생성 (메인화면으로 돌아갈 수 있도록)
        ImageIcon backIcon = new ImageIcon("C:\\yeons\\java\\final\\images\\back2main.jpeg");
        Image backImage = backIcon.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH); // 사진 크기 설정
        back2main = new JButton(new ImageIcon(backImage));
        back2main.setPreferredSize(new Dimension(55, 55)); // 크기 설정
        addHoverEffect(back2main);

        // back2main 버튼에 ActionListener 추가
        back2main.addActionListener(e -> {
            // FoodCategory 생성 및 보이기
            UserTPS mainFrame = new UserTPS("사용자 화면");
            FoodCategory foodCate = new FoodCategory();
            mainFrame.getLeftPanel().add(foodCate, BorderLayout.CENTER);

            // 현재 프레임 닫기
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        // 매장 간판 레이블 생성
        Image signImage = db.signIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 사진 크기 설정
        sign = new JLabel(new ImageIcon(signImage));
        sign.setPreferredSize(new Dimension(100, 100)); // 크기 설정
        sign.setBackground(Color.WHITE); // 배경색 설정(흰색)
        sign.setOpaque(true);

        // 매장 이름 레이블 생성
        name = new JLabel(db.name, SwingConstants.CENTER);
        name.setPreferredSize(new Dimension((frameWidth - (75 + 100 + 60)) * 3 / 4, 100)); // 크기 설정
        name.setBackground(Color.WHITE); // 배경색 설정(흰색)
        name.setBorder(b); // 테두리 설정
        name.setOpaque(true);
        name.setFont(new Font("국민연금체", Font.PLAIN, 36)); // 글자체 효과 크기 지정

        profile.add(back2main); // 레이블 및 버튼 패널에 추가
        profile.add(sign);
        profile.add(name);
 
        // 중간 패널 생성
        information = new JPanel();
        information.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        information.setPreferredSize(new Dimension(frameWidth, (int) (200 * 2 / 3.0 - 10))); // 크기 설정
        information.setBackground(Color.WHITE); // 배경색 설정(흰색)

        // 매장 정보 레이블 생성
        info = new JLabel(db.rsInformation, SwingConstants.CENTER);
        info.setPreferredSize(new Dimension(frameWidth - 120, 90)); // 크기 설정
        info.setBorder(b);
        info.setBackground(Color.WHITE); // 배경색 설정(흰색)
        info.setOpaque(true);
        info.setFont(new Font("국민연금체", Font.PLAIN, 18)); // 글자체 효과 크기 지정

        information.add(info); // 레이블 패널에 추가

        // 아래 패널 생성
        menu = new JPanel();
        menu.setLayout(new GridBagLayout()); // 레이아웃 설정
        menu.setBackground(Color.WHITE); // 배경색 설정(흰색)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 각 레이블 사이의 간격 설정

        int buttonSize = (frameWidth - 80) / 3; // 버튼 크기 설정
        int buttonHeight = buttonSize * 4 / 3;

        // 메뉴판 설정 (메뉴 개수만큼 레이블과 버튼 생성)
        for (int i = 1; i <= db.menudata.length; i++) {
            item = new JPanel();
            item.setLayout(new BorderLayout());
            item.setBackground(Color.WHITE);

            // 개별적으로 이미지 설정
            ImageIcon itemIcon = new ImageIcon(db.imagePaths[i - 1]);
            Image itemImage = itemIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
            picture = new JButton(new ImageIcon(itemImage));
            picture.setPreferredSize(new Dimension(buttonSize, buttonSize)); // 정사각형 크기 설정
            addHoverEffect(picture);

            menulabel = new JLabel(db.menudata[i - 1], SwingConstants.CENTER); // 번호. 메뉴명 (가격)
            menulabel.setPreferredSize(new Dimension(buttonSize, buttonHeight - buttonSize)); // 레이블 높이는 남은 공간으로 설정
            menulabel.setBackground(Color.WHITE); // 배경색 설정(흰색)
            menulabel.setOpaque(true);
            menulabel.setFont(defaultFont); // 글자체 효과 크기 지정

            item.add(picture, BorderLayout.CENTER); // 패널에 레이블과 사진 추가
            item.add(menulabel, BorderLayout.SOUTH);

            gbc.gridx = (i - 1) % 3;
            gbc.gridy = (i - 1) / 3;
            menu.add(item, gbc); // 패널에 패널 추가
        }

        // 메인 패널에 모든 패널 추가
        whole.add(profile);
        whole.add(information);
        whole.add(menu);

        // 전체 패널 스크롤 가능하게
        JScrollPane scrollPane = new JScrollPane(whole);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
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
        Border roundedBorder = BorderFactory.createLineBorder(new Color(255,140,0), 4, true);
        button.setBorder(roundedBorder);
    }


}
