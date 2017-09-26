import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
// For sounds
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//TODO: move GameViewBoard to its own file

public class GameView extends JLayeredPane  { 
	
	// Sound for moving pieces (Loaded for GameViewBoard statically)
	static File knockSound = new File ("audio/knock.wav");
	
	private ViewController vc;
	private GameViewBoard board;
	private Game game;
	
	public GameView (ViewController vc, Game game) {
		super();
		this.vc = vc;
		this.game = game;
		this.setLayout(new BorderLayout());
		this.board = new GameViewBoard(game);
		this.add(this.board, BorderLayout.CENTER);
		this.add(new GameViewHeader(), BorderLayout.NORTH);
		this.add(new GameViewFooter(), BorderLayout.SOUTH);
	}
	
	private class GameViewBoard extends JPanel implements Observer, MouseListener, MouseMotionListener {
		
		private GameViewTile[][] tiles;
		private Game game;
		// For moving pieces
		private Point startPoint;
		// Sound
		private boolean muteSound;
		
		
		public GameViewBoard(Game game) {
			super();
			
			this.unmute();

			this.startPoint = null;
			this.game = game;
			this.setBackground(vc.CHOCOLATE_BROWN);
			// draw tiles
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
			game.addObserver(this);
			this.setTileSize();
			this.redrawPieces();
			// Listen for resize events
			this.addComponentListener(new ComponentAdapter() {
		        public void componentResized(ComponentEvent e) {
		        	setTileSize ();
		        }
			});
			// Listen for mouse events
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			
			// Undo and redo Listeners (note: will still work even when returned to home screen)
			Action undo = new AbstractAction() {
			    public void actionPerformed(ActionEvent e) {game.undo();}
			};
			Action redo = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {game.redo();}
			};
			
			InputMap im = vc.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redo");
			
			ActionMap am = vc.getRootPane().getActionMap();
			am.put("undo", undo);
			am.put("redo", redo);
		}
		
		
		
		public void mouseClicked(MouseEvent e) {/*Click is a weird event - will use press and release instead*/}
		public void mousePressed(MouseEvent e) {		
			startPoint = board.getBoardPosition(e.getLocationOnScreen());
			if (game.movablePiece(startPoint)) {
				// We have grabbed a moveable (maybe) piece
				board.repaintTile(startPoint); // remove the piece from the tile
				Piece pieceGrabbed = game.getPieceAt(startPoint);
				vc.addDragObject(getBufferedImageForPiece(pieceGrabbed), getTileSize());
				vc.setMouseDragged();
				setMouseCursor(false);
			} else {
				startPoint = null;
			}			
		}
		public void mouseReleased(MouseEvent e) {
			vc.clearDragObjects ();
			Point startPoint = this.startPoint;
			this.startPoint = null;
			board.repaintTile(this.startPoint);// replace the piece
			// Move the piece
			Point endPoint = board.getBoardPosition(e.getLocationOnScreen());
			game.movePiece (startPoint, endPoint);
			setMouseCursor(true);
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {
			vc.setMouseDragged();
		}
		public void mouseMoved(MouseEvent e) {}
		
		private void setMouseCursor(boolean on) {
			if (on) {
				vc.getGlassPane().setCursor(Cursor.getDefaultCursor());
			} else {
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				vc.getGlassPane().setCursor(blankCursor);
			}
		}
		
		private Point getBoardPosition(Point screenPosition) {
			Point p = new Point (screenPosition.x - this.getLocationOnScreen().x, screenPosition.y - this.getLocationOnScreen().y);
			GameViewTile t = tiles[0][Game.BOARD_SIZE - 1];
			if (p.x < t.getX() || p.y < t.getY() || t.getWidth() == 0 || t.getHeight() == 0) return null; // stop integer floor issues
			int x = (p.x - t.getX())/t.getWidth();
			int y = (p.y - t.getY())/t.getHeight();
			if (x >= Game.BOARD_SIZE || y >= Game.BOARD_SIZE) return null;
			return new Point(x , Game.BOARD_SIZE - 1 - y); // change y from point coordinate to game
		}
		
		public void redrawPieces () {
			this.paintPieces();
			this.repaint();
		}
		
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
		
		@Override
		public void update(Observable o, Object arg) {
			this.redrawPieces ();
			this.playSound();
		}
		
		private void playSound() {
			if (muteSound) return;
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(knockSound));
				clip.start();
			} catch (Exception e) {
				System.out.println(e + " : sound not supported in call to GameView.playSound().");
				System.out.println("Muting sound.");
				this.mute();
			}
		}

		public void mute () {
		    this.muteSound = true;
		}

		public void unmute () {
		    this.muteSound = false;
		}
		
		private void paintPieces () {
			Piece[][] board = game.getBoard();
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				for (int y = 0; y < Game.BOARD_SIZE; y++) {
					tiles[x][y].placePiece(board[x][y]);
				}
			}
		}
		
		public void setTileSize () {
			int size = this.getTileSize();
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				for (int y = 0; y < Game.BOARD_SIZE; y++) {
					tiles[x][y].setTileSize(size);
				}
			}
			revalidate();
		}
		
		public int getTileSize() {
			int minDim = Math.min(this.getWidth(), this.getHeight());
			int tileWidth = minDim / Game.BOARD_SIZE;
			return tileWidth;
		}
		
		public void repaintTile (Point boardPosn) {
			if (boardPosn != null) tiles[boardPosn.x][Game.BOARD_SIZE - 1 - boardPosn.y].repaint(); // change from board to array, then repaint
		}
		
		public BufferedImage getBufferedImageForPiece(Piece p) {
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
		
		// Represent the individual squares of the board		
		private class GameViewTile extends JPanel {
			private Point position;		
			private int tileWidth;
			private BufferedImage pieceImage;
			
			public GameViewTile(int x, int y) {
				super();
				position = new Point(x,y);
				this.setBackground(Game.isBlackSquare(position) ? Color.GRAY : Color.WHITE);
				this.setLayout(new BorderLayout());
				this.pieceImage = null;
			}
			
			public void placePiece (Piece p) {
				pieceImage = getBufferedImageForPiece(p);
			}
			
			public void removePiece () {
				this.pieceImage = null;
			}

			public void setTileSize(int tileWidth) {
				this.tileWidth = tileWidth;
			}
			
			@Override
			protected void paintComponent (Graphics g) {
				super.paintComponent(g);
				if (startPoint == null || !startPoint.equals(position)) {// paint piece if not grabbed
					Graphics2D g2 = (Graphics2D) g;
					g2.drawImage(pieceImage, 0, 0, this.getWidth(), this.getHeight(), null);
				}				
			}
			@Override
	        public Dimension getPreferredSize() {
	            return new Dimension(tileWidth,tileWidth);
	        }
		}
	}
	
	private class GameViewHeader extends JPanel {
		public GameViewHeader () {
			super();
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
			this.setBackground(Color.BLACK);
			this.setLayout(new BorderLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.VERTICAL;
			
			// Left Buttons
			gbc.insets = new Insets(0, 5, 0, 0);
			
			JPanel leftButtons = new JPanel();
			leftButtons.setBackground(Color.BLACK);
			leftButtons.setLayout(new GridBagLayout());
			this.add(leftButtons, BorderLayout.WEST);
			
			JButton home = new JButton("Home");
			home.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  vc.selectView(ViewController.ViewSelector.MAIN);
				  }
			});
			leftButtons.add(home, gbc);
			
			// Right buttons
			gbc.insets = new Insets(0, 0, 0, 5);
			
			JPanel rightButtons = new JPanel();
			rightButtons.setBackground(Color.BLACK);
			rightButtons.setLayout(new GridBagLayout());
			this.add(rightButtons, BorderLayout.EAST);

			
			JButton undo = new JButton(Character.toString((char) 8678) + " Undo");// Unicode: LEFTWARDS WHITE ARROW
			undo.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  game.undo();
				  }
			});
			rightButtons.add(undo, gbc);
			
			JButton redo = new JButton("Redo " + Character.toString((char) 8680));// Unicode: LEFTWARDS WHITE ARROW
			redo.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  game.redo();
				  }
			});
			rightButtons.add(redo, gbc);
		}
	}
	
	private class GameViewFooter extends JPanel {
		public GameViewFooter () {
			super();
			this.setPreferredSize(new Dimension(0,vc.FOOTER_HEIGHT));
			this.setBackground(Color.BLACK);
		}
	}
}
