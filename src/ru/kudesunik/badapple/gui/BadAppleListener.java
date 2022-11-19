package ru.kudesunik.badapple.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ru.kudesunik.badapple.BadApple;

/**
 * Yes! I'm checking name of the only button in this project, any problem?
 * @author Kudesunik
 *
 */
public class BadAppleListener implements ActionListener {
	
	private final BadApple badApple;
	
	public BadAppleListener(BadApple badApple) {
		this.badApple = badApple;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source instanceof JButton) {
			JButton button = (JButton) source;
			String name = button.getName();
			if((name != null) && name.equals("badapple")) {
				badApple.start();
			}
		}
	}
}
