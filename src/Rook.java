import java.awt.Point;
import java.util.List;

public class Rook extends Piece {	
    public Rook (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.ROOK);
    }
    
    public Rook (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.ROOK);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "R";
    	} else {
    		return "r";
    	}
    }
}
