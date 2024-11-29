package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.WildBoar;
import direction.PlayerDirection;
import main.MoonRabbitGame;
import stage.*;

public class BackgroundWildBoarService implements Runnable {
	private BufferedImage img;
	private WildBoar wildboar;
	private PlayerRabbit player;
	private int stageNumber;
	private JPanel stage;
	private int state;
	int wildboarX;
	int wildboarY;
	int playerX;
	int playerY;
	
	private MoonRabbitGame game;
	private String backgroundPath;
	private PlayerRabbit currentPlayer;
	private boolean isColliding;
	private boolean isAttacked;
	
	// 무적 상태를 관리하는 플래그
	private boolean isInvincible;
	// 토끼 상태 확인
	private boolean touchingRabbit = false;
	// 토끼가 공격 중인가?
	private boolean isAttacking = false;
	// 떡방아 상태 확인
	private boolean attacked = false;
	
	
	
	public BackgroundWildBoarService(WildBoar wildboar, MoonRabbitGame game, PlayerRabbit player) {
		this.wildboar = wildboar;
		this.game = game;
		this.player = player;
		this.stage = game.getCurrentStage();
		this.stageNumber = game.getStageNumber();
		// System.out.println("현재 스테이지는 stage " + stageNumber + "입니다.");
		try {
			if (stageNumber == 1) backgroundPath = "image/background1.png";
			else if (stageNumber == 2) backgroundPath = "image/background2.png";
			else if (stageNumber == 3) backgroundPath = "image/background3.png";
			else if (stageNumber == 4) backgroundPath = "image/background4.png";
			else if (stageNumber == 5) backgroundPath = "image/background5.png";
			
			img = ImageIO.read(new File(backgroundPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	switch (stageNumber) {
		case 1:
			currentPlayer = ((Stage1)stage).getPlayer();
			break;
		case 2:
			currentPlayer = ((Stage2)stage).getPlayer();
			break;
		case 3:
			currentPlayer = ((Stage3)stage).getPlayer();
			break;
		case 4:
			currentPlayer = ((Stage4)stage).getPlayer();
			break;
		case 5:
			currentPlayer = ((Stage5)stage).getPlayer();
			break;
		default:
			break;
	}
	}
	
	public void run() {
		while (state != 2) {	
			// player와 wildboar 상태 업데이트
			// player를 스테이지별로 가져오기
			// wildboar의 state를 확인하기
			updateObjState();
				
			// 충돌여부 확인
			// state == 0일 때, 토끼 목숨 깎이고 2000ms 무적
			// state == 1일 때, 떡 / state == 2, 사라짐, 점수 올라감
			if (state == 0) {
				checkStageCollision();
				checkAttacked();
			}
			
			checkPlayerCollision();
			

	        // rushState 상태 확인 및 갱신
	        if (!wildboar.isRushState()) { // rushState가 false인 경우
	            long currentTime = System.currentTimeMillis();
	            if (currentTime - wildboar.getLastRushTime() >= 2000) { // 2초 경과 확인
	                wildboar.setRushState(true); // rushState 활성화
	                // System.out.println("rushState 활성화!");
	            }
	        }
	        game.checkStageCompletion();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateObjState() {
        try {	        	
            // 매 루프마다 player의 위치를 확인하고, wildboar과 비교      	
        	// 멧돼지와 플레이어 상태 확인
        	wildboarX = wildboar.getX();
        	wildboarY = wildboar.getY();
        	playerX = currentPlayer.getX();
        	playerY = currentPlayer.getY();
        	state = wildboar.getState();
        	
        	// 플레이어 무적 확인
        	isInvincible = currentPlayer.isInvincible();
        	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
        	
	}
	
	private void checkStageCollision() {
		try {
            // 바닥 없는 곳 확인
            Color leftColor = new Color(img.getRGB(wildboarX - 7, wildboarY + 25));
            Color rightColor = new Color(img.getRGB(wildboarX + 67 + 7, wildboarY + 25));
            Color leftBottom = new Color(img.getRGB(wildboarX - 2, wildboarY + 55));
            Color rightBottom = new Color(img.getRGB(wildboarX + 67 + 2, wildboarY + 55));

            // 좌측 및 우측 벽 충돌 검사
            if (isRed(leftColor)) {
                // System.out.println("왼쪽 충돌");
                wildboar.setLeft(false);
                if (!wildboar.isRight()) {
                    wildboar.right();
                    if (wildboar.isRushState()) wildboar.setRushState(false);
                }
            } else if (isRed(rightColor)) {
                // System.out.println("오른쪽 충돌");
                wildboar.setRight(false);
                if (!wildboar.isLeft()) {
                    wildboar.left();
                    if (wildboar.isRushState()) wildboar.setRushState(false);
                }
            }

            // 바닥 없음 확인
            boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
                    && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
                    && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);

            if (leftBottomMissing && wildboar.isLeft()) {
                // System.out.println("왼쪽 바닥 없음");
                wildboar.setLeft(false);
                if (!wildboar.isRight()) {
                    wildboar.right();
                }
            } else if (rightBottomMissing && wildboar.isRight()) {
                // System.out.println("오른쪽 바닥 없음");
                wildboar.setRight(false);
                if (!wildboar.isLeft()) {
                    wildboar.left();
                }
            }

	    } catch (Exception e) {
	        System.out.println("Error : " + e.getMessage());
	    }
	}
	
	private void checkPlayerCollision() {
		if (state != 2) {
			// 멧돼지와 플레이어의 충돌 영역 (50 x 50 기준)
        	isColliding = (wildboarX < playerX + 25) && (wildboarX + 62 > playerX) && 
        	                      (wildboarY < playerY + 50) && (wildboarY + 67 > playerY);       
        	if (state == 0) {
        	    try {
        	        // 충돌 확인 로직 -> 몸이랑 닿은 거
        	        if (!isInvincible) {
        	            if(isColliding) {
        	            	startInvincibilityTimer();
        	                handleEnemy();
        	            }
        	        }
        	    } catch (Exception e) {
        	        System.out.println("Error : " + e.getMessage());
        	    }
        	}
        	
            else if (state == 1) {
	            try {
	                // 충돌 확인 로직
	                if (isColliding) {
	                    handleTtoek();
	                }
	            } catch (Exception e) {
	                System.out.println("Error : " + e.getMessage());
	            }
            }
		}
	}
	
	// 공격 확인
	private void checkAttacked() {
    	// 공격 중임을 300ms 동안,,,? 일단 조절하면서 (300ms 동안 공격 이미지 유지하므로)
	    if (player.isSpacePressed() && !isAttacking) {
	        isAttacking = true;
	        System.out.println("공격 중");
	        
	        // 일정 시간 동안 공격 상태 유지 (100ms)
	        new Timer().schedule(new TimerTask() {
	            @Override
	            public void run() {
	                isAttacking = false;
	                System.out.println("공격 끝");
	            }
	        }, 100); // 100ms 후 공격 종료
	    }

    	// 떡방아에 닿았을 때
    	// 이 인식하는 걸 icon 방향에 따라서 왼쪽 공격은 왼쪽만 공격 당하게... 설정해야 함
    	// !isColliding 넣은 이유는 공격 때 isColliding 범위에 닿으면 몸에 닿은 거라서
    	// 플레이어가 공격 버튼을 누르고 멧돼지와 충돌하지 않았을 때
    	if (isAttacking && !isColliding && wildboar.getState() == 0) {
    	    // 공격 방향에 따라 범위를 설정
    	    if (this.player.getDirection() == PlayerDirection.LEFT) { // 왼쪽으로 공격할 때
    	        isAttacked = ((playerX - 60 <= wildboarX && wildboarX <= playerX) ||
    	        		(playerX - 60 <= wildboarX + 67 && wildboarX + 67 <= playerX)) && 
    	                     (playerY - 50 <= wildboarY && wildboarY <= playerY + 40); // 왼쪽 공격 범위
    	    } else if (this.player.getDirection() == PlayerDirection.RIGHT) { // 오른쪽으로 공격할 때
    	        isAttacked = ((playerX + 30 <= wildboarX && wildboarX <= playerX + 90) ||
    	        		(playerX + 30 <= wildboarX + 67 && wildboarX + 67 <= playerX + 30)) && 
    	                     (playerY - 50 <= wildboarY && wildboarY <= playerY + 40); // 오른쪽 공격 범위
    	    }
    	    
            // 디버깅용 출력 (공격 범위와 충돌 체크)
//    	    if (this.player.getDirection() == PlayerDirection.LEFT) {
//    	    	System.out.println("Left");
//    	    	System.out.println("X 공격 범위 체크: " + (playerX - 60) + " ~ " + playerX);
//    	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
//    	    }
//    	    else {
//    	    	System.out.println("Right");
//    	    	System.out.println("X 공격 범위 체크: " + (playerX + 30) + " ~ " + (playerX + 90));
//    	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
//    	    }
//            System.out.println("플레이어 X: " + playerX + ", 멧돼지 X: " + wildboarX);
//            System.out.println("플레이어 Y: " + playerY + ", 멧돼지 Y: " + wildboarY);
//            System.out.println("isAttacked: " + isAttacked);
    	}
    	
    	if (isAttacked) handleAttacked();
	}
	
	
	// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
	private void startInvincibilityTimer() {
	    this.currentPlayer.setStartInvincible(true);
	}
	
	private void handleEnemy() {
	    System.out.println("토끼와 닿았습니다!");
	    // 목숨 감소 등 충돌 처리
	}
	
	private void handleAttacked() {
	    System.out.println("공격 당했습니다!");
        wildboar.setLeft(false); // 왼쪽 이동 막기
        wildboar.setRight(false); // 오른쪽 이동 막기
	    wildboar.setState(1); // 상태를 '떡'으로 변경
	    wildboar.repaint();
	    stage.repaint();
	}
	
	private void handleTtoek() {
	    System.out.println("토끼가 떡을 획득했습니다.");
	    try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    wildboar.setState(2); // 최종 상태로 변경
	    wildboar.repaint();
	    stage.repaint();
	}
	
	private boolean isRed(Color color) {
	    return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
	}
	
}
