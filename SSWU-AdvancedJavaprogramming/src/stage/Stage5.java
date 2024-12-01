package stage;

import javax.swing.*;
import Item.Reverse;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import life.Life;
import main.MoonRabbitGame;
import music.BGM;
import score.Score;

public class Stage5 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel, heartLabel2, heartLabel3;
    private JLabel timerLabel;
    private JLabel scoreLabel;

    private PlayerRabbit player;
    private Turtle turtle;
    private WildBoar wildboar1;
    private WildBoar wildboar2;
    private Tiger tiger1;
    private Tiger tiger2;
    private Tiger tiger3;
    private Tiger tiger4;
    private Reverse reverseItem;
    private Life life;
    private int lifeCount;
    private BGM bgm;
    private Score score;
    private int currentScore;
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 70; // 남은 시간 (초 단위)
    
    public Stage5(MoonRabbitGame game) {
        this.game = game;
        this.player = new PlayerRabbit(this.game);
        
        // life 개수 받아오기
        this.life = game.getLife();
        this.lifeCount = life.getLifeCount();
        this.score = game.getScore();
        this.currentScore = score.getCurrentScore();
        score.setStage(this);

        
        initObject();
        initSetting();
        initThread();
        initTimer();
    }
    
    public PlayerRabbit getPlayer() {
        return this.player;
    }
    
    private void initObject() {
       //bgm 추가
    	this.bgm = new BGM(); // BGM 클래스의 생성자 호출
        bgm.play(); // BGM 재생 시작
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage5.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정
        this.setVisible(true);
    
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel2 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel3 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 42, 50, 50);
        this.heartLabel2.setBounds(100, 42, 50, 50);
        this.heartLabel3.setBounds(150, 42, 50, 50);
        this.loadLifeIcon();

        this.moonLabel = new JLabel(new ImageIcon("image/moon5.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);
        
     // 남은 시간 표시 라벨
        this.timerLabel = new JLabel(timeRemaining + "S");
        this.timerLabel.setBounds(210, 37, 150, 50); // 위치 조정
        this.timerLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.timerLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.timerLabel);
        
     // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 500); // 위치 초기화
        this.frontMap.add(this.reverseItem);
        
        
     // 오브젝트 추가
        this.frontMap.add(this.player);
        
        this.scoreLabel = new JLabel("score: "+ score.getCurrentScore());
        this.scoreLabel.setBounds(800, 37, 150, 50); // 위치 조정
        this.scoreLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.scoreLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.scoreLabel);

    }
    
    private void initSetting() {
        this.setSize(1010, 670);
        this.setPreferredSize(new Dimension(1010, 670));
        this.life.setStage(this);
        this.life.setStageNumber(5);
     }
    
    private void initThread() {
       SwingUtilities.invokeLater(() -> {
            // Stage5 초기화가 완료된 후에 Turtle 생성
            this.turtle = new Turtle(490,356, false, this.game, this.player);
            this.wildboar1 = new WildBoar(200, 560, false, this.game, this.player);
            this.wildboar2 = new WildBoar(750, 460, false, this.game, this.player);
            this.tiger1 = new Tiger(360, 460, false, this.game, this.player);
            this.tiger2 = new Tiger(700, 560, false, this.game, this.player);
            this.tiger3 = new Tiger(150, 460, false, this.game, this.player);
            this.tiger4 = new Tiger(750, 560, false, this.game, this.player);
            this.frontMap.add(this.turtle);
            this.frontMap.add(this.wildboar1);
            this.frontMap.add(this.wildboar2);
            this.frontMap.add(this.tiger1);
            this.frontMap.add(this.tiger2);
            this.frontMap.add(this.tiger3);
            this.frontMap.add(this.tiger4);
            new Thread(() -> turtle.start()).start(); // Turtle 실행
            new Thread(() -> wildboar1.start()).start();
            new Thread(() -> wildboar2.start()).start();
            new Thread(() -> tiger1.start()).start();
            new Thread(() -> tiger2.start()).start();
            new Thread(() -> tiger3.start()).start();
            new Thread(() -> tiger4.start()).start();
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
                     showGameOverImage();
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
    	 System.out.println("turtle 상태: " + turtle.getState());
    	 System.out.println("wildboar1 상태: " + wildboar1.getState());
    	 System.out.println("wildboar2: " + wildboar2.getState());
    	 System.out.println("tiger1 상태: " + tiger1.getState());
    	 System.out.println("tiger2 상태: " + tiger2.getState());
    	 System.out.println("tiger3 상태: " + tiger3.getState());
    	 System.out.println("tiger4 상태: " + tiger4.getState());
        return turtle.getState() == 2 && wildboar1.getState() == 2 &&
        		wildboar2.getState() == 2 && tiger4.getState() == 2 &&
	        				tiger3.getState() == 2 && tiger2.getState() == 2 
				&& tiger1.getState() == 2;
        
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
    public void updateScore() {
        System.out.println("updateScore called");

        // scoreUp 조건 없이 바로 점수 업데이트
        this.currentScore = score.getCurrentScore();
        System.out.println("Updated Score: " + currentScore);
        scoreLabel.setText("score: " + currentScore);
        
        // 화면 업데이트
        this.frontMap.revalidate();
        this.frontMap.repaint();
    }

    
    public MoonRabbitGame getGame() {
        return game;
    }  
}
