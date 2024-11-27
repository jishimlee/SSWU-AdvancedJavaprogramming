package stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;
import music.BGM;

public class Stage5 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private WildBoar wildboar1;
    private WildBoar wildboar2;
    private Tiger tiger1;
    private Tiger tiger2;
    private Tiger tiger3;
    private Tiger tiger4;
    
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 70; // 남은 시간 (초 단위)
    
    public Stage5(MoonRabbitGame game) {
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
        this.frontMap = new JLabel(new ImageIcon("image/stage5.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정
        this.setVisible(true);
    
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon5.png"));
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
            // Stage5 초기화가 완료된 후에 Turtle 생성
            this.turtle = new Turtle(490,356, false, this.game, this.player);
            this.wildboar1 = new WildBoar(200, 560, false, this.game, this.player);
            this.wildboar2 = new WildBoar(750, 460, false, this.game, this.player);
            this.tiger1 = new Tiger(360, 460, false, this.game, this.player);
            this.tiger2 = new Tiger(700, 560, false, this.game, this.player);
            this.tiger3 = new Tiger(150, 35, false, this.game, this.player);
            this.tiger4 = new Tiger(750, 35, false, this.game, this.player);
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
                 } else {
                     timer.stop();
                     JOptionPane.showMessageDialog(Stage5.this, "Time's up! Game over.");
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