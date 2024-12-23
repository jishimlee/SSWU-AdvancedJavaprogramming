package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.*;
import direction.PlayerDirection;
import stage.*;
import score.*;
import life.*;

public class MoonRabbitGame extends JFrame {
	private int stageNumber = 5;	// 1~5, 시작 전후 화면은 별도의 번호로 설정하도록 함 -> 다음 스테이지로 넘어갈 때 이 Number도 업데이트 해줘야 됨
	private CardLayout cardLayout;
	private JPanel stagePanel;
	private PlayerRabbit player;
	private ThrowHammer hammer;
	private JPanel currentStage;
	private MoonRabbitGame game = MoonRabbitGame.this;
	private Score score;
	private Life life;
	public boolean enemyAttacked = false;

	public MoonRabbitGame() {
		initLayout();
		showGameIntro(); // 게임 설명 화면 표시
        initListener();
        this.setVisible(true);
        this.score = new Score(this.game);
        this.life = new Life(this.game);
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
	
	private void showGameIntro() {
        GameIntro introPanel = new GameIntro(() -> {
            // 설명 종료 후 첫 번째 스테이지 로드
            loadStage(stageNumber);
        });
        stagePanel.add(introPanel, "Intro");
        cardLayout.show(stagePanel, "Intro");
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
	            this.currentStage = stage3;
	            break;
	        case 4:
	            Stage4 stage4 = new Stage4(this); // Stage4 로드, 실험위해 stage4로 함
	            this.player = stage4.getPlayer(); // player를 가져옴
	            stagePanel.add(stage4, "Stage4");
	            this.currentStage = stage4;
	            break;
	        case 5:
	            Stage5 stage5 = new Stage5(this); // Stage5 로드, 실험위해 stage5로 함
	            this.player = stage5.getPlayer(); // player를 가져옴
	            stagePanel.add(stage5, "Stage5");
	            this.currentStage = stage5;
	            break;
	      
	        //default:
	            //JOptionPane.showMessageDialog(null, "준비된 스테이지가 없습니다!");
	            //break;
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
	                	if (!player.isLeft()) {
	                        if (player.isReversedControls()) { // 반전 상태 확인
	                            player.right(); // 반전 상태라면 오른쪽으로 이동
	                        } else {
	                            player.left(); // 일반 상태라면 왼쪽으로 이동
	                        }
	                    }
	                	
	                    break;
	                case KeyEvent.VK_RIGHT: 
	                	if (!player.isRight()) {
	                        if (player.isReversedControls()) { // 반전 상태 확인
	                            player.left(); // 반전 상태라면 왼쪽으로 이동
	                        } else {
	                            player.right(); // 일반 상태라면 오른쪽으로 이동
	                        }
	                    }
	                    break;
	                case KeyEvent.VK_UP: 
	                	if(!player.isUp()&&!player.isDown()) {
	                		player.up();
	                	}
	                	break;
	                case KeyEvent.VK_S:
	                	player.spacePressed = true;
	                    player.updateAttackState();
	                    break;
	                case KeyEvent.VK_A:
	                       player.setAPressed(true);
	                       player.updateThrowAttackState();
	                       
	                       if (currentStage instanceof Stage1) {
	                           ((Stage1) currentStage).loadHammerIcon(); // Stage1의 loadHammerIcon 호출
	                       } else if (currentStage instanceof Stage2) {
	                           ((Stage2) currentStage).loadHammerIcon(); // Stage2의 loadHammerIcon 호출
	                       } else if (currentStage instanceof Stage3) {
	                           ((Stage3) currentStage).loadHammerIcon(); // Stage3의 loadHammerIcon 호출
	                       } else if (currentStage instanceof Stage4) {
	                           ((Stage4) currentStage).loadHammerIcon(); // Stage4의 loadHammerIcon 호출
	                       } else {
	                           ((Stage5) currentStage).loadHammerIcon(); // Stage5의 loadHammerIcon 호출
	                       }
	                       
	                       
	                       break;
	            }
	        }
	        
	        @Override
	        public void keyReleased(KeyEvent e) {  // 메서드 이름 수정
	            
	        	int keyCode = e.getKeyCode();
	        	if (keyCode == KeyEvent.VK_LEFT) {
	                if (player.isReversedControls()) {  // Reverse 상태일 때
	                    player.setRight(false);  // 반대 방향인 오른쪽으로 멈추기
	                } else {
	                    player.setLeft(false);  // 일반 상태일 때 왼쪽으로 멈추기
	                }
	            } else if (keyCode == KeyEvent.VK_RIGHT) {
	                if (player.isReversedControls()) {  // Reverse 상태일 때
	                    player.setLeft(false);  // 반대 방향인 왼쪽으로 멈추기
	                } else {
	                    player.setRight(false);  // 일반 상태일 때 오른쪽으로 멈추기
	                }
	            }
	        	
                 else if (keyCode == KeyEvent.VK_UP) {
                    //up = false;  // 점프 멈추기
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    //down = false;  // 내려가기 멈추기
                }
	        }
	    });
	}
	    
	    public void checkStageCompletion() {
		    // 현재 스테이지의 모든 적이 상태 2인지 확인
		    JPanel currentStage = getCurrentStage();
		    if (currentStage instanceof Stage1) {
		        Stage1 stage = (Stage1) currentStage;
		        if (stage.areAllEnemiesDefeated()) {
		            System.out.println("모든 적이 처치되었습니다. 다음 스테이지로 이동합니다.");
		            stage.stopTimer();  // 타이머 종료
		            stage.stopBGM();
		            nextStage();
		        }
		        }
		    
		        
		        // Stage2, Stage3 등 다른 스테이지에 대해 동일한 확인 가능
			    if (currentStage instanceof Stage2) {
			        Stage2 stage = (Stage2) currentStage;
			        if (stage.areAllEnemiesDefeated()) {
			            System.out.println("모든 적이 처치되었습니다. 다음 스테이지로 이동합니다.");
			            stage.stopTimer();  // 타이머 종료
			            stage.stopBGM();
			            nextStage();
			        }
			    }
			    if (currentStage instanceof Stage3) {
			        Stage3 stage = (Stage3) currentStage;
			        if (stage.areAllEnemiesDefeated()) {
			            System.out.println("모든 적이 처치되었습니다. 다음 스테이지로 이동합니다.");
			            stage.stopTimer();  // 타이머 종료
			            stage.stopBGM();
			            nextStage();
			        }
			    }
			    if (currentStage instanceof Stage4) {
			        Stage4 stage = (Stage4) currentStage;
			        if (stage.areAllEnemiesDefeated()) {
			            System.out.println("모든 적이 처치되었습니다. 다음 스테이지로 이동합니다.");
			            stage.stopTimer();  // 타이머 종료
			            stage.stopBGM();
			            nextStage();
			        }
			    }
			    if (currentStage instanceof Stage5) {
			        Stage5 stage = (Stage5) currentStage;
			        System.out.println("현재 스테이지는 Stage5."); // 로그 추가
			        if (stage.areAllEnemiesDefeated()) {
			            System.out.println("모든 적이 처치되었습니다. 스테이지 클리어.");
			            
			            //스테이지 클리어 화면 띄우기
			            System.out.println("Game Clear 호출 중...");
			            stage.stopTimer();  // 타이머 종료
			            stage.stopBGM();
			            showGameClearScreen();
			        }
			    }
			    
	    }
	    
	    private void showGameClearScreen() {
	        SwingUtilities.invokeLater(() -> {
	            this.getContentPane().removeAll(); // 기존 화면 제거
	            this.getContentPane().add(new GameClear()); // GameClear 패널 추가
	            this.revalidate(); // UI 갱신
	            this.repaint();    // 화면 다시 그리기
	            System.out.println("Game Clear 화면 표시 완료");
	        });
	    }
	    public void showGameOverScreen() {
	        // JFrame의 ContentPane을 사용해 화면 교체
	        SwingUtilities.invokeLater(() -> {
	            this.getContentPane().removeAll(); // 기존 화면 제거
	            this.getContentPane().add(new GameOver()); // GameOver 패널 추가
	            this.revalidate(); // UI 갱신
	            this.repaint();    // 화면 다시 그리기
	            System.out.println("Game Over 화면 표시 완료");
	        });
	    }

	    
	public Life getLife() {
	    if (life == null) {
	        life = new Life(this.game); // null 상태라면 새로 생성
	        System.out.println("Life 생성");
	    }
	    return life;
	}
	
	public Score getScore() {
	    if (score == null) {
	        score = new Score(this.game); // null 상태라면 새로 생성
	        System.out.println("Score 생성");
	    }
	    return score;
	}


	 public void resetGame() {
	        score.resetScore();  // 게임 종료 시 점수 리셋
	        life.resetLife();    // 목숨 리셋 (Life 객체에서 처리)
	        System.out.println("게임이 종료되었습니다. 최종 점수: " + score.getScore());
	}
	 
	public JPanel getCurrentStage() {
	    return this.currentStage;
	}
	
	public int getStageNumber() {
		return stageNumber;
	}

	public void nextStage() {
		System.out.println("Moving to the next stage. Current stage: " + stageNumber);
        stageNumber++;
        loadStage(stageNumber);
        System.out.println("Next stage loaded. New stage: " + stageNumber);
    }

	 
	public static void main(String[] args) {
	      new MoonRabbitGame();
	 }
} 
