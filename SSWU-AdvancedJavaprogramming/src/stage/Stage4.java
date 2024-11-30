package stage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Item.Reverse;
import life.Life;
import component.*;
import main.MoonRabbitGame;
import music.BGM;

public class Stage4 extends JPanel {
   private MoonRabbitGame game; //추가함
    private JLabel frontMap;
	private JLabel moonLabel;
    private JLabel heartLabel, heartLabel2, heartLabel3;
    private JLabel timerLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private WildBoar wildboar1, wildboar2;
    private Monkey monkey1, monkey2, monkey3, monkey4, monkey5;
    private Reverse reverseItem;
    private Life life;
    private int lifeCount;
    private BGM bgm;
    
    private javax.swing.Timer timer; // 게임 타이머
    private int timeRemaining = 80; // 남은 시간 (초 단위)
    
    public Stage4(MoonRabbitGame game) {
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
    
    private void initObject() {
       //bgm 추가
    	this.bgm = new BGM(); // BGM 클래스의 생성자 호출
        bgm.play(); // BGM 재생 시작
        // 배경 이미지 설정
        this.frontMap = new JLabel(new ImageIcon("image/stage4.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);

        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel2 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel3 = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 42, 50, 50);
        this.heartLabel2.setBounds(100, 42, 50, 50);
        this.heartLabel3.setBounds(150, 42, 50, 50);
        this.loadLifeIcon();
        
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
        
        // Reverse 아이템 초기화
        this.reverseItem = new Reverse(200, 500); // 위치 초기화
        this.frontMap.add(this.reverseItem);
    }
    
    private void initSetting() {
        this.setSize(1010, 670);
        this.setPreferredSize(new Dimension(1010, 670));
        this.life.setStage(this);
        this.life.setStageNumber(4);
     }
    
    private void initThread() {
       SwingUtilities.invokeLater(() -> {
            this.turtle = new Turtle(750, 145, false, this.game, this.player);
            this.wildboar1 = new WildBoar(750, 145, false, this.game, this.player);
            this.wildboar2 = new WildBoar(750, 145, false, this.game, this.player);
            this.monkey1 = new Monkey(350, 145, false, this.game, this.player);
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
        return turtle.getState() == 2 && wildboar1.getState() == 2 &&
        		wildboar2.getState() == 2 && monkey1.getState() == 2 &&
        				monkey2.getState() == 2 && monkey3.getState() == 2 
        				&& monkey4.getState() == 2 && monkey5.getState() == 2;
        
    }
    
    public void loadHammerIcon() {
        ThrowHammer throwHammer = new ThrowHammer(this.game, player);
        throwHammer.setBounds(100, 200, throwHammer.getWidth(), throwHammer.getHeight());
        this.frontMap.add(throwHammer);
        throwHammer.setVisible(true);
        this.frontMap.revalidate();
        this.frontMap.repaint();
    }
    
    public void loadBanana(Monkey monkey) {
    	ThrowBanana throwBanana = new ThrowBanana(this.game, monkey, player);
    	monkey.setBananaExist(true);
    	if (throwBanana.getIcon() == null) {
    	    System.out.println("바나나 이미지 로드 실패");
    	}
    	throwBanana.setBounds(monkey.getX(), monkey.getY(),
    			throwBanana.getWidth(), throwBanana.getHeight());
    	this.frontMap.add(throwBanana);
    	throwBanana.setVisible(true);
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
    
    public PlayerRabbit getPlayer() {
        return this.player;
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }  
    
    public JLabel getFrontMap() {
		return frontMap;
	}

	public void setFrontMap(JLabel frontMap) {
		this.frontMap = frontMap;
	}
}