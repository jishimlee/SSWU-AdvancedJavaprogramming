package stage;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {
    public GameOver() {
        this.setLayout(new BorderLayout());

        // 게임 클리어 이미지 표시
        JLabel imageLabel = new JLabel(new ImageIcon("image/gameover.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(imageLabel, BorderLayout.CENTER);
    }
}
