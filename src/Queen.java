import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {
	
    public Queen (Piece.Color color) {
    	super(color, Piece.Type.QUEEN);
    }

    public List<Move> getValidMoves(Point p, Game g) {
    	List<Move> validMoves = new ArrayList<Move>();
    	
    	// From Rook
    	for (int y = p.y + 1; y < Game.BOARD_SIZE; y ++) { // up
    		Point pp = new Point(p.x, y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int y = p.y - 1; y >= 0; y --) { // down
    		Point pp = new Point(p.x, y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int x = p.x + 1; x < Game.BOARD_SIZE; x ++) { // right
    		Point pp = new Point(x, p.y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	for (int x = p.x - 1; x >= 0; x --) { // left
    		Point pp = new Point(x, p.y);
		Move m = getMoveWithCapture(p,pp,g);
		if (m != null) validMoves.add(m); else break;
    	}
    	
    	// From Bishop
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

	// From Rook	
    	for (int y = p.y + 1; y < Game.BOARD_SIZE; y ++) { // up
    		Point pp = new Point(p.x, y);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int y = p.y - 1; y >= 0; y --) { // down
    		Point pp = new Point(p.x, y);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int x = p.x + 1; x < Game.BOARD_SIZE; x ++) { // right
    		Point pp = new Point(x, p.y);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}
    	for (int x = p.x - 1; x >= 0; x --) { // left
    		Point pp = new Point(x, p.y);
		if (Game.validPoint(pp)) squares.add(pp);
		if (checkBreak(pp, board)) break;
    	}

	// From Bishop
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
	List<Point> attackLine = new ArrayList<Point>();
	return attackLine;
    }
    public List<Point> getCapturablePieces (Point p, Game g) {
	List<Point> capturablePieces = new ArrayList<Point>();
	return capturablePieces;
    }
    
    // usability
    @Override
    public String toString () {
    	if (this.getColor() == Piece.Color.BLACK) {
    		return "Q";
    	} else {
    		return "q";
    	}
    }
}
