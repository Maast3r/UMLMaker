package src;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	RenderedImageProxy diagram;
	
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
					
					try {
						FirstASM.getInstance().oldmain();
//						System.out.println(FirstASM.getInstance().ark);
						ConfigurationManager.getInstance().initDrawConfiguration(FirstASM.getInstance().ark.seenClass);
						UI.this.mainScreen();
						render();
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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
	
	public void mainScreen() throws IOException{
		this.remove(panel);
		panel = new JPanel();
		this.setLayout(new BorderLayout());
		Container contentPane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
		
		
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

		HashMap<String, Boolean> config = ConfigurationManager.getInstance().getDrawConfiguration();
		
		String[] defaultPhases = ConfigurationManager.getInstance().defaultProps.getProperty("Phases").split(",");
		final HashMap<String, Boolean> enabledPhases  = new HashMap<String, Boolean>();
		for(String s : defaultPhases){
			enabledPhases.put(s, true);
		}
		
		
		final HashMap<String, Boolean> drawConfiguration = new HashMap<String, Boolean>();

		// Initialize phases
		final HashMap<String,DefaultMutableTreeNode> phases = new HashMap<String,DefaultMutableTreeNode>();
		for(String s : ConfigurationManager.getInstance().defaultProps.getProperty("Phases").split(",")){
			phases.put(s, add(root, s, true));
					
		}
		
		
		// Fo sho
		for(ClassPrototype c : FirstASM.getInstance().ark.getBoat().values()){
			if(c.phases.size() == 0){
				add(root, c.getName(), true);
			}
			for(String type: c.phases){
				add(phases.get(type.substring(0,1).toUpperCase() + type.substring(1) + "Detector"),c.getName(),true);
			}
			drawConfiguration.put(c.getName(), true);
		}
		
		
		for(DefaultMutableTreeNode n : phases.values()){
			root.add(n);
		}
		
		ConfigurationManager.getInstance().setDrawConfiguration(drawConfiguration);		
		
		
		
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
			}
		});

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			public void treeNodesChanged(final TreeModelEvent e) {
				String treeString = e.getChildren()[0].toString();
				String value = treeString.substring(treeString.indexOf("/") + 1, treeString.length()-1);
				String nodeString = treeString.substring(treeString.indexOf("[") +1, treeString.indexOf("/"));
				
				boolean val = Boolean.parseBoolean(value);
				if(phases.containsKey(nodeString)){
					// Toggle coloring/decorating of entire phase
					enabledPhases.put(nodeString, val);
					String newPhases = "";
					for(String s : enabledPhases.keySet()){
						if(enabledPhases.get(s))newPhases+=s+",";
					}
					if(newPhases.length() > 0){
						
						newPhases = newPhases.substring(0, newPhases.length()-1);
					}
					ConfigurationManager.getInstance().defaultProps.setProperty("Phases", newPhases);
					
					
				}
					// Otherwise, Knock out single node
					drawConfiguration.put(nodeString, val);
					ConfigurationManager.getInstance().setDrawConfiguration(drawConfiguration);
				
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
		JPanel p = new JPanel();
		scrollPane.setPreferredSize(new Dimension(300, 1080));
		p.add(scrollPane, BorderLayout.CENTER);
		p.setSize(new Dimension(500,900));
		
		panel.add(p, BorderLayout.WEST);
		String imagepath = ConfigurationManager.getInstance().defaultProps.getProperty("Input-Folder");
 	   imagepath = imagepath.substring(imagepath.lastIndexOf("\\") + 1) + ".png";
 	   imagepath = ConfigurationManager.getInstance().defaultProps.getProperty("Output-Directory") +"\\"+ imagepath;
 	   
 	   
 	   diagram = new RenderedImageProxy(imagepath, this);
 	  final JScrollPane scrollPane2 = new JScrollPane(diagram);
		panel.add(scrollPane2, BorderLayout.EAST);
		
		
		
		JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        JMenuItem regenThing= new JMenuItem("Regen", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_R);
        regenThing.setToolTipText("Regenerate The UML Diagram");
        regenThing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	try {
					render();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
        });

        file.add(eMenuItem);
        file.add(regenThing);
        menubar.add(file);
		
        setJMenuBar(menubar);
		
		
		
		
		
		this.add(panel, BorderLayout.WEST);
		
		this.pack();
		this.setSize(1820, 1080);
		this.setTitle("Make it stop...");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(1820, 1080);
		this.validate();
		this.revalidate();
		this.repaint();
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
	
	public void render() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		String packagePath = ConfigurationManager.getInstance().defaultProps.getProperty("Input-Folder");
// 	    packagePath = packagePath.substring(packagePath.lastIndexOf("\\") + 1);
// 	    imagepath = ConfigurationManager.getInstance().defaultProps.getProperty("Output-Directory") +"\\"+ imagepath;
	  	   
		final String command = "uml";
		final String pkg = packagePath;
		diagram.loading();
		Thread generator = new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				try {
					FirstASM.getInstance().oldmain();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FirstASM.getInstance().visualize(command, pkg+".");
				diagram.loaded();
			}
			
		});
		generator.start();
	}
	
}
