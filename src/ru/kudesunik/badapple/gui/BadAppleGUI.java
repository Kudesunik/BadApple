package ru.kudesunik.badapple.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import ru.kudesunik.badapple.BadApple;

import java.awt.GridBagLayout;
import java.awt.Toolkit;

public class BadAppleGUI extends JFrame {
	
	private final JPanel contentPane;
	
	private final MenuPanel panelMenu;
	private final PlayerPanel panelPlayer;
	
	private final BadAppleListener listener;
	
	public BadAppleGUI(BadApple badApple) {
		
		this.listener = new BadAppleListener(badApple);
		
		this.panelMenu = new MenuPanel(badApple, listener);
		this.panelPlayer = new PlayerPanel(this);
		
		setResizable(false);
		setTitle("Bad Apple");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResolution(Resolution.LOW);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setBackground(Color.BLACK);
		contentPane.setBackground(Color.BLACK);
		
		contentPane.add(panelMenu, BorderLayout.CENTER);
		GridBagLayout gblPanelMenu = new GridBagLayout();
		gblPanelMenu.columnWidths = new int[]{0};
		gblPanelMenu.rowHeights = new int[]{0};
		gblPanelMenu.columnWeights = new double[]{Double.MIN_VALUE};
		gblPanelMenu.rowWeights = new double[]{Double.MIN_VALUE};
		panelPlayer.setLayout(gblPanelMenu);
	}
	
	public void setupPlayer() {
		contentPane.removeAll();
		contentPane.add(panelPlayer, BorderLayout.CENTER);
		GridBagLayout gblPanelPlayer = new GridBagLayout();
		gblPanelPlayer.columnWidths = new int[]{0};
		gblPanelPlayer.rowHeights = new int[]{0};
		gblPanelPlayer.columnWeights = new double[]{Double.MIN_VALUE};
		gblPanelPlayer.rowWeights = new double[]{Double.MIN_VALUE};
		panelPlayer.setLayout(gblPanelPlayer);
	}
	
	public void setResolution(Resolution resolution) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) (screenSize.getWidth() / 2 - resolution.getWidth() / 2), (int) (screenSize.getHeight() / 2 - resolution.getHeight() / 2), resolution.getWidth(), resolution.getHeight());
	}
	
	public PlayerPanel getPlayerPanel() {
		return panelPlayer;
	}
	
	public MenuPanel getMenuPanel() {
		return panelMenu;
	}
}
