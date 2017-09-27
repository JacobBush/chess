import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece {	
	
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
    			Piece piece = g.getPieceAt(pp);
			if (isEnemy(piece)) {
			    sideEffects = new ArrayList<Move>();
			    sideEffects.add(new Move(piece, pp, null, null));
			    validMoves.add(new Move(this,p,pp,sideEffects));
			}
    			if (isEmpty(piece)) validMoves.add(new Move(this,p,pp,null));
    		}
    	}
	// Castling
	// Need : king hasn't moved
	// 	  rook hasn't moved
	//        there's clear space
	if (getCastle(p, -1, g) != null) {
	    //Possible to castle to the left
	    System.out.println("castleleft");
	}
	if (getCastle(p, 1, g) != null) {
	    // Possible to castle to the right
	    System.out.println("castleright");
	}
    	return validMoves;
    }

    private Move getCastle(Point posn, int direction, Game game) {
	if (!game.hasMoved(this)) {
	    Point p = new Point(posn.x + direction, posn.y);
	    while (game.validPoint(p)) {
		Piece piece = game.getPieceAt(p);
		if (piece == null) {
		    p.translate(direction, 0);
		    continue;
		} else if (piece instanceof Rook) {
		    if (!game.hasMoved(piece)) {
			// rook hasn't moved
		    }
		} else {
		    return null;
		}
	    }
	}
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
