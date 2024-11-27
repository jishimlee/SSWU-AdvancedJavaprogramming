package stage;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.Monkey;
import component.PlayerRabbit;
import component.ThrowBanana;
import component.ThrowHammer;
import component.Tiger;
import component.Toad;
import component.Turtle;
import component.WildBoar;
import main.MoonRabbitGame;

public class Stage3 extends JPanel {
	private MoonRabbitGame game; //추가함
    private JLabel frontMap;
    private JLabel moonLabel;
    private JLabel heartLabel;
    private PlayerRabbit player;
    private Turtle turtle;
    private Toad toad;
    private WildBoar wildboar;
    private Tiger tiger;
    private Monkey[] monkeys;
    
    
    public Stage3(MoonRabbitGame game) {
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
        this.frontMap = new JLabel(new ImageIcon("image/stage3.png"));
        this.frontMap.setBounds(0, 0, 1000, 640); // 배경 이미지 크기 설정, 겹치는거 아닌가..?
        this.setLayout(null); 
        this.add(this.frontMap); 
        this.setVisible(true);
        
     // 캐릭터 및 오브젝트 초기화
        // this.player = new PlayerRabbit();
        this.player.setBounds(100, 300, 50, 50); // 플레이어 위치 및 크기 설정
        //this.turtle = new Turtle(200, 230, false, this.game, this.player);
        //this.toad = new Toad(400, 230, false, this.game, this.player);
        //this.wildboar = new WildBoar(400, 230, false, this.game);
        
        this.heartLabel = new JLabel(new ImageIcon("image/heart.png"));
        this.heartLabel.setBounds(50, 40, 50, 50); // setLocation + setSize
        this.frontMap.add(this.heartLabel);

        this.moonLabel = new JLabel(new ImageIcon("image/moon3.png"));
        this.moonLabel.setBounds(480, 40, 50, 50);
        this.frontMap.add(this.moonLabel);
        
     // 오브젝트 추가
        this.frontMap.add(this.player);
        //this.frontMap.add(this.turtle);
        //this.frontMap.add(this.toad);
        //this.frontMap.add(this.wildboar);
    }
        
        private void initSetting() {
            this.setSize(1010, 670);
            this.setPreferredSize(new Dimension(1010, 670));
         }
        
        private void initThread() {
            SwingUtilities.invokeLater(() -> {
                this.wildboar = new WildBoar(350, 250, false, this.game, this.player);
                this.tiger = new Tiger(800, 560, true, this.game, this.player);
                this.frontMap.add(this.wildboar);
                this.frontMap.add(this.tiger);
                new Thread(() -> wildboar.start()).start();
                new Thread(() -> tiger.start()).start();
                
                this.monkeys = new Monkey[2];
                this.monkeys[0] = new Monkey(800, 250, true, this.game, this.player);
                this.monkeys[1] = new Monkey(650, 458, true, this.game, this.player);
                // 원숭이 추가
                for (Monkey monkey : monkeys) {
                    this.frontMap.add(monkey);
                    new Thread(() -> monkey.start()).start();
                }
            });
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
        
        // 바나나 추가
        public void loadBanana() {
            for (Monkey monkey : monkeys) {
            	if (!monkey.isExistBanana()) {
	                ThrowBanana banana = monkey.throwBanana(); // 각 원숭이 객체에서 바나나 던지기
	                System.out.println(monkey + "바나나 추가");
	                if (banana != null) {
	                    // 위치 설정
	                    banana.setLocation(monkey.getX(), monkey.getY());
	                    banana.setSize(50, 50); // 적절한 크기 설정
	                    this.frontMap.add(banana); // 바나나 객체를 frontMap에 추가
	                    banana.setVisible(true);
	                    this.frontMap.repaint(); // 화면 갱신
	                    this.frontMap.revalidate(); // 레이아웃 갱신
	                    System.out.println("갱신 완료");
	                }
	            }
            }
        }
        
        
        public MoonRabbitGame getGame() {
            return game;
        }  
}
