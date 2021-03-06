package src;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class RenderedImageProxy extends JPanel{

    private Image image;
	private boolean isLoading = false;
	JProgressBar loading;
	private UI parent;
	private String URI;

    public RenderedImageProxy(String s, UI parent) throws IOException {
    	   this(new ImageIcon(s).getImage());
    	   this.parent = parent;
    	   URI = s;
    	   final WatchService watcher = FileSystems.getDefault().newWatchService();
    	   final Path dir = Paths.get(ConfigurationManager.getInstance().defaultProps.getProperty("Output-Directory"));
    	   try {
    	       WatchKey key = dir.register(watcher,
    	                              ENTRY_CREATE,
    	                              ENTRY_DELETE,
    	                              ENTRY_MODIFY);
    	   } catch (IOException x) {
    	       System.err.println(x);
    	   }
    	   Thread R = new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				for (;;) {

				    // wait for key to be signaled
				    WatchKey key;
				    try {
				        key = watcher.take();
				    } catch (InterruptedException x) {
				        return;
				    }

				    for (WatchEvent<?> event: key.pollEvents()) {
				        WatchEvent.Kind<?> kind = event.kind();

				        // This key is registered only
				        // for ENTRY_CREATE events,
				        // but an OVERFLOW event can
				        // occur regardless if events
				        // are lost or discarded.
				        if (kind == OVERFLOW) {
				            continue;
				        }

				        // The filename is the
				        // context of the event.
				        WatchEvent<Path> ev = (WatchEvent<Path>)event;
				        Path filename = ev.context();

				        // Verify that the new
				        //  file is a text file.
				        try {
				            // Resolve the filename against the directory.
				            // If the filename is "test" and the directory is "foo",
				            // the resolved name is "test/foo".
				            Path child = dir.resolve(filename);
				            if (Files.probeContentType(child).equals("image/png")) {
				            	Thread.sleep(3000);
				            	System.out.println("Reloading UI");
				            	isLoading  = false;
				            	RenderedImageProxy.this.remove(loading);
				            	
				            	RenderedImageProxy.this.image = new ImageIcon(URI).getImage();
	//				              Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
					              Dimension size = new Dimension(1500, 1080);
					              RenderedImageProxy.this.image=image.getScaledInstance(1500, 1080, Image.SCALE_DEFAULT);
					              setPreferredSize(size);
					              setMinimumSize(size);
					              setMaximumSize(size);
					              setSize(size);
					              setLayout(new GridBagLayout());
					              RenderedImageProxy.this.setVisible(true);
					              RenderedImageProxy.this.validate();
					              RenderedImageProxy.this.revalidate();
					              RenderedImageProxy.this.repaint();
					              RenderedImageProxy.this.parent.validate();
					              RenderedImageProxy.this.parent.revalidate();
					              RenderedImageProxy.this.parent.repaint();
					              RenderedImageProxy.this.parent.getContentPane().revalidate();
					              RenderedImageProxy.this.parent.getContentPane().repaint();
					              RenderedImageProxy.this.parent.pack();
					              RenderedImageProxy.this.parent.setSize(1820, 1080);
				            	
				            }
				        } catch (IOException x) {
				            System.err.println(x);
				            continue;
				        } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        

				        // Email the file to the
				        //  specified email alias.
//				        System.out.format("Emailing file %s%n", filename);
				        //Details left to reader....
				    }

				    // Reset the key -- this step is critical if you want to
				    // receive further watch events.  If the key is no longer valid,
				    // the directory is inaccessible so exit the loop.
				    boolean valid = key.reset();
				    if (!valid) {
				        break;
				    }
				}
			}
    		   
    	   });
    	   
//    	   R.start();
    }
    
      public RenderedImageProxy(Image img) {
        this.image = img;
//        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        Dimension size = new Dimension(1500, 1080);
        this.image=image.getScaledInstance(1500, 1080, Image.SCALE_DEFAULT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new GridBagLayout());
        this.setVisible(true);
        this.validate();
        this.revalidate();
        this.repaint();
      }
     

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(!isLoading){
        	g2.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
        	this.revalidate(); 
        }
    }
    
    public void loading(){
    	this.isLoading  = true;
    	loading = new JProgressBar();
    	loading.setIndeterminate(true);
    	this.add(loading);

    	// change to JProgresBullshit
    }

	public void loaded() {
		// TODO Auto-generated method stub
		System.out.println("Reloading UI");
    	isLoading  = false;
    	RenderedImageProxy.this.remove(loading);
    	
    	RenderedImageProxy.this.image = new ImageIcon(this.URI).getImage();
//				              Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
          Dimension size = new Dimension(1500, 1080);
          RenderedImageProxy.this.image=image.getScaledInstance(1500, 1080, Image.SCALE_DEFAULT);
          setPreferredSize(size);
          setMinimumSize(size);
          setMaximumSize(size);
          setSize(size);
          setLayout(new GridBagLayout());
          this.setVisible(true);
          this.validate();
          this.revalidate();
          this.repaint();
          this.parent.validate();
          this.parent.revalidate();
          this.parent.repaint();
          this.parent.getContentPane().revalidate();
          this.parent.getContentPane().repaint();
          this.parent.pack();
          this.parent.setSize(1820, 1080);
          this.parent.setBounds(0, 0, 1820, 1080);
          this.parent.getContentPane().repaint();
		
	}
}