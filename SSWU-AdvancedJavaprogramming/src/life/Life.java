package life;
import javax.swing.JOptionPane;
public class Life {
	private int life;
	private boolean gameOver = false;
	
	public Life() {
		life = 3;
	}
	public int getLife() {
		return life;
	}
	
	public void addLife() {
		life++;
		System.out.println("목숨이 하나 추가되었습니다");
	}
	
	public void decreaseLife() {
	    if (life > 0) {
	        life--;
	        System.out.println("목숨이 하나 감소되었습니다");
	        System.out.println(life);
	    }

	    // 목숨이 0일 때 게임 오버 처리
	    if (life == 0) {
	        System.out.println("game over");
	        JOptionPane.showMessageDialog(null, "Game Over! 목숨이 0입니다.");
	        // 게임 종료 처리
	        gameOver = true;
	        System.exit(0); // 게임 종료
	    }
	}
	// gameOver 화면 띄울 때 사용하세요!
	public boolean isGameOver() {
		return gameOver;
	}
	
}

