package stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameIntro extends JPanel {
	 private JLabel imageLabel;
	    private int currentIndex = 0; // 현재 이미지 인덱스
	    private final String[] imagePaths = {
	            "image/intro1.png",
	            "image/intro2.png",
	            "image/intro3.png",
	            "image/intro4.png",
	            "image/intro5.png",
	            "image/intro6.png"
	    };
	    private Runnable onFinish; // 설명 종료 시 실행할 콜백

	    public GameIntro(Runnable onFinish) {
	        this.onFinish = onFinish;
	        this.setLayout(new BorderLayout());

	        // 첫 번째 이미지 추가
	        imageLabel = new JLabel(new ImageIcon(imagePaths[currentIndex]));
	        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        this.add(imageLabel, BorderLayout.CENTER);

	        // 클릭 이벤트로 이미지 전환
	        imageLabel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                currentIndex++;
	                if (currentIndex < imagePaths.length) {
	                    // 다음 이미지 표시
	                    imageLabel.setIcon(new ImageIcon(imagePaths[currentIndex]));
	                } else {
	                    // 설명 종료 후 메인 게임으로 전환
	                    onFinish.run();
	                }
	            }
	        });
	        // 방향키 입력 막기
	        /*this.setFocusable(true);
	        this.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                int keyCode = e.getKeyCode();
	                // 방향키일 경우 이벤트 소비 (기본 동작 방지)
	                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
	                    keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
	                    e.consume();
	                }
	            }
	        });*/
	        
	        
	    }
}
