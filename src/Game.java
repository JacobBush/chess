import java.awt.Point;
import java.util.Observable;
import javax.swing.undo.UndoManager;

// Implements the model
public class Game extends Observable {
	// Constants
	public static final int BOARD_SIZE = 8;
	
	// Fields
	private Piece[][] board;
	private Piece.Color turn;
	private UndoManager undoManager;
	
	// Constructor
    public Game () {
    	this.undoManager = new UndoManager();
    	this.resetGame();
    }
    
    // Public API
    public void resetGame() {
    	initializeBoard();
    	turn = Piece.Color.WHITE;
    	setChanged();
    	notifyObservers();
    }
    
    public void movePiece (Point startingLocation, Point endingLocation) {
    	if (validPoint(startingLocation) && validPoint(endingLocation)) {
    		Piece p = board[startingLocation.x][startingLocation.y];
    		if (isTurnOf(p)) {
    			if (p.validMove(startingLocation, endingLocation, this)) {
    				board[startingLocation.x][startingLocation.y] = null;
    				// TODO: Check for capture
    				board[endingLocation.x][endingLocation.y] = p;
    				changeTurn();
    				setChanged();
    			}
        	} else {
        		// p is null or its not p's turn
        	}
    	} else {
    		// one of the points is invalid
    	}
    	notifyObservers();
    }
    
    public boolean movablePiece (Point location) {
    	// Will return true if piece at location is movable
    	Piece p = board[location.x][location.y];
    	// TODO: Add checks for check and the like
    	return (p != null && p.getColor() == turn); 
    }
    
    public void undo () {
    	// TODO: Implement this
    }
    
    public void redo () {
    	// TODO: Implement this
    }
    
    // Getters
    public Piece getPieceAt(Point p) {
    	if (!validPoint(p)) return null;
    	return board[p.x][p.y];
    }
    
    public Piece getPieceAt (int x, int y) {
    	if (!validPoint(x,y)) return null;
    	return board[x][y];
    }
    
    public Piece[][] getBoard () {
    	// will return shallow copy of board
    	return board.clone();
    }
    
    public Piece.Color getTurn () {
    	return turn;
    }
    
    // private Methods    
    private void initializeBoard() {
    	this.clearBoard ();
    	this.createPieces(Piece.Color.BLACK);
    	this.createPieces(Piece.Color.WHITE);
    }
    
    private void clearBoard() {
    	this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
    }
    
    private void createPieces(Piece.Color color) {
    	int pawnRow;
    	int majorRow;
    	if (color == Piece.Color.BLACK) {
    		pawnRow = BOARD_SIZE-2;
    		majorRow = BOARD_SIZE-1;
    	} else {
    		pawnRow = 1;
    		majorRow = 0;
    	}
    	for (int x = 0; x < BOARD_SIZE; x++) {
    		createPiece(x,pawnRow,color,Piece.Type.PAWN);
    	}
    	createPiece(0, majorRow, color, Piece.Type.ROOK);
    	createPiece(BOARD_SIZE-1, majorRow, color, Piece.Type.ROOK);
    	createPiece(1, majorRow, color, Piece.Type.KNIGHT);
    	createPiece(BOARD_SIZE-2, majorRow, color, Piece.Type.KNIGHT);
    	createPiece(2, majorRow, color, Piece.Type.BISHOP);
    	createPiece(BOARD_SIZE-3, majorRow, color, Piece.Type.BISHOP);
    	createPiece(3, majorRow, color, Piece.Type.QUEEN);
    	createPiece(BOARD_SIZE-4, majorRow, color, Piece.Type.KING);
    }
    
    private void createPiece(int x, int y, Piece.Color color, Piece.Type type) {
    	Piece p = null;
    	switch (type) {
    	case PAWN:
    		p = new Pawn(color);
    		break;
    	case ROOK:
    		p = new Rook(color);
    		break;
    	case KNIGHT:
    		p = new Knight(color);
    		break;
    	case BISHOP:
    		p = new Bishop(color);
    		break;
    	case QUEEN:
    		p = new Queen(color);
    		break;
    	case KING:
    		p = new King(color);
    		break;
    	default:
    		break;
    	}
    	board[x][y] = p;
    }
    
    private void changeTurn() {
    	if (this.turn == Piece.Color.WHITE) {
    		this.turn = Piece.Color.BLACK;
    	} else if (this.turn == Piece.Color.BLACK) {
    		this.turn = Piece.Color.WHITE;
    	} else {
    		// turn is null - game not yet started
    		this.turn = Piece.Color.WHITE;
    	}
    }
    
    private boolean isTurnOf(Piece p) {
    	return (p != null) && (p.getColor() == turn);
    }
    
    // Useability
    @Override
    public String toString() {
    	String s = "";
    	for (int y = BOARD_SIZE-1; y >= 0; y--) {
    		for (int x = 0; x < BOARD_SIZE; x ++) {
    			Piece p = board[x][y];
    			if (isBlackSquare(x,y)) s+= "_"; else s+= " "; // black or white square
    			if (p != null) {
    				s += p.toString();
    			} else {
    				if (isBlackSquare(x,y)) s+= "_"; else s+= " "; // black or white square
    			}
    			if (isBlackSquare(x,y)) s+= "_"; else s+= " "; // black or white square
    		}
    		if (y != 0) s += "\n";
    	}
    	return s;
    }

    public static boolean validPoint (Point p) {
    	return (p != null) && validPoint(p.x, p.y);
    }
    
    public static boolean validPoint (int x, int y) {
    	return !(x < 0 || y < 0 || x >= BOARD_SIZE || y >= BOARD_SIZE);
    }
    
    public static boolean isBlackSquare (int x, int y) {
    	return (x+y)%2==0;
    }
    public static boolean isBlackSquare (Point p) {
    	return isBlackSquare (p.x, p.y);
    }
    
}
