package src;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.scijava.swing.checkboxtree.CheckBoxNodeData;
import org.scijava.swing.checkboxtree.CheckBoxNodeEditor;
import org.scijava.swing.checkboxtree.CheckBoxNodeRenderer;


public class UI extends JFrame{
	JPanel panel;
	boolean validConfiguration = false;
	public static void main(String[] args){
		

	}
	
	public UI(){
		panel = new JPanel();
		
		final JButton fileChooserButton = new JButton();
		fileChooserButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Properties Filter", "properties"));
//				fileChooser.setCurrentDirectory(new File("configurations"));
				int result = fileChooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
				    String filepath = fileChooser.getSelectedFile().getPath();
				    try {
						validConfiguration = ConfigurationManager.getInstance().setDefaultConfiguration(filepath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    if(validConfiguration) fileChooserButton.setText(filepath);
				}
			}
		});
		
		fileChooserButton.setText("Choose a file");
		panel.add(fileChooserButton);
		
		JButton analyze = new JButton();
		analyze.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(validConfiguration){
					System.out.println("do cool stuff");
					UI.this.mainScreen();
				} else {
					System.out.println("Not a valid configuration");
				}
			}
			
		});
		analyze.setText("Analyze");
		panel.add(analyze);
		this.add(panel);
		this.pack();
		this.setSize(900, 900);
		this.setTitle("Make it stop...");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public void mainScreen(){
		this.remove(panel);
		panel = new JPanel();
		Container contentPane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
		
		
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

		
		
		// Instead, Generate based off the configuration
		final DefaultMutableTreeNode accessibility =
			add(root, "Accessibility", true);
		add(accessibility, "Move system caret with focus/selection changes", false);
		add(accessibility, "Always expand alt text for images", true);
		root.add(accessibility);

		final DefaultMutableTreeNode browsing =
			new DefaultMutableTreeNode("Browsing");
		add(browsing, "Notify when downloads complete", true);
		add(browsing, "Disable script debugging", true);
		add(browsing, "Use AutoComplete", true);
		add(browsing, "Browse in a new process", false);
		root.add(browsing);

		
		
		
		
		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		final JTree tree = new JTree(treeModel);

		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
		tree.setCellEditor(editor);
		tree.setEditable(true);

		// listen for changes in the selection
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(final TreeSelectionEvent e) {
//				System.out.println(System.currentTimeMillis() + ": selection changed to:" + e.getPath().getLastPathComponent());
			}
		});

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			public void treeNodesChanged(final TreeModelEvent e) {
				String treeString = e.getChildren()[0].toString();
				System.out.println(treeString);
				if(treeString.contains("[")){
//					 Subnode
					String nodeString = treeString.substring(treeString.indexOf("[") +1, treeString.indexOf("/"));
					System.out.println("node: " + nodeString);
				}
				// Otherwise, entire pattern, so toggle entire subtree
				System.out.println(System.currentTimeMillis() + ": nodes changed: " + treeString);
			}

			public void treeNodesInserted(final TreeModelEvent e) {
			}

			public void treeNodesRemoved(final TreeModelEvent e) {
			}

			public void treeStructureChanged(final TreeModelEvent e) {
			}
		});

		// show the tree onscreen
		final JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(500,900));
		this.getContentPane().add(scrollPane, BorderLayout.WEST);
		
		
		this.add(panel);
		this.pack();
		this.setSize(1600, 900);
		this.setTitle("Make it stop...");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private static DefaultMutableTreeNode add(
			final DefaultMutableTreeNode parent, final String text,
			final boolean checked)
		{
			final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
			final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
			parent.add(node);
			return node;
		}
	
	
}
