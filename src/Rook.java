import java.awt.Point;
import java.util.List;

public class Rook extends Piece {	
    public Rook (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.ROOK, game);
    }
    
    public Rook (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.ROOK, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "R";
    	} else {
    		return "r";
    	}
    }
}
