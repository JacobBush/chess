import java.awt.Point;
import java.util.List;

public class King extends Piece {	
    public King (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public King (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
