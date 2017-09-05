import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece {
	
    public Bishop (Piece.Color color) {
    	super(color, Piece.Type.BISHOP);
    }

    public List<Point> getValidMoves(Point p, Game g) {
    	List<Point> validMoves = new ArrayList<Point>();
    	
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-right
    		Point pp = new Point(p.x + i, p.y + i);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-right
    		Point pp = new Point(p.x + i, p.y - i);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-left
    		Point pp = new Point(p.x - i, p.y + i);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-left
    		Point pp = new Point(p.x - i, p.y - i);
    		if (checkValidity(pp, g)) validMoves.add(pp);
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
