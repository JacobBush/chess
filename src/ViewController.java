import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class ViewController extends JFrame {
	
	// screen dimension
	public static final int HEADER_HEIGHT = 50;
	public static final int FOOTER_HEIGHT = HEADER_HEIGHT;
	
	public static final Color CHOCOLATE_BROWN = new Color(78,46,40);
	
	public enum ViewSelector {
		MAIN,
		GAME;
	}
	
	
	private Game game;
	private JPanel currentView;
	
	public ViewController(Game game) {
		super("Chess Application - Jacob Bush 2017");
		this.game = game;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setMinimumSize(new Dimension(600,600));
	    this.setVisible(true);
	    
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
			o.update(game);
		}
		this.revalidate();
		this.repaint();
	}
	
}