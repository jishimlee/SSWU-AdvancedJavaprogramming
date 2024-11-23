package component;

import java.awt.Image;


import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import direction.PlayerDirection;
import main.MoonRabbitGame;
import stage.*;

public class ThrowHammer extends JLabel {
	// 위치 상태
	private BufferedImage img;
	private int x;
	private int y;
	private PlayerDirection direction;
	private PlayerRabbit player; // 의존성 컴포지션
	private MoonRabbitGame game;
	private int stageNumber;
	private JPanel stage;
	private String backgroundPath;
	
	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	
	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	
	// 속도 상태
	private final int SPEED = 4;
	private final int JUMPSPEED = 4;
	
	// 이미지
	private ImageIcon hammerL;
	private ImageIcon hammerR;
	private ImageIcon throwL;
	private ImageIcon throwR;
	private ImageIcon bomb; // 적이 공격 당함
	private boolean isThrown = false; 
	private int hammerCount;
	
	// 적군을 맞춘 상태
	private int state; // 0 - 날아가는 떡방아, 1 - 적에 맞은 떡방아
	private BufferedImage image;
	private boolean isThreadRunning = false;
	public boolean spacePressed = false;
	private static boolean APressed = false;
	private boolean isThrowing = false;

	public ThrowHammer(MoonRabbitGame game, PlayerRabbit player) {
		this.player = player;
		this.game = game;
		initObject();
		initSetting();
		hammerCount = 0;
	}
		

		public void initObject() {
			this.hammerL = new ImageIcon("image/hammerL.png");
			this.hammerR = new ImageIcon("image/hammerR.png");
			this.stage = game.getCurrentStage();
			this.stageNumber = game.getStageNumber();
			try {
				if (stageNumber == 1) backgroundPath = "image/background1.png";
				else if (stageNumber == 2) backgroundPath = "image/background2.png";
				else if (stageNumber == 3) backgroundPath = "image/background3.png";
				else if (stageNumber == 4) backgroundPath = "image/background4.png";
				else if (stageNumber == 5) backgroundPath = "image/background5.png";
				
				img = ImageIO.read(new File(backgroundPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void initSetting() {
			this.left = false;
			this.right = false;
			
			this.y = player.getY() + 20;
			if (player.getDirection() == PlayerDirection.LEFT) {
				this.x = player.getX() - 20;
	            this.setIcon(hammerL);
	            throwLeft();
	        } else {
	        	this.x = player.getX() + 20;
	            this.setIcon(hammerR);
	            throwRight();
	        }
			this.setSize(34,26);
			this.state = 0;
		}
		
		private void setThrowAttackIcon() {
		    if(APressed) {
		    	if (player.getDirection() == PlayerDirection.LEFT) {
		    	    this.x = player.getX() - 20;
		    	    this.setIcon(hammerL);
		    	    this.left = true;
		    	} else {
		    	    this.x = player.getX() + 20;
		    	    this.setIcon(hammerR);
		    	    this.right = true;
		    	}

		    }
		}


		public void throwRight() {
	          this.right = true;
	    	  this.setIcon(hammerR);
	          
	          new Thread(() -> {
	              try {
	            	  System.out.println("오른쪽 공격 시작");
	                  while (this.x < 1000) {  // 예시: 1000px을 한계로 설정
	                	  System.out.println(this.x);
	                	  this.x += SPEED * 2;  // 캐릭터 속도의 2배로 설정
	                      this.setLocation(this.x, this.y);
	                      this.repaint();
	                      Thread.sleep(10);  // 이동 속도 제어
	                      
	                      // 충돌 감지: 적 또는 벽
	                      /*if (detectCollision()) {
	                    	  System.out.println("벽이나 적과 충돌!");
	                          Thread.sleep(30); // 1초 대기
	                          repaint();
	                          break;
	                      }*/
	                  }
	              } catch (Exception e) {
	                  e.printStackTrace();
	              }
	          }).start();
	      }


	      public void throwLeft() {
	          this.left = true;
	          this.setIcon(hammerL);
	          
	          new Thread(() -> {
	              try {
	            	  System.out.println("왼쪽 공격");
	                  while (this.x > 0) {  // 화면 좌측 한계로 설정
	                	  System.out.println(this.x);
	                	  this.x -= SPEED * 2;  // 캐릭터 속도의 2배로 설정
	                      this.setLocation(this.x, this.y);
	                      this.repaint();
	                      Thread.sleep(10);  // 이동 속도 제어

	                      // 충돌 감지: 적 또는 벽
	                      if (this.x == 0) {
	                    	  System.out.println("벽이나 적과 충돌!");
	                          Thread.sleep(30); // 1초 대기
	                          repaint();
	                          break;
	                      }
	                  }
	              } catch (Exception e) {
	                  e.printStackTrace();
	              }
	          }).start();
	      }
	      
	      private boolean detectCollision() {
	          try {
	              // 충돌 색상 또는 적 객체 확인 로직 추가
	              int objectColor = this.image.getRGB(this.x, this.y); 
	              return objectColor != -1;  // 벽 또는 적에 충돌 시
	          } catch (Exception e) {
	              return false;  // 오류 시 충돌 아님으로 처리
	          }
	      }
		
	     
		public PlayerRabbit getPlayer() {
			return player;
		}


		public void setPlayer(PlayerRabbit player) {
			this.player = player;
		}


		public ImageIcon getBomb() {
			return bomb;
		}


		public void setBomb(ImageIcon bomb) {
			this.bomb = bomb;
		}


		public BufferedImage getImage() {
			return image;
		}


		public void setImage(BufferedImage image) {
			this.image = image;
		}


		public boolean isThrowing() {
			return isThrowing;
		}


		public void setThrowing(boolean isThrowing) {
			this.isThrowing = isThrowing;
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
		
		public boolean isThreadRunning() {
			return isThreadRunning;
		}
		public void setThreadRunning(boolean isThreadRunning) {
			this.isThreadRunning = isThreadRunning;
		}
		public boolean isSpacePressed() {
			return spacePressed;
		}
		public void setSpacePressed(boolean spacePressed) {
			this.spacePressed = spacePressed;
		}
		public boolean isAPressed() {
			return APressed;
		}
		public static void setAPressed(boolean aPressed) {
			APressed = aPressed;
		}
		public int getSPEED() {
			return SPEED;
		}
		public int getJUMPSPEED() {
			return JUMPSPEED;
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

		public ImageIcon getThrowL() {
			return throwL;
		}

		public void setThrowL(ImageIcon throwL) {
			this.throwL = throwL;
		}

		public ImageIcon getThrowR() {
			return throwR;
		}

		public void setThrowR(ImageIcon throwR) {
			this.throwR = throwR;
		}

		public boolean isThrown() {
			return isThrown;
		}

		public void setThrown(boolean isThrown) {
			this.isThrown = isThrown;
		}

		public int getHammerCount() {
			return hammerCount;
		}

		public void setHammerCount(int hammerCount) {
			this.hammerCount = hammerCount;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
}