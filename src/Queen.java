import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {	
    public Queen (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.QUEEN, game);
    }
    
    public Queen (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.QUEEN, game);
    }

    public List<Point> getValidMoves() {
    	List<Point> validMoves = new ArrayList<Point>();
    	Point p = this.getLocation();
    	
    	// From Rook
    	for (int y = p.y + 1; y < Game.BOARD_SIZE; y ++) { // up
    		Point pp = new Point(p.x, y);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int y = p.y - 1; y >= 0; y --) { // down
    		Point pp = new Point(p.x, y);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int x = p.x + 1; x < Game.BOARD_SIZE; x ++) { // right
    		Point pp = new Point(x, p.y);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int x = p.x - 1; x >= 0; x --) { // left
    		Point pp = new Point(x, p.y);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	
    	// From Bishop
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-right
    		Point pp = new Point(p.x + i, p.y + i);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-right
    		Point pp = new Point(p.x + i, p.y - i);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-left
    		Point pp = new Point(p.x - i, p.y + i);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-left
    		Point pp = new Point(p.x - i, p.y - i);
    		if (checkValidity(pp)) validMoves.add(pp);
    		if (checkBreak(pp)) break;
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
