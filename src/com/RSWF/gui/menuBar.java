package com.RSWF.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.RSWF.methods.functions.Updater;
import com.RSWF.settings.Settings;

public class menuBar extends JMenuBar implements ActionListener {
	private flippingPanel flip = new flippingPanel(flippingPanel.class.getSimpleName());

	private static final long serialVersionUID = -7075843303864170836L;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JCheckBoxMenuItem cbMenuItem;

	public menuBar() {

		setMenuBar(new JMenuBar());

		/** Menu **/
		menu = new JMenu("Menu");
		getMenuBar().add(menu);

		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		/** Tools **/
		menu = new JMenu("Tools");
		menuItem = new JMenuItem("Flipping panel");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();

		menuItem = new JMenuItem("Timers");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();

		menuItem = new JMenuItem("Notes");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		getMenuBar().add(menu);
		if (Settings.getBoolean("foundUpdate")) {
			JLabel label = new JLabel(Settings.getNote());
			label.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (!e.isMetaDown()) Updater.Update(); // Let's not do it with the right click.
				}
			});
			label.setAlignmentX(Component.CENTER_ALIGNMENT);

			getMenuBar().add(Box.createHorizontalGlue());
			getMenuBar().add(label);
		}
		getMenuBar().add(Box.createHorizontalGlue());
		/** Options **/
		menu = new JMenu("Options");
		cbMenuItem = new JCheckBoxMenuItem("Always on top");
		cbMenuItem.addActionListener(this);
		menu.add(cbMenuItem);

		getMenuBar().add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		// Tools
		if (source.getText().equalsIgnoreCase("Flipping panel")) flip.setVisible(!flip.isVisible());
		if (source.getText().equalsIgnoreCase("Timers")) return;
		if (source.getText().equalsIgnoreCase("Notes")) return;
		// Options
		if (source.getText().equalsIgnoreCase("Always on top")) clientGUI.getFrame().setAlwaysOnTop(!clientGUI.getFrame().isAlwaysOnTop());

		if (source.getText().equalsIgnoreCase("About")) return;
		if (source.getText().equalsIgnoreCase("Exit")) return;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

}
