package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Monkey;
import direction.PlayerDirection;
import main.MoonRabbitGame;
import stage.*;

public class BackgroundMonkeyService implements Runnable {
	private BufferedImage img;
	private Monkey monkey;
	private PlayerRabbit player;	// player 움직임 확인용
	private int stageNumber;
	private JPanel stage;
	private int state;
	private int monkeyX;
	private int monkeyY;
	private int playerX;
	private int playerY;
	
	private MoonRabbitGame game;
	private String backgroundPath;
	PlayerRabbit currentPlayer;
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


	public BackgroundMonkeyService(Monkey monkey, MoonRabbitGame game, PlayerRabbit player) {
		this.monkey = monkey;
		this.game = game;
		this.player = player;
		this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		this.stageNumber = game.getStageNumber();
		this.monkey.setBananaExist(false);
		// System.out.println("현재 스테이지는 stage " + stageNumber + "입니다.");
		try {
			if (stageNumber == 1)	backgroundPath = "image/background1.png";
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
			// player와 monkey 상태 업데이트
			// player를 스테이지별로 가져오기
			// monkey의 state를 확인하기
			updateObjState();
				
			// 충돌여부 확인
			// state == 0일 때, 토끼 목숨 깎이고 2000ms 무적
			// state == 1일 때, 떡 / state == 2, 사라짐, 점수 올라감
			if (state == 0) {
				checkStageCollision();
				checkAttacked();
			}
			
			checkPlayerCollision();
			
			if (!this.monkey.isBananaExist()) throwBanana();
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	private void throwBanana() {
		if (stage instanceof Stage4) {
            ((Stage4) stage).loadBanana(this.monkey); // Stage4의 loadBanana 호출
        }
//      else {
//            ((Stage5) stage).loadBanana(this.monkey); // Stage5의 loadBanana 호출
//      }
	}
		
	
	private void updateObjState() {
        try {	        	
            // 매 루프마다 player의 위치를 확인하고, monkey과 비교
        	// 원숭이와 플레이어 상태 확인
        	monkeyX = monkey.getX();
        	monkeyY = monkey.getY();
        	playerX = currentPlayer.getX();
        	playerY = currentPlayer.getY();
        	state = monkey.getState();
        	
        	// 플레이어 무적 확인
        	isInvincible = currentPlayer.isInvincible();
        	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
        	
	}
		
		
		// 충돌 확인 (1. 플레이어랑 2. 벽이랑 3. 바닥이랑)
		private void checkStageCollision() {
	        	try {
		                // 바닥 없는 곳 확인
		                Color leftColor = new Color(img.getRGB(monkeyX - 7, monkeyY + 25));
		                Color rightColor = new Color(img.getRGB(monkeyX + 50 + 7, monkeyY + 25));
		                Color leftBottom = new Color(img.getRGB(monkeyX - 2, monkeyY + 55));
		                Color rightBottom = new Color(img.getRGB(monkeyX + 50 + 2, monkeyY + 55));
	
		                // 좌측 및 우측 벽 충돌 검사
		                if (isRed(leftColor)) {
		                    //System.out.println("왼쪽 충돌");
		                    monkey.setLeft(false);
		                    if (!monkey.isRight()) {
		                        monkey.right();
		                    }
		                } else if (isRed(rightColor)) {
		                    //System.out.println("오른쪽 충돌");
		                    monkey.setRight(false);
		                    if (!monkey.isLeft()) {
		                        monkey.left();
		                    }
		                }
	
		                // 바닥 없음 확인
		                boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
		                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
		                boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
		                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
	
		                if (leftBottomMissing && monkey.isLeft()) {
		                    //System.out.println("왼쪽 바닥 없음");
		                    monkey.setLeft(false);
		                    if (!monkey.isRight()) {
		                        monkey.right();
		                    }
		                } else if (rightBottomMissing && monkey.isRight()) {
		                    //System.out.println("오른쪽 바닥 없음");
		                    monkey.setRight(false);
		                    if (!monkey.isLeft()) {
		                        monkey.left();
		                    }
		                }

	            } catch (Exception e) {
	                System.out.println("Error : " + e.getMessage());
	            }
		}
		
		private void checkPlayerCollision() {
			if (state != 2) {
				// 원숭이와 플레이어의 충돌 영역 (50 x 50 기준)
	        	isColliding = (monkeyX < playerX + 25) && (monkeyX + 45 > playerX) && 
	        	                      (monkeyY < playerY + 50) && (monkeyY + 50 > playerY);       
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
		        
		        // 일정 시간 동안 공격 상태 유지 (300ms)
		        new Timer().schedule(new TimerTask() {
		            @Override
		            public void run() {
		                isAttacking = false;
		                System.out.println("공격 끝");
		            }
		        }, 300); // 100ms 후 공격 종료
		    }

        	if (isAttacking && !isColliding && monkey.getState() == 0) {
        	    // 공격 방향에 따라 범위를 설정
        	    if (player.getDirection() == PlayerDirection.LEFT) { // 왼쪽으로 공격할 때
        	        isAttacked = (playerX - 60 <= monkeyX && monkeyX <= playerX) && 
        	                     (playerY - 50 <= monkeyY && monkeyY <= playerY + 40); // 왼쪽 공격 범위
        	    } else if (player.getDirection() == PlayerDirection.RIGHT) { // 오른쪽으로 공격할 때
        	        isAttacked = (playerX + 30 <= monkeyX && monkeyX <= playerX + 90) && 
        	                     (playerY - 50 <= monkeyY && monkeyY <= playerY + 40); // 오른쪽 공격 범위
        	    }
        	    
//                // 디버깅용 출력 (공격 범위와 충돌 체크)
//        	    if (player.getDirection() == PlayerDirection.LEFT) {
//        	    	System.out.println("Left");
//        	    	System.out.println("X 공격 범위 체크: " + (playerX - 60) + " ~ " + playerX);
//        	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
//        	    }
//        	    else {
//        	    	System.out.println("Right");
//        	    	System.out.println("X 공격 범위 체크: " + (playerX + 30) + " ~ " + (playerX + 90));
//        	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
//        	    }
//                System.out.println("플레이어 X: " + playerX + ", 원숭이 X: " + monkeyX);
//                System.out.println("플레이어 Y: " + playerY + ", 원숭이 Y: " + monkeyY);
//                System.out.println("isAttacked: " + isAttacked);
        	}
        	
        	if (isAttacked) handleAttacked();
		}
		
		
		// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
		private void startInvincibilityTimer() {
		    this.currentPlayer.setStartInvincible(true);
		}
		
		private int count = 0;
		private void handleEnemy() {
		    System.out.println("토끼와 닿았습니다!");
		    count++;
		    System.out.println(count);
		    // 목숨 감소 등 충돌 처리
		}


		private void handleAttacked() {
		    System.out.println("공격 당했습니다!");
            monkey.setLeft(false); // 왼쪽 이동 막기
            monkey.setRight(false); // 오른쪽 이동 막기
		    monkey.setState(1); // 상태를 '떡'으로 변경
		    monkey.repaint();
		    stage.repaint();
		}
		
		private void handleTtoek() {
		    System.out.println("토끼가 떡을 획득했습니다.");
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    monkey.setState(2); // 최종 상태로 변경
		    monkey.repaint();
		    stage.repaint();
		}
		
		private boolean isRed(Color color) {
		    return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
		}
}