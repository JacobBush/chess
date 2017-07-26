import java.awt.Point;
import java.util.List;

public class Rook extends Piece {	
    public Rook (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public Rook (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
