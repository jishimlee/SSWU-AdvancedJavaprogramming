package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import direction.EnemyDirection;
import main.MoonRabbitGame;
import service.BackgroundWildBoarService;
import service.Moveable;

public class WildBoar extends JLabel implements Moveable {
	   private int x;
	   private int y;
	   private boolean left;
	   private boolean right;
	   private boolean startLeft;
	   private int state;	// 공격 당했는지 확인, 0은 공격 X, 1은 공격 당함
	   private boolean rushState = false;	// true는 rush 가능, false는 rush 불가능
	   								// 3000ms마다 true로 변경, rush 끝날 경우 다시 false으로 초기화
	   private long lastRushTime; // 마지막 rushState 해제 시간

	   public long getLastRushTime() {
	       return lastRushTime;
	   }
	   
	   public boolean isRushState() {
		return rushState;
	}

	   public void setRushState(boolean rushState) {
		    this.rushState = rushState;
		    if (!rushState) {
		        this.lastRushTime = System.currentTimeMillis(); // rushState 해제 시점 기록
		    }
		}

	   private EnemyDirection enemyDirection;
	   private boolean leftCrash;
	   private boolean rightCrash;
	   private static final int SPEED = 2;
	   private MoonRabbitGame game;
	   private PlayerRabbit player;
	   private int stageNumber;
	   private JPanel stage;
	   
	   private ImageIcon wildboarR;
	   private ImageIcon wildboarL;
	   private ImageIcon siru;

	   public WildBoar() {
	      this.initObject();
	   }

	   public WildBoar(int x, int y, boolean left, MoonRabbitGame game, PlayerRabbit player) {
		      this.initObject();
		      this.initSetting(x, y, left);
		      this.game = game;
		      this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		      this.player = player; // PlayerRabbit 객체를 직접 전달받음
		      this.stageNumber = game.getStageNumber();
	   }
	   
	   public void start() {
		   System.out.println("start() 호출됨");
		   this.initBackgroundWildBoarService();
		   this.state = 0;
		   if (startLeft) this.left();
		   else this.right();
	   }


	   public void initObject() {
	      this.wildboarL = new ImageIcon("image/wildboarL.png");
	      this.wildboarR = new ImageIcon("image/wildboarR.png");
	   }
	   

	   public void initSetting(int x, int y, boolean left) {
	      this.x = x;
	      this.y = y;
	      this.startLeft = left;
	      if (startLeft) {
	         this.setIcon(this.wildboarL);
	      } else {
	         this.setIcon(this.wildboarR);
	      }

	      this.setSize(67, 50);
	      this.setLocation(this.x, this.y);
	   }
	   
	   private void initBackgroundWildBoarService() {
		   System.out.println("스레드 시작");
		   (new Thread(new BackgroundWildBoarService(this, this.game, this.player))).start();
	   }

	   public void up() {
	   }

	   public void down() {
	   }

	   public void left() {
		   System.out.println("LEFT");
		   this.enemyDirection = EnemyDirection.LEFT;
		   this.setIcon(this.wildboarL);
		   this.left = true;
		   Thread t = new Thread(() -> {
			   while (this.left) {
				   if (rushState) this.x -= (SPEED * 2);
				   else this.x -= SPEED;
				   this.setLocation(this.x, this.y);
				   
				   try {
					   Thread.sleep(10L);
				   } catch (Exception e) {
					   System.out.println("왼쪽 이동 중 인터럽트 발생: " + e.getMessage());
				   }
				   
			   }
		   });
		   t.start();
	   }	   

	   public void right() {
		   System.out.println("RIGHT");
		   this.enemyDirection = EnemyDirection.RIGHT;
		   this.setIcon(wildboarR);
		   this.right = true;
		   Thread t = new Thread(() -> {
			   while(this.right) {
				   if (rushState) this.x += (SPEED * 2);
				   else this.x += SPEED;
				   this.setLocation(this.x, this.y);
				   
				   try {
					   Thread.sleep(10L);
				   } catch (Exception e) {
		               System.out.println("오른쪽 이동 중 인터럽트 발생: " + e.getMessage());
				   }
			   }
		   });
		   t.start();
	   }
	   
	   public void setState(int state) {
		      this.state = state;
		      if (state == 1) {
		    	  this.siru = new ImageIcon("image/siru.png");
		    	  this.setIcon(this.siru);
		    	  this.game.repaint();
		      }
		      else if (state == 2) {
		          this.setVisible(false); // 거북이 비활성화
		          this.game.getCurrentStage().remove(this); // 스테이지에서 제거
		          this.game.getCurrentStage().revalidate(); // 레이아웃 갱신
		          this.game.getCurrentStage().repaint(); // 화면 갱신
		          System.out.println("시루떡이 제거되었습니다.");
		      }
	   }
	   
	   public int getX() {
	      return this.x;
	   }

	   public int getY() {
	      return this.y;
	   }

	   public boolean isLeft() {
	      return this.left;
	   }

	   public boolean isRight() {
	      return this.right;
	   }
	   
	   public boolean isstartLeft() {
		   return this.startLeft;
	   }

	   public int getState() {
	      return this.state;
	   }

	   public EnemyDirection getEnemyDirection() {
	      return this.enemyDirection;
	   }

	   public boolean isLeftCrash() {
	      return this.leftCrash;
	   }

	   public boolean isRightCrash() {
	      return this.rightCrash;
	   }

	   public ImageIcon getWildBoarR() {
	      return this.wildboarR;
	   }

	   public ImageIcon getWildBoarL() {
	      return this.wildboarL;
	   }

	   public void setX(int x) {
	      this.x = x;
	   }

	   public void setY(int y) {
	      this.y = y;
	   }

	   public void setLeft(boolean left) {
	      this.left = left;
	   }

	   public void setRight(boolean right) {
	      this.right = right;
	   }
	   
	   public void setstartLeft(boolean startLeft) {
		   this.startLeft = startLeft;
	   }

	   public void setEnemyDirection(EnemyDirection enemyDirection) {
	      this.enemyDirection = enemyDirection;
	   }

	   public void setLeftCrash(boolean leftCrash) {
	      this.leftCrash = leftCrash;
	   }

	   public void setRightCrash(boolean rightCrash) {
	      this.rightCrash = rightCrash;
	   }

	   public void setWildBoarR(ImageIcon wildboarR) {
	      this.wildboarR = wildboarR;
	   }

	   public void setWildBoarL(ImageIcon wildboarL) {
	      this.wildboarL = wildboarL;
	   }
	}
