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
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-right
    		Point pp = new Point(p.x + i, p.y - i);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-left
    		Point pp = new Point(p.x - i, p.y + i);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-left
    		Point pp = new Point(p.x - i, p.y - i);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	
    	return validMoves;
    }
    
    public List<Point> getAttackedSquares (Point p, Piece[][] board) {
    	List<Point> squares = new ArrayList<Point>();

    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-right
    		Point pp = new Point(p.x + i, p.y + i);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-right
    		Point pp = new Point(p.x + i, p.y - i);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // up-left
    		Point pp = new Point(p.x - i, p.y + i);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int i = 1; i < Game.BOARD_SIZE; i ++) { // down-left
    		Point pp = new Point(p.x - i, p.y - i);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
	return squares;
    }    
    
    public List<Point> getAttackLine(Point start, Point end, Piece[][] board) {
	List<Point> attackedSquares = getAttackedSquares(start,board);
	if (attackedSquares != null && attackedSquares.contains(end)) {
		// The attack line exists
		List<Point> attackLine = new ArrayList<Point>();
		int xdir = end.x - start.x;
		int ydir = end.y - start.y;
		int maxDist = xdir > ydir ? xdir : ydir; // >= 1 since line exists
		if (maxDist == 0) return null; // could throw instead
		// Unit vectors
		xdir = xdir == 0 ? 0 : xdir > 0 ? 1 : -1;
		ydir = ydir == 0 ? 0 : ydir > 0 ? 1 : -1;
		
		for (int i = 1; i <= maxDist; i ++) {
			attackLine.add(new Point(start.x + i*xdir, start.y + i*ydir));
		}
		return attackLine;
	} else {
		return null;
	}
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
