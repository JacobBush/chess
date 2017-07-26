import java.awt.Point;
import java.util.List;

public class King extends Piece {	
    public King (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.KING, game);
    }
    
    public King (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.KING, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "K";
    	} else {
    		return "k";
    	}
    }
}
