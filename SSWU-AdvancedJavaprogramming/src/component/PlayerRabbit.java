package component;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.stream.events.StartDocument;
import direction.PlayerDirection;
import service.Moveable;
import service.BackgroundRabbitService;

public class PlayerRabbit extends JLabel implements Moveable {
   // 위치 상태
   private int x;
   private int y;
   private PlayerDirection direction;
   // 움직임 상태
   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   // 벽에 충돌한 상태
   private boolean leftWallCrash;
   private boolean rightWallCrash;
   // 속도 상태
   private final int SPEED = 4;
   private final int JUMPSPEED = 4;
   
   // 이미지
   private ImageIcon playerR;
   private ImageIcon playerL;

   private ImageIcon rabbitThrowL;
   private ImageIcon rabbitThrowR;
   // 공격 이미지
   private ImageIcon hitplayerR;
   private ImageIcon hitplayerL;
   private ImageIcon hammerL;
   private ImageIcon hammerR;
   private boolean isThreadRunning = false;
   public boolean spacePressed = false;
   private boolean APressed = false;
   private int high;
   // 목숨 
   private int life;
   private boolean rabbitAttacked;



   public PlayerRabbit() {
         this.initObject();
         this.initSetting();
         this.initBackgroundRabbitService();
   }
   
   private void initObject() {
        this.playerR = new ImageIcon("image/rabbitR.png");
        this.playerL = new ImageIcon("image/rabbitL.png");
        this.hitplayerL = new ImageIcon("image/rabbitHitL.png");
        this.hitplayerR = new ImageIcon("image/rabbitHitR.png");
        this.rabbitThrowL = new ImageIcon("image/rabbitThrowL.png");
        this.rabbitThrowR = new ImageIcon("image/rabbitThrowL.png");
        this.hammerL = new ImageIcon("image/hammerL.png");
        this.hammerR = new ImageIcon("image/hammerR.png");
    }
   private void initSetting() {
         this.x = 45;
         this.y = 555;
         
         this.left = false;
         this.right = false;
         this.up = false;
         this.down = false;
       
         this.leftWallCrash = false;
         this.rightWallCrash = false;
         
         this.life = 3;
         this.rabbitAttacked = false;
      
         this.setIcon(this.playerR);
         this.setSize(30, 50);
         this.setLocation(this.x, this.y);
   }
   
   
   public void hitAttackThread() {
      spacePressed = true;
      
      if(left) {
         setIcon(hitplayerL);
         this.setSize(39, 50);
      }
      else {
         setIcon(hitplayerR);
         this.setSize(39, 50);
      }
      spacePressed = false;
   }
   
   public void throwAttack() {
      APressed = true;
      if(left) {
         setIcon(rabbitThrowL);
      }
      else {
         setIcon(rabbitThrowR);
      }
      APressed = false;
   }
   
   @Override
   public void up() {
      up =true;
      
      new Thread(() -> {
         for(int i= 0; i<130/JUMPSPEED;i++) {
            y -= JUMPSPEED;
            setLocation(x,y);
            try {
               Thread.sleep(10);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
         up= false;
         down();
      }).start();
   }
   
   @Override
   public void down() {
      down =true;
      
      new Thread(() -> {
         while(down) {
            y += JUMPSPEED;
            setLocation(x,y);
            try {
               Thread.sleep(15);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
         down = false;
      }).start();
      
   }
   
   @Override
   public void left() {
      left = true;
     
      new Thread(()-> {
         while(left) {
            setIcon(playerL);
            x = x - SPEED;
            setLocation(x,y);
            try {
               Thread.sleep(10);
            } catch (InterruptedException e) {
                  e.printStackTrace();
            }
         }
      }).start();
   }
   
   @Override
   public void right() {
      right = true;
      
      new Thread(()-> {
         while(right) {
            setIcon(playerR);
            x += SPEED;
            setLocation(x,y);
            try {
               Thread.sleep(10);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }
   
   public void RabbitAttacked() {
      // 공격 상태가 아닐 때 적과 부딫히거나 바나나를 밟으면 목숨을 잃는다. 
      // 적의 색깔은 어떻게 처리할지가 필요한 거 같음 
      // 토끼가 공격당했을 때, 목숨을 하나 깎는다. 
      if (rabbitAttacked) {
         life--;
      }
   }
   
   public boolean RabbitDead() {
      // 목숨이 0이면 토끼가 죽었다는 뜻이므로 true를 return 한다. 
      if(life == 0) {
         return true;
      }
      else return false;
   }
   

   private void initBackgroundRabbitService() {
      new Thread(new BackgroundRabbitService(this)).start();
   }
   
   public boolean isSpacePressed() {
         return spacePressed;
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


   public PlayerDirection getDirection() {
      return direction;
   }


   public void setDirection(PlayerDirection direction) {
      this.direction = direction;
   }


   public boolean isLeft() {
      return left;
   }


   public void setLeft(boolean left) {
      this.left = left;
   }


   public boolean isRight() {
      return right;
   }


   public void setRight(boolean right) {
      this.right = right;
   }


   public boolean isUp() {
      return up;
   }
   
   
   public void setUp(boolean up) {
      this.up = up;
   }
   
   
   public boolean isDown() {
      return down;
   }
   
   
   public void setDown(boolean down) {
      this.down = down;
   }
   
   
   public boolean isLeftWallCrash() {
      return leftWallCrash;
   }
   
   
   public void setLeftWallCrash(boolean leftWallCrash) {
      this.leftWallCrash = leftWallCrash;
   }
   
   
   public boolean isRightWallCrash() {
      return rightWallCrash;
   }
   
   
   public void setRightWallCrash(boolean rightWallCrash) {
      this.rightWallCrash = rightWallCrash;
   }
   
   
   public ImageIcon getPlayerR() {
      return playerR;
   }
   
   
   public void setPlayerR(ImageIcon playerR) {
      this.playerR = playerR;
   }
   
   
   public ImageIcon getPlayerL() {
      return playerL;
   }
   
   
   public void setPlayerL(ImageIcon playerL) {
      this.playerL = playerL;
   }
   
   
   public ImageIcon getRabbitThrowL() {
      return rabbitThrowL;
   }
   
   
   public void setRabbitThrowL(ImageIcon rabbitThrowL) {
      this.rabbitThrowL = rabbitThrowL;
   }
   
   
   public ImageIcon getRabbitThrowR() {
      return rabbitThrowR;
   }
   
   
   public void setRabbitThrowR(ImageIcon rabbitThrowR) {
      this.rabbitThrowR = rabbitThrowR;
   }
   
   
   public ImageIcon getHitplayerR() {
      return hitplayerR;
   }
   
   
   public void setHitplayerR(ImageIcon hitplayerR) {
      this.hitplayerR = hitplayerR;
   }
   
   
   public ImageIcon getHitplayerL() {
      return hitplayerL;
   }
   
   
   public void setHitplayerL(ImageIcon hitplayerL) {
      this.hitplayerL = hitplayerL;
   }
   
   
   public ImageIcon getHammerL() {
      return hammerL;
   }
   
   
   public void setHammerL(ImageIcon hammerL) {
      this.hammerL = hammerL;
   }
   
   
   public ImageIcon getHammerR() {
      return hammerR;
   }
   
   
   public void setHammerR(ImageIcon hammerR) {
      this.hammerR = hammerR;
   }
   
   
   public boolean isThreadRunning() {
      return isThreadRunning;
   }
   
   
   public void setThreadRunning(boolean isThreadRunning) {
      this.isThreadRunning = isThreadRunning;
   }
   
   
   public boolean isAPressed() {
      return APressed;
   }
   
   
   public void setAPressed(boolean aPressed) {
      APressed = aPressed;
   }
   
   
   public int getHigh() {
      return high;
   }
   
   
   public void setHigh(int high) {
      this.high = high;
   }
   
   
   public int getLife() {
      return life;
   }
   
   
   public void setLife(int life) {
      this.life = life;
   }
   
   
   public boolean isRabbitAttacked() {
      return rabbitAttacked;
   }
   
   
   public void setRabbitAttacked(boolean rabbitAttacked) {
      this.rabbitAttacked = rabbitAttacked;
   }
   
   
   public int getSPEED() {
      return SPEED;
   }
   
   
   public int getJUMPSPEED() {
      return JUMPSPEED;
   }
   
   
   public void setSpacePressed(boolean spacePressed) {
      this.spacePressed = spacePressed;
   }

}
