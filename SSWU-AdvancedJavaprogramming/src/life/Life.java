package life;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.MoonRabbitGame;

public class Life {
    private int life;
    private boolean gameOver;
    private MoonRabbitGame game;

    public Life() {
        this.life = 3;  // 게임 시작 시 목숨 3으로 초기화
        this.gameOver = false;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        if (life > 0) {
            life--;
            System.out.println("목숨이 하나 감소되었습니다.");
            System.out.println("남은 목숨: " + life);
        }

        // 목숨이 0일 때 게임 오버 처리
        if (life == 0) {
            System.out.println("game over");
            JOptionPane.showMessageDialog(null, "Game Over! 목숨이 0입니다.");
            
           
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetLife() {
        this.life = 3;  // 목숨 초기화
        this.gameOver = false;
    }
}

