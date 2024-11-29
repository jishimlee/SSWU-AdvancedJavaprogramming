package Item;

import component.PlayerRabbit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Reverse extends JLabel{
    private int reverseX, reverseY;     // Reverse 아이템의 위치
    private boolean isPickedUp = false; // 아이템 픽업 여부
    private boolean isActive = false;   // Reverse 효과 활성화 여부
    private PlayerRabbit currentPlayer; // 현재 플레이어 참조
    private boolean isColliding;        // 충돌 상태 확인 변수
    private static final int REVERSE_DURATION = 7000; // 반전 상태 지속 시간 (밀리초)

    public Reverse(int x, int y) {
        this.reverseX = x;
        this.reverseY = y;
     // Reverse 아이템의 이미지 설정
        this.setIcon(new ImageIcon("image/reverse.png")); // 이미지 경로
        this.setBounds(x, y, 50, 50); // 위치와 크기 설정
    }

    // Reverse 아이템의 상태를 업데이트 (매 루프 호출)
    public void updateObjState(PlayerRabbit player) {
        try {
            this.currentPlayer = player;

            // 플레이어의 위치를 확인
            int playerX = currentPlayer.getX();
            int playerY = currentPlayer.getY();

            // 충돌 확인 (30 x 30 Reverse 아이템과 플레이어 비교)
            isColliding = (reverseX < playerX + 30) && (reverseX + 30 > playerX) &&
                          (reverseY < playerY + 50) && (reverseY + 30 > playerY);
            
            // 아이템이 충돌 상태이고 아직 픽업되지 않았다면
            if (isColliding && !isPickedUp) {
                handleItemPickup();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 아이템이 픽업되었을 때 처리
    private void handleItemPickup() {
        System.out.println("Reverse control item picked up!");
        isPickedUp = true; // 아이템 픽업 상태 설정
        this.setVisible(false);// Reverse 아이템 화면에서 제거
        activateReverseEffect(currentPlayer);
    }

    // Reverse 효과 활성화
    private void activateReverseEffect(PlayerRabbit playerRabbit) {
        isActive = true;
        playerRabbit.setReversedControls(true); // 플레이어의 방향 반전 활성화

        // 일정 시간이 지나면 효과 비활성화
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deactivateReverseEffect(playerRabbit);
            }
        }, REVERSE_DURATION); // REVERSE_DURATION 시간 후 비활성화
    }

    // Reverse 효과 비활성화
    private void deactivateReverseEffect(PlayerRabbit playerRabbit) {
        isActive = false;
        playerRabbit.setReversedControls(false); // 방향 반전 비활성화
        System.out.println("Reverse control effect ended!");
    }
    
    /*// Reverse 아이템의 위치를 업데이트 (필요시)
    public void setPosition(int x, int y) {
    	this.reverseX = x;
        this.reverseY = y;
        this.setBounds(x, y, 50, 50); // 위치 및 크기 갱신
    }

    // Reverse 아이템 상태 리셋 (필요시)
    public void reset() {
        isPickedUp = false;
        isActive = false;
        this.setVisible(true);
    }/*/
    }
