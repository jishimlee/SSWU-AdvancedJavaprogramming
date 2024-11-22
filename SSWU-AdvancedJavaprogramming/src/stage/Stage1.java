package stage;

import java.awt.Dimension;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.PlayerRabbit;
import component.Toad;
import component.Turtle;
import main.MoonRabbitGame;

public class Stage1 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private PlayerRabbit player;
    private Turtle turtle1;
    private Turtle turtle2;
    private Turtle turtle3;
    private Turtle turtle4;
    private Turtle turtle5;

    public Stage1(MoonRabbitGame game) {
        this.game = game;
        this.player = new PlayerRabbit(this.game);
        initObject();
        initSetting();
        initThread();
    }

	public PlayerRabbit getPlayer() {
        return this.player;
    }


    private void initObject() {
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
    
    public MoonRabbitGame getGame() {
        return game;
    }

}
