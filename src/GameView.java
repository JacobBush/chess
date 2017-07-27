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
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
	}
	
	
	private class GameViewHeader extends JPanel {
		public GameViewHeader() {
			super();
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
		}
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	private class GameViewFooter extends JPanel {
		public GameViewFooter() {
			super();
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(0,vc.FOOTER_HEIGHT));
		}
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	
	private class GameViewBody extends JPanel {
		public GameViewBody() {
			super();
			this.setLayout(new BorderLayout());
			this.add(new GameViewBoard(), BorderLayout.CENTER);
		}
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		
		private class GameViewBoard extends JPanel {
			public GameViewBoard() {
				super();
				int s = Game.BOARD_SIZE;
				this.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				for (int x = 0; x < s; x++) {
					for (int y = s - 1; y >= 0; y--) { // top to bottom
						c.gridx = x;
						c.gridy = y;
						this.add(new GameViewBoardTile(x,y), c);
					}
				}
			}
			public void paintComponent (Graphics g) {
				super.paintComponent(g);
			}
			
			private class GameViewBoardTile extends JPanel {
				private Point position;
				
				public GameViewBoardTile(int x, int y) {
					super();
					position = new Point(x,y);
					this.setBackground(Game.isBlackSquare(position) ? Color.BLACK : Color.WHITE);
				}
				public void paintComponent (Graphics g) {
					super.paintComponent(g);
				}
				@Override
		        public Dimension getPreferredSize() {
		            return new Dimension(25, 25);
		        }
			}
		}
	}
}