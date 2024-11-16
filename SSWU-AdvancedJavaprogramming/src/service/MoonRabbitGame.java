package service;
//import component.Frame1;

import java.awt.Component; 
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import component.PlayerRabbit;
import component.Turtle;
import direction.PlayerDirection;

public class MoonRabbitGame extends JFrame {
	private JLabel frontMap;
	private PlayerRabbit player;
	private Turtle turtle;
	private JLabel moonLabel;
	//private JLabel heartLabel;
	
	public MoonRabbitGame() {
		this.initObject();
	    this.initSetting();
	    this.initListener();
	    this.initThread();
	    this.setVisible(true);
	}
	
	private void initObject() {
	    this.frontMap = new JLabel(new ImageIcon("image/stage1.png"));
	    this.setContentPane(this.frontMap);
	    this.setLayout((LayoutManager)null);
	    this.player = new PlayerRabbit();
	    this.turtle = new Turtle(200, 232, false);
	    
	    /*
	     * this.moonLabel = new JLabel(new ImageIcon("image/moon1.png"));
	     * this.moonLabel.setLocation(480, 40);
	     * this.moonLabel.setSize(50, 50);
	     * this.frontMap.add(this.moonLabel);
	     */
	    /*
	     * this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
	     * this.moonLabel.setLocation(100, 40);
	     * this.moonLabel.setSize(50, 50);
	     * this.frontMap.add(this.heartLabel);
	     */    
	    
	    /* moon1_30, moon1_40, moon5_40 변경하면서 크기 확인해보세요! */
	    this.moonLabel = new JLabel(new ImageIcon("image/moon5_40.png"));
	    this.moonLabel.setLocation(480, 40);
		this.moonLabel.setSize(50, 50);
		this.frontMap.add(this.moonLabel);
	    
	    this.frontMap.add(this.player);
	    this.frontMap.add(this.turtle);
	}

	private void initSetting() {
		this.setTitle("달토끼전");
	    this.setSize(1010, 670);
	    this.setResizable(false);	// 사이즈 변경 불가
	    this.setLayout(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo((Component)null);
	}
	
	private void initListener() {
	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            System.out.println(e.getKeyCode());
	            
	            switch(e.getKeyCode()) {
	                case KeyEvent.VK_LEFT: 
	                	if(!player.isLeft()) {
	                		player.left();
	                	}
	                    break;
	                case KeyEvent.VK_RIGHT: 
	                	if(!player.isRight()) {
	                		player.right();
	                	}
	                    break;
	                case KeyEvent.VK_UP : 
	                	if(!player.isUp()&&!player.isDown()) {
	                		player.up();
	                	}
	                    break;
	                case KeyEvent.VK_SPACE :
	                	player.spacePressed = true;
		            	player.hitAttack();
		            	break;
	                case KeyEvent.VK_A :
	                	player.setAPressed(true);
	                	player.throwAttack();
		            	break;
	            }
	        }
	        
	        @Override
	        public void keyReleased(KeyEvent e) {  // 메서드 이름 수정
	            
	        	int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                	player.setLeft(false); // 왼쪽 이동 멈추기
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                	player.setRight(false);  // 오른쪽 이동 멈추기
                } else if (keyCode == KeyEvent.VK_A) {
                	
                }
	        }
	    });
	}

	private void initThread() {
		new Thread(()->{
			turtle.start();
		}).start();
	}
	
	public static void main(String[] args) {
	      new MoonRabbitGame();
	 }
} 
