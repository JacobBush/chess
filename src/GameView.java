import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JPanel {
	
	private ViewController vc;
	private Game game;
	
	public GameView(ViewController vc, Game game) {
		super();
		this.game = game;
		this.vc = vc;
		
		this.setBackground(Color.BLUE);
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
	}	
}