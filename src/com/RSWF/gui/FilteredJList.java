package com.RSWF.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilteredJList extends JList<Object> {
	class FilterField extends JTextField implements DocumentListener {
		public FilterField(int width) {
			super(width);
			getDocument().addDocumentListener(this);
		}

		public void changedUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}

		public void insertUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}

		public void removeUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}
	}

	class FilterModel extends AbstractListModel<Object> {
		ArrayList<Object> filterItems;
		ArrayList<Object> items;

		public FilterModel() {
			super();
			items = new ArrayList<Object>();
			filterItems = new ArrayList<Object>();
		}

		public void addElement(Object o) {
			items.add(o);
			refilter();

		}

		public Object getElementAt(int index) {
			if (index < filterItems.size())
				return filterItems.get(index);
			else
				return null;
		}

		public int getSize() {
			return filterItems.size();
		}

		private void refilter() {
			filterItems.clear();
			String term = getFilterField().getText();
			for (int i = 0; i < items.size(); i++)
				if (items.get(i).toString().toLowerCase().startsWith(term.toLowerCase())) filterItems.add(items.get(i));
			fireContentsChanged(this, 0, getSize());
		}
	}

	private int DEFAULT_FIELD_WIDTH = 20;

	private FilterField filterField;

	public FilteredJList() {
		super();
		setModel(new FilterModel());
		filterField = new FilterField(DEFAULT_FIELD_WIDTH);

	}

	public void addItem(Object o) {
		((FilterModel) getModel()).addElement(o);
	}

	public JTextField getFilterField() {
		return filterField;
	}

	public void setModel(ListModel m) {
		if (!(m instanceof FilterModel)) throw new IllegalArgumentException();
		super.setModel(m);
	}
}