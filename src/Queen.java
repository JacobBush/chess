import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {
	
    public Queen (Piece.Color color) {
    	super(color, Piece.Type.QUEEN);
    }

    public List<Point> getValidMoves(Point p, Game g) {
    	List<Point> validMoves = new ArrayList<Point>();
    	
    	// From Rook
    	for (int y = p.y + 1; y < Game.BOARD_SIZE; y ++) { // up
    		Point pp = new Point(p.x, y);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int y = p.y - 1; y >= 0; y --) { // down
    		Point pp = new Point(p.x, y);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int x = p.x + 1; x < Game.BOARD_SIZE; x ++) { // right
    		Point pp = new Point(x, p.y);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	for (int x = p.x - 1; x >= 0; x --) { // left
    		Point pp = new Point(x, p.y);
    		if (checkValidity(pp, g)) validMoves.add(pp);
    		if (checkBreak(pp, g)) break;
    	}
    	
    	// From Bishop
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
    		return "Q";
    	} else {
    		return "q";
    	}
    }
}
