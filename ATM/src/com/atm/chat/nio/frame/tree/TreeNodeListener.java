package com.atm.chat.nio.frame.tree;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * ��JTreeһ��ֻ��ѡ��һ�е��¼�������
 * 
 * @author ye
 * 2015.11.20
 */
public class TreeNodeListener implements TreeSelectionListener {
	private JTree tree;

	public TreeNodeListener(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		if (tree.getSelectionCount() > 5) {
			System.out.println(e.getPath().getPathComponent(2).toString());
			tree.removeSelectionPaths(e.getPaths());
		}
	}

}
