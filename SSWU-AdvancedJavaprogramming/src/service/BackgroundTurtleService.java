package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Turtle;
import main.MoonRabbitGame;
import stage.Stage1;
import stage.Stage2;
import stage.Stage3;
import stage.Stage4;
import stage.Stage5;

public class BackgroundTurtleService implements Runnable {
	private BufferedImage img;
	private Turtle turtle;
	private PlayerRabbit player;	// player 움직임 확인용
	private int stageNumber;
	private JPanel stage;
	private int state;
	int turtleX;
	int turtleY;
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
	
	public BackgroundTurtleService(Turtle turtle, MoonRabbitGame game, PlayerRabbit player) {
		this.turtle = turtle;
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
			// player와 turtle 상태 업데이트
			// player를 스테이지별로 가져오기
			// turtle의 state를 확인하기
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
	            // 매 루프마다 player의 위치를 확인하고, turtle과 비교
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
	        	
	        	// 거북이와 플레이어 상태 확인
	        	turtleX = turtle.getX();
	        	turtleY = turtle.getY();
	        	playerX = currentPlayer.getX();
	        	playerY = currentPlayer.getY();
	        	state = turtle.getState();
	        	
	        } catch (Exception e3) {
	        	System.out.println("Error : " + e3.getMessage());
	        }
	        	
		}
		
		
		// 충돌 확인 (1. 플레이어랑 2. 벽이랑 3. 바닥이랑)
		private void checkCollision() {
			if (state != 2) {
				// 거북이와 플레이어의 충돌 영역 (50 x 50 기준)
	        	isColliding = (turtleX < playerX + 30) && (turtleX + 50 > playerX) && 
	        	                      (turtleY < playerY + 50) && (turtleY + 50 > playerY);

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
		                Color leftColor = new Color(img.getRGB(turtleX - 7, turtleY + 25));
		                Color rightColor = new Color(img.getRGB(turtleX + 50 + 7, turtleY + 25));
		                Color leftBottom = new Color(img.getRGB(turtleX - 2, turtleY + 55));
		                Color rightBottom = new Color(img.getRGB(turtleX + 50 + 2, turtleY + 55));
	
		                // 좌측 및 우측 벽 충돌 검사
		                if (isRed(leftColor)) {
		                    System.out.println("왼쪽 충돌");
		                    turtle.setLeft(false);
		                    if (!turtle.isRight()) {
		                        turtle.right();
		                    }
		                } else if (isRed(rightColor)) {
		                    System.out.println("오른쪽 충돌");
		                    turtle.setRight(false);
		                    if (!turtle.isLeft()) {
		                        turtle.left();
		                    }
		                }
	
		                // 바닥 없음 확인
		                boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
		                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
		                boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
		                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
	
		                if (leftBottomMissing && turtle.isLeft()) {
		                    System.out.println("왼쪽 바닥 없음");
		                    turtle.setLeft(false);
		                    if (!turtle.isRight()) {
		                        turtle.right();
		                    }
		                } else if (rightBottomMissing && turtle.isRight()) {
		                    System.out.println("오른쪽 바닥 없음");
		                    turtle.setRight(false);
		                    if (!turtle.isLeft()) {
		                        turtle.left();
		                    }
		                }
		            }
		            
		            else if (state == 1) {
			            turtle.setLeft(false); // 왼쪽 이동 막기
			            turtle.setRight(false); // 오른쪽 이동 막기
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
        	// 플레이어가 공격 버튼을 누르고 거북이와 충돌하지 않았을 때
        	if (isAttacking && !isColliding && turtle.getState() == 0) {
        	    // 공격 방향에 따라 범위를 설정
        	    if (player.isLeft()) { // 왼쪽으로 공격할 때
        	        isAttacked = (playerX - 60 <= turtleX && turtleX <= playerX) && 
        	                     (playerY - 50 <= turtleY && turtleY <= playerY + 40); // 왼쪽 공격 범위
        	    } else if (player.isRight()) { // 오른쪽으로 공격할 때
        	        isAttacked = (playerX + 30 <= turtleX && turtleX <= playerX + 90) && 
        	                     (playerY - 50 <= turtleY && turtleY <= playerY + 40); // 오른쪽 공격 범위
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
                System.out.println("플레이어 X: " + playerX + ", 거북이 X: " + turtleX);
                System.out.println("플레이어 Y: " + playerY + ", 거북이 Y: " + turtleY);
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
		    turtle.setState(1); // 상태를 '떡'으로 변경
		    turtle.repaint();
		    stage.repaint();
		}
		
		private void handleTtoek() {
		    System.out.println("토끼가 떡을 획득했습니다.");
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    turtle.setState(2); // 최종 상태로 변경
		    turtle.repaint();
		    stage.repaint();
		}
}

/*
package service;

import java.awt.Color;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Turtle;
import main.MoonRabbitGame;
import stage.*;

public class BackgroundTurtleService implements Runnable {
	private BufferedImage img;
	private Turtle turtle;
	private PlayerRabbit player;	// player 움직임 확인용
	private int stageNumber;
	private JPanel stage;
	
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
	
	public BackgroundTurtleService(Turtle turtle, MoonRabbitGame game, PlayerRabbit player) {
		this.turtle = turtle;
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
	    while (true) {
	        try {	        	
	            // 매 루프마다 player의 위치를 확인하고, turtle과 비교
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
	            
	        	// 거북이와 플레이어의 충돌을 확인
	        	int turtleX = turtle.getX();
	        	int turtleY = turtle.getY();
	        	int playerX = currentPlayer.getX();
	        	int playerY = currentPlayer.getY();

	        	// 거북이와 플레이어의 충돌 영역 (50 x 50 기준)
	        	isColliding = (turtleX < playerX + 50) && (turtleX + 50 > playerX) && 
	        	                      (turtleY < playerY + 50) && (turtleY + 50 > playerY);
	        	
	        	// 공격 중임을 11ms 동안 유지 (10ms마다 체크하므로,,)
	        	if (player.isSpacePressed() && !isAttacking) {
	        		isAttacking = true;
	        		new Thread(() -> {
		        		System.out.println("공격 중");
		        		try {
		        			Thread.sleep(11);
		        			isAttacking = false;
		        			System.out.println("공격 끝");
		        		} catch (Exception e) {
		        			System.out.println("Error : " + e.getMessage());
		        		}
	        		}).start();
	        	}

	        	// 떡방아에 닿았을 때
	        	// 이 인식하는 걸 icon 방향에 따라서 왼쪽 공격은 왼쪽만 공격 당하게... 설정해야 함
	        	// !isColliding 넣은 이유는 공격 때 isColliding 범위에 닿으면 몸에 닿은 거라서
	        	// 플레이어가 공격 버튼을 누르고 거북이와 충돌하지 않았을 때
	        	if (isAttacking && !isColliding && turtle.getState() == 0) {
	        	    // 공격 방향에 따라 범위를 설정
	        	    if (player.isLeft()) { // 왼쪽으로 공격할 때
	        	        isAttacked = (turtleX < playerX && turtleX + 50 > playerX) && 
	        	                     (turtleY < playerY + 50 && turtleY + 50 > playerY); // 왼쪽 공격 범위
	        	    } else if (player.isRight()) { // 오른쪽으로 공격할 때
	        	        isAttacked = (turtleX < playerX + 100 && turtleX + 80 > playerX) && 
	        	                     (turtleY < playerY + 100 && turtleY + 80 > playerY); // 오른쪽 공격 범위
	        	    }
	        	}
	        	
	        	int state = turtle.getState();
	        	
		        // 살아 있으면 (= 공격 당하지 않았으면)
		        if (state == 0) {
		            try {
			            try {
			            	// 충돌 확인 로직 -> 몸이랑 닿은 거
			            	if (!isInvincible) {
				            	if(isColliding) {
				                    handleEnemy();
				                    startInvincibilityTimer();
				                }
				                // 공격에 닿았을 때
				                else if (isAttacked) {
				                	handleAttacked();
				                }
			            	}
			                Thread.sleep(10);
			            } catch (Exception e2) {
			                System.out.println("Error : " + e2.getMessage());
			            }
			            
			            if (state != 1) {
			                // 바닥 없는 곳 확인
			                Color leftColor = new Color(img.getRGB(turtleX - 7, turtleY + 25));
			                Color rightColor = new Color(img.getRGB(turtleX + 50 + 7, turtleY + 25));
			                Color leftBottom = new Color(img.getRGB(turtleX - 2, turtleY + 55));
			                Color rightBottom = new Color(img.getRGB(turtleX + 50 + 2, turtleY + 55));
		
			                // 좌측 및 우측 벽 충돌 검사
			                if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
			                    System.out.println("왼쪽 충돌");
			                    turtle.setLeft(false);
			                    if (!turtle.isRight()) {
			                        turtle.right();
			                    }
			                } else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
			                    System.out.println("오른쪽 충돌");
			                    turtle.setRight(false);
			                    if (!turtle.isLeft()) {
			                        turtle.left();
			                    }
			                }
		
			                // 바닥 없음 확인
			                boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
			                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
			                boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
			                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
		
			                if (leftBottomMissing && turtle.isLeft()) {
			                    System.out.println("왼쪽 바닥 없음");
			                    turtle.setLeft(false);
			                    if (!turtle.isRight()) {
			                        turtle.right();
			                    }
			                } else if (rightBottomMissing && turtle.isRight()) {
			                    System.out.println("오른쪽 바닥 없음");
			                    turtle.setRight(false);
			                    if (!turtle.isLeft()) {
			                        turtle.left();
			                    }
			                }
			            }
	
		                Thread.sleep(10);
		            } catch (Exception e) {
		                System.out.println("Error : " + e.getMessage());
		            }
		        }
	
		        // 공격 당해서 떡이 된 상태라면
		        else if (state == 1) {
		            turtle.setLeft(false); // 왼쪽 이동 막기
		            turtle.setRight(false); // 오른쪽 이동 막기
		            try {
		                // 충돌 확인 로직
		                if (isColliding) {
		                    handleTtoek();
		                }
		                Thread.sleep(10);
		            } catch (Exception e2) {
		                System.out.println("Error : " + e2.getMessage());
		            }
		        }
	        } catch (Exception e3) {
	        	System.out.println("Error : " + e3.getMessage());
	        }
	    }
	}


	// 적 상태 (state == 0) && 토끼의 몸체와 닿았을 때
	private void handleEnemy() {
		touchingRabbit = true;
		System.out.println("토끼와 닿았습니다");
		// 토끼 상태 변경해주는 변수 변경 - 닿았으면 true로 하고 목숨을 깎는 것 
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

	
	// 적 상태 (state == 0) && 토끼의 공격을 당했을 때
	private void handleAttacked() {
		System.out.println("공격 당했습니다!");
		new Thread(() -> {
			try {
				Thread.sleep(10);
				this.turtle.setState(1);	// 10ms 뒤에 떡으로 변해야 됨
	            this.turtle.repaint(); // 상태 변경 후 UI 업데이트
	            this.stage.repaint(); // 상위 컴포넌트도 업데이트
			} catch (Exception e) {
				
			}
		}).start();
	}
	
	// 떡 상태 (state == 1) && 토끼의 몸체와 닿았을 때
	private void handleTtoek() {
		System.out.println("토끼가 떡을 획득했습니다.");
	    this.stage.remove(this.turtle);
	    this.turtle.repaint();
	    this.stage.revalidate(); 
	    this.stage.repaint();
	    this.turtle.setState(2);
		// 점수 알고리즘에 사용할 변수
	}
*/