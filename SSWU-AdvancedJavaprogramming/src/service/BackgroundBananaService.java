package service;

import java.util.Timer;
import java.util.TimerTask;

import component.PlayerRabbit;
import component.ThrowBanana;
import direction.PlayerDirection;
import main.MoonRabbitGame;

public class BackgroundBananaService implements Runnable {
	private MoonRabbitGame game;
	private PlayerRabbit player;
	private ThrowBanana banana;
	private int playerX;
	private int playerY;
	private int bananaX;
	private int bananaY;
	// 토끼 무적을 체크하는 플래그
	private boolean isInvincible;
	private boolean isColliding = false;

	public BackgroundBananaService(ThrowBanana banana, PlayerRabbit player) {
		this.banana = banana;
		this.game = game;
		this.player = player;
		this.bananaX = banana.getX();
		this.bananaY = banana.getY();
	}

	public void run() {
		// 바나나가 유효한 상태만 실행 (= 스테이지에 보일 때)
		while (this.banana.getBananaState() == 0) {
			updateObjState();
			checkRabbitInvincible();
			checkPlayerCollision();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateObjState() {
		try {
			playerX = player.getX();
			playerY = player.getY();
//			System.out.println("플레이어 위치: x=" + playerX + ", y=" + playerY);
//			System.out.println("바나나 위치: x=" + bananaX + ", y=" + bananaY);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}

	private void checkRabbitInvincible() {
		// 토끼가 지금 무적이냐?
		this.isInvincible = player.isInvincible();
	}

	private void checkPlayerCollision() {
		isColliding = (bananaX < playerX + 28) && (playerX < bananaX + 28) && 
                (bananaY < playerY + 40) && (bananaY + 30 > playerY);
		
		if (isColliding && !isInvincible) {
			player.setCantMove(true);
			
			// 플레이어 아이콘 변경
			if (player.getDirection() == PlayerDirection.LEFT) {
				player.setIcon(player.getRabbitCrashL());
			}
			else {
				player.setIcon(player.getRabbitCrashR());
			}
			
			// 1초 뒤 해제
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                	player.resetPlayerIcon();
                	player.setCantMove(false);
                }
            }, 1000);
		}
	}
}