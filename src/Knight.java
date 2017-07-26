import java.awt.Point;
import java.util.List;

public class Knight extends Piece {	
    public Knight (Point location, Piece.Color color) {
    	super(location, color, Piece.Type.KNIGHT);
    }
    
    public Knight (int x, int y, Piece.Color color) {
    	super(x, y, color, Piece.Type.KNIGHT);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.color == Piece.Color.BLACK) {
    		return "N";
    	} else {
    		return "n";
    	}
    }    
}
