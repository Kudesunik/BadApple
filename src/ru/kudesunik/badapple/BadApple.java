package ru.kudesunik.badapple;

import org.opencv.core.Core;

import ru.kudesunik.badapple.gui.BadAppleGUI;
import ru.kudesunik.badapple.gui.MenuPanel;
import ru.kudesunik.badapple.gui.Resolution;

/**
 * BAD APPLE!! (main class, shoving it everywhere)
 * @author Kudesunik
 *
 */
public class BadApple {
	
	public static final int MENU_WIDHT = 800;
	public static final int MENU_HEIGHT = 600;
	
	public static final String PATH_TITLE = "./resources/badapple.png";
	public static final String PATH_VIDEO = "./resources/badapple.mp4";
	public static final String PATH_AUDIO = "./resources/badapple.wav";
	
	/**
	 * Worst possible solution to make video and audio start up in 
	 * sync with the hope that everything will load in SYNC_DELAY time;
	 * But I don't care, just start that BAD APPLE!!
	 */
	public static final long SYNC_DELAY = 1000L;
	
	private final BadAppleGUI gui;
	
	private final VideoHandler videoHandler;
	private final AudioHandler audioHandler;
	
	private long syncTime;
	
	private boolean isWorking;
	
	public BadApple() {
		this.isWorking = true;
		this.gui = new BadAppleGUI(this);
		this.videoHandler = new VideoHandler(this, gui.getPlayerPanel());
		this.audioHandler = new AudioHandler(this);
	}
	
	private void launch() {
		gui.setVisible(true);
	}
	
	public void start() {
		syncTime = System.currentTimeMillis() + SYNC_DELAY;
		gui.setupPlayer();
		MenuPanel menuPanel = gui.getMenuPanel();
		Resolution resolution = menuPanel.getResolution();
		gui.setResolution(resolution);
		videoHandler.play(menuPanel.getResolution(), menuPanel.isWithSobelFilter());
		audioHandler.play();
	}
	
	public void stop() {
		isWorking = false;
		System.exit(0);
	}
	
	public long getSyncTime() {
		return syncTime;
	}
	
	public boolean isWorking() {
		return isWorking;
	}
	
	public static void main(String[] args) {
		(new BadApple()).launch();
	}
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
