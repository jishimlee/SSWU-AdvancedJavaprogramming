package stage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;
import music.BGM;

public class Stage1 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Turtle turtle1;
    private Turtle turtle2;
    private Turtle turtle3;
    private Turtle turtle4;
    private Turtle turtle5;
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 30; // 남은 시간 (초 단위)

    public Stage1(MoonRabbitGame game) {
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
    	BGM bgm = new BGM();
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage1.png"));
        this.frontMap.setBounds(0, 0, 1000, 630); // 배경 이미지 크기 설정
        this.setLayout(null);
        this.add(this.frontMap); 
        this.setVisible(true);

        this.player.setHigh(130);	// 플레이어 점프 높이 설정
        this.player.setBounds(100, 300, 50, 50); // 플레이어 위치 및 크기 설정

        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

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
       
    }

    private void initSetting() {
    	this.setSize(1000, 640);
        this.setPreferredSize(new Dimension(1010, 670));
    }

    private void initThread() {
        SwingUtilities.invokeLater(() -> {
            // Stage1 초기화가 완료된 후에 Turtle 생성
            this.turtle1 = new Turtle(200, 230, false, this.game, this.player);
            this.turtle2 = new Turtle(400, 128, false, this.game, this.player);
            this.turtle3 = new Turtle(200, 340, false, this.game, this.player);
            this.turtle4 = new Turtle(600, 340, false, this.game, this.player);
            this.turtle5 = new Turtle(800, 454, false, this.game, this.player);
            System.out.println(turtle1.hashCode());
            System.out.println(turtle2.hashCode());
            System.out.println(turtle3.hashCode());

            
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
                 } else {
                     timer.stop();
                     JOptionPane.showMessageDialog(Stage1.this, "Time's up! Game over.");
                     game.dispose(); // 게임 창 닫기
                 }
             }
         });
         timer.start();
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
    
    public MoonRabbitGame getGame() {
        return game;
    }

}
