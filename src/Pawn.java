import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {	
    public Pawn (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.PAWN, game);
    }
    
    public Pawn (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.PAWN, game);
    }

    public List<Point> getValidMoves() {
    	return null;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "P";
    	} else {
    		return "p";
    	}
    }
}
