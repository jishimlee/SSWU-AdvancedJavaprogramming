package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import component.Monkey;
import main.MoonRabbitGame;

public class BackgroundMonkeyService implements Runnable {
	private BufferedImage img;
	private Monkey monkey;
	private int stage;
	private MoonRabbitGame game;
	private String backgroundPath;
	
	public BackgroundMonkeyService(Monkey monkey, MoonRabbitGame game) {
		this.monkey = monkey;
		this.game = game;	// 현재 실행 중인 stage 값 받아오기 위함
		stage = game.getStageNumber();
		try {
			if (stage == 1)	backgroundPath = "background1.png";
			else if (stage == 2) backgroundPath = "background2.png";
			else if (stage == 3) backgroundPath = "background3.png";
			else if (stage == 4) backgroundPath = "background4.png";
			else if (stage == 5) backgroundPath = "background5.png";
			
			img = ImageIO.read(new File(backgroundPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// 살아 있으면 (= 공격 당하지 않았으면)
		while (monkey.getState() == 0) {
			try {
				Color leftColor = new Color(img.getRGB(monkey.getX() - 7, monkey.getY() + 25));
				Color rightColor = new Color(img.getRGB(monkey.getX() + 50 + 7, monkey.getY() + 25));
				
				Color leftBottom = new Color(img.getRGB(monkey.getX() - 5, monkey.getY() + 50 + 10));
				Color rightBottom = new Color(img.getRGB(monkey.getX() + 50 + 5, monkey.getY() + 50 + 10));
								
				// 벽에 막힘
				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					System.out.println("왼쪽충돌");
					monkey.setLeft(false);
					if (!monkey.isRight()) {
						monkey.right();
					}

				} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					System.out.println("오른쪽충돌");
					monkey.setRight(false);
					if (!monkey.isLeft()) {
						monkey.left();
					}
				}
				
				// 빨간색도 파란색도 아니면
				boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
	            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
				
				// 바닥 없으면
				// 왼쪽 바닥의 RGB 값이 RGB(0, 0, 255)가 아니면
				if (leftBottomMissing && monkey.isLeft()) {
					System.out.println("Left Bottom Color: " + leftBottom);
					System.out.println("왼쪽 바닥 없음");
					monkey.setLeft(false);
					if (!monkey.isRight()) {
						monkey.right();
					}
				} else if (rightBottomMissing && monkey.isRight()) {
	                System.out.println("오른쪽 바닥 없음");
	                monkey.setRight(false);
	                if (!monkey.isLeft()) {
	                	monkey.left(); // 왼쪽으로 회전
	                }
	            }
				

				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}
	}
}
