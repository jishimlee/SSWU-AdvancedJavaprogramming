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

	        	// 떡방아에 닿았을 때
	        	// 이 인식하는 걸 icon 방향에 따라서 왼쪽 공격은 왼쪽만 공격 당하게... 설정해야 함
	        	// !isColliding 넣은 이유는 공격 때 isColliding 범위에 닿으면 몸에 닿은 거라서
	        	isAttacked = player.isSpacePressed() && !isColliding && ((turtleX < playerX + 80) && (turtleX + 80 > playerX) && 
	                      (turtleY < playerY + 80) && (turtleY + 80 > playerY));
	        	
		        // 살아 있으면 (= 공격 당하지 않았으면)
		        if (turtle.getState() == 0) {
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
	
		                Thread.sleep(10);
		            } catch (Exception e) {
		                System.out.println("Error : " + e.getMessage());
		            }
		        }
	
		        // 공격 당해서 떡이 된 상태라면
		        else if (turtle.getState() == 1) {
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
				Thread.sleep(100);
				this.turtle.setState(1);	// 100ms 뒤에 떡으로 변해야 됨
	            this.turtle.repaint(); // 상태 변경 후 UI 업데이트
	            this.stage.repaint(); // 상위 컴포넌트도 업데이트
			} catch (Exception e) {
				
			}
		});
	}
	
	// 떡 상태 (state == 1) && 토끼의 몸체와 닿았을 때
	private void handleTtoek() {
		System.out.println("토끼가 떡을 획득했습니다.");
	    this.stage.remove(this.turtle);
	    this.stage.repaint();
		// 점수 알고리즘에 사용할 변수
	}
}