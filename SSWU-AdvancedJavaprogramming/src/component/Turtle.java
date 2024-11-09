package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import direction.EnemyDirection;
import service.Moveable;

public class Turtle extends JLabel implements Moveable {
   private int x;
   private int y;
   private boolean left;
   private boolean right;
   private boolean startLeft;
   private EnemyDirection enemyDirection;
   private boolean leftCrash;
   private boolean rightCrash;
   private static final int SPEED = 2;
   
   private ImageIcon enemyR;
   private ImageIcon enemyL;

   public Turtle() {
      this.initObject();
   }

   public Turtle(int x, int y, boolean left) {
      this.initObject();
      this.initSetting(x, y, left);
   }
   
   public void start() {
	   if (startLeft) this.left();
	   else this.right();
   }

   public void initObject() {
      this.enemyL = new ImageIcon("image/turtleL.png");
      this.enemyR = new ImageIcon("image/turtleR.png");
   }
   
   // y 좌표를 토끼보다 5 크게 설정하면 토끼와 동일한 위치에 있음
   public void initSetting(int x, int y, boolean left) {
      this.x = x;
      this.y = y;
      this.startLeft = left;
      if (startLeft) {
         this.setIcon(this.enemyL);
      } else {
         this.setIcon(this.enemyR);
      }

      this.setSize(50, 50);
      this.setLocation(this.x, this.y);
   }

   public void up() {
   }

   public void down() {
   }

   public void left() {
	   System.out.println("LEFT");
	   this.enemyDirection = EnemyDirection.LEFT;
	   this.setIcon(this.enemyL);
	   this.left = true;
	   Thread t = new Thread(() -> {
		   while (this.left) {
			   this.x -= 3;
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
	   this.setIcon(enemyR);
	   this.right = true;
	   Thread t = new Thread(() -> {
		   while(this.right) {
			   this.x += 3;
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
}
