package service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import component.Turtle;

public class BackgroundTurtleService implements Runnable {
	private BufferedImage img;
	private Turtle turtle;
	
	public BackgroundTurtleService(Turtle turtle) {
		this.turtle = turtle;
		try {
			img = ImageIO.read(new File("image/background1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// 살아 있으면 (= 공격 당하지 않았으면)
		while (turtle.getState() == 0) {
			try {
				Color leftColor = new Color(img.getRGB(turtle.getX() - 7, turtle.getY() + 25));
				Color rightColor = new Color(img.getRGB(turtle.getX() + 50 + 7, turtle.getY() + 25));
				
				Color leftBottom = new Color(img.getRGB(turtle.getX() - 10, turtle.getY() + 50));
				Color rightBottom = new Color(img.getRGB(turtle.getX() + 50 + 7, turtle.getY() + 50));
				int bottomColor = img.getRGB(turtle.getX() - 10, turtle.getY() + 50)
						+ img.getRGB(turtle.getX() + 50 + 7, turtle.getY() + 50);
								
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
				
				// 바닥 없음
				if (bottomColor == -2) {
					if (turtle.isLeft()) {
						System.out.println("왼쪽 바닥 없음");
						turtle.setLeft(false);
						if (!turtle.isRight()) {
							turtle.right();
						}
					}
					else {
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
	}
}
