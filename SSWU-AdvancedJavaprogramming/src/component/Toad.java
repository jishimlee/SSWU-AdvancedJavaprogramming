package component;

import javax.swing.ImageIcon;

import javax.swing.JLabel;

import direction.EnemyDirection;
import main.MoonRabbitGame;
import service.BackgroundToadService;
import service.Moveable;

public class Toad extends JLabel implements Moveable {
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
	   private static final int SPEED = 1;
	   private static final int JUMPSPEED = 2;
	   private int stageHeight;	// up용 스테이지 높이 표시
	   
	   private boolean isJumping; // 점프 상태 플래그
		
	   public boolean isJumping() {
		   return this.isJumping;
	   }
		
	   public void setJumping(boolean jumping) {
		   this.isJumping = jumping;
	   }

	   private MoonRabbitGame game;
	   
	   private ImageIcon toadR;
	   private ImageIcon toadL;
	   
	
	   public void setStageHeight(int stageHeight) {
		   this.stageHeight = stageHeight;
	   }
	   
		
	   public Toad() {
	      this.initObject();
	   }

	   
	   public Toad(int x, int y, boolean left, MoonRabbitGame game) {
		      this.initObject();
		      this.initSetting(x, y, left);
		      this.game = game;
	   }
	   
	   
	   public void start() {
		   System.out.println("start() 호출됨");
		   this.initBackgroundToadService();
		   this.state = 0;
		   if (startLeft) this.left();
		   else this.right();
	   }

	   
	   public void initObject() {
	      this.toadL = new ImageIcon("image/toadL.png");
	      this.toadR = new ImageIcon("image/toadR.png");
	   }
	   
	   
	   public void initSetting(int x, int y, boolean left) {
	      this.x = x;
	      this.y = y;
	      this.startLeft = left;
	      if (startLeft) {
	         this.setIcon(this.toadL);
	      } else {
	         this.setIcon(this.toadR);
	      }

	      this.setSize(50, 50);
	      this.setLocation(this.x, this.y);
	   }
	   
	   
	   private void initBackgroundToadService() {
		   System.out.println("스레드 시작");
		   (new Thread(new BackgroundToadService(this, game))).start();
	   }

	   
	   // 위층으로 점프 (up 메서드 시 위로 올라가기만 하고 좌우 이동 없음)
	   public void up() {
		   
	   }

	   
	   // 랜덤으로 아래로 내려갈 수 있도록 구현하려 하는데 언제 내려갈지 고민
	   // e.g., 좌우 왕복을 n회 했으면 아래로 내려갈 수 있는 조건 충족 등...
	   public void down() {
		   
	   }
	   
	   
	   // 점프 시 펄쩍 뛰어오르게
	   public void left() {
		   System.out.println("LEFT");
		   this.enemyDirection = EnemyDirection.LEFT;
		   this.setIcon(this.toadL);
		   this.left = true;
		   this.isJumping = true;
		   
		   Thread t = new Thread(() -> {
			   while (this.left) {
				   // 점프로 올라갔다가
				   for (int i = 0; i < 20; i++) {
					   this.x -= SPEED;
					   this.y -= JUMPSPEED;
					   this.setLocation(this.x, this.y);
					   try {
						Thread.sleep(10);
					   } catch (InterruptedException e) {
						   e.printStackTrace();
					   }
				   }
				   // 포물선 모양으로 내려감
				   for (int i = 0; i < 20; i++) {
					   this.x -= SPEED;
					   this.y += JUMPSPEED;
					   this.setLocation(this.x, this.y);
					   try {
						Thread.sleep(10);
					   } catch (InterruptedException e2) {
						   e2.printStackTrace();
					   }
				   } this.isJumping = false;	// 바닥 검사 재시작
			   }
		   });
		   
		   t.start();
	   }

	   public void right() {
		   System.out.println("RIGHT");
		   this.enemyDirection = EnemyDirection.RIGHT;
		   this.setIcon(toadR);
		   this.right = true;
		   Thread t = new Thread(() -> {
			   while(this.right) {
				   this.x += SPEED;
				   this.setLocation(this.x, this.y);
				   
				   try {
					   Thread.sleep(10L);
				   } catch (Exception e2) {
		               System.out.println("오른쪽 이동 중 인터럽트 발생: " + e2.getMessage());
				   }
			   }
		   });
		   t.start();
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

	   public ImageIcon getToadR() {
	      return this.toadR;
	   }

	   public ImageIcon getToadL() {
	      return this.toadL;
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

	   public void setState(int state) {
	      this.state = state;
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

	   public void setToadR(ImageIcon monkeyR) {
	      this.toadR = monkeyR;
	   }

	   public void setToadL(ImageIcon monkeyL) {
	      this.toadL = monkeyL;
	   }
	}
