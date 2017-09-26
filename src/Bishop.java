import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece {
	
    public Bishop (Piece.Color color) {
    	super(color, Piece.Type.BISHOP);
    }

    public List<Move> getValidMoves(Point p, Game g) {
    	List<Move> validMoves = new ArrayList<Move>();
    	
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-right
    		Point pp = new Point(p.x + i, p.y + i);
    		if (checkValidity(pp, g)) validMoves.add(new Move(p,pp,null));
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-right
    		Point pp = new Point(p.x + i, p.y - i);
    		if (checkValidity(pp, g)) validMoves.add(new Move(p,pp,null));
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-left
    		Point pp = new Point(p.x - i, p.y + i);
    		if (checkValidity(pp, g)) validMoves.add(new Move(p,pp,null));
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-left
    		Point pp = new Point(p.x - i, p.y - i);
    		if (checkValidity(pp, g)) validMoves.add(new Move(p,pp,null));
    		if (checkBreak(pp, g)) break;
    	}
    	
    	return validMoves;
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
