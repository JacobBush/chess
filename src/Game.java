import java.awt.Point;
import java.util.Observable;
import java.util.Stack;
import java.util.List;

// Implements the model
public class Game extends Observable {
    // Constants
    public static final int BOARD_SIZE = 8;
	
    // Fields
    private Piece[][] board;
    private Piece.Color turn;
    private Stack<Move> undoStack;
    private Stack<Move> redoStack;
    private Piece.Color victor;
	
	// Constructor
    public Game () {
    	this.resetGame();
    }
    
    // Public API
    public void resetGame() {
    	initializeBoard();
	victor = null; // Set to winning color if a player wins
    	turn = Piece.Color.WHITE;
    	this.undoStack = new Stack<Move>();
    	this.redoStack = new Stack<Move>();
    	setChanged();
    	notifyObservers();
    }
    
    public void movePiece (Point startingLocation, Point endingLocation) {
    	// TODO:
    	//    -Check/mate
    	//    -Pawn Promotion
    	// check on castle
	
	if (victor != null) return; // Don't move if player has won
	
    	if (validPoint(startingLocation) && validPoint(endingLocation)) {
    		Piece p = board[startingLocation.x][startingLocation.y];
    		if (isTurnOf(p)) {
		    Move m = p.getMove(startingLocation, endingLocation, this);
		    if (m != null) {
			if (executeMove(m)) {
	    		    if (isChecked(turn)) {
				// Player moved and is still in check - not allowed
				revertMove(m);
				clearChanged();
	    		    } else {
				// Update undo and change turn
			    	undoStack.push(m);
			    	clearStack(redoStack);
			    	changeTurn();
				if (isCheckMate(turn)) {
					// Current Player Loses
					victor = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
				}
			    }
			}
		    }
        	} else {
        		// p is null or its not p's turn
        	}
    	} else {
    		// one of the points is invalid
    	}
    	notifyObservers();
    }

    private boolean executeMove (Move m) {
	// True if moved successful
	if (m != null) { 
	    // execute side effects
	    List<Move> se = m.getSideEffects();
	    if (se != null) {
		for (Move move : se) executeMove(move);
	    }
	    // Move the piece
	    Piece p = m.getPiece();
    	    if (m.getStartLoc() != null)
		board[m.getStartLoc().x][m.getStartLoc().y] = null;
    	    if (m.getEndLoc() != null)
		board[m.getEndLoc().x][m.getEndLoc().y] = p;
	    
	    // For castling : keep track of if has moved
	    p.setHasMoved();
    	    setChanged();
	    return true;
    	}
	return false;
    }

    private boolean revertMove (Move m) {
	// True if moved successful
	if (m!= null) {
	    // Move the piece
	    Piece p = m.getPiece();
    	    if (m.getEndLoc() != null)
		board[m.getEndLoc().x][m.getEndLoc().y] = null;
    	    if (m.getStartLoc() != null)
		board[m.getStartLoc().x][m.getStartLoc().y] = p;
	    // For castling : keep track of if has moved
	    if (m.isFirstMove()) p.resetHasMoved();
	    // revert side effects
	    List<Move> se = m.getSideEffects();
	    if (se != null) {
		for (Move move : se) revertMove(move);
	    }
    	    setChanged();
	    return true;
	}
	return false;
    }

    public boolean movablePiece (Point location) {
    	if (location == null) return false;
	if (victor != null) return false; // Cant move if player has won
    	Piece p = board[location.x][location.y];
    	// TODO: Add checks for check and the like
    	return (p != null && p.getColor() == turn); 
    }

    private boolean[][] getSquaresAttackedBy (Piece.Color player, Piece[][] board) {
	// Will get all the squares that pieces belonging to
	// the passed player can attack. (not where they can move - pawns)
	// Will return a 2D array of booleans of size BOARD_SIZE squared,
	// where each entry will be true if attackable, and false otherwise
	boolean[][] squares = new boolean[BOARD_SIZE][BOARD_SIZE];
	for (int x = 0; x < BOARD_SIZE; x ++) {
	    for (int y=0; y < BOARD_SIZE; y++) {
		// For each square check if piece is of player's color
		Piece p = board[x][y]; // always will be valid index
		if (p == null || p.getColor() != player) continue;
	    	for (Point attack : p.getAttackedSquares(new Point(x,y), board)) {
		    squares[attack.x][attack.y] = true;
		}
	    }
	}
	return squares;
    }

    private boolean isAttackedBy (Point p, Piece.Color player, Piece[][] board) {
	boolean[][] attackedSquares = getSquaresAttackedBy(player, board);
	return attackedSquares[p.x][p.y];
    }

    private Point getKingPosn(Piece.Color player, Piece[][] board){
	for (int x = 0; x < BOARD_SIZE; x++) {
	    for (int y = 0; y < BOARD_SIZE; y++) {
		Piece p = board[x][y];
		if (p != null && p instanceof King && p.getColor() == player) {
		    return new Point (x,y);
		}
	    }
	}
	// Invalid state - there is no king
	// could throw exception
    	return null;
    }

    public boolean isChecked (Piece.Color player) {
	return isChecked(player, this.getBoard());
    }

    public boolean isChecked (Piece.Color player, Piece[][] board) {	
	// Will return true if player is in check
	Point kingPosn = getKingPosn(player,board);
	// kingPosn only null if there is no king - will not throw currently.
	if (kingPosn == null) return false;
	Piece.Color oppColor = player == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
	return isAttackedBy(kingPosn, oppColor, board);
    }

    public boolean isCheckMate (Piece.Color player) {
	if (isChecked(player, this.getBoard())) {
	    if (kingCanMove(player) || pieceCanCapture(player) || pieceCanBlock(player)) {
		return true;
	    }
	    return false;
	}
	return false;
    }

    // 3 subproblems for checkmate
    private boolean kingCanMove(Piece.Color player) {
	return false;
    }
    private boolean pieceCanCapture(Piece.Color player) {
	return false;
    }
    private boolean pieceCanBlock(Piece.Color player) {
	return false;
    }
    
    // Undo / Redo
    
    public void undo () {
	if (!undoStack.empty()) {
	    Move m = undoStack.pop();
	    if (this.revertMove(m)) {
		this.redoStack.push(m);
		changeTurn();
	    	if (victor != null) victor = null; // Player has no longer won
	    } else {
	        this.undoStack.push(m);
	    }
    	    this.notifyObservers();
	}
    }
    
    public void redo () {
    	if (!redoStack.empty()) {
	    Move m = redoStack.pop();
	    if (this.executeMove(m)) {
		this.undoStack.push(m);
		changeTurn();
		if (isCheckMate(turn)) {
		    // Current Player Loses
		    victor = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
		}
	    } else {
		this.redoStack.push(m);
	    }
	    this.notifyObservers();
	}
    }

    public Move getLastMove () {
	// Will return top of undo stack
	return undoStack.empty() ? null : undoStack.peek();
    }
    
    private void clearStack(Stack s) {
    	while (!s.empty()) s.pop();
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
    	Piece [][] boardCopy = new Piece[BOARD_SIZE][BOARD_SIZE];
    	for (int x = 0; x < BOARD_SIZE; x++) {
    		for (int y = 0; y < BOARD_SIZE; y++) {
        		boardCopy[x][y] = board[x][y];
        	}
    	}
    	return boardCopy;
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
