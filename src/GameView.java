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
		
		this.setLayout(new BorderLayout());
		
		this.add(new GameViewHeader(), BorderLayout.NORTH);
		this.add(new GameViewBody(), BorderLayout.CENTER);
		this.add(new GameViewFooter(), BorderLayout.SOUTH);
	}
	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
	}
	
	
	private class GameViewHeader extends JPanel {
		public GameViewHeader() {
			super();
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	private class GameViewFooter extends JPanel {
		public GameViewFooter() {
			super();
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(0,vc.FOOTER_HEIGHT));
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	
	private class GameViewBody extends JPanel {
		public GameViewBody() {
			super();
			this.setLayout(new BorderLayout());
			this.add(new GameViewBoard(), BorderLayout.CENTER);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		
		
	}
	
	private class GameViewBoard extends JPanel {
		
		private GameViewTile[][] tiles;
		
		public GameViewBoard() {
			super();
			int s = Game.BOARD_SIZE;
			tiles = new GameViewTile[s][s];
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			for (int x = 0; x < s; x++) {
				for (int y = s - 1; y >= 0; y--) { // top to bottom
					c.gridx = x;
					c.gridy = y;
					tiles[x][y] = new GameViewTile(x,y);
					this.add(tiles[x][y], c);
				}
			}
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
			paintPieces();
		}
		
		private void paintPieces () {
			Piece[][] board = game.getBoard();
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				for (int y = 0; y < Game.BOARD_SIZE; y++) {
					
				}
			}
		}
	}
	
	private class GameViewTile extends JPanel {
		private Point position;
		
		public GameViewTile(int x, int y) {
			super();
			position = new Point(x,y);
			this.setBackground(Game.isBlackSquare(position) ? Color.BLACK : Color.WHITE);
			this.setLayout(new BorderLayout());
			
			ImageIcon icon = new ImageIcon("img/white_pawn.png");
			icon = scaleImageToSize(icon, 25, 25);
			JLabel label = new JLabel();
		    label.setIcon(icon); 
		    this.add(label, BorderLayout.CENTER);
		}
		
		private ImageIcon scaleImageToSize (ImageIcon imageIcon, int x, int y) {
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			return new ImageIcon(newimg);  // transform it back
		}
		
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		@Override
        public Dimension getPreferredSize() {
            return new Dimension(25, 25);
        }
	}
}