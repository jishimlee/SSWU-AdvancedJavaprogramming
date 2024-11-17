package component;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import direction.PlayerDirection;

public class ThrowHammer extends JLabel {
	// 위치 상태
		private int x;
		private int y;
		private PlayerDirection direction;
		private PlayerRabbit player; // 의존성 컴포지션
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
		
		public ThrowHammer(PlayerRabbit player) {
			this.player = player;
			initObject();
			initSetting();
			hammerCount = 0;
		}
		

		public void initObject() {
			hammerL = new ImageIcon("image/hammerL.png");
			hammerR = new ImageIcon("image/hammerR.png");
		}
		
		public void initSetting() {
			this.left = false;
			this.right = false;
			this.up = false;
			
			this.y = player.getY() + 20;
			if (player.getDirection() == PlayerDirection.LEFT) {
				this.x = player.getX() - 20;
	            this.setIcon(hammerL);
	            this.left = true;
	        } else {
	        	this.x = player.getX() + 20;
	            this.setIcon(hammerR);
	            this.right = true;
	        }
			this.setSize(34,26);
			this.state = 0;
		}
		private void setThrowAttackIcon() {
		    if(APressed) {
		    	if (this.left) {
			        setIcon(hammerL);
			    } else {
			        setIcon(hammerR);
			    }
			    setSize(34, 26);
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


		public void throwRight() {
	        this.right = true;  // 오른쪽으로 던지는 상태
	        new Thread(() -> {
	            try {
	                for (int i = 0; i < 300; ++i) {  // 300칸 정도 이동
	                    this.setLocation(this.x, this.y);
	                    ++this.x;  // 오른쪽으로 이동
	                    Thread.sleep(3L);  // 속도 설정
	                    // 충돌 감지 (오른쪽)
	                    int rightColor = this.image.getRGB(this.x + 50 + 10, this.y + 25);
	                    if (rightColor != -1) {  // 벽에 충돌하면 멈춤
	                        break;
	                    }
	                }

	                //this.shootUp();  // 오른쪽 던진 후 상승 시작
	            } catch (Exception var3) {
	                System.out.println(var3.getMessage());
	            }

	        }).start();
	    }

	    public void throwLeft() {
	        this.left = true;  // 왼쪽으로 던지는 상태
	        new Thread(() -> {
	            try {
	                for (int i = 0; i < 300; ++i) {  // 300칸 정도 이동
	                    this.setLocation(this.x, this.y);
	                    --this.x;  // 왼쪽으로 이동
	                    Thread.sleep(3L);  // 속도 설정
	                    // 충돌 감지 (왼쪽)
	                    int leftColor = this.image.getRGB(this.x - 10, this.y + 25);
	                    if (leftColor != -1) {  // 벽에 충돌하면 멈춤
	                        break;
	                    }
	                }

	                //this.shootUp();  // 왼쪽 던진 후 상승 시작
	            } catch (Exception var3) {
	                System.out.println(var3.getMessage());
	            }

	        }).start();
	    }
		/*public void throwLeft() { // 벽에 충돌하지 않으면 왼쪽으로 이동
		        this.left = true;
		        new Thread(() -> {
		            try {
		                //this.setIcon(hammerL); // 이동 중 아이콘 유지
		                for (int i = 0; i < 400; i++) {
		                    this.setLocation(x, y);
		                    --this.x;
		                    Thread.sleep(20);
		                    int leftColor = this.image.getRGB(this.x - 10, this.y + 25);
		                    if (leftColor != -1) {
		                       break;
		                    }
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            } finally {
		                this.left = false;
		            }
		        }).start();
		    }
		

		public void throwRight() { // 벽에 충돌하지 않으면 오른쪽으로 이동
		        this.right = true;
		        new Thread(() -> {
		            try {
		                //setIcon(hammerR); // 이동 중 아이콘 유지
		                for (int i = 0; i < 400; i++) {
		                	++this.x;
		                    this.setLocation(x, y);
		                    Thread.sleep(20);
		                    int rightColor = this.image.getRGB(this.x + 50 + 10, this.y + 25);
		                    if (rightColor != -1) {
		                       break;
		                    }
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            } finally {
		                this.right = false;
		            }
		        }).start();
		    }*/
		
	
		

		
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