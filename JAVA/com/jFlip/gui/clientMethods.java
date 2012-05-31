package com.jFlip.gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

import com.jFlip.classes.Log;
import com.jFlip.inc.Settings;

public class clientMethods implements ActionListener {

	private Applet applet;
	private JButton btnFlippingPanel;
	private JButton btnOtherTools;
	private flippingPanel f = new flippingPanel();
	private JFrame frame = new JFrame();
	private JLabel lABEL_status;
	private JPanel tools;
	private flippingPanel flip;

	public void centreWindow(Window frame) {
		Log.debug("Call method centreWindow(" + frame.getClass().getSimpleName() + ")");
		Dimension area = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((area.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((area.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	/**
	 * @return the applet
	 */
	public Applet getApplet() {
		return applet;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JCheckBoxMenuItem cbMenuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Menu");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Build second menu in the menu bar.
		menu = new JMenu("Tools");
		menuItem = new JMenuItem("Flipping panel");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Timers");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		menuBar.add(Box.createHorizontalGlue());

		menu = new JMenu("Options");
		cbMenuItem = new JCheckBoxMenuItem("Always on top");
		cbMenuItem.addActionListener(this);
		menu.add(cbMenuItem);

		menuBar.add(menu);
		return menuBar;
	}

	public JPanel getTool() {
		tools = new JPanel();

		tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));

		tools.add(createMenuBar());

		getFrame().getContentPane().add(applet, BorderLayout.CENTER);

		getFrame().getContentPane().add(tools, BorderLayout.SOUTH);

		return tools;
	}

	public void initApplet() {
		applet.init();
		applet.start();
		applet.setPreferredSize(new Dimension(769, 504));
	}

	public void initClient() {
		if (applet == null) throw new IllegalArgumentException("applet == null.");

		frame.setResizable(true);
		frame.setAlwaysOnTop(false);

		lABEL_status.setVisible(false);

		getTool();

		flip = new flippingPanel();

		pack();
		centreWindow(frame);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void initLoader(String Name) {
		getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Won't exit if you close it so only one way to go it seems.
				// TODO: Add save.
				System.out.println("Ending application.");
				System.exit(0);
			}
		}

		);

		getFrame().setTitle(Settings.getGuiTitle(Name));
		getFrame().setAlwaysOnTop(true);
		getFrame().setResizable(false);

		lABEL_status = new JLabel("<HTML><center><br />Loading up.<br /><br /></center></html>");
		lABEL_status.setHorizontalAlignment(SwingConstants.CENTER);
		getFrame().getContentPane().add(lABEL_status, BorderLayout.CENTER);

		pack();

		centreWindow(getFrame());
	}

	public void initLoaderProcess() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				visualLoader();
			}
		}).start();
	}

	public void pack() {

		getFrame().pack();
	}

	public void setApplet(Applet applet) {
		this.applet = applet;
	}

	public void toolGap(int width) {
		tools.add(Box.createHorizontalStrut(width));
	}

	public void visualLoader() {
		int amount = 20;
		int reloadTime = 150;
		boolean reverseAtZero = true;
		char Char = '-';
		char Replace = '-';
		// The code
		boolean reverse = false;
		int x = reverseAtZero ? 1 : 2;
		String amountString = "";
		int whenToReverse = reverseAtZero ? 1 : 2;

		for (int i = 0; i < amount + 1; i++) {
			amountString += String.valueOf(Char);
		}

		do {
			if (reverse) {
				x++;
			} else {
				x--;
			}
			String out = "[";
			out += amountString.substring(0, x % amountString.length());
			out += "|";
			out += amountString.substring(0, amount - x % amountString.length()).replace(String.valueOf(Char), String.valueOf(Replace));
			out += "]";

			lABEL_status.setText("<html><center>" + Settings.getStatus() + "<br/>" + out + "</html>");
			if (x >= amount) {
				reverse = false;
			} else if (x < whenToReverse) {
				reverse = true;
			}

			try {
				Thread.sleep(reloadTime);
			} catch (Exception e) {
			}

		} while (Settings.getBoolean("showLoader"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		switch (source.getText()) {
		case "Always on top":
			frame.setAlwaysOnTop(!frame.isAlwaysOnTop());
			break;
		case "Flipping panel":
			flip.setVisible(!flip.isVisible());
			break;
		case "About":
			System.out.println("k");
			break;
		}

	}

}
