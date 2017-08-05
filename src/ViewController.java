import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewController extends JFrame {
	
	// screen dimension
	public static final int HEADER_HEIGHT = 50;
	public static final int FOOTER_HEIGHT = 0;
	
	public static final Color CHOCOLATE_BROWN = new Color(78,46,40);
	
	public enum ViewSelector {
		MAIN,
		GAME;
	}
	
	
	private Game game;
	private JComponent currentView;
	
	public ViewController(Game game) {
		super("Chess Application - Jacob Bush 2017");
		this.game = game;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(600,600));
	    //this.setMinimumSize(new Dimension(600,600));
	    this.setVisible(true);
	    
	    addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent evt) {
            	if (currentView != null && currentView instanceof Resizable) {
            		Resizable r = (Resizable) currentView;
            		r.updateSize();
            	}
            }
	    });
	    
	    selectView (ViewSelector.MAIN);	    
	}
	
	public void selectView (ViewSelector view) {
		if (currentView != null && currentView instanceof Observer) {
			Observer o = (Observer) currentView;
			game.removeObserver(o);
		}
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
		if (currentView != null && currentView instanceof Observer) {
			Observer o = (Observer) currentView;
			game.addObserver(o);
			o.update(game); // force view to draw game
		}
		this.revalidate();
		this.repaint();
	}
	
	
	
}