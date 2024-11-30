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

public class Stage2 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel, heartLabel2, heartLabel3;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private PlayerRabbit player;
    private Turtle turtle1;
    private Turtle turtle2;
    private Toad toad1;
    private Toad toad2;
    private Toad toad3;
    private Toad toad4;
    private Reverse reverseItem;
    private Life life;
    private int lifeCount;

    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 60; // 남은 시간 (초 단위)
    
    public Stage2(MoonRabbitGame game) {
        this.game = game;
        this.player = new PlayerRabbit(this.game);
        
        // life 개수 받아오기
        this.life = game.getLife();
        this.lifeCount = life.getLifeCount();
        
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
    	//new BGM();
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage2.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null);
        this.add(this.frontMap);
//        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정
//        this.setLayout(null); 
//        this.add(this.frontMap);
//        this.setVisible(true);
        
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel2 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel3 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 42, 50, 50);
        this.heartLabel2.setBounds(100, 42, 50, 50);
        this.heartLabel3.setBounds(150, 42, 50, 50);
        this.loadLifeIcon();

        this.moonLabel = new JLabel(new ImageIcon("image/moon2.png"));
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
        
     // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 500); // 위치 초기화
        this.frontMap.add(this.reverseItem);
    }
    
    private void initSetting() {
       this.setSize(1010, 670);
        this.setPreferredSize(new Dimension(1010, 670));
        this.life.setStage(this);
        this.life.setStageNumber(2);
    }

    private void initThread() {
        SwingUtilities.invokeLater(() -> {
            // Stage1 초기화가 완료된 후에 Turtle 생성
            this.turtle1 = new Turtle(750, 145, false, this.game, this.player);
            this.turtle2 = new Turtle(200, 464, false, this.game, this.player);
            this.toad1 = new Toad(750, 262, false, this.game, this.player);
            this.toad2 = new Toad(200, 262, false, this.game, this.player);
            this.toad3 = new Toad(700, 470, true, this.game, this.player);
            this.toad4 = new Toad(420, 155, false, this.game, this.player);
            this.frontMap.add(this.turtle1);
            this.frontMap.add(this.turtle2);
            this.frontMap.add(this.toad1);
            this.frontMap.add(this.toad2);
            this.frontMap.add(this.toad3);
            this.frontMap.add(this.toad4);
            new Thread(() -> turtle1.start()).start(); // Turtle 실행
            new Thread(() -> turtle2.start()).start();
            new Thread(() -> toad1.start()).start();
            new Thread(() -> toad2.start()).start();
            new Thread(() -> toad3.start()).start();
            new Thread(() -> toad4.start()).start();
            }
        );
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
                    JOptionPane.showMessageDialog(Stage2.this, "Time's up! Game over.");
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
    
    public boolean areAllEnemiesDefeated() {
        return turtle1.getState() == 2 && turtle2.getState() == 2 &&
        		toad1.getState() == 2 && toad2.getState() == 2 &&
        				toad3.getState() == 2 && toad4.getState() == 2;
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
    
}