import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Rook extends Piece {	
    public Rook (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.ROOK, game);
    }
    
    public Rook (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.ROOK, game);
    }

    public List<Point> getValidMoves() {
    	List<Point> validMoves = new ArrayList<Point>();
    	Point p = this.getLocation();
    	
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
    	
    	return validMoves;
    }
    
    private boolean checkValidity (Point p) {
    	if (Game.validPoint (p)) {
			Piece piece = this.getGame().getPieceAt(p);
			return isEmpty(piece) || isEnemy(piece);
    	}
    	return false;
    }
    private boolean checkBreak (Point p) {
    	if (Game.validPoint (p)) {
    		Piece piece = this.getGame().getPieceAt(p);
			return isAlly(piece) || isEnemy(piece);
    	}
    	return false;
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
