import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JPanel implements Observer {
	
	private ViewController vc;
	private Game game;
	private GameViewBoard board;
	
	public GameView(ViewController vc, Game game) {
		super();
		this.game = game;
		this.vc = vc;
		this.board = null;
		
		
		this.setLayout(new BorderLayout());
		
		this.add(new GameViewHeader(), BorderLayout.NORTH);
		this.add(new GameViewBody(), BorderLayout.CENTER);
		this.add(new GameViewFooter(), BorderLayout.SOUTH);
	}
	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
	}
	
	private void handleMovedPieces() {
		if (board != null) {
			board.paintPieces();
		}
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
			board = new GameViewBoard();
			this.add(board, BorderLayout.CENTER);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		
		
	}
	
	private class GameViewBoard extends JPanel implements MouseListener, MouseMotionListener {
		
		private GameViewTile[][] tiles;
		
		public GameViewBoard() {
			super();
			
			this.setBackground(vc.CHOCOLATE_BROWN);
			
			tiles = new GameViewTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				for (int y = 0; y < Game.BOARD_SIZE; y++) {
					c.gridx = x; 
					c.gridy = Game.BOARD_SIZE - y; // invert y coordinate
					tiles[x][y] = new GameViewTile(x,y);
					this.add(tiles[x][y], c);
				}
			}
			
			addMouseListener(this);
		}
		
		// Mouse events
		public void mousePressed(MouseEvent e) {
			game.grabPiece(getBoardPosition(e.getPoint()));
		}
		public void mouseReleased(MouseEvent e) {
			game.releasePiece(getBoardPosition(e.getPoint()));
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if (game.getSelectedPiece() == null) {
				game.grabPiece(getBoardPosition(e.getPoint()));
			} else {
				game.releasePiece(getBoardPosition(e.getPoint()));
			}
		}
		public void mouseMoved(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		
		
		private Point getBoardPosition(Point p) {
			GameViewTile t = tiles[0][Game.BOARD_SIZE - 1];
			if (p.x < t.getX() || p.y < t.getY()) return null; // stop integer floor issues
			int x = (p.x - t.getX())/t.getWidth();
			int y = (p.y - t.getY())/t.getHeight();
			if (x >= Game.BOARD_SIZE || y >= Game.BOARD_SIZE) return null;
			return new Point(x , Game.BOARD_SIZE - 1 - y); // change y from point coordinate to game
		}
		
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		
		private void paintPieces () {
			Piece[][] board = game.getBoard();
			Piece selectedPiece = game.getSelectedPiece ();
			Point selectedPieceLocation = null;
			if (selectedPiece != null) {
				selectedPieceLocation = selectedPiece.getLocation();
			}	
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				for (int y = 0; y < Game.BOARD_SIZE; y++) {
					Piece p = board[x][y];
					if (p == null || (selectedPieceLocation != null && selectedPieceLocation.x == x && selectedPieceLocation.y == y)) {
						tiles[x][y].removeSprite ();
					} else {
						switch(p.getType()) {
						case PAWN:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_PAWN);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_PAWN);
							}
							break;
						case ROOK:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_ROOK);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_ROOK);
							}
							break;
						case KNIGHT:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_KNIGHT);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_KNIGHT);
							}
							break;
						case BISHOP:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_BISHOP);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_BISHOP);
							}
							break;
						case QUEEN:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_QUEEN);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_QUEEN);
							}
							break;
						case KING:
							if (p.getColor() == Piece.Color.BLACK) {
								tiles[x][y].addSprite(ImageManager.BLACK_KING);
							} else {
								tiles[x][y].addSprite(ImageManager.WHITE_KING);
							}
							break;
						default:
							break;
						}
					}
				}
			}
			this.revalidate();
		}
	}
	
	private class GameViewTile extends JPanel {
		private Point position;
		private JLabel pieceImage;		
		private Dimension size;
		
		public GameViewTile(int x, int y) {
			super();
			position = new Point(x,y);
			pieceImage = null;
			size = new Dimension(50,50);
			this.setBackground(Game.isBlackSquare(position) ? Color.GRAY : Color.WHITE);
			this.setLayout(new BorderLayout());			
		}
		
		public void addSprite (ImageIcon icon) {
			if (this.pieceImage == null) {
				icon = scaleImageIconToSize(icon, size);
				pieceImage = new JLabel();
				pieceImage.setIcon(icon); 
				this.add(pieceImage, BorderLayout.CENTER);
			}

		}
		
		public void removeSprite () {
			if (this.pieceImage != null) {
				this.remove(pieceImage);
				this.pieceImage = null;
			}
		}
		
		private ImageIcon scaleImageIconToSize (ImageIcon imageIcon, Dimension size) {
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			return new ImageIcon(newimg);  // transform it back
		}
		
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		@Override
        public Dimension getPreferredSize() {
            return new Dimension(size);
        }
	}
	
	public void update(Object observable, String message) {
		this.handleMovedPieces();
		this.repaint();
	}
	
    public void update(Object observable) {
    	this.update(observable, "");
    }
}