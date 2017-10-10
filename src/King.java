import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece {	
	
    // Constants for castling
    private static int RIGHT = 1;
    private static int LEFT = -1;

    public King (Piece.Color color) {
    	super(color, Piece.Type.KING);
    }
    public List<Move> getValidMoves(Point p, Game g) {
    	List<Move> validMoves = new ArrayList<Move>();
	ArrayList<Move> sideEffects;
    	for (int x = -1; x <= 1; x ++) {
    	    for (int y = -1; y <= 1; y++) {
    		if (x == 0 && y == 0) continue;
    		Point pp = new Point(p.x + x, p.y + y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m);
    	    }
    	}
	
	// Castling
	Move m = getCastle(p, RIGHT,g);
	if (m!=null) validMoves.add(m);

	m = getCastle(p, LEFT, g);
	if (m!= null) validMoves.add(m);

    	return validMoves;
    }

    private Move getCastle(Point loc, int dir, Game g) {
	//TODO: Castling
	// To castle we need:
	//	King hasn't moved
	//	Rook hasn't moved
	//	There are no pieces between king and rook
	//	** king isn't in check (move through check?
	if (g.hasMoved(this)) return null;
	if (g.isAttackedBy(loc, getEnemy(this.getColor()))) return null; // can't castle - in check 
	int x = loc.x;
	int y = loc.y;
	Point p = new Point(x + dir, y);
	while (Game.validPoint(p)) {
	    Piece piece = g.getPieceAt(p);
	    if (piece == null) {
		// Need to check that the king wont move throgh check
		if (g.isAttackedBy(p, getEnemy(this.getColor()))) return null; // can't castle 
		p.translate(dir, 0);
		continue;
	    }
	    if (piece.getType() == Type.ROOK && isAlly(piece) && !g.hasMoved(piece)) {
		// is an allied rook that hasn't moved
		List<Move> sideEffects = new ArrayList<Move>();
		sideEffects.add(new Move(piece, p, new Point(x + dir, y), true, null));
		return new Move(this, loc, new Point(x+2*dir, y), true, sideEffects);
	    }
	    return null; // not an allied rook, or has moved
	}	
	return null;
    }

    public List<Point> getAttackedSquares (Point p, Piece[][] board) {
	List<Point> squares = new ArrayList<Point>();
    	for (int x = -1; x <= 1; x ++) {
    	    for (int y = -1; y <= 1; y++) {
    		if (x == 0 && y == 0) continue;
		Point loc = new Point (p.x + x, p.y + y);
    		if (Game.validPoint(loc)) squares.add(loc);
    	    }
    	}
	return squares;
    }


    public List<Point> getAttackLine(Point start, Point end, Piece[][] board) {
	List<Point> attackLine = new ArrayList<Point>();
	List<Point> attackedSquares = getAttackedSquares(start, board);
	if (attackedSquares != null && attackedSquares.contains(end)) {
	    attackLine.add(end); // king can only move 1 space
	}
	return attackLine;
    }

    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "K";
    	} else {
    		return "k";
    	}
    }
}
