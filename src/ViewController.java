import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Observer;


public class ViewController extends JFrame {
	
	// screen dimension
	public static final int HEADER_HEIGHT = 50;
	public static final int FOOTER_HEIGHT = 50;
	
	public static final Color CHOCOLATE_BROWN = new Color(78,46,40);
	
	public static final int FPS = 30;
	
	public enum ViewSelector {
		MAIN,
		GAME;
	}
	
	
	private Game game;
	private JComponent currentView;
	
	// For dragging
	private ArrayList<BufferedImage> dragImages;
	private DragPane dragLayer;
	private Timer timer;
	private int imageSize;
	private boolean mouseDragged;
	
	public ViewController(Game game) {
		super("Chess Application - Jacob Bush 2017");
		this.game = game;
		
		dragImages = new ArrayList<BufferedImage>();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(600,600));
	    //this.setMinimumSize(new Dimension(600,600));
	    this.setVisible(true);
	    
	    /*addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent evt) {
            	if (currentView != null && currentView instanceof Resizable) {
            		Resizable r = (Resizable) currentView;
            		r.updateSize();
            	}
            }
	    });*/
	    
	    // We want a glass pane
	    this.dragLayer = new DragPane();
	    this.setGlassPane(this.dragLayer);
	    dragLayer.setVisible(true);
	    
	    this.timer = new Timer(1000/FPS, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if ((dragImages.size() >= 1) && mouseDragged)  {
        			dragLayer.repaint();
        		}
    			mouseDragged = false;
        	}
        });
        this.timer.start();
	    selectView (ViewSelector.MAIN);
	}
	
	public void addDragObject (BufferedImage image, int imageSize) {
		// Calling this will position the image under the mouse until it is removed
		this.imageSize = imageSize;
		dragImages.add(image);
	}
	
	public void clearDragObjects () {
		dragImages.clear();
		dragLayer.repaint();
	}
	public void setMouseDragged() {
		mouseDragged = true;
	}
	
	public void selectView (ViewSelector view) {
		this.getContentPane().removeAll();
		switch (view) {
		case MAIN:
			currentView = new MainView (this);
			this.add(currentView);
			break;
		case GAME:
			currentView = new GameView(this, game);
			this.add(currentView);
			break;
		default:
			break;
		}
		this.revalidate();
		this.repaint();
	}
	
	private class DragPane extends JComponent {
		public DragPane () {
			super();
		}
		
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			int mx = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
			int my = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
			
			if (dragImages != null) {
				for (BufferedImage bi : dragImages) {
					g2.drawImage(bi, mx - imageSize/2, my - imageSize/2, imageSize, imageSize, null);
				}
			}
		}
	}
	
	
}