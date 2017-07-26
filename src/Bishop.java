import java.awt.Point;
import java.util.List;

public class Bishop extends Piece {	
    public Bishop (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.BISHOP, game);
    }
    
    public Bishop (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.BISHOP, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "B";
    	} else {
    		return "b";
    	}
    }
}
