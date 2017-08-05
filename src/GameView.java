import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GameView extends JLayeredPane implements Observer, Resizable {
	
	private ViewController vc;
	private Game game;
	private GameViewBoard board;
	private GameContent content;
	private GameView topLevel;
	
	private JLabel dragComponent;
	private JLabel turnCounter;
	
	public GameView(ViewController vc, Game game) {
		super();
		this.game = game;
		this.vc = vc;
		this.board = null;
		this.topLevel = this;
		this.dragComponent = null;
		
		content = new GameContent();
		this.add(content, JLayeredPane.DEFAULT_LAYER);
		updateSize();
	}
	
	public void updateSize() {
		content.setBounds(0, 0, vc.getWidth(), vc.getHeight());
		//resizeTiles();
		revalidate();
		repaint();
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

	private void setDragComponentPosition () {
		if (dragComponent != null) {
			Point mousePosn = MouseInfo.getPointerInfo().getLocation();
			Point componentPosn = this.getLocationOnScreen();
			int size = 50;
			dragComponent.setBounds(mousePosn.x - componentPosn.x - size/2, mousePosn.y - componentPosn.y - size/2, size, size);
		}
	}
	
	private void updateTurnCounter () {
		if (turnCounter == null) return;
		switch(game.getTurn()) {
		case WHITE:
			this.turnCounter.setText("White's Turn");
			break;
		case BLACK:
			this.turnCounter.setText("Black's Turn");
			break;
		default:
			this.turnCounter.setText("");
			break;
		}
	}
	
	private void resetGame() {
		game.reset();
	}
	
	private class GameContent extends JPanel {
		JPanel header;
		JPanel body;
		JPanel footer;
		
		public GameContent() {
			super();
			this.setLayout(new BorderLayout());
			
			this.header = new GameViewHeader();
			this.body = new GameViewBody();
			this.footer = new GameViewFooter();
			
			this.add(header, BorderLayout.NORTH);
			this.add(body, BorderLayout.CENTER);
			this.add(footer, BorderLayout.SOUTH);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	
	private class GameViewHeader extends JPanel {
		
		public GameViewHeader() {
			super();
			
			Color backGround = Color.BLACK;
			
			this.setBackground(backGround);
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
			
			this.setLayout(new BorderLayout());
			
			JPanel leftPanel = new JPanel();
			JPanel centerPanel = new JPanel();
			JPanel rightPanel = new JPanel();
			
			leftPanel.setBackground(backGround);
			centerPanel.setBackground(backGround);
			rightPanel.setBackground(backGround);
			
			this.add(leftPanel, BorderLayout.WEST);
			this.add(centerPanel, BorderLayout.CENTER);
			this.add(rightPanel, BorderLayout.EAST);
			
			leftPanel.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.insets = new Insets(5,20,5,5);
			
			JButton homeButton = new JButton("Home");
			homeButton.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					vc.selectView(ViewController.ViewSelector.MAIN);
		        }
			});
			
			leftPanel.add(homeButton, gc);
			
			centerPanel.setLayout(new GridBagLayout());
			gc = new GridBagConstraints();
			
			turnCounter = new JLabel();
			turnCounter.setForeground(Color.WHITE);
			turnCounter.setFont(new Font("Sans-Serif", Font.BOLD, 20));
			
			centerPanel.add(turnCounter, gc);
			
			rightPanel.setLayout(new GridBagLayout());
			gc = new GridBagConstraints();
			gc.insets = new Insets(5,5,5,20);
			
			JButton resetButton = new JButton("Reset");
			resetButton.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					resetGame();
		        }
			});
			
			rightPanel.add(resetButton, gc);
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
			addMouseMotionListener(this);
		}
		
		// Mouse events
		
		private Point mouseDownTile = null;
		private boolean pieceGrabbedByClick = false;
		
		public void mousePressed(MouseEvent e) {
			if (pieceGrabbedByClick) {
				// do nothing
			} else {
				mouseDownTile = getBoardPosition(e.getPoint());
				this.grabPiece(mouseDownTile);
				setMouseCursor();
			}
		}
		public void mouseReleased(MouseEvent e) {
			if (pieceGrabbedByClick) {
				// nothing done on mousedown - release piece
				this.releasePiece(getBoardPosition(e.getPoint()));
				pieceGrabbedByClick = false;
				setMouseCursor();
			} else {
				if (mouseDownTile != null && mouseDownTile.equals(getBoardPosition(e.getPoint()))) { // if tiles are same - click event
					if (game.getSelectedPiece () != null) {
						pieceGrabbedByClick = true;				
					}
				} else {
					this.releasePiece(getBoardPosition(e.getPoint()));		
					pieceGrabbedByClick = false;
					setMouseCursor();
				}
				mouseDownTile = null;
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			// causes double triggers on mousePressed/mouseReleased
			// will implement custom click events
		}
		public void mouseMoved(MouseEvent e) {
			if (pieceGrabbedByClick) {
				dragMouse (e.getPoint()); // if piece grabbed, same as dragging
			}
		}
		public void mouseDragged(MouseEvent e) {
			dragMouse (e.getPoint());
		}
		
		private void grabPiece (Point p) {
			game.grabPiece(p);
			Piece selectedPiece = game.getSelectedPiece();
			ImageIcon selectedSprite = getSpriteForPiece(selectedPiece);
			dragComponent = new PieceSprite(selectedSprite, new Dimension(50,50));
			topLevel.add(dragComponent, JLayeredPane.DRAG_LAYER);
			setDragComponentPosition();
			topLevel.revalidate();
		}
		
		private void releasePiece (Point p) {
			game.releasePiece(p);
			topLevel.remove(dragComponent);
			dragComponent = null;
			topLevel.revalidate();
		}
		
		private void setMouseCursor() {
			if (game.getSelectedPiece() == null) {
				vc.getContentPane().setCursor(Cursor.getDefaultCursor());
			} else {
				// Transparent 16 x 16 pixel cursor image.
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				// Create a new blank cursor.
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				    cursorImg, new Point(0, 0), "blank cursor");
				// Set the blank cursor to the JFrame.
				vc.getContentPane().setCursor(blankCursor);
			}
		}
		
		private void dragMouse (Point p) {
			setDragComponentPosition ();
			revalidate();
		}
		
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
						ImageIcon sprite = getSpriteForPiece(p);
						if (sprite != null) tiles[x][y].addSprite(sprite);
					}
				}
			}
			this.revalidate();
		}
	}
	
	private ImageIcon getSpriteForPiece(Piece p) {
		if (p == null) return null;
		
		switch(p.getType()) {
		case PAWN:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_PAWN;
			} else {
				return ImageManager.WHITE_PAWN;
			}
		case ROOK:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_ROOK;
			} else {
				return ImageManager.WHITE_ROOK;
			}
		case KNIGHT:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_KNIGHT;
			} else {
				return ImageManager.WHITE_KNIGHT;
			}
		case BISHOP:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_BISHOP;
			} else {
				return ImageManager.WHITE_BISHOP;
			}
		case QUEEN:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_QUEEN;
			} else {
				return ImageManager.WHITE_QUEEN;
			}
		case KING:
			if (p.getColor() == Piece.Color.BLACK) {
				return ImageManager.BLACK_KING;
			} else {
				return ImageManager.WHITE_KING;
			}
		default:
			return null;
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
				pieceImage = new PieceSprite(icon, size);
				this.add(pieceImage, BorderLayout.CENTER);
			}

		}
		
		public void removeSprite () {
			if (this.pieceImage != null) {
				this.remove(pieceImage);
				this.pieceImage = null;
			}
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
	
	private class PieceSprite extends JLabel {
		public PieceSprite (ImageIcon icon, Dimension size) {
			super();
			icon = icon == null ? null : scaleImageIconToSize (icon, size);
			this.setIcon(icon);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		private ImageIcon scaleImageIconToSize (ImageIcon imageIcon, Dimension size) {
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			return new ImageIcon(newimg);  // transform it back
		}
	}
	
	public void update(Object observable, String message) {
		this.handleMovedPieces();
		this.updateTurnCounter();
		this.repaint();
	}
	
    public void update(Object observable) {
    	this.update(observable, "");
    }
}