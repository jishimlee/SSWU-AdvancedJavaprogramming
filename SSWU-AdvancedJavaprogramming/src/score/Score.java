package score;
import javax.swing.JLabel;

public class Score {
	private int score;
	
	public Score() {
		this.score = 0;
	}
	public int getScore() {
		return score;
	}
	
	public void addScore(int points) {
		// 점수를 더하는 함수 
        score += points;
        System.out.println("점수: " + score);
    }
	
	public void resetScore() {
		this.score = 0;
	}
}

