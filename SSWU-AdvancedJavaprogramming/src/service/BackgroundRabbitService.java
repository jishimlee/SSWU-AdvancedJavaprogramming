package service;

import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;
import component.PlayerRabbit;
import component.Toad;
import main.MoonRabbitGame;
import javax.swing.*;
import java.awt.*;

public class BackgroundRabbitService implements Runnable {
	private BufferedImage image;
	private PlayerRabbit player;
	private int stageNum;
	private MoonRabbitGame game;


	public BackgroundRabbitService(PlayerRabbit player, MoonRabbitGame game)  {
		
		this.game = game;
		this.player = player;
		this.stageNum = game.getStageNumber();
		
		try {
			switch(stageNum) {
            case 1:
                image = ImageIO.read(new File("image/background1.png"));
                break; 
            case 2:
                image = ImageIO.read(new File("image/background2.png"));
                break;
            case 3:
                image = ImageIO.read(new File("image/background3.png"));
                break;
            case 4:
                image = ImageIO.read(new File("image/background4.png"));
                break;
            case 5:
                image = ImageIO.read(new File("image/background5.png"));
                break;
            default:
                System.out.println("Invalid stage number");
			}
        
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	   public synchronized void run() {  
	      // 색상 확인
	       while (true) {
	             Color leftcolor = new Color(image.getRGB(player.getX() - 7, player.getY()+25));
	               Color rightcolor = new Color(image.getRGB(player.getX() + 40 + 7, player.getY()+25));
	               int bottomcolor = image.getRGB(player.getX()+7, player.getY()+50+5) +
	                     image.getRGB(player.getX()+35, player.getY()+50+5);
	               int ceilingcolor = image.getRGB(player.getX() + 7, player.getY() - 1); 
	            
	               if(bottomcolor != -2) { // 바닥 색이 하얀색이라는 것이다. 
	                  player.setDown(false);
	               }else if (!this.player.isUp() && !this.player.isDown()) {
	                   this.player.down();
	               }
	               
	               if(ceilingcolor == -65536) {
	                  player.setUp(false);
	                  player.down();
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
	              }
	              
	              if (this.player.isStartInvincible()) startInvincibilityTimer();
	          }
	   }
	
	// 부딪혔을 때 무적 시간 타이머 (비동기로!!!!)
	private void startInvincibilityTimer() {
		if (!this.player.isInvincible()) {// 무적 상태 시작
			System.out.println("무적 시작");
			this.player.setInvincible(true);
			new Thread(() -> {
		        try {
		            Thread.sleep(2000); // 무적 시간
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        this.player.setInvincible(false); // 무적 상태 해제
		        this.player.setStartInvincible(false); // 무적 상태 메서드 실행 방지
		        System.out.println("무적 끝");
			}).start();
		}
	}
}

