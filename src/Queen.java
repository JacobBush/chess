import java.awt.Point;
import java.util.List;

public class Queen extends Piece {	
    public Queen (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.QUEEN, game);
    }
    
    public Queen (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.QUEEN, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "Q";
    	} else {
    		return "q";
    	}
    }
}
