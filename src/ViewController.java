import javax.swing.JFrame;
import java.awt.Dimension;

public class ViewController extends JFrame implements Observer {
	
	// screen dimension
	public static final int HEADER_HEIGHT = 50;
	public static final int FOOTER_HEIGHT = HEADER_HEIGHT;
	
	public enum ViewSelector {
		MAIN,
		GAME;
	}
	
	
	private Game game;
	
	public ViewController(Game game) {
		super("Chess Application - Jacob Bush 2017");
		this.game = game;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(new Dimension(750,600));
	    this.setVisible(true);
	    
	    selectView (ViewSelector.MAIN);
	}
	
	public void selectView (ViewSelector view) {
		this.getContentPane().removeAll();
		switch (view) {
		case MAIN:
			this.add(new MainView(this));
			break;
		case GAME:
			this.add(new GameView(this, game));
			break;
		default:
			break;
		}
		this.revalidate();
		this.repaint();
	}
	
	public void update(Object observable, String message) {
		
	}
    public void update(Object observable) {
    	this.update(observable, "");
    }
	
}