package stage;

import javax.swing.*;
import java.awt.*;

import component.Monkey;
import component.PlayerRabbit;
import component.Toad;
import component.Turtle;
import component.WildBoar;
import main.MoonRabbitGame;

public class Stage4 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private PlayerRabbit player;
    //private Turtle turtle;
    private Toad toad;
    private WildBoar wildboar;
    private Monkey monkey;
    
    public Stage4(MoonRabbitGame game) {
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
        this.frontMap = new JLabel(new ImageIcon("image/stage4.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);
        
     // 캐릭터 및 오브젝트 초기화
        this.player = new PlayerRabbit();
        this.player.setBounds(100, 300, 50, 50); // 플레이어 위치 및 크기 설정
        //this.turtle = new Turtle(200, 230, false, this.game);
        this.toad = new Toad(400, 230, false, this.game);
        this.wildboar = new WildBoar(400, 230, false, this.game);
        this.monkey = new Monkey(400, 230, false, this.game);
        
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon4.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);
        
     // 오브젝트 추가
        this.frontMap.add(this.player);
        //this.frontMap.add(this.turtle);
        this.frontMap.add(this.toad);
        this.frontMap.add(this.wildboar);
        this.frontMap.add(this.monkey);
    }
    
    private void initSetting() {
        this.setSize(1010, 670);
        this.setPreferredSize(new Dimension(1010, 670));
     }
    
    private void initThread() {
        //new Thread(() -> turtle.start()).start();
        new Thread(() -> toad.start()).start();
        new Thread(() -> wildboar.start()).start();
        new Thread(() -> monkey.start()).start();
    }
    public MoonRabbitGame getGame() {
        return game;
    }  
	

}
