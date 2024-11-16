package service;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import component.PlayerRabbit;
//import component.GameFrame1;
import javax.swing.*;
import java.awt.*;

public class BackgroundRabbitService implements Runnable {
	private BufferedImage image;
	private PlayerRabbit player;
	
	public BackgroundRabbitService(PlayerRabbit player) {
		
		this.player = player;
		try {
			image = ImageIO.read(new File("image/background1.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run() {  
		// 색상 확인
		 while (true) {
			 	Color leftcolor = new Color(image.getRGB(player.getX() - 7, player.getY()+25));
	            Color rightcolor = new Color(image.getRGB(player.getX() + 50 + 7, player.getY()+25));
	            int bottomcolor = image.getRGB(player.getX()+7, player.getY()+50+5) +
	            		image.getRGB(player.getX()+50, player.getY()+50+5);
	         
	            if(bottomcolor != -2) { // 바닥 색이 하얀색이라는 것이다. 
	            	player.setDown(false);
	            }else if (!this.player.isUp() && !this.player.isDown()) {
	                this.player.down();
	            }
	       
	            if (leftcolor.getRed() == 255 && leftcolor.getGreen() ==0 && leftcolor.getBlue()==0) {
	            	player.setLeftWallCrash(true);
	            	player.setLeft(false);
	            } else if (rightcolor.getRed() == 255 && rightcolor.getGreen()==0 && rightcolor.getBlue()==0) {
	            	player.setRightWallCrash(true);
	            	player.setRight(false);
	            } else {
	            	player.setLeftWallCrash(false);
	            	player.setRightWallCrash(false);
	            }
		        try {
		            Thread.sleep(10); // 0.5초 대기
		        } catch (Exception e) {
		            System.out.println(e.getMessage());
		            //break;
		        }
		    }
	}
}
