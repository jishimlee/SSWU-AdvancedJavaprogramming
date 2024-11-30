package life;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.MoonRabbitGame;
import stage.*;

// MoonRabbitGame에 본체 두고 나머지는 get으로 가져와서 쓰도록 함
public class Life extends JLabel {
    private static int lifeCount;	// 모든 스테이지에서 함께 사용
    private MoonRabbitGame game;
    private boolean gameOver;	// 게임 오버인가?
    // 스테이지 바뀔 때마다 스테이지 업데이트 해주기
    // 설정자로 스테이지 setting에 넣어두겠음!!
	private int stageNumber;	// 기본 0
	private JPanel stage;		// 기본 null

	public Life(MoonRabbitGame game) {
        lifeCount = 3;  // 게임 시작 시 목숨 3으로 초기화
    	this.game = game;
        this.gameOver = false;
		this.stage = game.getCurrentStage();	// 현재 실행 중인 stage 값 받아오기 위함
		this.stageNumber = game.getStageNumber();
    }

    public void decreaseLife() {
        if (lifeCount > 0) lifeCount--;
        
		if (stage instanceof Stage1) {
            ((Stage1) stage).loadLifeIcon(); // Stage4의 loadLifeIcon 호출
        } else if (stage instanceof Stage2) {
            ((Stage2) stage).loadLifeIcon(); // Stage4의 loadLifeIcon 호출
        } else if (stage instanceof Stage3) {
            ((Stage3) stage).loadLifeIcon(); // Stage4의 loadLifeIcon 호출
        } else if (stage instanceof Stage4) {
            ((Stage4) stage).loadLifeIcon(); // Stage4의 loadLifeIcon 호출
        } else if (stage instanceof Stage5) {
            ((Stage5) stage).loadLifeIcon(); // Stage4의 loadLifeIcon 호출
        }
        
        // 목숨이 0일 때 게임 오버 처리
        if (lifeCount == 0) {
        	this.gameOver = true;
            System.out.println("game over");
            JOptionPane.showMessageDialog(null, "Game Over! 목숨이 0입니다."); 
        }
    }
    
    public void increaseLife() {
    	if (lifeCount == 3) return;
    	
    	lifeCount++;
		if (stage instanceof Stage4) {
            ((Stage4) stage).loadLifeIcon(); // Stage4의 loadBanana 호출
        }
    	
    }

    public void resetLife() {
        this.lifeCount = 3;  // 목숨 초기화
        this.gameOver = false;
    }

    
//-----------------아래는 접근자, 생성자-----------------
    
	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

    public JPanel getStage() {
		return stage;
	}

	public void setStage(JPanel stage) {
		this.stage = stage;
	}
	
    public int getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}
	
    public boolean isGameOver() {
        return gameOver;
    }
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}

