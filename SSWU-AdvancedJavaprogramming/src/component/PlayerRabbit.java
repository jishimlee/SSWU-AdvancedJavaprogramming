package component;

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
	PlayerDirection direction;
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
	
	private ImageIcon playerR;
	private ImageIcon playerL;
	private ImageIcon hitplayerR;
	private ImageIcon hitplayerL;
	private boolean isThreadRunning = false;
	public boolean spacePressed = false;
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
            if (left) {
                setIcon(hitplayerL);
                setSize(39,50);
            }
            else if (right){
            	setIcon(hitplayerR);
                setSize(39,50);
            } else {
                setIcon(hitplayerR); 
                setSize(39, 50);
            }
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    if (left) {
                        setIcon(playerL);
                    } else if (right) {
                        setIcon(playerR);
                    }
                    spacePressed = false; // 스페이스바 눌림 상태 초기화
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

	@Override
	public void up() {
		up =true;
		
		new Thread(() -> {
			for(int i= 0; i<100/JUMPSPEED;i++) {
				y -= JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
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
		new Thread(new BackgroundRabbitService(this)).start();
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



