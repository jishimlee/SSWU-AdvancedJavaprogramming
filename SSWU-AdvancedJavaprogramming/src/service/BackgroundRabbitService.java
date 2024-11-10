package service;

import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;
import component.PlayerRabbit;
import javax.swing.*;
import java.awt.*;

public class BackgroundRabbitService implements Runnable {
	private BufferedImage image;
	private PlayerRabbit player;
	
	public BackgroundRabbitService(PlayerRabbit player) {
		try {
			image = ImageIO.read(new File("image/background1.png"));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		/*Color color = new Color(image.getRGB(player.getX(), player.getY()));
		System.out.println("색상: " + color);*/
	}
}
