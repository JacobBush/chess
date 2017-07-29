import java.awt.Point;
import java.util.ArrayList;

// Implements the model
public class Game {
	// Constants
	public static final int BOARD_SIZE = 8;
	
	// Fields
	private Piece[][] board;
	private ArrayList<Observer> observers;
	private Piece selectedPiece;
	private Piece.Color turn;
	
	// Constructor
    public Game () {
    	this.observers = new ArrayList<Observer>();
    	this.initializeBoard();
    	selectedPiece = null;
    	turn = null;
    }
    
    // Public API
    public void play () {
    	turn = Piece.Color.WHITE;
    	notifyObservers("Game Started");
    }
    
    public void grabPiece (Point p) {
    	if (validPoint(p)) {
    		Piece piece = board[p.x][p.y];
    		if (isTurnOf(piece)) {
        		selectedPiece = piece;
        		notifyObservers();
    		} else {
    			// p is null or its not p's turn
    		}
    	}
    }
    
    public void releasePiece (Point p) {
    	movePiece(selectedPiece, p);
    	selectedPiece = null;
		notifyObservers();
    }
    
    public void movePiece (Point startingLocation, Point endingLocation) {
    	if (validPoint(startingLocation) && validPoint(endingLocation)) {
    		Piece p = board[startingLocation.x][startingLocation.y];
    		if (isTurnOf(p)) {
        		boolean success = p.move(endingLocation);
        		if (success) {
        			board[startingLocation.x][startingLocation.y] = null;
        			board[endingLocation.x][endingLocation.y] = p;
        			changeTurn();
        			notifyObservers();
        		}
        	} else {
        		// p is null or its not p's turn
        	}
    	}
    }
    
    public void movePiece (Piece p, Point endingLocation) {
    	if (p != null) {
    		Point startingLocation = p.getLocation();
    		movePiece(startingLocation, endingLocation);
    	}
    }
    
    // Getters
    public Piece getPieceAt(Point p) {
    	if (!validPoint(p)) return null;
    	return board[p.x][p.y];
    }
    
    public Piece[][] getBoard () {
    	// will return shallow copy of board
    	return board.clone();
    }
    
    public Piece getSelectedPiece () {
    	return selectedPiece;
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
    		p = new Pawn(x,y,color, this);
    		break;
    	case ROOK:
    		p = new Rook(x,y,color, this);
    		break;
    	case KNIGHT:
    		p = new Knight(x,y,color, this);
    		break;
    	case BISHOP:
    		p = new Bishop(x,y,color, this);
    		break;
    	case QUEEN:
    		p = new Queen(x,y,color, this);
    		break;
    	case KING:
    		p = new King(x,y,color, this);
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
    	}
    }
    
    private boolean isTurnOf(Piece p) {
    	return (p != null) && (p.getColor() == turn);
    }
    
    
    public static boolean validPoint (Point p) {
    	return !(p == null || p.x < 0 || p.y < 0 || p.x >= BOARD_SIZE || p.y >= BOARD_SIZE);
    }
    
    // Observers
    public void addObserver(Observer o) {
    	this.observers.add(o);
    }
    
    public void removeObserver(Observer o) {
    	this.observers.remove(o);
    }
    
    private void notifyObservers() {
    	notifyObservers("");
    }
    
    private void notifyObservers(String message) {
    	for (Observer o : observers) {
    		o.update(this, message);
    	}
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
    
    public static boolean isBlackSquare (int x, int y) {
    	return (x+y)%2==0;
    }
    public static boolean isBlackSquare (Point p) {
    	return isBlackSquare (p.x, p.y);
    }
    
}
