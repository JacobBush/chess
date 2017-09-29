import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece {	
	
    // Constants for castling
    private static int RIGHT = 1;
    private static int LEFT = -1;

    public King (Piece.Color color) {
    	super(color, Piece.Type.KING);
    }
    public List<Move> getValidMoves(Point p, Game g) {
    	List<Move> validMoves = new ArrayList<Move>();
	ArrayList<Move> sideEffects;
    	for (int x = -1; x <= 1; x ++) {
    	    for (int y = -1; y <= 1; y++) {
    		if (x == 0 && y == 0) continue;
    		Point pp = new Point(p.x + x, p.y + y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m);
    	    }
    	}
	
	// Castling
	Move m = getCastle(p, RIGHT,g);
	if (m!=null) validMoves.add(m);

	m = getCastle(p, LEFT, g);
	if (m!= null) validMoves.add(m);

    	return validMoves;
    }

    private Move getCastle(Point loc, int dir, Game g) {
	//TODO: Castling
	// To castle we need:
	//	King hasn't moved
	//	Rook hasn't moved
	//	There are no pieces between king and rook
	//	** king isn't in check (move through check?
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
