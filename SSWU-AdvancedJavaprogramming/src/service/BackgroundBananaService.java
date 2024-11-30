package service;

import javax.swing.JPanel;

import component.PlayerRabbit;
import main.MoonRabbitGame;

public class BackgroundBananaService implements Runnable {
	private MoonRabbitGame game;
	private PlayerRabbit player;	
	private int playerX;
	private int playerY;	
	// 토끼 무적을 체크하는 플래그
	private boolean isInvincible;


	public BackgroundBananaService(PlayerRabbit player) {
		this.game = game;
		this.player = player;
	}
	
	public void run() {
		while (true) {
			updateObjState();
			checkRabbitInvincible();
			
			if (!isInvincible) {
				System.out.println("토끼가 바나나를 밟았습니다!");
			}
		}
	}
	
    private void updateObjState() {
        try {              
            playerX = player.getX();
            playerY = player.getY();
        } catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
        }
   }
	
	private void checkRabbitInvincible() {
		// 토끼가 지금 무적이냐?
		this.isInvincible = player.isInvincible();
	}
}