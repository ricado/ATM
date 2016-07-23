package com.atm.chat.nio.frame.jlist;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListFrame extends JFrame {

	private JPanel contentPane;

	private JList<String> list;

	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListFrame frame = new ListFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}

	public JList<String> getList() {
		if (list == null) {
			String[] data = { "kaka", "messi", "ccy", "dagh", "dagh", "dagh",
					"dagh", "dagh", "dagh", "dagh", "dagh", "dagh", "dagh",
					"dagh", "dagh", "dagh", "dagh", "dagh", "dagh" };
			list = new JList<String>(data);
			ListModel<String> model = list.getModel();
			data[2] = "aaaaa";
			list.updateUI();
			// 每一次点击触发两次
			list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					System.out.println("----------------------------");
					// TODO Auto-generated method stub
					List<String> lists = list.getSelectedValuesList();
					for (String string : lists) {
						System.out.println("list:" + string);
					}
				}
			});
		}
		return list;
	}

	/**
	 * 获取scrollPane
	 * 
	 * @return
	 */
	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getList());
		}
		return scrollPane;
	}

}