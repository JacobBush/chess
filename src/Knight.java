import java.awt.Point;
import java.util.List;

public class Knight extends Piece {	
    public Knight (Point location, Piece.Color color) {
    	super(location, color);
    }
    
    public Knight (int x, int y, Piece.Color color) {
    	super(x, y, color);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
}
