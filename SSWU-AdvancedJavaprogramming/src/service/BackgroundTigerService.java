package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Tiger;
import direction.PlayerDirection;
import main.MoonRabbitGame;
import stage.Stage1;
import stage.Stage2;
import stage.Stage3;
import stage.Stage4;
import stage.Stage5;

public class BackgroundTigerService implements Runnable {
	private BufferedImage img;
	private Tiger tiger;
	private PlayerRabbit player;
	private int stageNumber;
	private JPanel stage;
	private int state;
	int tigerX;
	int tigerY;
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
	
	
	
	public BackgroundTigerService(Tiger tiger, MoonRabbitGame game, PlayerRabbit player) {
		this.tiger = tiger;
		this.game = game;	// 현재 실행 중인 stage 값 받아오기 위함
		this.player = player;
		this.stage = game.getCurrentStage();
		this.stageNumber = game.getStageNumber();
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
	}
	
	public void run() {
		while (state != 2) {	
			// player와 tiger 상태 업데이트
			// player를 스테이지별로 가져오기
			// tiger의 state를 확인하기
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
	        if (!tiger.isRushState()) { // rushState가 false인 경우
	            long currentTime = System.currentTimeMillis();
	            if (currentTime - tiger.getLastRushTime() >= 3000) { // 3초 경과 확인
	                tiger.setRushState(true); // rushState 활성화
	                // System.out.println("tiger: rushState 활성화!");
	            }
	        }
	        
	        if (tiger.isRushState()) {
	            long currentTime = System.currentTimeMillis();
	            if (currentTime - tiger.getLastRushStartTime() >= 1000) { // 1초 경과 확인
	                tiger.setRushState(false); // rushState 활성화
	                // System.out.println("tiger: rushState 비활성화");
	            }
	        }
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateObjState() {
        try {	        	
            // 매 루프마다 player의 위치를 확인하고, tiger과 비교
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
        	
        	// 호랑이와 플레이어 상태 확인
        	tigerX = tiger.getX();
        	tigerY = tiger.getY();
        	playerX = currentPlayer.getX();
        	playerY = currentPlayer.getY();
        	state = tiger.getState();
        	
        	// 플레이어 무적 확인
        	isInvincible = currentPlayer.isInvincible();
        	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
	}
	
	private void checkStageCollision() {
		try {
            // 바닥 없는 곳 확인
            Color leftColor = new Color(img.getRGB(tigerX - 7, tigerY + 25));
            Color rightColor = new Color(img.getRGB(tigerX + 67 + 7, tigerY + 25));
            Color leftBottom = new Color(img.getRGB(tigerX - 2, tigerY + 55));
            Color rightBottom = new Color(img.getRGB(tigerX + 67 + 2, tigerY + 55));

            // 좌측 및 우측 벽 충돌 검사
            if (isRed(leftColor)) {
                //System.out.println("왼쪽 충돌");
                tiger.setLeft(false);
                if (!tiger.isRight()) {
                    tiger.right();
                    if (tiger.isRushState()) tiger.setRushState(false);
                }
            } else if (isRed(rightColor)) {
                //System.out.println("오른쪽 충돌");
                tiger.setRight(false);
                if (!tiger.isLeft()) {
                    tiger.left();
                    if (tiger.isRushState()) tiger.setRushState(false);
                }
            }

            // 바닥 없음 확인
            boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
                    && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
                    && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);

            if (leftBottomMissing && tiger.isLeft()) {
                //System.out.println("왼쪽 바닥 없음");
                tiger.setLeft(false);
                if (!tiger.isRight()) {
                    tiger.right();
                }
            } else if (rightBottomMissing && tiger.isRight()) {
                //System.out.println("오른쪽 바닥 없음");
                tiger.setRight(false);
                if (!tiger.isLeft()) {
                    tiger.left();
                }
            }

	    } catch (Exception e) {
	        System.out.println("Error : " + e.getMessage());
	    }
	}
	
	private void checkPlayerCollision() {
		if (state != 2) {
			// 호랑이와 플레이어의 충돌 영역 (50 x 50 기준)
        	isColliding = (tigerX < playerX + 25) && (tigerX + 62 > playerX) && 
        	                      (tigerY < playerY + 50) && (tigerY + 67 > playerY);       
        	if (state == 0) {
        	    try {
        	        // 충돌 확인 로직 -> 몸이랑 닿은 거
        	        if (!isInvincible) {
        	            if(isColliding) {
        	                handleEnemy();
        	                startInvincibilityTimer();
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
	

	
	// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
	private void startInvincibilityTimer() {
	    this.currentPlayer.setStartInvincible(true);
	}
	
	
	// 공격 확인
	private void checkAttacked() {
    	// 공격 중임을 300ms 동안,,,? 일단 조절하면서 (300ms 동안 공격 이미지 유지하므로)
	    if (player.isSpacePressed() && !isAttacking) {
	        isAttacking = true;
	        System.out.println("공격 중");
	        
	        // 일정 시간 동안 공격 상태 유지 (300ms)
	        new Timer().schedule(new TimerTask() {
	            @Override
	            public void run() {
	                isAttacking = false;
	                System.out.println("공격 끝");
	            }
	        }, 300); // 300ms 후 공격 종료
	    }

    	// 떡방아에 닿았을 때
    	// 이 인식하는 걸 icon 방향에 따라서 왼쪽 공격은 왼쪽만 공격 당하게... 설정해야 함
    	// !isColliding 넣은 이유는 공격 때 isColliding 범위에 닿으면 몸에 닿은 거라서
    	// 플레이어가 공격 버튼을 누르고 호랑이와 충돌하지 않았을 때
    	if (isAttacking && !isColliding && tiger.getState() == 0) {
    	    // 공격 방향에 따라 범위를 설정
    	    if (this.player.getDirection() == PlayerDirection.LEFT) { // 왼쪽으로 공격할 때
    	        isAttacked = ((playerX - 60 <= tigerX && tigerX <= playerX) ||
    	        		(playerX - 60 <= tigerX + 67 && tigerX + 67 <= playerX)) && 
    	                     (playerY - 50 <= tigerY && tigerY <= playerY + 40); // 왼쪽 공격 범위
    	    } else if (this.player.getDirection() == PlayerDirection.RIGHT) { // 오른쪽으로 공격할 때
    	        isAttacked = ((playerX + 30 <= tigerX && tigerX <= playerX + 90) ||
    	        		(playerX + 30 <= tigerX + 67 && tigerX + 67 <= playerX + 30)) && 
    	                     (playerY - 50 <= tigerY && tigerY <= playerY + 40); // 오른쪽 공격 범위
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
//            System.out.println("플레이어 X: " + playerX + ", 호랑이 X: " + tigerX);
//            System.out.println("플레이어 Y: " + playerY + ", 호랑이 Y: " + tigerY);
//            System.out.println("isAttacked: " + isAttacked);
    	}
    	
    	if (isAttacked) handleAttacked();
	}
	
	private void handleEnemy() {
	    System.out.println("토끼와 닿았습니다!");
	    // 목숨 감소 등 충돌 처리
	}
	
	private void handleAttacked() {
	    System.out.println("공격 당했습니다!");
        tiger.setLeft(false); // 왼쪽 이동 막기
        tiger.setRight(false); // 오른쪽 이동 막기
	    tiger.setState(1); // 상태를 '떡'으로 변경
	    tiger.repaint();
	    stage.repaint();
	}
	
	private void handleTtoek() {
	    System.out.println("토끼가 떡을 획득했습니다.");
	    try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    tiger.setState(2); // 최종 상태로 변경
	    tiger.repaint();
	    stage.repaint();
	}
	
	private boolean isRed(Color color) {
	    return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
	}
	
}
