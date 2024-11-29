package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Toad;
import main.MoonRabbitGame;
import stage.Stage1;
import stage.Stage2;
import stage.Stage3;
import stage.Stage4;
import stage.Stage5;

public class BackgroundToadService implements Runnable {
	private BufferedImage img;
	private Toad toad;
	private PlayerRabbit player;	// player 움직임 확인용
	private int stageNumber;
	private JPanel stage;
	private int state;
	int toadX;
	int toadY;
	int playerX;
	int playerY;
	private boolean isRed(Color color) {
	    return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
	}
	
	private MoonRabbitGame game;
	private String backgroundPath;
	PlayerRabbit currentPlayer;
	private boolean isColliding;
	private boolean isAttacked;
	// 무적 상태를 확인하는 플래그
	private boolean isInvincible;
	// 토끼 상태 확인
	private boolean touchingRabbit = false;
	// 토끼가 공격 중인가?
	private boolean isAttacking = false;
	// 떡방아 상태 확인
	private boolean attacked = false;
	
	
	public BackgroundToadService(Toad toad, MoonRabbitGame game, PlayerRabbit player) {
		this.toad = toad;
		this.game = game;
		this.player = player;
		this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		this.stageNumber = game.getStageNumber();
		// System.out.println("현재 스테이지는 stage " + stageNumber + "입니다.");
		
		// 뼈대 가져오기
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
		
		// 플레이어 참조하기
        try {	        	
            
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
        	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
	}
	
	
	public void run() {
		while (state != 2) {	
			// player와 toad 위치, state 업데이트
			updateObjState();
				
			// 충돌여부 확인
			// state == 0일 때, 토끼 목숨 깎이고 2000ms 무적
			// state == 1일 때, 떡 / state == 2, 사라짐, 점수 올라감
			if (state == 0) {
				checkStageCollision();
				checkAttacked();
			}
			
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
	    	// 두꺼비와 플레이어 상태 확인
	    	toadX = toad.getX();
	    	toadY = toad.getY();
	    	playerX = currentPlayer.getX();
	    	playerY = currentPlayer.getY();
	    	state = toad.getState();    
	    	
	    	// 플레이어 무적 확인
	    	isInvincible = currentPlayer.isInvincible();
	    	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
	}
	
	private boolean canGo = true;
	
	// 벽, 바닥과 충돌 확인 -> 이걸 수정해야댕...
	private void checkStageCollision() {
	    if (state == 0) {
	        try {
	        	// false면 반대로 돌음 
	            boolean wallBlocked = checkWall() || checkExpectedWall(); // 벽에 막히냐
	            boolean bottomSafe = !checkbottom() && !checkExpectedBottom(); // 바닥이 있냐

	            // 이동 가능 여부 결정
	            canGo = !wallBlocked && bottomSafe;	// 안 막히고 바닥 있음
	            // 디버깅 출력
//	            System.out.println("벽: " + wallBlocked + ", 바닥: " + bottomSafe);
//	            System.out.println("최종 canGo: " + canGo);
//	            System.out.println("isJumping: " + toad.isJumping());
	        	 
	        	// 근데 이렇게 하면 좀 애매한디...다시생각해보기......잉
	        	if (!canGo) {	// 갈 수 없으면
	        		changeDirection();	// 방향 변경
	        	} else if (canGo && !toad.isJumping()) { 
	        		System.out.println("로깅");
	        		toad.setCanJump(true);
	        	}
		    }  catch (Exception e) {
		        e.printStackTrace();
		    }
	    } else updateObjState();
	}
	
	// canjump랑 jumping이랑 cango 잘 만져보기
	private void changeDirection() {
//		System.out.println("Right: " + toad.isRight() + " / Left: " + toad.isLeft());
		// 오른쪽 이동 중이었다면
		if (!toad.isJumping()) {
			if (toad.isRight()) {
				toad.setRight(false);
	            if (!toad.isLeft()) {
	    			System.out.println("left로 변경");
	    			toad.setCanJump(true);	// 검사 끝났으니까 이동할 수 있게 해줌
	                toad.left();
	            }
			}
			else {
				toad.setLeft(false);
	            if (!toad.isRight()) {
	            	System.out.println("right로 변경");
	            	toad.setCanJump(true);	// 검사 끝났으니까 이동할 수 있게 해줌
	                toad.right();
	            }
			}
		}
	}
	
	
	private boolean checkWall() {
        // 바로 옆
        Color leftColor = new Color(img.getRGB(toadX - 7, toadY + 20));
        Color rightColor = new Color(img.getRGB(toadX + 40 + 7, toadY + 20));

        // 왼쪽으로 이동하던 상태면 왼쪽 반환
        // 빨간색이냐? (=벽이냐?)
        return toad.isLeft() ? isRed(leftColor) : isRed(rightColor);
	}
	
	
	private boolean checkExpectedWall() {
        // 도착할 벽도 확인
        boolean expectedLeft, expectedRight;
        
        // 도착 전에 벽에 부딪히지 않을 때
        if (toad.getX() - 5 - (10 * toad.getSpeed()) >= 0) {
            expectedLeft = false;
        } else { // 도착 전에 벽에 부딪힐 때
            expectedLeft = true;	// 벽이라고 판정
        }

        // 도착 전에 벽에 부딪히지 않을 때
        if (toad.getX() + 40 + 5 + (10 * toad.getSpeed()) < img.getWidth()) {
            expectedRight = false;
        } else { // 도착 전에 벽에 부딪힐 때
            expectedRight = true;
        }
   
        return toad.isLeft() ? expectedLeft : expectedRight;
	}
	
	
	// 바닥이 있어야 계속 이동
	private boolean checkbottom() {
        // 바닥 없음 확인
        Color leftBottom = new Color(img.getRGB(toadX - 5, toadY + 45));
        Color rightBottom = new Color(img.getRGB(toadX + 40 + 5, toadY + 45));
                
        boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
                && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
        boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
                && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
        
        // 바닥 없는지 체크
        return toad.isLeft() ? leftBottomMissing : rightBottomMissing;
	}
	
	
	private boolean checkExpectedBottom() {
        // 도착할 바닥 체크
        Color expectedLeftBottom = new Color(img.getRGB(toad.getX() - 5 - (10 * toad.getSpeed()), toad.getY() + 40 + 5));
        Color expectedRightBottom = new Color(img.getRGB(toad.getX() + 40 + 5 + (10 * toad.getSpeed()), toad.getY() + 40 + 5));
        
        // 도착지가 빨간색도 파란색도 아니면
        boolean expectedLeftBottomMissing = (expectedLeftBottom.getRed() != 0 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 255) 
                && (expectedLeftBottom.getRed() != 255 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 0);
        boolean expectedRightBottomMissing = (expectedRightBottom.getRed() != 0 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 255) 
                && (expectedRightBottom.getRed() != 255 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 0);
        
        // 점프할 수 있는 상태면
        return toad.isLeft() ? expectedLeftBottomMissing : expectedRightBottomMissing;
	}
	
	// 플레이어랑 충돌 확인
	private void checkPlayerCollision() {
	    if (state != 2) {
	        // 두꺼비와 플레이어의 충돌 영역 (40 x 40 기준)
	        isColliding = (toadX < playerX + 30) && (toadX + 40 > playerX) && 
	                              (toadY < playerY + 40) && (toadY + 40 > playerY);

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
	            } catch (Exception e2) {
	                System.out.println("Error : " + e2.getMessage());
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

	    if (isAttacking && !isColliding && toad.getState() == 0) {
	        if (player.isLeft()) { // 왼쪽으로 공격할 때
	            isAttacked = (playerX - 60 <= toadX && toadX <= playerX) && 
	                         (playerY - 50 <= toadY && toadY <= playerY + 40); // 왼쪽 공격 범위
	        } else if (player.isRight()) { // 오른쪽으로 공격할 때
	            isAttacked = (playerX + 30 <= toadX && toadX <= playerX + 90) && 
	                         (playerY - 50 <= toadY && toadY <= playerY + 40); // 오른쪽 공격 범위
	        }
	        
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
        toad.setLeft(false); // 왼쪽 이동 막기
        toad.setRight(false); // 오른쪽 이동 막기
	    toad.setState(1); // 상태를 '떡'으로 변경
	    toad.repaint();
	    stage.repaint();
	}

	private void handleTtoek() {
	    System.out.println("토끼가 떡을 획득했습니다.");
	    try {
	        Thread.sleep(100);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    toad.setState(2); // 최종 상태로 변경
	    toad.repaint();
	    stage.repaint();
	}
}



//private void checkWall() {
//    // 바로 옆
//    Color leftColor = new Color(img.getRGB(toadX - 7, toadY + 25));
//    Color rightColor = new Color(img.getRGB(toadX + 50 + 7, toadY + 25));
//
//    // 좌측 및 우측 벽 충돌 검사
//        if (isRed(leftColor)) {
//            System.out.println("왼쪽 충돌");
//            toad.setLeft(false);
//            if (!toad.isRight() && toad.isCanJump()) {
//                toad.right();
//            }
//        } else if (isRed(rightColor)) {
//            System.out.println("오른쪽 충돌");
//            toad.setRight(false);
//            if (!toad.isLeft() && toad.isCanJump()) {
//                toad.left();
//            }
//        }
//    
//    // 도착할 벽도 확인
//        Color expectedLeft;
//        if (toad.getX() - 5 - (15 * toad.getSpeed()) >= 0) {
//            expectedLeft = new Color(img.getRGB(toad.getX() - 5 - (15 * toad.getSpeed()), toad.getY() + 40 + 10));
//        } else {
//            expectedLeft = Color.RED; // 벽이 없다고 가정
//        }
//
//        Color expectedRight;
//        if (toad.getX() + 40 + 5 + (15 * toad.getSpeed()) < img.getWidth()) {
//            expectedRight = new Color(img.getRGB(toad.getX() + 40 + 5 + (15 * toad.getSpeed()), toad.getY() + 40 + 10));
//        } else {
//            expectedRight = Color.RED; // 벽이 없다고 가정
//        }
//
//        // 도착지가 빨간색도 파란색도 아니면
//    boolean expectedLeftMissing = (expectedLeft.getRed() != 0 || expectedLeft.getGreen() != 0 || expectedLeft.getBlue() != 255) 
//            && (expectedLeft .getRed() != 255 || expectedLeft.getGreen() != 0 || expectedLeft.getBlue() != 0);
//    boolean expectedRightMissing = (expectedRight.getRed() != 0 || expectedRight.getGreen() != 0 || expectedRight.getBlue() != 255) 
//            && (expectedRight.getRed() != 255 || expectedRight.getGreen() != 0 || expectedRight.getBlue() != 0);
//        
//        // 점프할 수 있는 상태면
//            if (expectedLeftMissing && toad.isLeft()) {
//                System.out.println("왼쪽 벽에 닿을 예정임");
//                if (!toad.isRight() && toad.isCanJump()) {
//                    toad.setLeft(false);
//                    toad.right();
//                }
//            } else if (expectedRightMissing && toad.isRight()) {
//                System.out.println("오른쪽 벽에 닿을 예정임");
//                if (!toad.isLeft() && toad.isCanJump()) {
//                    toad.setRight(false);
//                    toad.left();
//                }
//            }
//}
//
//private void checkExpectedWall() {
//	
//}
//
//// 바닥이 있어야 계속 이동
//private void checkbottom() {
//    // 바닥 없음 확인
//    Color leftBottom = new Color(img.getRGB(toadX - 2, toadY + 46));
//    Color rightBottom = new Color(img.getRGB(toadX + 40 + 2, toadY + 45));
//            
//    boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
//            && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
//    boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
//            && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
//    
//    // 바닥 없는지 체크
//    if (leftBottomMissing && toad.isLeft()) {
//        //System.out.println("왼쪽 바닥 없음");
//        toad.setLeft(false); // 바닥 없으면 left 멈춤
//        if (!toad.isRight() && toad.isCanJump()) {	// 오른쪽 이동 중이 아니고 점프 가능 상태
//            toad.right();
//        }
//    } else if (rightBottomMissing && toad.isRight()) {
//        //System.out.println("오른쪽 바닥 없음");
//        toad.setRight(false);
//        if (!toad.isLeft() && toad.isCanJump()) {
//            toad.left();
//        }
//    }
//}
//
//private void checkExpectedBottom() {
//    // 도착할 바닥 체크
//    Color expectedLeftBottom = new Color(img.getRGB(toad.getX() - 5 - (15 * toad.getSpeed()), toad.getY() + 40 + 10));
//    Color expectedRightBottom = new Color(img.getRGB(toad.getX() + 40 + 5 + (15 * toad.getSpeed()), toad.getY() + 40 + 10));
//    
//    // 도착지가 빨간색도 파란색도 아니면
//    boolean expectedLeftBottomMissing = (expectedLeftBottom.getRed() != 0 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 255) 
//            && (expectedLeftBottom.getRed() != 255 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 0);
//    boolean expectedRightBottomMissing = (expectedRightBottom.getRed() != 0 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 255) 
//            && (expectedRightBottom.getRed() != 255 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 0);
//    
//    // 점프할 수 있는 상태면
//    if (expectedLeftBottomMissing && toad.isLeft()) {
//        // System.out.println("왼쪽 바닥에 착지할 수 없음");
//        toad.setLeft(false);
//        if (!toad.isRight() && toad.isCanJump()) {
//            toad.right();
//        }
//    } else if (expectedRightBottomMissing && toad.isRight()) {
//        // System.out.println("오른쪽 바닥에 착지할 수 없음");
//        toad.setRight(false);
//        if (!toad.isLeft() && toad.isCanJump()) {
//            toad.left();
//        }
//    }
//}