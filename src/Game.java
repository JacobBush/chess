import java.awt.Point;
import java.util.Observable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
    private Map<Piece, Boolean> hasMoved;
	
	// Constructor
    public Game () {
    	this.resetGame();
    }
    
    // Public API
    public void resetGame() {
	this.hasMoved = new HashMap<Piece, Boolean> ();
	victor = null; // Set to winning color if a player wins
    	turn = Piece.Color.WHITE;
    	this.undoStack = new Stack<Move>();
    	this.redoStack = new Stack<Move>();
    	initializeBoard();
    	setChanged();
    	notifyObservers();
    }
    
    public void movePiece (Point startingLocation, Point endingLocation) {
    	// TODO:
    	//    -mate
    	//    -Pawn Promotion
	
	if (victor != null) return; // Don't move if player has won
	
    	if (validPoint(startingLocation) && validPoint(endingLocation)) {
    		Piece p = board[startingLocation.x][startingLocation.y];
    		if (isTurnOf(p)) {
		    Move m = p.getMove(startingLocation, endingLocation, this);
		    if (m != null) {
			if (executeMove(m, board)) {
	    		    if (isChecked(turn)) {
				// Player moved and is still in check - not allowed
				revertMove(m,board);
	    		    } else {
				// Update undo and change turn
	    		        updateHasMoved(m,true);
			    	undoStack.push(m);
			    	clearStack(redoStack);
			    	changeTurn();
				if (isCheckMate(turn)) {
					// Current Player Loses
					victor = Piece.getEnemy(turn);
				}
			    }
			    setChanged();
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

    private boolean executeMove (Move m, Piece[][] board) {
	// NOTE : Will mutate the Piece[][] passed
	// True if moved successful
	if (m != null) { 
	    // execute side effects
	    List<Move> se = m.getSideEffects();
	    if (se != null) {
		for (Move move : se) executeMove(move, board);
	    }
	    // Move the piece
	    Piece p = m.getPiece();
    	    if (m.getStartLoc() != null)
		board[m.getStartLoc().x][m.getStartLoc().y] = null;
    	    if (m.getEndLoc() != null)
		board[m.getEndLoc().x][m.getEndLoc().y] = p;
	    return true;
    	}
	return false;
    }

    private boolean revertMove (Move m, Piece[][] board) {
	// NOTE : Will mutate the Piece[][] passed
	// True if moved successful
	if (m!= null) {
	    // Move the piece
	    Piece p = m.getPiece();
    	    if (m.getEndLoc() != null)
		board[m.getEndLoc().x][m.getEndLoc().y] = null;
    	    if (m.getStartLoc() != null)
		board[m.getStartLoc().x][m.getStartLoc().y] = p;
	    // revert side effects
	    List<Move> se = m.getSideEffects();
	    if (se != null) {
		for (Move move : se) revertMove(move, board);
	    }
	    return true;
	}
	return false;
    }

    private void updateHasMoved (Move m, boolean moveDo) {
	// moveDo will be false if the move is being undone
	if (m == null) return;
	if (moveDo) {
	    hasMoved.put(m.getPiece(), Boolean.TRUE);
	} else {
	    if (m.isFirstMove()) hasMoved.put(m.getPiece(), Boolean.FALSE);	
	}
	if (m.getSideEffects() != null) {    
	    for (Move side : m.getSideEffects()) updateHasMoved(side, moveDo);
	}
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
	// NOTE : Will not mutate board
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
	// NOTE : Will not mutate board
	boolean[][] attackedSquares = getSquaresAttackedBy(player, board);
	return attackedSquares[p.x][p.y];
    }

    public boolean isAttackedBy (Point p, Piece.Color player) {
	return isAttackedBy(p, player, board); // assuming that will not be mutated
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
	Piece.Color oppColor = Piece.getEnemy(player);
	return isAttackedBy(kingPosn, oppColor, board);
    }

    public boolean isCheckMate (Piece.Color player) {
	Point p = getKingPosn (player, board);
	Piece.Color oppCol = Piece.getEnemy(player);
	Piece[][] b = this.getBoard();
	if (isChecked(player, b)) {
	    if (kingCanMove(p,oppCol,b) || 
		pieceCanCaptureChecker(p,oppCol,b) ||
		pieceCanBlockChecker(p,oppCol,b)) {
		return false;
	    }
	    return true;
	}
	return false;
    }

    // 3 subproblems for checkmate
    private boolean kingCanMove(Point kingPosn, Piece.Color oppCol, Piece[][] board) {
    	boolean[][] attackedSquares =  getSquaresAttackedBy (oppCol, board);
	for (int x = -1; x <= 1; x++) {
	    for (int y = -1; y <= 1; y++) {
		if (x==0 && y==0) continue;
		Point p = new Point (kingPosn.x + x, kingPosn.y + y);
		if (!validPoint(p)) continue;
		Piece piece = board[p.x][p.y];
		if (piece != null && piece.getColor() != oppCol) continue; 
		if (!attackedSquares[p.x][p.y]) return true;
	    }
	}
	return false;
    }

    private boolean pieceCanCaptureChecker(Point kingPosn, Piece.Color oppCol, Piece[][] board) {
	List<Point> piecesAttackingKing = getPiecesAttackingKing(kingPosn, oppCol, board);
	// Know king is in check, so at least 1 piece checking king
	// Could throw error as an error check if 0 pieces
	if (piecesAttackingKing.size() >= 2) return false;
	// 1 piece is attacking king
	// -> need to check if a piece can take the checker, and that we don't end in check
	Point attackerPosn = piecesAttackingKing.get(0);// will throw if no piece checking the king
	Piece attacker = board[attackerPosn.x][attackerPosn.y]; 
	// For each of our pieces, see if we can move to attacker's posn
	for (int x = 0; x < BOARD_SIZE; x++) {
	    for (int y = 0; y < BOARD_SIZE; y++) {
		Piece piece = board[x][y];
		if (piece == null || piece.getColor() == oppCol) continue;
		// Piece is one of ours
		Point piecePosn = new Point(x,y);
		List<Point> capturablePieces = piece.getCapturablePieces(piecePosn, board);
	    	if (capturablePieces != null && capturablePieces.contains(attackerPosn)) {
		    // We can capture the attacker!
		    Move m = piece.getCapture(piecePosn, attackerPosn, this); // get the move to capture this piece
    		    if (executeMove (m, board)) {
			// Check to see that we wouldn't be in check
			if (!isChecked(piece.getColor(), board)) {
			    // This is a valid capture
			    return true;
			} else {
                            // We are in check after this capture -- not valid
			    revertMove(m, board);
                        }
		    } else continue; // we couldn't execute the move
		} else  continue; // we can't capture
	    }
	}
	return false;
    }
    private boolean pieceCanBlockChecker(Point kingPosn, Piece.Color oppCol, Piece[][] board) {
	List<Point> piecesAttackingKing = getPiecesAttackingKing(kingPosn, oppCol, board);
	if (piecesAttackingKing.size() >= 2) return false;
	// need to check if a piece can block the attacker
	Point attackerPosn = piecesAttackingKing.get(0);// will throw if no piece checking the king
	Piece attacker = board[attackerPosn.x][attackerPosn.y]; 
	List<Point> attackLine = attacker.getAttackLine(attackerPosn, kingPosn, board);
	if (attackLine == null || attackLine.size() == 0) {
		return false; // Malformed attackline - could throw
	} else if (attackLine.size() == 1) {
	    return false; // attacker attacks kingPosn directly 
	} else {
	    // There are squares to block!
	    // TODO: Check that a piece can move to one of these squares
	    // TODO: If it can, check that we aren't in check afterwards.
	}
	return true;
    }

    private List<Point> getPiecesAttackingKing(Point kingPosn, Piece.Color oppCol, Piece[][] board) {
	List<Point> piecesAttackingKing = new ArrayList<Point>();
	for (int x = 0; x < BOARD_SIZE; x ++) {
	    for (int y=0; y < BOARD_SIZE; y++) {
		// For each square check if piece is of player's color
		Piece p = board[x][y]; // always will be valid index
		Point pp = new Point(x,y);
		if (p == null || p.getColor() != oppCol) continue;
	    	for (Point attack : p.getAttackedSquares(pp, board)) {
		    if (attack != null && attack.equals(kingPosn)) {
			piecesAttackingKing.add(pp);
			break;
		    }
		}
	    }
	}
	return piecesAttackingKing;
    }
    
    // Undo / Redo
    
    public void undo () {
	if (!undoStack.empty()) {
	    Move m = undoStack.pop();
	    if (this.revertMove(m, board)) {
	    	updateHasMoved(m,false);
		this.redoStack.push(m);
		changeTurn();
	    	if (victor != null) victor = null; // Player has no longer won
		setChanged();
	    } else {
	        this.undoStack.push(m);
	    }
    	    this.notifyObservers();
	}
    }
    
    public void redo () {
    	if (!redoStack.empty()) {
	    Move m = redoStack.pop();
	    if (this.executeMove(m,board)) {
	        updateHasMoved(m,true);
		this.undoStack.push(m);
		changeTurn();
		if (isCheckMate(turn)) {
		    // Current Player Loses
		    victor = Piece.getEnemy(turn);
		}
		setChanged();
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

    public boolean hasMoved(Piece p) {return hasMoved.get(p).booleanValue();}
    
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
    	this.board[x][y] = p;
	// Add piece to hasMoved map
	this.hasMoved.put(p, false);
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
