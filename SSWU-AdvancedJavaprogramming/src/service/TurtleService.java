//package service;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.*;
//
//import javax.imageio.ImageIO;
//import javax.swing.JPanel;
//
//import component.PlayerRabbit;
//import component.Turtle;
//import direction.PlayerDirection;
//import main.MoonRabbitGame;
//import stage.*;
//
//public class BackgroundTurtleService implements Runnable {
//	private BufferedImage img;
//	private Turtle turtle;
//	private PlayerRabbit player;	// player 움직임 확인용
//	private int stageNumber;
//	private JPanel stage;
//	private int state;
//	int turtleX;
//	int turtleY;
//	int playerX;
//	int playerY;
//	
//	private MoonRabbitGame game;
//	private String backgroundPath;
//	private PlayerRabbit currentPlayer;
//	
//	// 토끼 상태 확인
//	private boolean touchingRabbit = false;
//	// 토끼가 공격 중인가?
//	private boolean isAttacking = false;
//	// 떡방아 상태 확인
//	private boolean attacked = false;
//	
//	
//	
//	public BackgroundTurtleService(Turtle turtle, MoonRabbitGame game, PlayerRabbit player) {
//		this.turtle = turtle;
//		this.game = game;
//		this.player = player;
//		this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
//		this.stageNumber = game.getStageNumber();
//		try {
//			if (stageNumber == 1)	backgroundPath = "image/background1.png";
//			else if (stageNumber == 2) backgroundPath = "image/background2.png";
//			else if (stageNumber == 3) backgroundPath = "image/background3.png";
//			else if (stageNumber == 4) backgroundPath = "image/background4.png";
//			else if (stageNumber == 5) backgroundPath = "image/background5.png";
//			
//			img = ImageIO.read(new File(backgroundPath));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//		
//	public void run() {
//		while (this.state != 2) {	
//			// player와 turtle 상태 업데이트
//			// player를 스테이지별로 가져오기
//			// turtle의 state를 확인하기
//			updateObjState();
//				
//			// 충돌여부 확인
//			// state == 0일 때, 토끼 목숨 깎이고 2000ms 무적
//			// state == 1일 때, 떡 / state == 2, 사라짐, 점수 올라감
//			if (this.state == 0) checkStageCollision();
//			checkPlayerCollision();
//			
//			if (this.state == 0) checkAttacked();
//				
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//			
//	}
//		
//		private void updateObjState() {
//	        try {	        	
//	            // 매 루프마다 player의 위치를 확인하고, turtle과 비교
//	        	switch (stageNumber) {
//	        		case 1:
//	        			currentPlayer = ((Stage1)stage).getPlayer();
//	        			break;
//	        		case 2:
//	        			currentPlayer = ((Stage2)stage).getPlayer();
//	        			break;
//	        		case 3:
//	        			currentPlayer = ((Stage3)stage).getPlayer();
//	        			break;
//	        		case 4:
//	        			currentPlayer = ((Stage4)stage).getPlayer();
//	        			break;
//	        		case 5:
//	        			currentPlayer = ((Stage5)stage).getPlayer();
//	        			break;
//	        		default:
//	        			break;
//	        	}
//	        	
//	        	// 거북이와 플레이어 상태 확인
//	        	turtleX = this.turtle.getX();
//	        	turtleY = this.turtle.getY();
//	        	playerX = this.currentPlayer.getX();
//	        	playerY = this.currentPlayer.getY();
//	        	this.state = this.turtle.getState();
//	        	
//	        } catch (Exception e) {
//	        	System.out.println("Error : " + e.getMessage());
//	        }
//	        	
//		}
//		
//		
//		// 충돌 확인 (1. 플레이어랑 2. 벽이랑 3. 바닥이랑)
//		private void checkStageCollision() {
//	        	try {
//		                // 바닥 없는 곳 확인
//		                Color leftColor = new Color(img.getRGB(turtleX - 7, turtleY + 25));
//		                Color rightColor = new Color(img.getRGB(turtleX + 50 + 7, turtleY + 25));
//		                Color leftBottom = new Color(img.getRGB(turtleX - 2, turtleY + 55));
//		                Color rightBottom = new Color(img.getRGB(turtleX + 50 + 2, turtleY + 55));
//	
//		                // 좌측 및 우측 벽 충돌 검사
//		                if (isRed(leftColor)) {
//		                    System.out.println("왼쪽 충돌");
//		                    this.turtle.setLeft(false);
//		                    if (!this.turtle.isRight()) {
//		                    	this.turtle.right();
//		                    }
//		                } else if (isRed(rightColor)) {
//		                    System.out.println("오른쪽 충돌");
//		                    this.turtle.setRight(false);
//		                    if (!this.turtle.isLeft()) {
//		                    	this.turtle.left();
//		                    }
//		                }
//	
//		                // 바닥 없음 확인
//		                boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
//		                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
//		                boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
//		                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
//	
//		                if (leftBottomMissing && this.turtle.isLeft()) {
//		                    System.out.println("왼쪽 바닥 없음");
//		                    this.turtle.setLeft(false);
//		                    if (!this.turtle.isRight()) {
//		                    	this.turtle.right();
//		                    }
//		                } else if (rightBottomMissing && this.turtle.isRight()) {
//		                    System.out.println("오른쪽 바닥 없음");
//		                    this.turtle.setRight(false);
//		                    if (!this.turtle.isLeft()) {
//		                    	this.turtle.left();
//		                    }
//		                }
//
//	            } catch (Exception e) {
//	                System.out.println("Error : " + e.getMessage());
//	            }
//		}
//		
//		
//		private void checkPlayerCollision() {
//			if (this.state != 2) {
//				if (this.state == 0) {
//	        	    try {
//	        	        // 충돌 확인 로직 -> 몸이랑 닿은 거
//	        	        if (!this.player.isInvincible() && this.checkBodyCollision()) {
//	        	        	this.handleEnemy();
//	        	        	this.startInvincibilityTimer();
//	        	        }
//	        	    } catch (Exception e) {
//	        	        System.out.println("Error : " + e.getMessage());
//	        	    }
//	        	}
//	        	
//	            else if (this.state == 1) {
//		            try {
//		                // 충돌 확인 로직
//		                if (this.checkBodyCollision()) {
//		                    this.handleTtoek();
//		                }
//		            } catch (Exception e) {
//		                System.out.println("Error : " + e.getMessage());
//		            }
//	            }
//			}
//		}
//		
//		// 공격 확인
//		private void checkAttacked() {
//        	// 공격 중임을 300ms 동안,,,? 일단 조절하면서 (300ms 동안 공격 이미지 유지하므로)
//		    if (this.player.isSpacePressed() && this.turtle.getState() == 0) {
//		    	if (!this.checkBodyCollision()) {
//		    		this.handleAttacked();
//		    	}
//		    }
//		}
//		
//		
//		// 처리 메서드
//		private boolean isRed(Color color) {
//		    return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
//		}
//		
//	    public boolean checkBodyCollision() {
//	        boolean result = this.player.getBodyHitbox().intersects(this.turtle.getHitbox());
//	        if (result) System.out.println(this.turtle.hashCode() + "와 닿았습니다.");
//	        return result;
//	    }
//	    
//	    
//	    public boolean checkAttackCollision() {
//	        return this.player.getAttackHitbox().intersects(this.turtle.getHitbox());
//	    }
//	    
//		
//		private void handleEnemy() {
//		    System.out.println("토끼와 닿았습니다!");
//		    // 목숨 감소 등 충돌 처리
//		}
//
//		
//		// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
//		private void startInvincibilityTimer() {
//		    this.player.setInvincible(true); // 무적 상태 시작
//		    new Thread(() -> {
//		        try {
//		            Thread.sleep(2000); // 무적 시간
//		        } catch (InterruptedException e) {
//		            e.printStackTrace();
//		        }
//		        this.player.setInvincible(false); // 무적 상태 해제
//		    }).start();
//		}
//		
//		private void handleAttacked() {
//		    System.out.println(this.turtle.hashCode() + "가 공격 당했습니다!");
//		    this.turtle.setLeft(false); // 왼쪽 이동 막기
//		    this.turtle.setRight(false); // 오른쪽 이동 막기
//		    this.turtle.setState(1); // 상태를 '떡'으로 변경
//		    this.turtle.repaint();
//		    this.stage.repaint();
//		}
//		
//		private void handleTtoek() {
//		    System.out.println("토끼가 떡을 획득했습니다.");
//		    try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		    this.turtle.setState(2); // 최종 상태로 변경
//		    this.turtle.repaint();
//		    this.stage.repaint();
//		}
//}