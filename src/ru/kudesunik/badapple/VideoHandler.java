package ru.kudesunik.badapple;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import ru.kudesunik.badapple.gui.PlayerPanel;
import ru.kudesunik.badapple.gui.Resolution;

/**
 * Just sleep 1 more ms over and over and over again. Oh, there's also a long 100ms sleep!
 * @author Kudesunik
 *
 */
public class VideoHandler implements Runnable {
	
	private final PlayerPanel drawPanel;
	
	private VideoCapture camera;
	private int targetFPS;
	
	private final BadApple badApple;
	
	private Resolution resolution;
	private boolean withFilter;
	
	public VideoHandler(BadApple badApple, PlayerPanel drawPanel) {
		this.badApple = badApple;
		this.drawPanel = drawPanel;
	}
	
	public void play(Resolution resolution, boolean withFilter) {
		this.resolution = resolution;
		this.withFilter = withFilter;
		camera = new VideoCapture(BadApple.PATH_VIDEO);
		if(!camera.isOpened()) {
			System.err.println("Error! Camera can't be opened!");
			return;
		}
		targetFPS = (int) camera.get(Videoio.CAP_PROP_FPS);
		Thread videoDecoderThread = new Thread(this);
		videoDecoderThread.setName("Video decoder thread");
		videoDecoderThread.start();
		Thread videoPlayerThread = new Thread(() -> {
			while((System.currentTimeMillis() < badApple.getSyncTime()) && badApple.isWorking()) {
				try {
					Thread.sleep(1L);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
					badApple.stop();
				}
			}
			double nextFrameTime = System.currentTimeMillis();
			while(badApple.isWorking()) {
				if(System.currentTimeMillis() < nextFrameTime) {
					try {
						Thread.sleep(1L);
					} catch(InterruptedException ex) {
						ex.printStackTrace();
						badApple.stop();
					}
				} else {
					nextFrameTime += (1000.0 / targetFPS);
					drawPanel.render();
				}
			}
		});
		videoPlayerThread.setName("Video player thread");
		videoPlayerThread.start();
	}
	
	@Override
	public void run() {
		Mat sourceFrame = new Mat();
		Mat frame1 = new Mat();
		Mat frame2 = new Mat();
		Mat result = new Mat();
		Size size = new Size(resolution.getWidth(), resolution.getHeight());
		while(badApple.isWorking()) {
			if(drawPanel.getFramesCount() > 120) {
				try {
					Thread.sleep(10L);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
					badApple.stop();
				}
			}
			if(camera.read(sourceFrame)) {
				Imgproc.cvtColor(sourceFrame, frame1, Imgproc.COLOR_RGB2GRAY, 0);
				if(withFilter) {
					Imgproc.cvtColor(sourceFrame, frame2, Imgproc.COLOR_RGB2GRAY, 0);
					Imgproc.Sobel(frame2, frame2, CvType.CV_8U, 1, 0, 3, 1, 0);
					Core.addWeighted(frame1, 0.6, frame2, 1.0, 0.0, result);
				} else {
					result = frame1;
				}
				Imgproc.resize(result, result, size, 0, 0, Imgproc.INTER_AREA);
				BufferedImage image = BadApplePool.getBufferedImage();
				if(image == null) {
					image = new BufferedImage(result.width(), result.height(), BufferedImage.TYPE_BYTE_GRAY);
				}
				result.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
				drawPanel.addFrame(image);
			} else {
				break;
			}
		}
		badApple.stop();
	}
}
