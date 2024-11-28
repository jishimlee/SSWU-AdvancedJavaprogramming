package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import direction.EnemyDirection;
import main.MoonRabbitGame;
import service.BackgroundMonkeyService;
import service.Moveable;

public class Monkey extends JLabel implements Moveable {
	   private int x;
	   private int y;
	   /*
	    * stage1 층별 y 좌표 값
	    * 1층: y = 560
	    * 2층: y = 455
	    * 3층: y = 340
	    * 4층: y = 232
	    * 5층: y = 128
	    */
	   private boolean left;
	   private boolean right;
	   private boolean startLeft;
	   private int state;	// 공격 당했는지 확인, 0은 공격 X, 1은 공격 당함
	   private EnemyDirection enemyDirection;
	   private boolean leftCrash;
	   private boolean rightCrash;
	   private static final int SPEED = 2;
	   private MoonRabbitGame game;
	   private PlayerRabbit player;
	   private int stageNumber;
	   private JPanel stage;
	   
	   private ImageIcon monkeyR;
	   private ImageIcon monkeyL;
	   private ImageIcon strawberry;
	   private ThrowBanana banana;
	   private boolean bananaExist = false;

	   

	   public Monkey() {
	      this.initObject();
	   }
	   

	   public Monkey(int x, int y, boolean left, MoonRabbitGame game, PlayerRabbit player) {
		      this.initObject();
		      this.initSetting(x, y, left);
		      this.game = game;
		      this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		      this.player = player; // PlayerRabbit 객체를 직접 전달받음
		      this.stageNumber = game.getStageNumber();    
	   }
	   
	   
	   public void start() {
		   System.out.println("start() 호출됨");
		   this.initBackgroundMonkeyService();
		   this.state = 0;
		   if (startLeft) this.left();
		   else this.right();
	   }

	   
	   public void initObject() {
	      this.monkeyL = new ImageIcon("image/monkeyL.png");
	      this.monkeyR = new ImageIcon("image/monkeyR.png");
	   }
	   
	   
	   public void initSetting(int x, int y, boolean left) {
	      this.x = x;
	      this.y = y;
	      this.startLeft = left;
	      if (startLeft) {
	         this.setIcon(this.monkeyL);
	      } else {
	         this.setIcon(this.monkeyR);
	      }

	      this.setSize(50, 50);
	      this.setLocation(this.x, this.y);
	   }

	   
	   private void initBackgroundMonkeyService() {
		   System.out.println("스레드 시작");
		   (new Thread(new BackgroundMonkeyService(this, this.game, this.player))).start();
	   }


	   public void left() {
//		   System.out.println("LEFT");
		   this.enemyDirection = EnemyDirection.LEFT;
		   this.setIcon(this.monkeyL);
		   this.left = true;
		   Thread t = new Thread(() -> {
			   while (this.left) {
				   this.x -= SPEED;
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
//		   System.out.println("RIGHT");
		   this.enemyDirection = EnemyDirection.RIGHT;
		   this.setIcon(monkeyR);
		   this.right = true;
		   Thread t = new Thread(() -> {
			   while(this.right) {
				   this.x += SPEED;
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
	   
	   
//	   public void throwBanana() {
//	        if (banana != null) return; // 이미 바나나가 있으면 생성하지 않음
//	        
//	        banana = new ThrowBanana(this.game, this, this.player);
//	        existBanana = true;
//	        System.out.println("banana 추가");
//	        
//	        // 바나나 일정 시간 후 제거 & null 처리
//	        new Thread(() -> {
//	            try {
//	                Thread.sleep(ThrowBanana.getBananaLifetime()); // 바나나 수명만큼 대기
//	            } catch (InterruptedException e) {
//	                e.printStackTrace();
//	            }
//	            if (banana != null) {
//		            this.banana.setVisible(false); // 화면에서 숨기기
//		            this.stage.remove(this.banana); // 부모 패널에서 제거
//		            this.stage.repaint();
//		            existBanana = false;
//	                banana = null; // 참조를 null로 설정
//	            }
//	        }).start();
//	   }

	public void setState(int state) {
		   this.state = state;
		   if (this.state == 1) {
			   this.strawberry = new ImageIcon("image/strawberry.png");
			   this.setIcon(this.strawberry);
			   this.game.repaint();
		   }
		   else if (this.state == 2) {
			   this.setVisible(false);
		       this.game.getCurrentStage().remove(this); // 스테이지에서 제거
		       this.game.getCurrentStage().revalidate(); // 레이아웃 갱신
		       this.game.getCurrentStage().repaint(); // 화면 갱신
		       System.out.println("딸기모찌가 제거되었습니다.");
		   }
	}
	   
//--------------------------------------
	   
	   public boolean isBananaExist() {
			return bananaExist;
		}
	
		public void setBananaExist(boolean bananaExist) {
			this.bananaExist = bananaExist;
		}
	
	   public void up() {
	   }

	   public void down() {
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

	   public ImageIcon getMonkeyR() {
	      return this.monkeyR;
	   }

	   public ImageIcon getMonkeyL() {
	      return this.monkeyL;
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

	   public void setMonkeyR(ImageIcon monkeyR) {
	      this.monkeyR = monkeyR;
	   }

	   public void setMonkeyL(ImageIcon monkeyL) {
	      this.monkeyL = monkeyL;
	   }
	}
