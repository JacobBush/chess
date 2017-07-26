import java.awt.Point;
import java.util.List;

public class King extends Piece {	
    public King (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.KING);
    }
    
    public King (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.KING);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "K";
    	} else {
    		return "k";
    	}
    }
}
