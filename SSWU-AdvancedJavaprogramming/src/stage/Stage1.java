package stage;

import javax.swing.*;
import java.awt.*;

import component.PlayerRabbit;
import component.Turtle;
import main.MoonRabbitGame;

public class Stage1 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private Toad toad;

    public Stage1(MoonRabbitGame game) {
        this.game = game;
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

        // 캐릭터 및 오브젝트 초기화
        this.player = new PlayerRabbit();
        this.player.setBounds(100, 300, 50, 50); // 플레이어 위치 및 크기 설정
        this.turtle = new Turtle(200, 230, false, this.game);

        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon1.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);

        // 오브젝트 추가
        this.frontMap.add(this.player);
        this.frontMap.add(this.turtle);
    }

    private void initSetting() {
    	this.setSize(1000, 640);
        this.setPreferredSize(new Dimension(1010, 670));
    }

    private void initThread() {
        new Thread(() -> turtle.start()).start();
    }
    
    public MoonRabbitGame getGame() {
        return game;
    }

}
