package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import component.Tiger;
import main.MoonRabbitGame;

public class BackgroundTigerService implements Runnable {
	private BufferedImage img;
	private Tiger tiger;
	private int stage;
	private MoonRabbitGame game;
	private String backgroundPath;
	
	public BackgroundTigerService(Tiger tiger, MoonRabbitGame game) {
		this.tiger = tiger;
		this.game = game;	// 현재 실행 중인 stage 값 받아오기 위함
		stage = game.getStageNumber();
		try {
			if (stage == 1)	backgroundPath = "image/background1.png";
			else if (stage == 2) backgroundPath = "image/background2.png";
			else if (stage == 3) backgroundPath = "image/background3.png";
			else if (stage == 4) backgroundPath = "image/background4.png";
			else if (stage == 5) backgroundPath = "image/background5.png";
			
			img = ImageIO.read(new File(backgroundPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// 살아 있으면 (= 공격 당하지 않았으면)
		while (tiger.getState() == 0) {
			try {
				Color leftColor = new Color(img.getRGB(tiger.getX() - 7, tiger.getY() + 25));
				Color rightColor = new Color(img.getRGB(tiger.getX() + 69 + 7, tiger.getY() + 25));
				
				Color leftBottom = new Color(img.getRGB(tiger.getX(), tiger.getY() + 50));
				Color rightBottom = new Color(img.getRGB(tiger.getX() + 69, tiger.getY() + 50));
								
				// 벽에 막힘
				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					System.out.println("왼쪽충돌");
					tiger.setLeft(false);
					if (!tiger.isRight()) {
						tiger.right();
					}

				} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					System.out.println("오른쪽충돌");
					tiger.setRight(false);
					if (!tiger.isLeft()) {
						tiger.left();
					}
				}
				
				// 빨간색도 파란색도 아니면
				boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
	            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
				
				// 바닥 없으면
				// 왼쪽 바닥의 RGB 값이 RGB(0, 0, 255)가 아니면
				if (leftBottomMissing && tiger.isLeft()) {
					System.out.println("Left Bottom Color: " + leftBottom);
					System.out.println("왼쪽 바닥 없음");
					tiger.setLeft(false);
					if (!tiger.isRight()) {
						tiger.right();
					}
				} else if (rightBottomMissing && tiger.isRight()) {
	                System.out.println("오른쪽 바닥 없음");
	                tiger.setRight(false);
	                if (!tiger.isLeft()) {
	                	tiger.left(); // 왼쪽으로 회전
	                }
	            }
				

				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}
	}
}
