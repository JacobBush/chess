import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {	
    public Knight (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.KNIGHT, game);
    }
    
    public Knight (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.KNIGHT, game);
    }

    public List<Point> getValidMoves() {
    	ArrayList<Point> validMoves = new ArrayList<Point>();
    	Point p = this.getLocation();
    	
    	Point pp = new Point (p.x + 2, p.y + 1); // right-up
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x + 2, p.y - 1); // right-down
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x - 2, p.y + 1); // left-up
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x - 2, p.y - 1); // left-down
    	if (checkValidity (pp)) validMoves.add(pp);
    	
    	pp = new Point (p.x + 1, p.y + 2); // up-right
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x - 1, p.y + 2); // up-left
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x + 1, p.y - 2); // down-right
    	if (checkValidity (pp)) validMoves.add(pp);

    	pp = new Point (p.x - 1, p.y - 2); // down-left
    	if (checkValidity (pp)) validMoves.add(pp);
    	
    	return validMoves;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "N";
    	} else {
    		return "n";
    	}
    }    
}
