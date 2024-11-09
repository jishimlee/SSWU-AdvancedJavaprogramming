package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import direction.EnemyDirection;
import service.BackgroundTurtleService;
import service.Moveable;

public class Turtle extends JLabel implements Moveable {
   private int x;
   private int y;
   private boolean left;
   private boolean right;
   private boolean startLeft;
   private int state;	// 공격 당했는지 확인, 0은 공격 X, 1은 공격 당함
   private EnemyDirection enemyDirection;
   private boolean leftCrash;
   private boolean rightCrash;
   private static final int SPEED = 2;
   
   private ImageIcon turtleR;
   private ImageIcon turtleL;

   public Turtle() {
      this.initObject();
   }

   public Turtle(int x, int y, boolean left) {
      this.initObject();
      this.initSetting(x, y, left);
   }
   
   public void start() {
	   this.state = 0;
	   if (startLeft) this.left();
	   else this.right();
   }

   public void initObject() {
      this.turtleL = new ImageIcon("image/turtleL.png");
      this.turtleR = new ImageIcon("image/turtleR.png");
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
   
   private void initBackgroundEnemyService() {
	      (new Thread(new BackgroundTurtleService(this))).start();
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
				   System.out.println("\uc67c\ucabd \uc774\ub3d9\uc911 \uc778\ud130\ub7fd\ud2b8 \ubc1c\uc0dd : " + e2.getMessage());
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
	               System.out.println("\uc624\ub978\ucabd \uc774\ub3d9\uc911 \uc778\ud130\ub7fd\ud2b8 \ubc1c\uc0dd : " + e2.getMessage());
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
