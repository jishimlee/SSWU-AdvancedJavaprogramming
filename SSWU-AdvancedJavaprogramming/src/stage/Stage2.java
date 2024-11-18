package stage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;

public class Stage2 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private Toad toad;
    private Tiger tiger;

    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 60; // 남은 시간 (초 단위)
    
    public Stage2(MoonRabbitGame game) {
        this.game = game;
        this.player = new PlayerRabbit(this.game);
        initObject();
        initSetting();
        initThread();
        initTimer(); 
    }
    
    public PlayerRabbit getPlayer() {
        return this.player;
    }

    private void initObject() {
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage2.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);
        

        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

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
    }
    
    private void initSetting() {
       this.setSize(1010, 670);
        this.setPreferredSize(new Dimension(1010, 670));
    }

    private void initThread() {
        SwingUtilities.invokeLater(() -> {
            // Stage1 초기화가 완료된 후에 Turtle 생성
            this.turtle = new Turtle(100, 255, false, this.game, this.player);
            // this.toad = new Toad(750, 250, false, this.game);
            this.frontMap.add(this.turtle);
            // this.frontMap.add(this.toad);
            new Thread(() -> turtle.start()).start(); // Turtle 실행
            // new Thread(() -> toad.start()).start();
        });
    }
    
    private void initTimer() {
       timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText(timeRemaining + "S");
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(Stage2.this, "Time's up! Game over.");
                    game.dispose(); // 게임 창 닫기
                }
            }
        });
        timer.start();
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }   
    
}