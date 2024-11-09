package service;

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
		while (turtle.getState() == 0) {
			
		}
	}
}
