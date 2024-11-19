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

public class ToadService implements Runnable {
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
	// 무적 상태를 관리하는 플래그
	private boolean isInvincible;
	// 토끼 상태 확인
	private boolean touchingRabbit = false;
	// 토끼가 공격 중인가?
	private boolean isAttacking = false;
	// 떡방아 상태 확인
	private boolean attacked = false;
	
	public ToadService(Toad toad, MoonRabbitGame game, PlayerRabbit player) {
		this.toad = toad;
		this.game = game;
		this.player = player;
		this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		this.stageNumber = game.getStageNumber();
		System.out.println("현재 스테이지는 stage " + stage + "입니다.");
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
	}
	
	public void run() {
		while (state != 2) {	
			// player와 toad 상태 업데이트
			// player를 스테이지별로 가져오기
			// toad의 state를 확인하기
			updateObjState();
				
			// 충돌여부 확인
			// state == 0일 때, 토끼 목숨 깎이고 2000ms 무적
			// state == 1일 때, 떡 / state == 2, 사라짐, 점수 올라감
			checkCollision();
			
			if (state == 0) checkAttacked();
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateObjState() {
        try {	        	
            // 매 루프마다 player의 위치를 확인하고, toad과 비교
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
        	
        	// 두꺼비와 플레이어 상태 확인
        	toadX = toad.getX();
        	toadY = toad.getY();
        	playerX = currentPlayer.getX();
        	playerY = currentPlayer.getY();
        	state = toad.getState();
        	
        } catch (Exception e3) {
        	System.out.println("Error : " + e3.getMessage());
        }
        	
	}
	
	// 충돌 확인 (1. 플레이어랑 2. 벽이랑 3. 바닥이랑)
			private void checkCollision() {
				if (state != 2) {
					// 두꺼비와 플레이어의 충돌 영역 (50 x 50 기준)
		        	isColliding = (toadX < playerX + 30) && (toadX + 50 > playerX) && 
		        	                      (toadY < playerY + 50) && (toadY + 50 > playerY);

		        	try {            
			            if (state == 0) {
				            try {
				            	// 충돌 확인 로직 -> 몸이랑 닿은 거
				            	if (!isInvincible) {
					            	if(isColliding) {
					                    handleEnemy();
					                    startInvincibilityTimer();
					                }
				            	}
				            } catch (Exception e2) {
				                System.out.println("Error : " + e2.getMessage());
				            }
			            	
			                // 바닥 없는 곳 확인
			                Color leftColor = new Color(img.getRGB(toadX - 7, toadY + 25));
			                Color rightColor = new Color(img.getRGB(toadX + 50 + 7, toadY + 25));
			                Color leftBottom = new Color(img.getRGB(toadX - 2, toadY + 55));
			                Color rightBottom = new Color(img.getRGB(toadX + 50 + 2, toadY + 55));
			                
			                // 도착할 바닥 체크
			                Color expectedLeftBottom = new Color(img.getRGB(toad.getX() - 5 - 40, toad.getY() + 50 + 10));
							Color expectedRightBottom = new Color(img.getRGB(toad.getX() + 50 + 5 + 40, toad.getY() + 50 + 10));
		
			                // 좌측 및 우측 벽 충돌 검사
			                if (isRed(leftColor)) {
			                    System.out.println("왼쪽 충돌");
			                    toad.setLeft(false);
			                    if (!toad.isRight()) {
			                        toad.right();
			                    }
			                } else if (isRed(rightColor)) {
			                    System.out.println("오른쪽 충돌");
			                    toad.setRight(false);
			                    if (!toad.isLeft()) {
			                        toad.left();
			                    }
			                }
		
			                // 바닥 없음 확인
			                boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
			                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
			                boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
			                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
							
			                
			                if (leftBottomMissing && toad.isLeft()) {
			                    System.out.println("왼쪽 바닥 없음");
			                    toad.setLeft(false);
			                    if (!toad.isRight()) {
			                        toad.right();
			                    }
			                } else if (rightBottomMissing && toad.isRight()) {
			                    System.out.println("오른쪽 바닥 없음");
			                    toad.setRight(false);
			                    if (!toad.isLeft()) {
			                        toad.left();
			                    }
			                }
			                
			             // 도착지가 빨간색도 파란색도 아니면
							boolean expectedLeftBottomMissing = (expectedLeftBottom.getRed() != 0 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 255) 
			                        && (expectedLeftBottom.getRed() != 255 || expectedLeftBottom.getGreen() != 0 || expectedLeftBottom.getBlue() != 0);
				            boolean expectedRightBottomMissing = (expectedRightBottom.getRed() != 0 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 255) 
			                        && (expectedRightBottom.getRed() != 255 || expectedRightBottom.getGreen() != 0 || expectedRightBottom.getBlue() != 0);
				            
				            // 점프 중이 아니라면 체크
				            if (!toad.isJumping()) {
				                if (expectedLeftBottomMissing && toad.isLeft()) {
				                    System.out.println("왼쪽 바닥에 착지할 수 없음");
				                    toad.setLeft(false);
				                    if (!toad.isRight()) {
				                        toad.right();
				                    }
				                } else if (expectedRightBottomMissing && toad.isRight()) {
				                    System.out.println("오른쪽 바닥에 착지할 수 없음");
				                    toad.setRight(false);
				                    if (!toad.isLeft()) {
				                        toad.left();
				                    }
				                }
				            }
			                
			            }
			            
			            else if (state == 1) {
				            toad.setLeft(false); // 왼쪽 이동 막기
				            toad.setRight(false); // 오른쪽 이동 막기
				            try {
				                // 충돌 확인 로직
				                if (isColliding) {
				                    handleTtoek();
				                }
				            } catch (Exception e2) {
				                System.out.println("Error : " + e2.getMessage());
				            }
			            }

		            } catch (Exception e) {
		                System.out.println("Error : " + e.getMessage());
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
	        	// 플레이어가 공격 버튼을 누르고 두꺼비와 충돌하지 않았을 때
	        	if (isAttacking && !isColliding && toad.getState() == 0) {
	        	    // 공격 방향에 따라 범위를 설정
	        	    if (player.isLeft()) { // 왼쪽으로 공격할 때
	        	        isAttacked = (playerX - 60 <= toadX && toadX <= playerX) && 
	        	                     (playerY - 50 <= toadY && toadY <= playerY + 40); // 왼쪽 공격 범위
	        	    } else if (player.isRight()) { // 오른쪽으로 공격할 때
	        	        isAttacked = (playerX + 30 <= toadX && toadX <= playerX + 90) && 
	        	                     (playerY - 50 <= toadY && toadY <= playerY + 40); // 오른쪽 공격 범위
	        	    }
	        	    
	                // 디버깅용 출력 (공격 범위와 충돌 체크)
	        	    if (player.isLeft()) {
	        	    	System.out.println("Left");
	        	    	System.out.println("X 공격 범위 체크: " + (playerX - 60) + " ~ " + playerX);
	        	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
	        	    }
	        	    else {
	        	    	System.out.println("Right");
	        	    	System.out.println("X 공격 범위 체크: " + (playerX + 30) + " ~ " + (playerX + 90));
	        	    	System.out.println("Y 공격 범위 체크: " + (playerY - 50) + " ~ " + (playerY + 40));
	        	    }
	                System.out.println("플레이어 X: " + playerX + ", 두꺼비 X: " + toadX);
	                System.out.println("플레이어 Y: " + playerY + ", 두꺼비 Y: " + toadY);
	                System.out.println("isAttacked: " + isAttacked);
	        	}
	        	
	        	if (isAttacked) handleAttacked();
			}
			
			private void handleEnemy() {
			    System.out.println("토끼와 닿았습니다!");
			    // 목숨 감소 등 충돌 처리
			}

			
			// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
			private void startInvincibilityTimer() {
			    isInvincible = true; // 무적 상태 시작
			    new Thread(() -> {
			        try {
			            Thread.sleep(2000); // 무적 시간
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
			        isInvincible = false; // 무적 상태 해제
			    }).start();
			}
			
			private void handleAttacked() {
			    System.out.println("공격 당했습니다!");
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
