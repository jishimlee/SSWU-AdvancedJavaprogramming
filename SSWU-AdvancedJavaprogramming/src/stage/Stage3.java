package stage;

import javax.swing.*;

import Item.Reverse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.*;
import main.MoonRabbitGame;
import music.BGM;

public class Stage3 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Toad toad1;
    private Toad toad2;
    private WildBoar wildboar1;
    private WildBoar wildboar2;
    private WildBoar wildboar3;
    private WildBoar wildboar4;
    private Reverse reverseItem;
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 60; // 남은 시간 (초 단위)
    
    public Stage3(MoonRabbitGame game) {
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
        this.frontMap = new JLabel(new ImageIcon("image/stage3.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);
        
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon3.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);
        
     // 남은 시간 표시 라벨
        this.timerLabel = new JLabel(timeRemaining + "S");
        this.timerLabel.setBounds(870, 35, 150, 50); // 위치 조정
        this.timerLabel.setFont(new Font("Lexend", Font.BOLD, 25));
        this.timerLabel.setForeground(Color.WHITE);
        this.frontMap.add(this.timerLabel);
        
     // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 500); // 위치 초기화
        this.frontMap.add(this.reverseItem);

        
     // 오브젝트 추가
        this.frontMap.add(this.player);
    }
        
        private void initSetting() {
            this.setSize(1010, 670);
            this.setPreferredSize(new Dimension(1010, 670));
         }
        
        private void initThread() {
            SwingUtilities.invokeLater(() -> {
                this.wildboar1 = new WildBoar(250, 120, false, this.game, this.player);
                this.wildboar2 = new WildBoar(550, 100, false, this.game, this.player);
                this.wildboar3 = new WildBoar(350, 355, false, this.game, this.player);
                this.wildboar4 = new WildBoar(350, 563, false, this.game, this.player);
                this.toad1 = new Toad(400, 260, true, this.game, this.player);
                this.toad2 = new Toad(800, 260, true, this.game, this.player);
                this.frontMap.add(this.wildboar1);
                this.frontMap.add(this.wildboar2);
                this.frontMap.add(this.wildboar3);
                this.frontMap.add(this.wildboar4);
                this.frontMap.add(this.toad1);
                this.frontMap.add(this.toad2);
                new Thread(() -> wildboar1.start()).start();
                new Thread(() -> wildboar2.start()).start();
                new Thread(() -> wildboar3.start()).start();
                new Thread(() -> wildboar4.start()).start();
                new Thread(() -> toad1.start()).start();
                new Thread(() -> toad2.start()).start();
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
                         JOptionPane.showMessageDialog(Stage3.this, "Time's up! Game over.");
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
            return toad1.getState() == 2 && toad2.getState() == 2 &&
                  wildboar1.getState() == 2 && wildboar2.getState() == 2 &&
                        wildboar3.getState() == 2 && wildboar4.getState() == 2;
        }
        
        // 떡방아 추가
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
