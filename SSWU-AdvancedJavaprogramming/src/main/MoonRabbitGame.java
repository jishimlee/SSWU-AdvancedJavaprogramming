package main;

import java.awt.CardLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
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
	private int stageNumber = 2;	// 1~5, 시작 전후 화면은 별도의 번호로 설정하도록 함 -> 다음 스테이지로 넘어갈 때 이 Number도 업데이트 해줘야 됨
	private CardLayout cardLayout;
	private JPanel stagePanel;
	private PlayerRabbit player;
	private JPanel currentStage;
	
	public MoonRabbitGame() {
		initLayout();
        loadStage(stageNumber);
        initListener();
        this.setVisible(true);
	}
	
	private void initLayout() {
        this.setTitle("달토끼전");
        this.setSize(1010, 670);
        // this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        stagePanel = new JPanel(cardLayout);
        this.setContentPane(stagePanel);
    }
	
	private void loadStage(int stageNumber) {
	    switch (stageNumber) {
	        case 1:
	        	this.stageNumber = 1;
	            Stage1 stage1 = new Stage1(this); // Stage1 로드, 실험위해 stage2로 함
	            this.player = stage1.getPlayer(); // player를 가져옴
	            stagePanel.add(stage1, "Stage1");
	            this.currentStage = stage1;
	            break;
	        case 2:
	        	this.stageNumber = 2;
	            Stage2 stage2 = new Stage2(this); // Stage2 로드, 실험위해 stage2로 함
	            this.player = stage2.getPlayer(); // player를 가져옴
	            stagePanel.add(stage2, "Stage2");
	            this.currentStage = stage2;
	            break;
	        case 3:
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
	
	private void initListener() {
	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            // System.out.println(e.getKeyCode());
	            
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
	                	
	                case KeyEvent.VK_SPACE:
	                	player.spacePressed = true;
	                    player.updateAttackState();
	                    break;
	                case KeyEvent.VK_A:
	                	player.setAPressed(true);
	                    player.updateThrowAttackState();
	                    ThrowHammer throwhammer = new ThrowHammer(player);
	                    add(throwhammer);
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
	
	public JPanel getCurrentStage() {
	    return this.currentStage;
	}
	
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
