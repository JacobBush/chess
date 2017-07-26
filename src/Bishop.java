import java.awt.Point;
import java.util.List;

public class Bishop extends Piece {	
    public Bishop (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.BISHOP);
    }
    
    public Bishop (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.BISHOP);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "B";
    	} else {
    		return "b";
    	}
    }
}
