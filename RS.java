import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class RS extends JFrame {
    
    JFrame f;
    JPanel whole, profile, information, menu, item;
    JLabel name, sign, info, menulabel;
    JButton back2main, picture;
    
    public RS(String msg) {
        // 데이타베이스 끌고오기
        DB db = new DB();

        // 화면 크기 초기 설정
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 2;
        int frameHeight = screenSize.height;

        // 테두리 초기 설정
        Border b = BorderFactory.createLineBorder(Color.black, 1);

        // 창 생성
        f = new JFrame(msg);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(frameWidth, frameHeight);
        f.setLocation(0, 0); // 왼쪽 절반에 위치하게 설정

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
        
        // 매장 간판 레이블 생성
        Image signImage = db.signIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 사진 크기 설정
        sign = new JLabel(new ImageIcon(signImage));
        sign.setPreferredSize(new Dimension(100, 100)); // 크기 설정
        sign.setBorder(b);
        sign.setBackground(Color.WHITE); // 배경색 설정(흰색)
        sign.setOpaque(true);
        
        // 매장 이름 레이블 생성
        name = new JLabel(db.name, SwingConstants.CENTER);
        name.setPreferredSize(new Dimension((frameWidth - (75 + 100 + 60)) * 3 / 4, 100)); // 크기 설정
        name.setBackground(Color.WHITE); // 배경색 설정(흰색)
        name.setBorder(b); // 테두리 설정
        name.setOpaque(true);
        name.setFont(new Font("맑은고딕",Font.BOLD, 28)); // 글자체 효과 크기 지정
        
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
        info.setFont(new Font("맑은고딕",Font.BOLD, 18)); // 글자체 효과 크기 지정

        information.add(info); // 레이블 패널에 추가


        // 아래 패널 생성
        menu = new JPanel();
        menu.setLayout(new GridBagLayout()); // 레이아웃 설정
        menu.setBackground(Color.WHITE); // 배경색 설정

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 각 레이블 사이의 간격 설정

        int buttonSize = (frameWidth - 80) / 3; // 버튼 크기 설정
        int buttonHeight = buttonSize * 4 / 3;

        // 메뉴판 설정 (임시로 12개의 레이블과 버튼 생성)
        for (int i = 1; i <= 9; i++) {
            item = new JPanel();
            item.setLayout(new BorderLayout());
            item.setBackground(Color.WHITE);

            // 개별적으로 이미지 설정
            ImageIcon itemIcon = new ImageIcon(db.imagePaths[i - 1]);
            Image itemImage = itemIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
            picture = new JButton(new ImageIcon(itemImage));
            picture.setPreferredSize(new Dimension(buttonSize, buttonSize)); // 정사각형 크기 설정
            
            menulabel = new JLabel(db.menudata[i - 1], SwingConstants.CENTER); // 번호. 메뉴명 (가격)
            menulabel.setPreferredSize(new Dimension(buttonSize, buttonHeight - buttonSize)); // 레이블 높이는 남은 공간으로 설정
            menulabel.setBackground(Color.WHITE); // 배경색 설정(흰색)
            menulabel.setOpaque(true);
            menulabel.setFont(new Font("맑은고딕",Font.BOLD, 16)); // 글자체 효과 크기 지정

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
        f.add(scrollPane);

        f.setVisible(true);
    }
}
