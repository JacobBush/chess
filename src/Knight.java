import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {	
    public Knight (Piece.Color color) {
    	super(color, Piece.Type.KNIGHT);
    }

    public List<Point> getValidMoves(Point p, Game g) {
    	ArrayList<Point> validMoves = new ArrayList<Point>();
    	
    	Point pp = new Point (p.x + 2, p.y + 1); // right-up
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x + 2, p.y - 1); // right-down
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x - 2, p.y + 1); // left-up
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x - 2, p.y - 1); // left-down
    	if (checkValidity (pp, g)) validMoves.add(pp);
    	
    	pp = new Point (p.x + 1, p.y + 2); // up-right
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x - 1, p.y + 2); // up-left
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x + 1, p.y - 2); // down-right
    	if (checkValidity (pp, g)) validMoves.add(pp);

    	pp = new Point (p.x - 1, p.y - 2); // down-left
    	if (checkValidity (pp, g)) validMoves.add(pp);
    	
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
