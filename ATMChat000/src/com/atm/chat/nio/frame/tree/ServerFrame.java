package com.atm.chat.nio.frame.tree;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class ServerFrame extends JFrame {

	private JScrollPane scrollPane;
	private JPanel contentPane;
	// 一个socketChannel的总树
	private JTree tree;
	// 根结点
	private DefaultMutableTreeNode root;
	// 已经登录的socketChannel
	private DefaultMutableTreeNode loginNode;
	// 所有的socketChannel
	private DefaultMutableTreeNode allNode;
	// 按钮面板
	private JPanel btnPanel;
	// 模拟的添加 socket 的按钮
	private JButton addBtn;
	// 模拟的移除 socket 的按钮
	private JButton moveBtn;

	private TreeNodeListener treeNodeListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
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
	public ServerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getBtnPanel(), BorderLayout.SOUTH);
		getTree().addTreeSelectionListener(new TreeNodeListener(getTree()));
	}

	/**
	 * get tree,if tree is null,create one
	 * 
	 * @return JTree
	 */
	public JTree getTree() {
		if (tree == null) {
			tree = new JTree(getRoot());
		}
		return tree;
	}

	/**
	 * get the tree's root,if root is null,create one
	 * 
	 * @return tree's root(MutableTreeNode)
	 */
	public DefaultMutableTreeNode getRoot() {
		if (root == null) {
			root = new DefaultMutableTreeNode("socketChannel");
			root.add(getAllNode());
			root.add(getLoginNode());
		}
		return root;
	}

	/**
	 * get the Node that has all login socket
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getLoginNode() {
		if (loginNode == null) {
			loginNode = new DefaultMutableTreeNode("login");
			loginNode.add(new DefaultMutableTreeNode("梅西"));
			loginNode.add(new DefaultMutableTreeNode("c罗"));
			loginNode.add(new DefaultMutableTreeNode("卡卡"));
			loginNode.add(new DefaultMutableTreeNode("内马尔"));
		}
		return loginNode;
	}

	/**
	 * get the Node that has all socket
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getAllNode() {
		if (allNode == null) {
			allNode = new DefaultMutableTreeNode("allSocket");
			allNode.add(new DefaultMutableTreeNode("皮尔洛"));
			allNode.add(new DefaultMutableTreeNode("贝克汉姆"));
			allNode.add(new DefaultMutableTreeNode("儒儒尼奥"));
			allNode.add(new DefaultMutableTreeNode("卡洛斯"));
		}
		return allNode;
	}

	public JPanel getBtnPanel() {
		if (btnPanel == null) {
			btnPanel = new JPanel();
			btnPanel.add(getAddBtn());
			btnPanel.add(getMoveBtn());
		}
		return btnPanel;
	}

	public JButton getAddBtn() {
		if (addBtn == null) {
			addBtn = new JButton("add");
			addBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stubtr
					System.out.println("点击了 add 按钮");
					if (tree.getSelectionCount() > 0) {
						System.out.println("添加");
						int number = (int) (Math.random() * 1000);
						MutableTreeNode treeNode = new DefaultMutableTreeNode(
								"" + number);
						DefaultMutableTreeNode treeNode1 = (DefaultMutableTreeNode) tree
								.getSelectionPath().getPathComponent(1);
						treeNode1.add(treeNode);
						// tree.repaint();
						tree.updateUI();
						System.out.println(treeNode1.toString() + "--"
								+ treeNode.toString());
					}
				}
			});
		}
		return addBtn;
	}

	public JButton getMoveBtn() {
		if (moveBtn == null) {
			moveBtn = new JButton("move");
			moveBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("点击了 move 按钮");
					if (tree.getSelectionCount() > 0) {
						System.out.println("move");
						TreePath[] treePaths = tree.getSelectionPaths();;
						for (int i = 0; i < treePaths.length; i++) {
							MutableTreeNode treeNode = (MutableTreeNode)treePaths[i].getPathComponent(1);
							treeNode.remove((MutableTreeNode)treePaths[i].getLastPathComponent());
						}
						tree.updateUI();
					}
				}
			});
		}
		return moveBtn;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTree());
			scrollPane.getVerticalScrollBar().addAdjustmentListener(
					new AdjustmentListener() {
						@Override
						public void adjustmentValueChanged(AdjustmentEvent e) {
							// TODO Auto-generated method stub
							scrollPane.getVerticalScrollBar().setValue(
									scrollPane.getVerticalScrollBar()
											.getMaximum());
						}
					});
		}
		return scrollPane;
	}

}