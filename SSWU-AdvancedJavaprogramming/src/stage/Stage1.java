package stage;

import javax.swing.*;

import Item.Reverse;
import Item.*;

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
    private ThrowHammer throwHammer;
    private Turtle turtle1, turtle2, turtle3, turtle4, turtle5;
    private Reverse reverseItem; // Reverse 객체 추가
    private Score score;
    private Life life;
    private int lifeCount;
    private int currentScore;
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
        
        this.score = game.getScore();
        this.currentScore = score.getCurrentScore();
        score.setStage(this);
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

//        this.player.setHigh(130);   // 플레이어 점프 높이 설정
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
        this.timerLabel.setBounds(350, 35, 150, 50); // 위치 조정
        this.timerLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.timerLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.timerLabel);
        
        // 오브젝트 추가
        this.frontMap.add(this.player);
        
        this.scoreLabel = new JLabel("score: "+ score.getCurrentScore());
        this.scoreLabel.setBounds(800, 35, 150, 50); // 위치 조정
        this.scoreLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.scoreLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.scoreLabel);
     // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 450); // 위치 초기화
        this.frontMap.add(this.reverseItem);
        
        
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
                     showGameOverText();// 게임 오버 이미지 표시
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
    
    private void showGameOverText() {
        // BGM 정지
        if (bgm != null) {
            bgm.stop(); // BGM 클래스에서 제공하는 정지 메서드 호출
        }

        // 게임 오버 메시지 JLabel 생성
        JLabel gameOverLabel = new JLabel("Game Over!", JLabel.CENTER); // 텍스트 중앙 정렬
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50)); // 글씨 크기와 스타일 설정
        gameOverLabel.setForeground(Color.RED); // 텍스트 색상 설정

        // 게임 화면에 JLabel 추가
        gameOverLabel.setBounds(0, 0, game.getWidth(), game.getHeight()); // 화면 중앙에 위치
        gameOverLabel.setLocation(game.getWidth() / 2 - gameOverLabel.getWidth() / 2, game.getHeight() / 2 - gameOverLabel.getHeight() / 2);

        // 화면에 추가
        game.add(gameOverLabel);
        game.repaint(); // 화면 갱신
    }

    
    public boolean areAllEnemiesDefeated() {
        return turtle1.getState() == 2 && turtle2.getState() == 2 &&
               turtle3.getState() == 2 && turtle4.getState() == 2 &&
               turtle5.getState() == 2;
        
    }
    
    public void loadHammerIcon() {
        // 이미 생성된 상태인지 확인
        if (throwHammer == null) {
            throwHammer = new ThrowHammer(this.game, player);
            throwHammer.setBounds(100, 200, throwHammer.getWidth(), throwHammer.getHeight());
            this.frontMap.add(throwHammer);
            throwHammer.setVisible(true);
            this.frontMap.revalidate();
            this.frontMap.repaint();
        }
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
    public void deleteAllLifeIcon() {
        this.frontMap.remove(this.heartLabel);
        this.frontMap.remove(this.heartLabel2);
        this.frontMap.remove(this.heartLabel3);
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }
    
   public ThrowHammer getThrowHammer() {
      return this.throwHammer;
   }
    
    public void setThrowHammer(ThrowHammer throwHammer) {
      this.throwHammer = throwHammer;
   }
    
   public PlayerRabbit getPlayer() {
        return this.player;
   }

   public JLabel getFrontMap() {
      return frontMap;
   }
}
