package ru.kudesunik.badapple;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Extra fast, funny and !working! way to pool some objects :"D
 * @author Kudesunik
 *
 */
public class BadApplePool {
	
	private static final Deque<BufferedImage> bufferedImages = new ArrayDeque<>();
	
	public static BufferedImage getBufferedImage() {
		return bufferedImages.poll();
	}
	
	public static void addBufferedImage(BufferedImage image) {
		bufferedImages.add(image);
	}
	
	private BadApplePool() {
		//Class instantiation not allowed
	}
}
