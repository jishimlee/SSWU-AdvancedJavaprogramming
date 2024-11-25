package score;

public class Score {
	private int score;
	
	public Score() {
		score = 0;
	}
	public int getScore() {
		// 점수를 얻는 함수 
		return score;
	}
	
	public void addScore(int points) {
		// 점수를 더하는 함수 
        score += points;
        //System.out.println("점수: " + score);
    }
	
	public void resetScore() {
		score = 0;
	}
}

