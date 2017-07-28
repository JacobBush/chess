import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece {	
    public Bishop (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.BISHOP, game);
    }
    
    public Bishop (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.BISHOP, game);
    }

    public List<Point> getValidMoves() {
    	List<Point> validMoves = new ArrayList<Point>();
    	Point p = this.getLocation();
    	
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
    		return "B";
    	} else {
    		return "b";
    	}
    }
}
