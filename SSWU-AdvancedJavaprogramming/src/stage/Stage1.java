package stage;

import javax.swing.*;

import Item.Reverse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;
import music.BGM;
import score.Score;
import life.*;

public class Stage1 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel, heartLabel2, heartLabel3;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private PlayerRabbit player;
    private ThrowHammer hammer;
    private Turtle turtle1, turtle2, turtle3, turtle4, turtle5;
    private Reverse reverseItem; // Reverse 객체 추가
    private Score score;
    private Life life;
    private int lifeCount;
    
    private BGM bgm;


	private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 30; // 남은 시간 (초 단위)

    public Stage1(MoonRabbitGame game) {
        this.game = game;
        this.player = new PlayerRabbit(this.game);
        //this.hammer = new ThrowHammer(this.game, this.player);
        
        // life 개수 받아오기
        this.life = game.getLife();
        this.lifeCount = life.getLifeCount();
        
        initObject();
        initSetting();
        initThread();
        initTimer(); 
    }

    private void initObject() {
    	//bgm 추가
    	this.bgm = new BGM(); // BGM 클래스의 생성자 호출
        bgm.play(); // BGM 재생 시작
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage1.png"));
        this.frontMap.setBounds(0, 0, 1000, 630); // 배경 이미지 크기 설정
        this.setLayout(null);
        this.add(this.frontMap); 
        this.setVisible(true);

//        this.player.setHigh(130);	// 플레이어 점프 높이 설정
//        this.player.setBounds(100, 300, 50, 50); // 플레이어 위치 및 크기 설정

        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel2 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel3 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 42, 50, 50);
        this.heartLabel2.setBounds(100, 42, 50, 50);
        this.heartLabel3.setBounds(150, 42, 50, 50);
        this.loadLifeIcon();

        this.moonLabel = new JLabel(new ImageIcon("image/moon1.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);

        // 남은 시간 표시 라벨
        this.timerLabel = new JLabel(timeRemaining + "S");
        this.timerLabel.setBounds(870, 35, 150, 50); // 위치 조정
        this.timerLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.timerLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.timerLabel);
        
        // 오브젝트 추가
        this.frontMap.add(this.player);
        
        this.scoreLabel= new JLabel("score : 0");
        this.scoreLabel.setBounds(900, 30, 150, 30);
        this.frontMap.add(this.scoreLabel);
        
     // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 500); // 위치 초기화
        this.frontMap.add(this.reverseItem);
    }
    private void updateScoreDisplay() {
        this.scoreLabel.setText("score : " + score.getScore());  // 점수 업데이트
    }
    

    private void initSetting() {
    	this.setSize(1000, 640);
        this.setPreferredSize(new Dimension(1010, 670));
        this.life.setStage(this);
        this.life.setStageNumber(1);
    }

    private void initThread() {
        SwingUtilities.invokeLater(() -> {
            // Stage1 초기화가 완료된 후에 Turtle 생성
            this.turtle1 = new Turtle(200, 230, false, this.game, this.player);
            this.turtle2 = new Turtle(400, 128, false, this.game, this.player);
            this.turtle3 = new Turtle(200, 340, false, this.game, this.player);
            this.turtle4 = new Turtle(600, 340, false, this.game, this.player);
            this.turtle5 = new Turtle(800, 454, false, this.game, this.player);
            
            this.frontMap.add(this.turtle1);
            this.frontMap.add(this.turtle2);
            this.frontMap.add(this.turtle3);
            this.frontMap.add(this.turtle4);
            this.frontMap.add(this.turtle5);
            
            new Thread(() -> turtle1.start()).start(); // Turtle 실행
            new Thread(() -> turtle2.start()).start();
            new Thread(() -> turtle3.start()).start();
            new Thread(() -> turtle4.start()).start();
            new Thread(() -> turtle5.start()).start();
        });
    }
    
    private void initTimer() {
        timer = new javax.swing.Timer(1000, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if (timeRemaining > 0) {
                     timeRemaining--;
                     timerLabel.setText(timeRemaining + "S");
                  // Reverse 아이템 상태 업데이트
                    if (reverseItem != null) {
                         reverseItem.updateObjState(player);} // Player와의 충돌 검사 및 업데이트
                     
                 } else {
                     timer.stop();
                     showGameOverImage();// 게임 오버 이미지 표시
                     game.dispose(); // 게임 창 닫기
                 }
             }
         });
         timer.start();
     }
    
    public void stopTimer() {
        if (timer != null) {
            timer.stop();  // 타이머 종료
        }
    }
    public void stopBGM() {
        // 배경 음악 종료
        if (bgm != null) {
            bgm.stop();
        }
    }
    
    private void showGameOverImage() {
    	// BGM 정지
        if (bgm != null) {
            bgm.stop(); // BGM 클래스에서 제공하는 정지 메서드 호출
        }
        // 새 JFrame을 생성하여 이미지 표시
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setSize(400, 300); // 적절한 크기로 설정
        // JLabel에 이미지 설정
        ImageIcon gameOverIcon = new ImageIcon("image/gameover.png"); // 그냥 일단 넣어봄
        JLabel gameOverLabel = new JLabel(gameOverIcon); 
        gameOverFrame.add(gameOverLabel);
        // 창의 크기를 내용물에 맞게 조정
        gameOverFrame.pack();
        gameOverFrame.setLocationRelativeTo(null); // 화면 중앙에 배치
        gameOverFrame.setVisible(true);
    }

    
    public boolean areAllEnemiesDefeated() {
        return turtle1.getState() == 2 && turtle2.getState() == 2 &&
               turtle3.getState() == 2 && turtle4.getState() == 2 &&
               turtle5.getState() == 2;
        
    }
    
    
    public void loadHammerIcon() {
        ThrowHammer throwHammer = new ThrowHammer(this.game, player);
        throwHammer.setBounds(100, 200, throwHammer.getWidth(), throwHammer.getHeight());
        this.frontMap.add(throwHammer);
        throwHammer.setVisible(true);
        this.frontMap.revalidate();
        this.frontMap.repaint();
    }
    
    public void loadLifeIcon() {
    	System.out.println("loadLifeIcon");
    	deleteAllLifeIcon();
    	this.lifeCount = life.getLifeCount();
    	System.out.println("목숨이 " + this.lifeCount + "개입니다.");
    	if (this.lifeCount == 3) {
            this.frontMap.add(this.heartLabel);
            this.frontMap.add(this.heartLabel2);
            this.frontMap.add(this.heartLabel3);
    	} else if (this.lifeCount == 2) {
            this.frontMap.add(this.heartLabel);
            this.frontMap.add(this.heartLabel2);
    	} else if (this.lifeCount == 1) {
            this.frontMap.add(this.heartLabel);
    	}
        this.frontMap.revalidate();
        this.frontMap.repaint();
    }
    
    public void deleteAllLifeIcon() {
        this.frontMap.remove(this.heartLabel);
        this.frontMap.remove(this.heartLabel2);
        this.frontMap.remove(this.heartLabel3);
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }
    
    public void setHammer(ThrowHammer hammer) {
		this.hammer = hammer;
	}
    
	public PlayerRabbit getPlayer() {
        return this.player;
    }
	
	public ThrowHammer getHammer() {
		return this.hammer;
	}

}
