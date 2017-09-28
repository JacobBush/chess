import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {	
    public Knight (Piece.Color color) {
    	super(color, Piece.Type.KNIGHT);
    }

    public List<Move> getValidMoves(Point p, Game g) {
    	ArrayList<Move> validMoves = new ArrayList<Move>();
	Move m;

    	Point pp = new Point (p.x + 2, p.y + 1); // right-up
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x + 2, p.y - 1); // right-down
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x - 2, p.y + 1); // left-up
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x - 2, p.y - 1); // left-down
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);
    	
    	pp = new Point (p.x + 1, p.y + 2); // up-right
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x - 1, p.y + 2); // up-left
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x + 1, p.y - 2); // down-right
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);

    	pp = new Point (p.x - 1, p.y - 2); // down-left
	m = getMoveWithCapture(p,pp,g);
	if (m != null) validMoves.add(m);
    	
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
