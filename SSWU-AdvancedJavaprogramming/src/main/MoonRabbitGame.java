package main;
// = bubblegame.class
//import component.Frame1;


import java.awt.CardLayout;

import java.awt.Component;

import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Turtle;
import component.Toad;
import component.WildBoar;
import component.Monkey;


import stage.Stage1;
import stage.Stage2;
import stage.Stage3;
import stage.Stage4;
import stage.Stage5;

public class MoonRabbitGame extends JFrame {
	private int stageNumber;	// 1~5, 시작 전후 화면은 별도의 번호로 설정하도록 함 -> 다음 스테이지로 넘어갈 때 이 Number도 업데이트 해줘야 됨
	//private JLabel frontMap;
	private CardLayout cardLayout;
	private JPanel stagePanel;
	private PlayerRabbit player;
	//private Turtle turtle;
	//private JLabel moonLabel;
	//private JLabel heartLabel;
	
	public MoonRabbitGame() {
		this.stageNumber = 1;
		initLayout();
        loadStage(stageNumber);
        initListener();
        this.setVisible(true);
		
		/*this.initObject();
	    this.initSetting();
	    this.initListener();
	    this.initThread();
	    this.setVisible(true);*/
	}
	
	private void initLayout() {
        this.setTitle("달토끼전");
        this.setSize(1010, 670);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        stagePanel = new JPanel(cardLayout);
        this.setContentPane(stagePanel);
    }
	
	private void loadStage(int stageNumber) {
	    switch (stageNumber) {
	        case 1:
	            Stage3 stage3 = new Stage3(this); // Stage3 로드, 실험위해 stage3로 함
	            this.player = stage3.getPlayer(); // player를 가져옴
	            stagePanel.add(stage3, "Stage3");
	            break;
	        // 이후 다른 스테이지 추가
	        default:
	            JOptionPane.showMessageDialog(null, "준비된 스테이지가 없습니다!");
	            break;
	    }
	    cardLayout.show(stagePanel, "Stage" + stageNumber);
	}
	
		/*private void initObject() {
	    this.frontMap = new JLabel(new ImageIcon("image/stage1.png"));
	    this.setContentPane(this.frontMap);
	    this.setLayout((LayoutManager)null);
	    this.player = new PlayerRabbit();
	    this.turtle = new Turtle(200, 230, false, this);*/
	    
	    /*
	     * this.moonLabel = new JLabel(new ImageIcon("image/moon1.png"));
	     * this.moonLabel.setLocation(480, 40);
	     * this.moonLabel.setSize(50, 50);
	     * this.frontMap.add(this.moonLabel);
	     */
	    
	     /*this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
	     this.heartLabel.setLocation(50, 40);
	     this.heartLabel.setSize(50, 50);
	     this.frontMap.add(this.heartLabel);*/
	      
	    
	    /* moon1_30, moon1_40, moon5_40 변경하면서 크기 확인해보세요! */
	    /*this.moonLabel = new JLabel(new ImageIcon("image/moon5.png"));
	    this.moonLabel.setLocation(480, 40);
		this.moonLabel.setSize(50, 50);
		this.frontMap.add(this.moonLabel);
	    
	    this.frontMap.add(this.player);
	    this.frontMap.add(this.turtle);
	}*/

	/*private void initSetting() {
		this.stageNumber = 1;
		this.setTitle("달토끼전");
	    this.setSize(1010, 670);
	    this.setResizable(false);	// 사이즈 변경 불가
	    this.setLayout(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo((Component)null);
	}*/
	
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
	                /*case KeyEvent.VK_DOWN: 
	                	if(!player.isDown()) {
	                		player.down();
	                	}
	                    break;*/
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

	/*private void initThread() {
		new Thread(()->{
			turtle.start();
		}).start();
	}*/
	
	public int getStageNumber() {
		return stageNumber;
	}

	public void nextStage() {
        stageNumber++;
        loadStage(stageNumber);
    }
	
	public static void main(String[] args) {
	      new MoonRabbitGame();
	 }
} 
