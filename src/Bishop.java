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
    
    public List<Point> getAttackLine(Point start, Point end, Piece[][] board) {return null;}
    public List<Point> getCapturablePieces (Point p, Piece[][] board) {return null;}

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
