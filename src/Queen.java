import java.awt.Point;
import java.util.List;

public class Queen extends Piece {	
    public Queen (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.QUEEN);
    }
    
    public Queen (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.QUEEN);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "Q";
    	} else {
    		return "q";
    	}
    }
}
