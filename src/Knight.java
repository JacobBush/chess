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
    
    public List<Point> getAttackedSquares (Point p, Piece[][] board) {
	List<Point> squares = new ArrayList<Point>();	
    	
	Point pp = new Point (p.x + 2, p.y + 1); // right-up
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x + 2, p.y - 1); // right-down
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x - 2, p.y + 1); // left-up
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x - 2, p.y - 1); // left-down
	if (Game.validPoint(pp)) squares.add(pp);
    	
    	pp = new Point (p.x + 1, p.y + 2); // up-right
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x - 1, p.y + 2); // up-left
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x + 1, p.y - 2); // down-right
	if (Game.validPoint(pp)) squares.add(pp);

    	pp = new Point (p.x - 1, p.y - 2); // down-left
	if (Game.validPoint(pp)) squares.add(pp);
	
	return squares;
    }

    public List<Point> getAttackLine(Point start, Point end, Piece[][] board) {
	List<Point> attackLine = new ArrayList<Point>();
	List<Point> attackedSquares = getAttackedSquares(start, board);
	if (attackedSquares != null && attackedSquares.contains(end)) {
	    attackLine.add(end); // knight moves directly
	}
	return attackLine;
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
