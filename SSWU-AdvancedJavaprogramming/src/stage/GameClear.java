package stage;

import javax.swing.*;
import java.awt.*;

public class GameClear extends JPanel {
    public GameClear() {
        this.setLayout(new BorderLayout());

        // 게임 클리어 이미지 표시
        JLabel imageLabel = new JLabel(new ImageIcon("img/stageclear.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(imageLabel, BorderLayout.CENTER);
    }
}
