package ru.kudesunik.badapple;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Just simple WAV player, nothing more
 * @author Kudesunik
 *
 */
public class AudioHandler implements Runnable {
	
	private final int BUFFER_SIZE = 128000;
	
	private AudioInputStream audioStream;
	private SourceDataLine sourceLine;
	
	private final BadApple badApple;
	
	public AudioHandler(BadApple badApple) {
		this.badApple = badApple;
	}
	
	public void play() {
		File audioFile = new File(BadApple.PATH_AUDIO);
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch(Exception ex) {
			ex.printStackTrace();
			badApple.stop();
		}
		AudioFormat audioFormat = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch(LineUnavailableException ex) {
			ex.printStackTrace();
			badApple.stop();
		} catch(Exception ex) {
			ex.printStackTrace();
			badApple.stop();
		}
		Thread audioPlayerThread = new Thread(this);
		audioPlayerThread.setName("Audio player thread");
		audioPlayerThread.start();
	}
	
	@Override
	public void run() {
		sourceLine.start();
		int bytesRead = 0;
		byte[] data = new byte[BUFFER_SIZE];
		while(System.currentTimeMillis() < badApple.getSyncTime()) {
			try {
				Thread.sleep(1L);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
				badApple.stop();
			}
		}
		while((bytesRead != (-1)) && badApple.isWorking()) {
			try {
				bytesRead = audioStream.read(data, 0, data.length);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			if(bytesRead >= 0) {
				sourceLine.write(data, 0, bytesRead);
			}
		}
		sourceLine.drain();
		sourceLine.close();
		badApple.stop();
	}
}
