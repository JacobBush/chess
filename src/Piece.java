import java.awt.Point;
import java.util.List;

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
	
	// Constructor
	public Piece (Color color, Type type) {
		this.color = color;
		this.type = type;
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
	    if (m.getEndLoc().equals(end)) {
		return m;
	    }
	}
    	return null;
    }
    
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
    
    // Abstract Methods
    public abstract List<Move> getValidMoves(Point p, Game g); // returns all valid points for piece at Point p in Game g
    
}
