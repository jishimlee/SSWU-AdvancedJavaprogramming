package score;

import javax.swing.JPanel;

import main.MoonRabbitGame;
import stage.*;

public class Score {
    private static int score;
    private MoonRabbitGame game;
    public boolean scoreUp = false;
    public Stage1 stage1;
    public Stage2 stage2;
    public Stage3 stage3;
    public Stage4 stage4;
    public Stage5 stage5;
    public JPanel stage;
    public int stageNum;
    
    public Score(MoonRabbitGame game) {
        score = 0;
        this.game = game;
        this.stage = game.getCurrentStage();
        this.stageNum = game.getStageNumber();
    }

    public int getCurrentScore() {
        return this.score;
    }

    public void setStage(JPanel stage) {
        this.stage = stage;
    }

    public void addScore(int points) {
        this.score += points; // 점수 추가
        this.scoreUp = true;  // 점수 업데이트 플래그 설정
        System.out.println("Score updated: " + this.score + ", scoreUp 상태: " + this.scoreUp);
        if (stage instanceof Stage1) {
            ((Stage1) stage).updateScore(); // Stage4의 loadLifeIcon 호출
        }
        if (stage instanceof Stage2) {
            ((Stage2) stage).updateScore(); // Stage4의 loadLifeIcon 호출
        }
        if (stage instanceof Stage3) {
            ((Stage3) stage).updateScore(); // Stage4의 loadLifeIcon 호출
        }

        if (stage instanceof Stage4) {
            ((Stage4) stage).updateScore(); // Stage4의 loadLifeIcon 호출
        }

        if (stage instanceof Stage5) {
            ((Stage5) stage).updateScore(); // Stage4의 loadLifeIcon 호출
        }

    }



    public void resetScore() {
        //this.score = 0;
        this.scoreUp = false;
    }
}
