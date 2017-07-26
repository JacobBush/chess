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
	private Point location;
	public final Color color;
	public final Type type;
	
	// Constructors
	public Piece (int x, int y, Color color, Type type) {
		this.location = new Point(x,y);
		this.color = color;
		this.type = type;		
	}
	public Piece (Point location, Color color, Type type) {
		this.location = new Point(location); // copy constructor
		this.color = color;
		this.type = type;
	}
	
	// getters
    public Point getLocation () {
    	return new Point(location); // copy constructor
    }
    
    // setters
    public void setLocation(Point destination) {
    	// Note - Will only set location if move is valid
    	this.movePiece(destination);
    }
    
    // Other Public Methods
    
    public boolean movePiece (Point destination) {
    	if (validMove(destination)) {
    		this.location.setLocation(destination);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean validMove (Point destination) {
    	List<Point> validMoves = this.getValidMoves();
    	return validMoves.contains(destination);
    }
    // Private Methods
    
    // Abstract Methods
    public abstract List<Point> getValidMoves();
    
}
