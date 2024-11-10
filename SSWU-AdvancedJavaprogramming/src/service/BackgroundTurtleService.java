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
				
				Color leftBottom = new Color(img.getRGB(turtle.getX() - 5, turtle.getY() + 50 + 10));
				Color rightBottom = new Color(img.getRGB(turtle.getX() + 50 + 5, turtle.getY() + 50 + 10));
				/* int bottomColor = img.getRGB(turtle.getX() - 10, turtle.getY() + 50)
						+ img.getRGB(turtle.getX() + 50 + 7, turtle.getY() + 50); */
								
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
				
				boolean leftBottomMissing = leftBottom.getRed() != 0 || leftBottom.getGreen() != 0 || leftBottom.getBlue() != 255;
	            boolean rightBottomMissing = rightBottom.getRed() != 0 || rightBottom.getGreen() != 0 || rightBottom.getBlue() != 255;
				
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
	}
}
