package component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import service.Moveable;

public class Turtle extends JLabel implements Moveable {
   private int x;
   private int y;
   private ImageIcon enemyR;
   private ImageIcon enemyL;

   public Turtle() {
      this.initObject();
   }

   public Turtle(int x, int y, boolean left) {
      this.initObject();
      this.initSetting(x, y, left);
   }

   public void initObject() {
      this.enemyL = new ImageIcon("image/turtleL.png");
      this.enemyR = new ImageIcon("image/turtleR.png");
   }

   public void initSetting(int x, int y, boolean left) {
      this.x = x;
      this.y = y;
      if (left) {
         this.setIcon(this.enemyL);
      } else {
         this.setIcon(this.enemyR);
      }

      this.setSize(50, 50);
      this.setLocation(this.x, this.y);
   }

   public void up() {
   }

   public void down() {
   }

   public void left() {
   }

   public void right() {
   }
}
