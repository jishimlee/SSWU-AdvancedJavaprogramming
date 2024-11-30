package component;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MoonRabbitGame;
import service.BackgroundBananaService;
import stage.Stage4;

public class ThrowBanana extends JLabel {
    private int x;
    private int y;
    // 0 화면에 있음, 1 화면에서 사라짐(또는 이미 닿아서 인식하지 않게 처리)
    private int bananaState;

	private PlayerRabbit player;
	private ThrowBanana throwBanana;
	private Monkey monkey;
	private MoonRabbitGame game;
	private JPanel stage;
	private int stageNumber;
	private static final int BANANA_LIFETIME = 3000;
	
	private ImageIcon banana;
	
	public ThrowBanana(MoonRabbitGame game, Monkey monkey, PlayerRabbit player) {
		this.throwBanana = this;
		this.game = game;
		this.monkey = monkey;
		this.player = player;
		initObject();
		initSetting();
		checkCollision();
		
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
		this.y = monkey.getY() + 10;
//		System.out.println("바나나 초기화 위치: x=" + x + ", y=" + y);

//		System.out.println("빠나나");
		this.setSize(31, 39);
		this.setLocation(x, y);
	}
	

	private void startBananaLifetime() {
	    Timer timer = new Timer();

	    // 첫 번째 타이머: BANANA_LIFETIME 후 바나나 제거
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            if (bananaState == 0) { // 상태 확인
	                setBananaState(1); // 바나나 상태를 1로 설정하며 제거 로직 실행
	            }
	            timer.cancel(); // 타이머 종료
	        }
	    }, BANANA_LIFETIME); // BANANA_LIFETIME (3초) 후 실행
	}

	
	private void checkCollision() {
		(new Thread(new BackgroundBananaService(this, this.player))).start();
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
	
	public int getBananaState() {
		return bananaState;
	}

	public void setBananaState(int bananaState) {
	    this.bananaState = bananaState;

	    if (bananaState == 1) {
	        // 바나나 제거 로직
	        if (stage instanceof Stage4) {
	            ((Stage4) stage).getFrontMap().remove(this); // 바나나 제거
	            ((Stage4) stage).getFrontMap().repaint();
	        }

	        // 500ms 뒤에 monkey 상태 업데이트
	        Timer delayTimer = new Timer();
	        delayTimer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                monkey.setBananaExist(false);
	                delayTimer.cancel(); // 타이머 종료
	            }
	        }, 500); // 500ms 후 실행
	    }
	}



	public static int getBananaLifetime() {
		return BANANA_LIFETIME;
	}
}



