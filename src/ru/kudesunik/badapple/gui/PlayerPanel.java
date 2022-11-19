package ru.kudesunik.badapple.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JPanel;

import ru.kudesunik.badapple.BadApplePool;

public class PlayerPanel extends JPanel {
	
	private final Deque<BufferedImage> frames = new ArrayDeque<>();
	
	private final BadAppleGUI gui;
	
	private BufferedImage currentFrame;
	
	private long lastUpdate;
	
	private long time;
	private int fps;
	
	private final StringBuilder timeStringBuilder;
	
	public PlayerPanel(BadAppleGUI gui) {
		this.gui = gui;
		setBackground(Color.BLACK);
		this.timeStringBuilder = new StringBuilder("00:00:000");
	}
	
	public int getFramesCount() {
		return frames.size();
	}
	
	public void addFrame(BufferedImage frame) {
		synchronized(frames) {
			frames.add(frame);
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(currentFrame != null) {
			graphics2d.drawImage(currentFrame, 0, 0, null);
			BadApplePool.addBufferedImage(currentFrame);
			currentFrame = null;
		}
		if(gui.getMenuPanel().isWithTimeAndFPS()) {
			graphics2d.setColor(Color.DARK_GRAY);
			graphics2d.drawString("Time: " + timeStringBuilder.toString(), 4, 20);
			graphics2d.drawString("FPS: " + fps, 4, 40);
		}
	}
	
	private void update() {
		updateTime();
		updateFPS();
		lastUpdate = System.currentTimeMillis();
	}
	
	private void updateFPS() {
		int diff = (int) (System.currentTimeMillis() - lastUpdate);
		fps = (diff > 0) ? (1000 / diff) : 0;
	}
	
	private void updateTime() {
		if(lastUpdate != 0) {
			time += System.currentTimeMillis() - lastUpdate;
		}
		timeStringBuilder.setLength(0);
		long minutes = (time / (1000 * 60)) % 60;
		long seconds = (time / 1000) % 60;
		//@formatter:off
		timeStringBuilder
		.append((minutes > 9) ? "" : "0").append(minutes).append(":")
		.append((seconds > 9) ? "" : "0").append(seconds).append(":")
		.append(time % 1000);
		//@formatter:on
	}
	
	public void render() {
		synchronized(frames) {
			if(!frames.isEmpty()) {
				currentFrame = frames.poll();
				update();
			}
		}
		revalidate();
		repaint();
	}
}
