package stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;
import music.BGM;

public class Stage4 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private WildBoar wildboar1;
    private WildBoar wildboar2;
    private Monkey monkey1;
    private Monkey monkey2;
    private Monkey monkey3;
    private Monkey monkey4;
    private Monkey monkey5;
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 80; // 남은 시간 (초 단위)
    
    public Stage4(MoonRabbitGame game) {
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
       //bgm 추가
       //new BGM();
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage4.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);
        
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon4.png"));
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
            this.turtle = new Turtle(750, 145, false, this.game, this.player);
            this.wildboar1 = new WildBoar(750, 145, false, this.game, this.player);
            this.wildboar2 = new WildBoar(750, 145, false, this.game, this.player);
            this.monkey1 = new Monkey(750, 145, false, this.game, this.player);
            this.monkey2 = new Monkey(750, 145, false, this.game, this.player);
            this.monkey3 = new Monkey(750, 145, false, this.game, this.player);
            this.monkey4 = new Monkey(750, 145, false, this.game, this.player);
            this.monkey5 = new Monkey(750, 145, false, this.game, this.player);
            
            this.frontMap.add(this.turtle);
            this.frontMap.add(this.wildboar1);
            this.frontMap.add(this.wildboar2);
            this.frontMap.add(this.monkey1);
            this.frontMap.add(this.monkey2);
            this.frontMap.add(this.monkey3);
            this.frontMap.add(this.monkey4);
            this.frontMap.add(this.monkey5);
            new Thread(() -> turtle.start()).start(); // Turtle 실행
            new Thread(() -> wildboar1.start()).start();
            new Thread(() -> wildboar2.start()).start();
            new Thread(() -> monkey1.start()).start();
            new Thread(() -> monkey2.start()).start();
            new Thread(() -> monkey3.start()).start();
            new Thread(() -> monkey4.start()).start();
            new Thread(() -> monkey5.start()).start();
            
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
                     JOptionPane.showMessageDialog(Stage4.this, "Time's up! Game over.");
                     game.dispose(); // 게임 창 닫기
                 }
             }
         });
         timer.start();
     }
    
    public void loadHammerIcon() {
        ThrowHammer throwHammer = new ThrowHammer(this.game, player);
        throwHammer.setBounds(100, 200, throwHammer.getWidth(), throwHammer.getHeight());
        this.frontMap.add(throwHammer);
        throwHammer.setVisible(true);
        this.frontMap.revalidate();
        this.frontMap.repaint();
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }  
   

}