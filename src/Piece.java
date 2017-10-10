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
//	private boolean hasMoved;
	
	// Constructor
	public Piece (Color color, Type type) {
		this.color = color;
		this.type = type;
//		this.hasMoved = false;
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
    
    public Move getCapture (Point start, Point end,Game g) {
	List<Point> capturablePieces = getCapturablePieces(start, g);
	if (capturablePieces != null && capturablePieces.contains(end)) {
	    // Piece is capturable
	    return getMoveWithCapture(start,end,g);
	} else {
	    return null;
	}
    }

    public List<Point> getAttackedSquares (Point p, Game g) {
	//List<Point> squares = new ArrayList<Point>();
	//for (Move m : getValidMoves(p,g)) {
	//    squares.add(m.getEndLoc());
	//}
	//return squares;
	return getAttackedSquares(p, g.getBoard());
    }
   
    // Pawn will need to override 
    public List<Point> getCapturablePieces (Point p, Game g) {
	List<Point> capturablePieces = new ArrayList<Point>();
	for (Point point : getAttackedSquares(p, g.getBoard())) {
		if (isEnemy(g.getPieceAt(point))) {
			capturablePieces.add(point);
		}
	}
	return capturablePieces;
    }
/*
    public boolean hasMoved() {return hasMoved;}
    public void setHasMoved() {this.hasMoved = true;}
    public void resetHasMoved() {this.hasMoved = false;} 
*/
  
    // Helper methods
   
    public static Color getEnemy (Color c) {return c == Color.WHITE ? Color.BLACK : Color.WHITE;}
 
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
	    sideEffects.add(new Move(endPiece, end, null, !g.hasMoved(endPiece), null));
	}
	return new Move(this, start, end, !g.hasMoved(this), sideEffects);
    }
    
    // Abstract Methods
    public abstract List<Move> getValidMoves(Point p, Game g); // returns all valid points for piece at Point p in Game g
    public abstract List<Point> getAttackedSquares (Point p, Piece[][] board); // returns all attacked squares
    // return list of point to attack end from start    
    public abstract List<Point> getAttackLine (Point start, Point end, Piece[][] board);
}
