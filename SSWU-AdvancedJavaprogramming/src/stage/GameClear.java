package stage;

import javax.swing.*;
import java.awt.*;

public class GameClear extends JPanel {
    public GameClear() {
    	System.out.println("GameClear 생성 중...");
        this.setLayout(new BorderLayout());

        // 게임 클리어 이미지 표시
        JLabel imageLabel = new JLabel(new ImageIcon("image/stageclear.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(imageLabel, BorderLayout.CENTER);
    }
}
