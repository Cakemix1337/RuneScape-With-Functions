package com.jFlip.gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jFlip.classes.Log;
import com.jFlip.inc.Settings;

public class clientMethods {

	private Applet applet;
	private JFrame frame = new JFrame();
	private JLabel lABEL_status;
	private JPanel tools;
	private JButton btnFlippingPanel;
	private JButton btnOtherTools;
	private flippingPanel f = new flippingPanel();

	public void centreWindow(Window frame) {
		Log.debug("Call method centreWindow("
				+ frame.getClass().getSimpleName() + ")");
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

	public void toolGap(int width) {
		tools.add(Box.createHorizontalStrut(width));
	}

	public void initClient() {
		if (applet == null)
			throw new IllegalArgumentException("applet == null.");

		frame.setResizable(true);
		frame.setAlwaysOnTop(false);

		lABEL_status.setVisible(false);

		getTool();

		pack();
		centreWindow(frame);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public JPanel getTool() {
		tools = new JPanel();

		tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));

		btnFlippingPanel = new JButton("Flipping Panel");
		btnOtherTools = new JButton("Other tools");

		JButton btnOptions = new JButton("Options");

		tools.add(btnFlippingPanel);

		toolGap(2);

		btnFlippingPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setVisible(!f.isVisible());
			}
		});

		tools.add(btnOtherTools);

		tools.add(Box.createHorizontalGlue());

		JCheckBox onTopMost = new JCheckBox("Always On Top Most");
		onTopMost.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFrame().setAlwaysOnTop(!getFrame().isAlwaysOnTop());
			}
		});
		tools.add(onTopMost);
		tools.add(btnOptions);

		getFrame().getContentPane().add(applet, BorderLayout.CENTER);

		getFrame().getContentPane().add(tools, BorderLayout.SOUTH);

		return tools;
	}

	public void initLoader(String Name) {
		getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Won't exit if you close it so only one way to go it seems.
				//TODO: Add save.
				System.exit(0);
			}
		}

		);
		Log.debug("Set title [" + Settings.getGuiTitle(Name)
				+ "]; AlwaysOnTop [TRUE]; setResizable [FALSE]");
		getFrame().setTitle(Settings.getGuiTitle(Name));
		getFrame().setAlwaysOnTop(true);
		getFrame().setResizable(false);

		lABEL_status = new JLabel(
				"<HTML><center><br />Loading up.<br /><br /></center></html>");
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
			out += amountString
					.substring(0, amount - x % amountString.length()).replace(
							String.valueOf(Char), String.valueOf(Replace));
			out += "]";

			lABEL_status.setText("<html><center>" + Settings.getStatus()
					+ "<br/>" + out + "</html>");
			if (x >= amount) {
				reverse = false;
			} else if (x < whenToReverse) {
				reverse = true;
			}

			try {
				Thread.sleep(reloadTime);
			} catch (Exception e) {
			}

		} while (Settings.isShowLoader());
	}

	public void initApplet() {
		applet.init();
		applet.start();
		applet.setPreferredSize(new Dimension(769, 504));
	}

	//
	// f = new flippinGUI();
	//
	// f.setSize(f.getSize().width, g.getSize().height - 15);
	// f.setLocation(g.getLocation().x + g.getSize().width + 15,
	// g.getLocation().y + 13);
	//
	// g.setOntop(false);
	// g.setResizable(true);
	// Runnable run1 = new Runnable() {
	// @Override
	// public void run() {
	// while (g.isVisible()) {
	// if (f.isVisible()) {
	//
	// if (!g.getLocation().equals(lastGlocation)
	// || !g.getSize().equals(lastGlocation)) {
	// f.setSize(f.getSize().width,
	// g.getSize().height - 15);
	// f.setLocation(g.getLocation().x + g.getSize().width
	// + 15, g.getLocation().y + (int) 7.5);
	// f.setPreferredSize(new Dimension(f.getSize().width,
	// g.getSize().height - 15));
	// f.pack();
	// lastGlocation = g.getLocation();
	// lastGsize = g.getSize();
	// }
	// }
	//
	// if (g.showFlippingPanel != f.isVisible()) {
	// System.out.println("Turning "
	// + (g.showFlippingPanel ? "on" : "off")
	// + " the flipping panel.");
	// f.setVisible(g.showFlippingPanel);
	// showFlippingPanel = g.showFlippingPanel;
	// }
	//
	// try {
	// Thread.sleep(350);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// };
	// Thread mouseChanges = new Thread(run1);
	// mouseChanges.start();
}
