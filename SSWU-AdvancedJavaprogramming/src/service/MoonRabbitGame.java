package service;

import component.Turtle;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MoonRabbitGame extends JFrame {
	// ** 여기부터 main 위까지 캐릭터 잘 뜨는지 확인용 코드 작성해뒀습니다!! **
	   private JLabel stage1Map;
	   private Turtle enemy;

	   public MoonRabbitGame() {
	      this.initObject();
	      this.initSetting();
	      this.setVisible(true);
	   }

	   private void initObject() {
	      this.enemy = new Turtle(35, 558, false);
	      this.stage1Map = new JLabel(new ImageIcon("image/stage1.png"));
	   }

	   private void initSetting() {
	      this.setTitle("MoonRabbitGame");
	      this.setSize(1010, 670);
	      this.setLayout((LayoutManager)null);
	      this.setDefaultCloseOperation(3);
	      this.setLocationRelativeTo((Component)null);
	      this.setContentPane(this.stage1Map);
	      this.add(this.enemy);
	   }
	// ** 여기까지 지우셔도 괜찮습니다! **
	
	public static void main(String[] args) {
		new MoonRabbitGame();
	}

}
