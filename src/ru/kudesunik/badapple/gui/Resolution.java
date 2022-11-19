package ru.kudesunik.badapple.gui;

/**
 * Resolutions have names you won't see anywhere. More useless information needed!
 * @author Kudesunik
 */
public enum Resolution {
	
	LOW("SVGA", 800, 600), MEDIUM("XGA", 1024, 768), HIGH("HDV 1080i", 1440, 1080);
	
	private final String name;
	
	private final int width;
	private final int height;
	
	private Resolution(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
