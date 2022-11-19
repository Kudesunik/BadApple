package ru.kudesunik.badapple.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ru.kudesunik.badapple.BadApple;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

/**
 * 
 * @author Kudesunik
 *
 */
public class MenuPanel extends JPanel {
	
	private BufferedImage defaultImage;
	
	private final JLabel labelResolution = new JLabel("Resolution:");
	
	private final JComboBox<Resolution> comboBoxResolution = new JComboBox<>();
	
	private final JCheckBox checkBoxSobelFilter = new JCheckBox("With Sobel filter");
	private final JCheckBox checkBoxShowTime = new JCheckBox("Show time and FPS");
	
	private final JButton buttonBadApple = new JButton("BAD APPLE");
	
	private final Component rigidAreaUp = Box.createRigidArea(new Dimension(100, 280));
	private final Component rigidAreaLeft = Box.createRigidArea(new Dimension(80, 100));
	
	public MenuPanel(BadApple badApple, BadAppleListener listener) {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbcRigidAreaUp = new GridBagConstraints();
		gbcRigidAreaUp.fill = GridBagConstraints.BOTH;
		gbcRigidAreaUp.gridheight = 6;
		gbcRigidAreaUp.gridwidth = 2;
		gbcRigidAreaUp.gridx = 1;
		gbcRigidAreaUp.gridy = 0;
		add(rigidAreaUp, gbcRigidAreaUp);
		
		GridBagConstraints gbcRigidAreaLeft = new GridBagConstraints();
		gbcRigidAreaLeft.fill = GridBagConstraints.BOTH;
		gbcRigidAreaLeft.gridheight = 4;
		gbcRigidAreaLeft.gridx = 0;
		gbcRigidAreaLeft.gridy = 6;
		add(rigidAreaLeft, gbcRigidAreaLeft);
		
		GridBagConstraints gbcLabelResolution = new GridBagConstraints();
		gbcLabelResolution.insets = new Insets(2, 2, 2, 2);
		gbcLabelResolution.anchor = GridBagConstraints.EAST;
		gbcLabelResolution.gridx = 1;
		gbcLabelResolution.gridy = 6;
		add(labelResolution, gbcLabelResolution);
		
		GridBagConstraints gbcComboBoxResolution = new GridBagConstraints();
		gbcComboBoxResolution.insets = new Insets(2, 2, 2, 2);
		gbcComboBoxResolution.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxResolution.gridx = 2;
		gbcComboBoxResolution.gridy = 6;
		comboBoxResolution.setBackground(Color.GRAY);
		comboBoxResolution.setModel(new DefaultComboBoxModel<>(Resolution.values()));
		comboBoxResolution.setSelectedIndex(1);
		add(comboBoxResolution, gbcComboBoxResolution);
		
		GridBagConstraints gbcCheckBoxSobelFilter = new GridBagConstraints();
		gbcCheckBoxSobelFilter.insets = new Insets(2, 2, 2, 2);
		gbcCheckBoxSobelFilter.fill = GridBagConstraints.HORIZONTAL;
		gbcCheckBoxSobelFilter.gridwidth = 2;
		gbcCheckBoxSobelFilter.gridx = 1;
		gbcCheckBoxSobelFilter.gridy = 7;
		checkBoxSobelFilter.setBackground(Color.GRAY);
		checkBoxSobelFilter.setSelected(true);
		add(checkBoxSobelFilter, gbcCheckBoxSobelFilter);
		
		GridBagConstraints gbcCheckBoxShowTime = new GridBagConstraints();
		gbcCheckBoxShowTime.fill = GridBagConstraints.HORIZONTAL;
		gbcCheckBoxShowTime.insets = new Insets(2, 2, 2, 2);
		gbcCheckBoxShowTime.gridwidth = 2;
		gbcCheckBoxShowTime.gridx = 1;
		gbcCheckBoxShowTime.gridy = 8;
		checkBoxShowTime.setSelected(true);
		checkBoxShowTime.setBackground(Color.GRAY);
		add(checkBoxShowTime, gbcCheckBoxShowTime);
		
		GridBagConstraints gbcButtonBadApple = new GridBagConstraints();
		gbcButtonBadApple.insets = new Insets(2, 2, 2, 2);
		gbcButtonBadApple.fill = GridBagConstraints.HORIZONTAL;
		gbcButtonBadApple.gridwidth = 2;
		gbcButtonBadApple.gridx = 1;
		gbcButtonBadApple.gridy = 9;
		buttonBadApple.setBackground(Color.GRAY);
		add(buttonBadApple, gbcButtonBadApple);
		
		buttonBadApple.setName("badapple");
		buttonBadApple.addActionListener(listener);
		
		try {
			defaultImage = ImageIO.read(new File(BadApple.PATH_TITLE));
		} catch(IOException ex) {
			ex.printStackTrace();
			badApple.stop();
		}
	}
	
	public Resolution getResolution() {
		return (Resolution) comboBoxResolution.getSelectedItem();
	}
	
	public boolean isWithSobelFilter() {
		return checkBoxSobelFilter.isSelected();
	}
	
	public boolean isWithTimeAndFPS() {
		return checkBoxShowTime.isSelected();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(defaultImage != null) {
			graphics2d.drawImage(defaultImage, 0, 0, null);
		}
	}
}
