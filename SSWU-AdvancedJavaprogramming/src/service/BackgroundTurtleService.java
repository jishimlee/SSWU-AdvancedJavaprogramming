package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import component.PlayerRabbit;
import component.Turtle;
import main.MoonRabbitGame;

public class BackgroundTurtleService implements Runnable {
	private BufferedImage img;
	private Turtle turtle;
	private PlayerRabbit player;	// player 움직임 확인용
	private int stageNumber;
	private JPanel stage;
	
	private MoonRabbitGame game;
	private String backgroundPath;
	
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
				if ((turtle.getX() == this.player.getX() + 50) && (turtle.getY() == this.player.getY() + 50) ||
						(turtle.getX() + 50 == this.player.getX()) && (turtle.getY() == this.player.getY() + 50) ||
						(turtle.getX() == this.player.getX() + 50) && (turtle.getY() + 50 == this.player.getY()) ||
						(turtle.getX() + 50 == this.player.getX()) && (turtle.getY() + 50 == this.player.getY())) {
					handleEnemy();
					Thread.sleep(100);	// 0.1초 동안 확인 멈춤
					touchingRabbit = false; // 다시 false로 초기화
				}
				Thread.sleep(10);
			} catch (Exception e2) {
				System.out.println("Error : " + e2.getMessage());
			} 
			
			// 살아 있으면 (= 공격 당하지 않았으면)
			while (turtle.getState() == 0) {
				try {
					Color leftColor = new Color(img.getRGB(turtle.getX() - 7, turtle.getY() + 25));
					Color rightColor = new Color(img.getRGB(turtle.getX() + 50 + 7, turtle.getY() + 25));
					
					Color leftBottom = new Color(img.getRGB(turtle.getX() - 5, turtle.getY() + 50 + 10));
					Color rightBottom = new Color(img.getRGB(turtle.getX() + 50 + 5, turtle.getY() + 50 + 10));
									
					// 벽에 막힘
					if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
						System.out.println("왼쪽충돌");
						turtle.setLeft(false);
						if (!turtle.isRight()) {
							turtle.right();
						}
	
					} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
						System.out.println("오른쪽충돌");
						turtle.setRight(false);
						if (!turtle.isLeft()) {
							turtle.left();
						}
					}
					
					// 빨간색도 파란색도 아니면
					boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
	                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
		            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
	                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
					
					// 바닥 없으면
					// 왼쪽 바닥의 RGB 값이 RGB(0, 0, 255)가 아니면
					if (leftBottomMissing && turtle.isLeft()) {
						System.out.println("Left Bottom Color: " + leftBottom);
						System.out.println("왼쪽 바닥 없음");
						turtle.setLeft(false);
						if (!turtle.isRight()) {
							turtle.right();
						}
					} else if (rightBottomMissing && turtle.isRight()) {
		                System.out.println("오른쪽 바닥 없음");
		                turtle.setRight(false);
		                if (!turtle.isLeft()) {
		                    turtle.left(); // 왼쪽으로 회전
		                }
		            }
					
	
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("Error : " + e.getMessage());
				}
			}
			// 공격 당해서 떡이 된 상태라면
			while (turtle.getState() == 1) {
				// player를 인식해서 닿을 경우 없어짐
				try {
					if ((turtle.getX() == player.getX() + 50) && (turtle.getY() == player.getY() + 50) ||
							(turtle.getX() + 50 == player.getX()) && (turtle.getY() == player.getY() + 50) ||
							(turtle.getX() == player.getX() + 50) && (turtle.getY() + 50 == player.getY()) ||
							(turtle.getX() + 50 == player.getX()) && (turtle.getY() + 50 == player.getY())) {
						handleTtoek();
					}
					Thread.sleep(10);
				} catch (Exception e2) {
					System.out.println("Error : " + e2.getMessage());
				}
			}
		}
	}

	// 적 상태 (state == 0) && 토끼의 몸체와 닿았을 때
	private void handleEnemy() {
		touchingRabbit = true;
		System.out.println("토끼와 닿았습니다");
		// 토끼 상태 변경해주는 변수 변경
	}
	
	// 적 상태 (state == 0) && 토끼의 공격을 당했을 때
	private void handleAttacked() {
		System.out.println("공격 당했습니다!");
		this.turtle.setState(1);	// 떡으로 변해야댕
	}
	
	// 떡 상태 (state == 1) && 토끼의 몸체와 닿았을 때
	private void handleTtoek() {
		System.out.println("토끼가 떡을 획득했습니다.");
	    this.stage.remove(this.turtle);
	    // this.stage.repaint();
		// 점수 알고리즘에 사용할 변수
	}
}