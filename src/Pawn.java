import java.awt.Point;
import java.util.List;

public class Pawn extends Piece {	
    public Pawn (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public Pawn (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
