package component;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MoonRabbitGame;
import stage.Stage4;

public class ThrowBanana extends JLabel {
    private int x;
    private int y;
	private PlayerRabbit player;
	private Monkey monkey;
	private MoonRabbitGame game;
	private JPanel stage;
	private int stageNumber;
	private static final int BANANA_LIFETIME = 3000;
	
	private ImageIcon banana;
	
	public ThrowBanana(MoonRabbitGame game, Monkey monkey, PlayerRabbit player) {
		this.game = game;
		this.monkey = monkey;
		this.player = player;
		initObject();
		initSetting();
		
        // 일정 시간 후 바나나 제거
        startBananaLifetime();
    }
	
	public void initObject() {
		this.banana = new ImageIcon("image/banana.png");	
		this.setIcon(this.banana);
		this.stage = game.getCurrentStage();
		this.stageNumber = game.getStageNumber();
	}
	
	public void initSetting() {
		this.x = monkey.getX() + 9;
		this.y = monkey.getY() + 5;

		System.out.println("빠나나");
		this.setSize(31, 39);
		this.setLocation(x, y);
	}
	

	private void startBananaLifetime() {
	    Timer timer = new Timer();

	    // 첫 번째 타이머: BANANA_LIFETIME 후 바나나 제거
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            // 바나나 객체 제거
	    		if (stage instanceof Stage4) {
	                ((Stage4) stage).getFrontMap().remove(ThrowBanana.this);
	                ((Stage4) stage).getFrontMap().repaint();
	                System.out.println("바나나 제거됨");
	    		}
	            // 다른 스테이지 구현 시 추가

	            // 두 번째 타이머: 추가 0.5초 후 monkey 상태 업데이트
	            new Timer().schedule(new TimerTask() {
	                @Override
	                public void run() {
	                    // monkey 상태 업데이트
	                    monkey.setBananaExist(false);
//	                    System.out.println("0.5초 후 BananaExist: " + monkey.isBananaExist());
	                }
	            }, 500); // 500ms (0.5초) 후 실행

	            // 첫 번째 타이머 종료
	            timer.cancel();
	        }
	    }, BANANA_LIFETIME); // BANANA_LIFETIME (3초) 후 실행
	}



	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static int getBananaLifetime() {
		return BANANA_LIFETIME;
	}
}



