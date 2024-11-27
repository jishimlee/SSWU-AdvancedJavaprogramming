package component;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import component.Monkey;
import component.PlayerRabbit;
import main.MoonRabbitGame;
import stage.*;

public class ThrowBanana extends JLabel {
    private int x;
    private int y;
	private PlayerRabbit player;
	private Monkey monkey;
	private MoonRabbitGame game;
	private JPanel stage;
	private int stageNumber;
	private static final int BANANA_LIFETIME = 4000;
	
	private ImageIcon banana;
	
	public ThrowBanana(MoonRabbitGame game, Monkey monkey, PlayerRabbit player) {
		this.game = game;
		this.monkey = monkey;
		this.player = player;
		
		this.x = monkey.getX() + 9;
		this.y = monkey.getY() + 5;

		this.banana = new ImageIcon("image/banana.png");
		this.stage = game.getCurrentStage();
		this.stageNumber = game.getStageNumber();
		this.setSize(31, 39);
		this.setLocation(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static int getBananaLifetime() {
		return BANANA_LIFETIME;
	}
}



