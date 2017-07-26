import java.awt.Point;
import java.util.List;

public class Knight extends Piece {	
    public Knight (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.KNIGHT, game);
    }
    
    public Knight (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.KNIGHT, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "N";
    	} else {
    		return "n";
    	}
    }    
}
