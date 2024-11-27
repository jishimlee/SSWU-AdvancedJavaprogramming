package component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.stream.events.StartDocument;
import direction.PlayerDirection;
import service.Moveable;
import service.BackgroundRabbitService;
import main.MoonRabbitGame;

import java.util.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class PlayerRabbit extends JLabel {
   // 위치 상태
   private int x;
   private int y;
   // 크기
//   private int width = 30;
//   private int height = 50;
   private PlayerDirection direction;
   // 움직임 상태
   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   // 벽에 충돌한 상태
   private boolean leftWallCrash;
   private boolean rightWallCrash;
   private boolean celingCrash;
   private boolean bottomCrash;
   private boolean attacked;

   // 속도 상태
   private final int SPEED = 4;
   private final int JUMPSPEED = 4;
   // 공격 상태
   private boolean hitLeft;
   private boolean hitRight;
   // 이미지
   private ImageIcon playerR;
   private ImageIcon playerL;
   // 공격 이미지
   private ImageIcon hitplayerR;
   private ImageIcon hitplayerL;
   
   private ImageIcon throwplayerL;
   private ImageIcon throwplayerR;
   
   private boolean isThreadRunning = false;
   public boolean spacePressed = false;
   private boolean APressed = false;
   
//	// 무적 상태를 관리하는 플래그
//	private boolean isInvincible;
   

	private ThrowHammer hammer;
	private ImageIcon hammerL;
	private ImageIcon hammerR;
	private MoonRabbitGame game;
   
	// 목숨 상태
	private int lives = 3; // 목숨 개수 초기값
	private List<JLabel> hearts; 
   
	private int high;
	private Turtle turtle;
	private boolean reversedControls = false;
   
   public PlayerRabbit(MoonRabbitGame game) {
        this.game = game;
         this.initObject();
         this.initSetting();
         this.initBackgroundRabbitService();
   }

//<<<<<<< HEAD
//   private void initObject() {
//        this.playerR = new ImageIcon("image/rabbitR.png");
//        this.playerL = new ImageIcon("image/rabbitL.png");
//        this.hitplayerL = new ImageIcon("image/rabbitHitL.png");
//        this.hitplayerR = new ImageIcon("image/rabbitHitR.png");
//        this.throwplayerL = new ImageIcon("image/rabbitthrowL.png");
//        this.throwplayerR = new ImageIcon("image/rabbitthrowR.png");
//        hammerL = new ImageIcon("image/hammerL.png");
//       hammerR = new ImageIcon("image/hammerR.png");
//   }
//   private void initSetting() {
//         this.x = 45;
//         this.y = 555;
//         
//         this.left = false;
//         this.right = false;
//         this.up = false;
//         this.down = false;
//         this.hitLeft = false;
//         this.hitRight = false;
//         this.leftWallCrash = false;
//         this.rightWallCrash = false;
//      
//         this.setIcon(this.playerR);
//         this.setSize(30, 50);
//         this.setLocation(this.x, this.y);
//         
//         //목숨 초기화
//         hearts = new ArrayList<>();
//         for (int i = 0; i < lives; i++) {
//             JLabel heart = new JLabel(new ImageIcon("image/heart.png"));
//             heart.setSize(30, 30);
//             heart.setLocation(10 + (i * 35), 10); // 하트 간격
//             game.add(heart); // 게임 화면에 추가
//             hearts.add(heart);
//         }
//   }
//   
//   public void updateAttackState() {
//       if (spacePressed) {
//           setAttackIcon();
//           System.out.println("Attack!");
//           new Timer().schedule(new TimerTask() {
//              
//               public void run() {
//                   resetPlayerIcon();
//                   spacePressed = false; 
//               }
//           }, 300);  
//       }
//   }
//   
//   public void updateThrowAttackState() {
//      if (APressed) {
//           setThrowAttackIcon();
//=======
	private void initObject() {
	     this.playerR = new ImageIcon("image/rabbitR.png");
	     this.playerL = new ImageIcon("image/rabbitL.png");
	     this.hitplayerL = new ImageIcon("image/rabbitHitL.png");
	     this.hitplayerR = new ImageIcon("image/rabbitHitR.png");
	     this.throwplayerL = new ImageIcon("image/rabbitthrowL.png");
	     this.throwplayerR = new ImageIcon("image/rabbitthrowR.png");
	     hammerL = new ImageIcon("image/hammerL.png");
		 hammerR = new ImageIcon("image/hammerR.png");
	}
	private void initSetting() {
	      this.x = 45;
	      this.y = 560;
	      
	      this.left = false;
	      this.right = false;
	      this.up = false;
	      this.down = false;
	      this.hitLeft = false;
	      this.hitRight = false;
	      this.leftWallCrash = false;
	      this.rightWallCrash = false;
	   
	      this.setIcon(this.playerR);
	      this.setSize(30, 50);
	      this.setLocation(this.x, this.y);
	}
	
	public void updateAttackState() {
	    if (spacePressed) {
	        setAttackIcon();
	        System.out.println("Attack!");
	        new Timer().schedule(new TimerTask() {
	           
	            public void run() {
	                resetPlayerIcon();
	                spacePressed = false; 
	            }
	        }, 300);  
	    }
	}
	
	public void updateThrowAttackState() {
		if (APressed) {
	        setThrowAttackIcon();
//>>>>>>> branch 'main' of https://github.com/jishimlee/SSWU-AdvancedJavaprogramming.git

           new Timer().schedule(new TimerTask() {
              
               public void run() {
                   resetPlayerIcon();
                   spacePressed = false; 
               }
           }, 300); 
       }
   }
   int countTime =0; 
   private void setAttackIcon() {
      if (direction == PlayerDirection.LEFT) {
           setIcon(hitplayerL);

       } else {
           setIcon(hitplayerR);
       }
       setSize(39, 50);
   }
   
   private void setThrowAttackIcon() {
       if(APressed) {
          if (direction == PlayerDirection.LEFT) {
              setIcon(throwplayerL);
              
          } else {
              setIcon(throwplayerR);
          }
          setSize(39, 50);
       }
   }
   
   private void resetPlayerIcon() {
       if (direction == PlayerDirection.LEFT) {
           setIcon(playerL);
       } else {
           setIcon(playerR);
       }
   }

   public void left() {
       if (!this.left && !this.leftWallCrash) { 
          // 벽에 충돌하지 않으면 왼쪽으로 이동
          direction = PlayerDirection.LEFT;
           left = true;
           new Thread(() -> {
               try {
                   setIcon(playerL); // 이동 중 아이콘 유지
                   for (int i = 0; i < 1010 / SPEED; i++) {
                       if (leftWallCrash) { // 벽에 충돌하면 이동 중단
                           break;
                       }
                       if(!left) break;
                       x -= SPEED;
                       
                       setLocation(x, y);
                       Thread.sleep(20);
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } finally {
                   left = false;
               }
           }).start();
       }
   }

   public void right() {
       if (!this.right && !this.rightWallCrash) { // 벽에 충돌하지 않으면 오른쪽으로 이동
          direction = PlayerDirection.RIGHT;
           right = true;
           new Thread(() -> {
               try {
                   setIcon(playerR); // 이동 중 아이콘 유지
                   for (int i = 0; i < 1010 / SPEED; i++) {
                       if (rightWallCrash) { // 벽에 충돌하면 이동 중단
                           break;
                       }
                       if(!right) break;
                       x += SPEED;
                       setLocation(x, y);
                       Thread.sleep(20);
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } finally {
                   right = false;
               }
           }).start();
       }
   }

   public void up() {
       if (!this.up && !this.down) {
          direction = PlayerDirection.UP;
           up = true;
           new Thread(() -> {
               try {
                   for (int i = 0; i < 130 / JUMPSPEED; i++) {
                       y -= JUMPSPEED;
                       setLocation(x, y);
                       Thread.sleep(10);
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } finally {
                   up = false;
                   down();  // 점프가 끝난 후에만 내려가기 시작
               }
           }).start();
       }
   }

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
      
      public boolean isReversedControls() {
    	    return reversedControls;
    	}

    	public void setReversedControls(boolean reversedControls) {
    	    this.reversedControls = reversedControls;
    	}
      
      public void loseLife() {
          if (lives > 0) {
              lives--; // 목숨 감소
              JLabel heart = hearts.remove(hearts.size() - 1); // 마지막 하트 제거
              game.remove(heart); // 게임 화면에서 하트 제거
              game.repaint(); // 화면 갱신
              if (lives == 0) {
                  System.out.println("Game Over!"); // 게임 종료 처리
                  // 추가 게임 오버 로직
              }
          }
      }
      
//      // 히트박스
//      public Rectangle getBodyHitbox() {
//          return new Rectangle(x, y, width, height);
//      }
//      
//      public Rectangle getAttackHitbox() {
//    	  // 왼쪽 공격
//    	  if (direction == PlayerDirection.LEFT)
//    		  return new Rectangle(x-50, y+10, 50, height+10);
//    	  // 오른쪽 공격
//    	  else
//    		  return new Rectangle(x+30, y, 50, height+10);
//      }

      
      public boolean isSpacePressed() {
         return spacePressed;
      }

      public void setSpacePressed(boolean spacePressed) {
         this.spacePressed = spacePressed;
      }

      public int getHigh() {
         return high;
      }

      public void setHigh(int high) { // 높이를 입력하는 코드 
         this.high = high;
      }

      

      public ImageIcon getThrowplayerL() {
         return throwplayerL;
      }

      public void setThrowplayerL(ImageIcon throwplayerL) {
         this.throwplayerL = throwplayerL;
      }

      public ImageIcon getThrowplayerR() {
         return throwplayerR;
      }

      public void setThrowplayerR(ImageIcon throwplayerR) {
         this.throwplayerR = throwplayerR;
      }

      public boolean isAPressed() {
         return APressed;
      }

      public void setAPressed(boolean aPressed) {
         APressed = aPressed;
      }

      public Turtle getTurtle() {
         return turtle;
      }

      public void setTurtle(Turtle turtle) {
         this.turtle = turtle;
      }

   public PlayerDirection getDirection() {
      return direction;
   }

   public void setDirection(PlayerDirection direction) {
      this.direction = direction;
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
   

   public boolean isCelingCrash() {
      return celingCrash;
   }

   public void setCelingCrash(boolean celingCrash) {
      this.celingCrash = celingCrash;
   }

   public boolean isBottomCrash() {
      return bottomCrash;
   }

   public void setBottomCrash(boolean bottomCrash) {
      this.bottomCrash = bottomCrash;
   }

   public boolean isAttacked() {
      return attacked;
   }

   public void setAttacked(boolean attacked) {
      this.attacked = attacked;
   }

   public ThrowHammer getHammer() {
      return hammer;
   }

   public void setHammer(ThrowHammer hammer) {
      this.hammer = hammer;
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

   public MoonRabbitGame getGame() {
      return game;
   }

   public void setGame(MoonRabbitGame game) {
      this.game = game;
   }

   public int getLives() {
      return lives;
   }

   public void setLives(int lives) {
      this.lives = lives;
   }

   public List<JLabel> getHearts() {
      return hearts;
   }

   public void setHearts(List<JLabel> hearts) {
      this.hearts = hearts;
   }

   public int getCountTime() {
      return countTime;
   }

   public void setCountTime(int countTime) {
      this.countTime = countTime;
   }

   public boolean isThreadRunning() {
      return isThreadRunning;
   }

   public void setThreadRunning(boolean isThreadRunning) {
      this.isThreadRunning = isThreadRunning;
   }

   public int getSPEED() {
      return SPEED;
   }

   public int getJUMPSPEED() {
      return JUMPSPEED;
   }

   private void initBackgroundRabbitService() {
      new Thread(new BackgroundRabbitService(this, this.game)).start();
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

   public boolean isHitLeft() {
      return hitLeft;
   }

   public void setHitLeft(boolean hitLeft) {
      this.hitLeft = hitLeft;
   }

   public boolean isHitRight() {
      return hitRight;
   }

   public void setHitRight(boolean hitRight) {
      this.hitRight = hitRight;
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
   
//   public boolean isInvincible() {
//		return isInvincible;
//	}
//
//	public void setInvincible(boolean isInvincible) {
//		this.isInvincible = isInvincible;
//	}
}