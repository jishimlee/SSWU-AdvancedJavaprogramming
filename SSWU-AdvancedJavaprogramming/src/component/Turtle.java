package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import direction.EnemyDirection;
import main.MoonRabbitGame;
import service.BackgroundTurtleService;
import service.Moveable;
import stage.Stage1;
import stage.Stage2;

public class Turtle extends JLabel implements Moveable {
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
   private int state;	// 공격 당했는지 확인, 0은 공격 X, 1은 공격 당함 (떡으로 변함)
   private EnemyDirection enemyDirection;
   private boolean leftCrash;
   private boolean rightCrash;
   private static final int SPEED = 1;
   private MoonRabbitGame game;
   private PlayerRabbit player;
   
   private ImageIcon turtleR;
   private ImageIcon turtleL;
   private ImageIcon songpyeon;

   public Turtle() {
      this.initObject();
   }

   public Turtle(int x, int y, boolean left, Object game, PlayerRabbit player) {
      this.initObject();
      this.initSetting(x, y, left);
      //일단 해놈. 수정해야함!
      if (game instanceof MoonRabbitGame) {
          this.game = (MoonRabbitGame) game;
          // this.player = ((MoonRabbitGame) game).getPlayer();
          // 이 부분 MoonRabbitGame에 getPlayer 없어서 오류 뜨는데 사실 없어두 되는 거 아닌가요??
      } else if (game instanceof Stage1) {
          this.game = ((Stage1) game).getGame(); // Stage1이 MoonRabbitGame 반환 가능
          this.player = ((Stage1) game).getPlayer();
      } else if (game instanceof Stage2) {
          this.game = ((Stage2) game).getGame();
          this.player = ((Stage2) game).getPlayer();
      }
      
  }
   
   public void start() {
	   System.out.println("start() 호출됨");
	   this.initBackgroundTurtleService();
	   this.state = 0;
	   if (startLeft) this.left();
	   else this.right();
   }

   public void initObject() {
      this.turtleL = new ImageIcon("image/turtleL.png");
      this.turtleR = new ImageIcon("image/turtleR.png");
      this.songpyeon = new ImageIcon("image/songpyeon.png");
   }
   
   // y 좌표를 토끼보다 5 크게 설정하면 토끼와 동일한 위치에 있음
   public void initSetting(int x, int y, boolean left) {
      this.x = x;
      this.y = y;
      this.startLeft = left;
      if (startLeft) {
    	  this.setIcon(this.turtleL);
      } else {
    	  this.setIcon(this.turtleR);
      }
      this.setSize(50, 50);
      this.setLocation(this.x, this.y);
   }
   
   private void initBackgroundTurtleService() {
	   System.out.println("스레드 시작");
	   (new Thread(new BackgroundTurtleService(this, game, this.player))).start();
   }

   public void up() {
   }

   public void down() {
   }

   public void left() {
	   System.out.println("LEFT");
	   this.enemyDirection = EnemyDirection.LEFT;
	   this.setIcon(this.turtleL);
	   this.left = true;
	   Thread t = new Thread(() -> {
		   while (this.left) {
			   this.x -= SPEED;
			   this.setLocation(this.x, this.y);
			   
			   try {
				   Thread.sleep(10L);
			   } catch (Exception e2) {
				   System.out.println("왼쪽 이동 중 인터럽트 발생: " + e2.getMessage());
			   }
			   
		   }
	   });
	   t.start();
   }

   public void right() {
	   System.out.println("RIGHT");
	   this.enemyDirection = EnemyDirection.RIGHT;
	   this.setIcon(turtleR);
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

   public ImageIcon getTurtleR() {
      return this.turtleR;
   }

   public ImageIcon getTurtleL() {
      return this.turtleL;
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
      if (state == 1) {
    	  this.setIcon(this.songpyeon);
    	  this.game.repaint();
      }
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

   public void setTurtleR(ImageIcon turtleR) {
      this.turtleR = turtleR;
   }

   public void setTurtleL(ImageIcon turtleL) {
      this.turtleL = turtleL;
   }
}
