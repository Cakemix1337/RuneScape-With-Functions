package com.RSWF.gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.RSWF.methods.functions.Functions;
import com.RSWF.settings.Settings;

public class clientGUI {

	private Applet applet;
	private static JFrame frame = new JFrame();
	private JLabel lABEL_status;
	private JPanel tools;


	/**
	 * @return the applet
	 */
	public Applet getApplet() {
		return applet;
	}

	public static JFrame getFrame() {
		return frame;
	}


	public JPanel getTool() {
		tools = new JPanel();

		tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));

		tools.add(new menuBar().getMenuBar());

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

		frame.setResizable(Boolean.TRUE);
		frame.setAlwaysOnTop(Boolean.FALSE);

		lABEL_status.setVisible(Boolean.FALSE);

		getTool();

		pack();
		Functions.centreWindow(frame);
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
		getFrame().setAlwaysOnTop(Boolean.TRUE);
		getFrame().setResizable(Boolean.FALSE);

		lABEL_status = new JLabel("<HTML><center><br />Loading up.<br /><br /></center></html>");
		lABEL_status.setHorizontalAlignment(SwingConstants.CENTER);
		getFrame().getContentPane().add(lABEL_status, BorderLayout.CENTER);

		pack();

		Functions.centreWindow(getFrame());
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
		boolean reverseAtZero = Boolean.TRUE;
		char Char = '-';
		char Replace = '-';
		// The code
		boolean reverse = Boolean.FALSE;
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
				reverse = Boolean.FALSE;
			} else if (x < whenToReverse) {
				reverse = Boolean.TRUE;
			}

			try {
				Thread.sleep(reloadTime);
			} catch (Exception e) {
			}

		} while (Settings.getBoolean("showLoader"));
	}


}
