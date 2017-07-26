import java.awt.Point;
import java.util.List;

public class Pawn extends Piece {	
    public Pawn (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.PAWN);
    }
    
    public Pawn (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.PAWN);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "P";
    	} else {
    		return "p";
    	}
    }
}
