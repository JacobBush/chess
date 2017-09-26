import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece {	
	
    public King (Piece.Color color) {
    	super(color, Piece.Type.KING);
    }
    public List<Move> getValidMoves(Point p, Game g) {
    	List<Move> validMoves = new ArrayList<Move>();
    	for (int x = -1; x <= 1; x ++) {
    		for (int y = -1; y <= 1; y++) {
    			if (x == 0 && y == 0) continue;
    			Point pp = new Point(p.x + x, p.y + y);
    			Piece piece = g.getPieceAt(pp);
    			if (isEmpty(piece) || isEnemy(piece)) validMoves.add(new Move(p,pp,null));
    		}
    	}
    	return validMoves;
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
