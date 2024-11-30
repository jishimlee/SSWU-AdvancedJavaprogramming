package service;

import javax.swing.JPanel;

import component.PlayerRabbit;
import main.MoonRabbitGame;

public class BackgroundBananaService implements Runnable {
	private MoonRabbitGame game;
	private PlayerRabbit player;
	private int stageNumber;
	private JPanel stage;
	
	// 토끼 무적을 체크하는 플래그
	private boolean isInvincible;
	
	BackgroundBananaService(PlayerRabbit player, MoonRabbitGame game) {
		this.game = game;
		this.stage = game.getCurrentStage();
		this.stageNumber = game.getStageNumber();
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
          // 매 루프마다 player의 위치를 확인하고, turtle과 비교
           // 거북이와 플레이어 상태 확인
           turtleX = turtle.getX();
           turtleY = turtle.getY();
           playerY = player.getY();
           state = turtle.getState();
        } catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
        }
           
   }
	
	private void checkRabbitInvincible() {
		// 토끼가 지금 무적이냐?
		this.isInvincible = player.isInvincible();
	}
}