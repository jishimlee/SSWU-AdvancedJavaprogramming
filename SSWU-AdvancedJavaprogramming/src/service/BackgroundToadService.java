package service;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import component.Toad;
import main.MoonRabbitGame;

public class BackgroundToadService implements Runnable {
	private BufferedImage img;
	private Toad toad;
	private int stage;
	private MoonRabbitGame game;
	private String backgroundPath;
	
	public BackgroundToadService(Toad toad, MoonRabbitGame game) {
		this.toad = toad;
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
		while (toad.getState() == 0) {
			try {
				// 1번 점프당 x 기준 80 이동
				Color leftColor = new Color(img.getRGB(toad.getX() - 7, toad.getY() + 25));
				Color rightColor = new Color(img.getRGB(toad.getX() + 50 + 7, toad.getY() + 25));
				
				if (!toad.isJumping()) {
					Color leftBottom = new Color(img.getRGB(toad.getX() - 5 - 80, toad.getY() + 50 + 10));
					Color rightBottom = new Color(img.getRGB(toad.getX() + 50 + 5 - 80, toad.getY() + 50 + 10));
									
					// 벽에 막힘
					if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
						System.out.println("왼쪽충돌");
						toad.setLeft(false);
						if (!toad.isRight()) {
							toad.right();
						}
	
					} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
						System.out.println("오른쪽충돌");
						toad.setRight(false);
						if (!toad.isLeft()) {
							toad.left();
						}
					}
					
					// 빨간색도 파란색도 아니면
					boolean leftBottomMissing = (leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255) 
	                        && (leftBottom.getRed() != 255 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 0);
		            boolean rightBottomMissing = (rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255) 
	                        && (rightBottom.getRed() != 255 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 0);
					
					// 바닥 없으면
					// 왼쪽 바닥의 RGB 값이 RGB(0, 0, 255)가 아니면
					if (leftBottomMissing && toad.isLeft()) {
						System.out.println("Left Bottom Color: " + leftBottom);
						System.out.println("왼쪽 바닥 없음");
						toad.setLeft(false);
						if (!toad.isRight()) {
							toad.right();
						}
					} else if (rightBottomMissing && toad.isRight()) {
		                System.out.println("오른쪽 바닥 없음");
		                toad.setRight(false);
		                if (!toad.isLeft()) {
		                	toad.left(); // 왼쪽으로 회전
		                }
		            }
				}
				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}
	}
}
