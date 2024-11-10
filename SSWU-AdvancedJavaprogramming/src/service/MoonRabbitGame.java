package service;
// = bubblegame.class
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

public class MoonRabbitGame extends JFrame {
	private JLabel frontMap;
	private PlayerRabbit player;
	private Turtle turtle;
	
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
	    this.frontMap.add(this.player);
	    this.frontMap.add(this.turtle);
	}

	private void initSetting() {
	    this.setSize(1010, 670);
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
	                case KeyEvent.VK_UP: 
	                	if(!player.isUp()&&!player.isDown()) {
	                		player.up();
	                	}
	                    break;
	                case KeyEvent.VK_DOWN: 
	                	if(!player.isDown()) {
	                		player.down();
	                	}
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
                } else if (keyCode == KeyEvent.VK_UP) {
                    //up = false;  // 점프 멈추기
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    //down = false;  // 내려가기 멈추기
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
