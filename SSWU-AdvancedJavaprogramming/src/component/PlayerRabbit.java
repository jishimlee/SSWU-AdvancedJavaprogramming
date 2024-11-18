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
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class PlayerRabbit extends JLabel {
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
	private ThrowHammer hammer;
	private ImageIcon hammerL;
	private ImageIcon hammerR;
	private MoonRabbitGame game;
	

	private int high;
	
	private Turtle turtle;
	
	
	public PlayerRabbit(MoonRabbitGame game) {
		  this.game = game;
	      this.initObject();
	      this.initSetting();
	      this.initBackgroundRabbitService();
	}

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
	      this.y = 555;
	      
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
		
		if (left) {
	        setIcon(hitplayerL);
	        //hammer.setIcon(new ImageIcon("image/hammerL.png"));

	    } else {
	        setIcon(hitplayerR);
	        //hammer.setIcon(new ImageIcon("image/hammerR.png"));
	    }
	    setSize(39, 50);
	}
	
	private void setThrowAttackIcon() {
	    if(APressed) {
	    	if (left) {
		        setIcon(throwplayerL);
		        
		    } else {
		        setIcon(throwplayerR);
		    }
		    setSize(39, 50);
	    }
	}
	private void resetPlayerIcon() {
	    if (left) {
	        setIcon(playerL);
	    } else {
	        setIcon(playerR);
	    }
	}

	public void left() {
	    if (!this.left && !this.leftWallCrash) { // 벽에 충돌하지 않으면 왼쪽으로 이동
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
}


