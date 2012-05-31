package com.RSWF.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.RSWF.methods.functions.SpringUtilities;
import com.RSWF.settings.Settings;

public class flippingPanel extends JFrame implements ActionListener {
	public class Items {
		private int buying;
		private String item;
		private int selling;

		public Items(String item, int buying, int selling) {
			this.setItem(item);
			this.setBuying(buying);
			this.setSelling(selling);
		}

		public Items(String item, String buying, String selling) {
			this.setItem(String.valueOf(item));
			this.setBuying(Integer.valueOf(buying));
			this.setSelling(Integer.valueOf(selling));
		}

		public int getBuying() {
			return buying;
		}

		public String getItem() {
			return item;
		}

		public int getSelling() {
			return selling;
		}

		public void setBuying(int buying) {
			this.buying = buying;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public void setSelling(int selling) {
			this.selling = selling;
		}

	}

	public static class SpinnerEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		JSpinner.DefaultEditor editor;
		JSpinner spinner;
		JTextField textField;
		boolean valueSet;

		public SpinnerEditor() {
			super(new JTextField());
			spinner = new JSpinner();
			editor = ((JSpinner.DefaultEditor) spinner.getEditor());
			textField = editor.getTextField();
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent fe) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (valueSet) {
								textField.setCaretPosition(1);
							}
						}
					});
				}

				public void focusLost(FocusEvent fe) {
					table.updateUI();
				}
			});

			textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					stopCellEditing();
				}
			});
		}

		public Object getCellEditorValue() {
			return spinner.getValue();
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if (!valueSet) {
				spinner.setValue(value);
			}
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textField.requestFocus();
				}
			});
			return spinner;
		}

		public boolean isCellEditable(EventObject eo) {
			if (eo instanceof KeyEvent) {
				KeyEvent ke = (KeyEvent) eo;
				textField.setText(String.valueOf(ke.getKeyChar()));
				valueSet = Boolean.TRUE;
			} else {
				valueSet = Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		public boolean stopCellEditing() {
			textField.setText(textField.getText().trim());
			if (textField.getText().isEmpty()) textField.setText("0");
			try {
				editor.commitEdit();
				spinner.commitEdit();
			} catch (java.text.ParseException e) {
				JOptionPane.showMessageDialog(null, "Invalid value, discarding.");
			}
			return super.stopCellEditing();
		}
	}

	private class TableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2127128682445426255L;

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Item";
			case 1:
				return "Buying price";
			case 2:
				return "Selling price";
			case 3:
				return "Profit per item";
			}
			return null;
		}

		@Override
		public int getRowCount() {
			return items.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			switch (col) {
			case 0:
				return (items.get(row).getItem());
			case 1:
				return (items.get(row).getBuying());
			case 2:
				return (items.get(row).getSelling());
			case 3:
				return (items.get(row).getSelling()) - (items.get(row).getBuying());
			}
			return null;
		}

		@Override
		public boolean isCellEditable(final int row, final int column) {
			return column != 3;
		}

		@Override
		public void setValueAt(final Object value, final int row, final int column) {

			Items item = items.get(row);
			if (item == null) {
				return;
			}

			if (column == 0) {
				item.setItem(String.valueOf(value));
			} else if (column == 1) {
				item.setBuying(Integer.valueOf((String) value));
			} else if (column == 2) {
				item.setSelling(Integer.valueOf((String) value));
			}

			fireTableCellUpdated(row, column);
		}

	}

	private class TableSelectionListener implements ListSelectionListener {
		public void valueChanged(final ListSelectionEvent evt) {
			final int row = table.getSelectedRow();
			if (!evt.getValueIsAdjusting()) {
				removeButton.setEnabled(row >= 0 && row < table.getRowCount());
			}
		}
	}

	/**
	 * Got some problems with my code so I borrowed it from the non-copyrighted
	 * RSBot. k.
	 **/

	private static final long serialVersionUID = -3784022200204468521L;

	private static JTable table;

	public static boolean isNumeric(String str) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	public static void main(String[] args) {
		new flippingPanel(flippingPanel.class.getSimpleName()).setVisible(Boolean.TRUE);
	}

	private JButton addButton;

	private JButton favButton;
	public boolean itemIsloaded = Boolean.FALSE;

	ArrayList<Items> items = new ArrayList<Items>();

	private JButton jItems;

	private FilteredJList list;

	private List<String> listItems = null;

	private JButton removeButton;

	private JButton saveButton;

	public flippingPanel(String name) {
		setTitle(Settings.getGuiTitle(name));

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (new File("Items.txt").exists())
						listItems = Files.readAllLines(Paths.get("C:/Users/Pie/Desktop/Items.txt"), // Damn it, forgot the items.txt back 13 miles at home...
								Charset.defaultCharset());
					else {
						return;
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list = new FilteredJList();
				for (String string : listItems)
					if (!string.trim().isEmpty()) list.addItem(string);

				System.out.println("Loaded and clear.");
				itemIsloaded = Boolean.TRUE;
			}
		}).start();

		final JScrollPane scrollPane = new JScrollPane();
		table = new JTable(new TableModel());
		table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		final JToolBar bar = new JToolBar();
		bar.setMargin(new Insets(1, 1, 1, 1));
		bar.setFloatable(Boolean.FALSE);
		removeButton = new JButton("Remove");
		addButton = new JButton("Add");
		favButton = new JButton("Favorite items");
		JCheckBox onTopMost = new JCheckBox("Always On Top Most");
		saveButton = new JButton("Save [Not done]");
		saveButton.setEnabled(Boolean.FALSE);
		removeButton.setEnabled(Boolean.FALSE);

		final TableColumnModel cm = table.getColumnModel();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int x = 0; x < 4; x++) {
			table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		}

		cm.getColumn(1).setCellEditor(new SpinnerEditor());
		cm.getColumn(2).setCellEditor(new SpinnerEditor());

		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(table);
		add(scrollPane, BorderLayout.CENTER);
		bar.add(addButton);
		bar.add(removeButton);
		bar.add(Box.createHorizontalGlue());
		bar.add(onTopMost);
		bar.add(favButton);
		bar.add(saveButton);
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		onTopMost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAlwaysOnTop(!isAlwaysOnTop());
			}
		});
		saveButton.addActionListener(this);
		add(bar, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addButton)) {
			final JPanel p = new JPanel(new SpringLayout());
			final JDialog f = new JDialog();
			JPanel jPanel = new JPanel();

			JLabel l = new JLabel("Item:", JLabel.TRAILING);
			p.add(l);
			final JTextField Item = new JTextField(10);
			l.setLabelFor(Item);
			jPanel.add(Item);
			jItems = new JButton(">");

			if (!itemIsloaded) jItems.setEnabled(Boolean.FALSE);

			jItems.setPreferredSize(new Dimension(41, 20));
			jItems.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					final JFrame frame = new JFrame("Items");
					frame.getContentPane().setLayout(new BorderLayout());
					// add to gui
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list.addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							final int row = list.getSelectedIndex();
							if (!e.getValueIsAdjusting()) {
								Object selected_row = ((JList) e.getSource()).getSelectedValue();
								Item.setText(selected_row.toString());
								frame.dispose();
							}
						}
					});
					JScrollPane pane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					frame.getContentPane().add(pane, BorderLayout.CENTER);
					frame.getContentPane().add(list.getFilterField(), BorderLayout.NORTH);
					frame.pack();
					frame.setLocationRelativeTo(f);
					frame.setAlwaysOnTop(Boolean.TRUE);
					frame.setVisible(Boolean.TRUE);

				}
			});

			jPanel.add(jItems);

			p.add(jPanel);

			l = new JLabel("Buying price:", JLabel.TRAILING);
			p.add(l);
			final JTextField buyPrice = new JTextField(10);
			l.setLabelFor(buyPrice);
			p.add(buyPrice);

			l = new JLabel("Selling price:", JLabel.TRAILING);
			p.add(l);
			final JTextField sellPrice = new JTextField(10);
			l.setLabelFor(sellPrice);
			p.add(sellPrice);

			JButton b = new JButton("Add");
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (Item.getText().trim().isEmpty()) {
						Item.requestFocus();
						Item.setBackground(Color.red);
						return;
					} else if (!isNumeric(buyPrice.getText()) || buyPrice.getText().trim().isEmpty()) {
						buyPrice.requestFocus();
						buyPrice.setBackground(Color.red);
						return;
					} else if (!isNumeric(sellPrice.getText()) || sellPrice.getText().trim().isEmpty()) {
						sellPrice.requestFocus();
						sellPrice.setBackground(Color.red);
						return;
					}

					char[] stringArray = Item.getText().toCharArray();
					stringArray[0] = Character.toUpperCase(stringArray[0]);
					String item = new String(stringArray);

					addItem(new Items(item, buyPrice.getText(), sellPrice.getText()));
					f.dispose();
				}
			});

			// (Container parent, int rows, int cols, int initialX, int
			// initialY, int xPad, int yPad)
			SpringUtilities.makeCompactGrid(p, 3, 2, 6, 6, 6, 6);

			f.setTitle("Add Item");
			f.setAlwaysOnTop(Boolean.TRUE);
			f.setResizable(Boolean.FALSE);
			f.add(p, BorderLayout.NORTH);
			f.add(b, BorderLayout.SOUTH);
			f.pack();

			f.setLocationRelativeTo(f.getOwner());

			f.setVisible(Boolean.TRUE);
		} else if (e.getSource().equals(removeButton)) {
			int row = table.getSelectedRow();
			if (items.get(row) != null) {
				items.remove(row);
				((TableModel) table.getModel()).fireTableRowsDeleted(row, row);
			}
		}
	}

	public void addItem(Items item) {
		items.add(item);
		table.updateUI();
	}

	public void setOnTop(Boolean top) {
		setAlwaysOnTop(top);
	}
}