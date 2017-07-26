import java.awt.Point;
import java.util.List;

public class Bishop extends Piece {	
    public Bishop (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public Bishop (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
