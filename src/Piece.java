import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public abstract class Piece {
	// Types
	public enum Color {
		BLACK,
		WHITE;
	}
	
	public enum Type {
		PAWN,
		ROOK,
		KNIGHT,
		BISHOP,
		QUEEN,
		KING;
	}
	
	// Private Fields
	private final Color color;
	private final Type type;
	private boolean hasMoved;
	
	// Constructor
	public Piece (Color color, Type type) {
		this.color = color;
		this.type = type;
		this.hasMoved = false;
	}
	
	// getters
	public Color getColor () {
    	return color;
    }
    public Type getType () {
    	return type;
    }
    
    // Public Methods
    
    public boolean validMove (Point start, Point end, Game g) {
    	List<Move> validMoves = this.getValidMoves(start, g);
    	if (validMoves == null) return false;
	for (Move m : validMoves) {
	    if (m.getEndLoc().equals(end)) {
		return true;
	    }
	}
    	return false;
    }

    public Move getMove (Point start, Point end, Game g) {	
    	List<Move> validMoves = this.getValidMoves(start, g);
    	if (validMoves == null) return null;
	for (Move m : validMoves) {
	    if (m != null && m.getEndLoc().equals(end)) {
		return m;
	    }
	}
    	return null;
    }

    public List<Point> getAttackedSquares (Point p, Game g) {
	//List<Point> squares = new ArrayList<Point>();
	//for (Move m : getValidMoves(p,g)) {
	//    squares.add(m.getEndLoc());
	//}
	//return squares;
	return getAttackedSquares(p, g.getBoard());
    }


    public boolean hasMoved() {return hasMoved;}
    public void setHasMoved() {this.hasMoved = true;}
    public void resetHasMoved() {this.hasMoved = false;} 
    
    // Helper methods
    
    public boolean isEnemy (Piece p) {
    	return (!isEmpty(p) && p.getColor() != this.getColor());
    }
    
    public boolean isAlly (Piece p) {
    	return (!isEmpty(p) && p.getColor() == this.getColor());
    }
    
    public boolean isEmpty (Piece p) {
    	return p == null;
    }
    
    // helpers for getValidMoves
    protected boolean checkValidity (Point p, Game g) {
    	if (Game.validPoint (p)) {
			Piece piece = g.getPieceAt(p);
			return isEmpty(piece) || isEnemy(piece);
    	}
    	return false;
    }
    
    protected boolean checkBreak (Point p, Game g) {
    	if (Game.validPoint (p)) {
    		Piece piece = g.getPieceAt(p);
			return isAlly(piece) || isEnemy(piece);
    	}
    	return false;
    }
   
    // helpers for getAttackedSquares 
    protected boolean checkValidity (Point p, Piece[][] board) {
    	if (Game.validPoint (p)) {
			Piece piece = board[p.x][p.y];
			return isEmpty(piece) || isEnemy(piece);
    	}
    	return false;
    }
    
    protected boolean checkBreak (Point p, Piece[][] board) {
    	if (Game.validPoint (p)) {
    		Piece piece = board[p.x][p.y];
			return isAlly(piece) || isEnemy(piece);
    	}
    	return false;
    }

    // Helper for adding a move
    protected Move getMoveWithCapture(Point start, Point end, Game g) {
	if (!checkValidity(end, g)) return null;
	Piece endPiece = g.getPieceAt(end);
	List<Move> sideEffects = null;
	if (isEnemy(endPiece)) {
	    sideEffects = new ArrayList<Move>();
	    sideEffects.add(new Move(endPiece, end, null,!endPiece.hasMoved(), null));
	}
	return new Move(this, start, end, !this.hasMoved(), sideEffects);
    }
    
    // Abstract Methods
    public abstract List<Move> getValidMoves(Point p, Game g); // returns all valid points for piece at Point p in Game g
    public abstract List<Point> getAttackedSquares (Point p, Piece[][] board); // returns all attacked squares
    
}
