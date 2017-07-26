import java.awt.Point;
import java.util.List;

public class Queen extends Piece {	
    public Queen (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public Queen (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
