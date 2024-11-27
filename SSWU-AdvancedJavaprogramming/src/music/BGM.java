package music;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;


public class BGM {
	private Clip clip;
	 
	public BGM() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("music/bgm.wav"));
			clip = AudioSystem.getClip();
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30.0f); // Reduce volume by 10 decibels.
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY); // 반복 재생 (옵션)
		} catch (Exception e) {
			e.printStackTrace();}}
	
		public void play() {
	        if (clip != null) {
	            clip.start(); // 음악 재생
	            clip.loop(Clip.LOOP_CONTINUOUSLY); // 반복 재생 (옵션)
	        }
	    }

	    public void stop() {
	        if (clip != null && clip.isRunning()) {
	            clip.stop(); // 음악 정지
	            clip.close(); // 자원 해제
	        }
	}
}
