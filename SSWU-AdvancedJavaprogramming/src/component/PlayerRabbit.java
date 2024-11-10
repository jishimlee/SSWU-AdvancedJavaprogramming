package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import direction.PlayerDirection;
import service.Moveable;

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
	private final int SPEED = 4;
	private final int JUMPSPEED = 4;
	private ImageIcon playerR;
	private ImageIcon playerL;
	
	
	public PlayerRabbit() {
	      this.initObject();
	      this.initSetting();
	}

	private void initObject() {
	     this.playerR = new ImageIcon("image/rabbitR.png");
	     this.playerL = new ImageIcon("image/rabbitL.png");
	 }
	private void initSetting() {
	      this.x = 35;
	      this.y = 555;
	      this.left = false;
	      this.right = false;
	      this.up = false;
	      this.down = false;
	      
	      this.setIcon(this.playerR);
	      this.setSize(30, 50);
	      this.setLocation(this.x, this.y);
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
			for(int i= 0; i<130/JUMPSPEED;i++) {
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
				x -=SPEED;
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



