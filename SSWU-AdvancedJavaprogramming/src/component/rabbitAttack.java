package component;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import direction.PlayerDirection;
import service.Moveable;

public class rabbitAttack extends JLabel implements Moveable {
    private PlayerRabbit player;
    private BufferedImage image;
    private int x;
    private int y;
    private PlayerDirection direction;
    private final int SPEED = 4;

    private ImageIcon hammerL;
    private ImageIcon hammerR;

    public rabbitAttack(PlayerRabbit player) throws IOException {
        this.player = player;
        this.initObject();
        this.initSetting();
        this.image = ImageIO.read(new File("image/background1.png"));
    }

    private void initObject() {
        this.hammerL = new ImageIcon(getClass().getResource("/image/hammerL.png"));
        this.hammerR = new ImageIcon(getClass().getResource("/image/hammerR.png"));
    }

    private void initSetting() {
        this.x = 45;
        this.y = 555;
        this.setIcon(hammerR); // 초기 이미지를 오른쪽으로 설정
        this.setSize(50, 50);
        this.setLocation(x, y);
    }

    private void initThread() {
        new Thread(() -> {
            if (player != null && player.getDirection() == PlayerDirection.LEFT) {
                left();
            } else if (player != null) {
                right();
            }
        }).start();
    }

    @Override
    public void left() {
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void right() {
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void up() {
		
		
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		
	}
}
