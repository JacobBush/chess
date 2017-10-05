import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn (Piece.Color color) {
        super(color, Piece.Type.PAWN);
    }

    public List<Move> getValidMoves(Point p, Game g) {
        List<Move> validMoves = new ArrayList<Move>();
        int direction = this.getColor() == Piece.Color.BLACK ? -1 : 1;
        int homeRow = direction == 1 ? 1 : 6;

        Point pp = new Point(p.x, p.y + direction); // Move forward
        if (isEmpty(g.getPieceAt(pp))) 
	    validMoves.add(new Move (this,p,pp,p.y==homeRow,null));

        pp = new Point(p.x, p.y + 2*direction); // Move forward 2
        if (p.y == homeRow && isEmpty(g.getPieceAt(pp)))
	     validMoves.add(new Move(this,p,pp,true,null));

	// Here is the check for en-passant
	Move lastMove = g.getLastMove();
	if (lastMove != null && lastMove.getPiece() instanceof Pawn) {
	    // Last move must be enemy. Now also must be pawn.
	    // could throw error if it is not an enemy.
	    Point enemEnd = new Point (p.x-1, p.y); // to our left
	    Point enemStart = new Point (p.x-1, p.y + 2*direction); // Pawn moved 2 in opposite of our direction
	    if (enemStart.equals(lastMove.getStartLoc()) && enemEnd.equals(lastMove.getEndLoc())) {
	    	// We are able to en-passant
	    	List<Move> sideEffects = new ArrayList<Move>();
		sideEffects.add(new Move(lastMove.getPiece(), lastMove.getEndLoc(), null,lastMove.getPiece().hasMoved(), null));
		validMoves.add(new Move(this, p, new Point (p.x-1, p.y+direction),!this.hasMoved(), sideEffects));
	    }

	    enemEnd = new Point (p.x+1, p.y); // to our right
	    enemStart = new Point (p.x+1, p.y + 2*direction); // Pawn moved 2 in opposite of our direction
	    if (enemStart.equals(lastMove.getStartLoc()) && enemEnd.equals(lastMove.getEndLoc())) {
	    	// We are able to en-passant
		List<Move> sideEffects = new ArrayList<Move>();
		sideEffects.add(new Move(lastMove.getPiece(), lastMove.getEndLoc(), null,lastMove.getPiece().hasMoved(), null));
		validMoves.add(new Move(this, p, new Point (p.x+1, p.y+direction),!this.hasMoved(), sideEffects));
	    }
	}
	
	// If there is a piece that is capturable, we could not have done en-passant
	// Therefore do not need to check for that
        pp = new Point (p.x - 1, p.y + direction);
	if (isEnemy(g.getPieceAt(pp))) { 
	    // must make this check since getMoveWithCapture works with empty space
	    Move ml = getMoveWithCapture(p,pp,g);
	    if (ml != null) validMoves.add(ml);
	}
    	
	pp = new Point (p.x + 1, p.y + direction);
	if (isEnemy(g.getPieceAt(pp))) { 
	    // must make this check since getMoveWithCapture works with empty space
	    Move ml = getMoveWithCapture(p,pp,g);
	    if (ml != null) validMoves.add(ml);
	}
		
    	return validMoves;
    }

    public List<Point> getAttackedSquares (Point p, Piece[][] board) {
	List<Point> squares = new ArrayList<Point>();	
        int direction = this.getColor() == Piece.Color.BLACK ? -1 : 1;
	Point loc = new Point (p.x + 1, p.y + direction);
	if (Game.validPoint(loc)) squares.add(loc);
	loc = new Point (p.x - 1, p.y + direction);
	if (Game.validPoint(loc)) squares.add(loc);
	return squares;
    }
    
    public List<Point> getAttackLine(Point start, Point end, Piece[][] board) {return null;}
    public List<Point> getCapturablePieces (Point p, Piece[][] board) {return null;}
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "P";
    	} else {
    		return "p";
    	}
    }
}
