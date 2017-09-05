import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {	
    public Pawn (Piece.Color color) {
    	super(color, Piece.Type.PAWN);
    }

    public List<Point> getValidMoves(Point p, Game g) {
    	List<Point> validMoves = new ArrayList<Point>();
    	int direction = this.getColor() == Piece.Color.BLACK ? -1 : 1;
    	int homeRow = direction == 1 ? 1 : 6;
    	
    	Point pp = new Point(p.x, p.y + direction);
    	if (isEmpty(g.getPieceAt(pp))) validMoves.add(pp);
    	
		pp = new Point(p.x, p.y + 2*direction);
		if (p.y == homeRow && isEmpty(g.getPieceAt(pp))) validMoves.add(pp);
		
		pp = new Point (p.x - 1, p.y + direction);
		Piece piece = g.getPieceAt(pp);
		if (isEnemy(g.getPieceAt(pp))) validMoves.add(pp);
    	
		pp = new Point (p.x + 1, p.y + direction);
		piece = g.getPieceAt(pp);
		if (isEnemy(g.getPieceAt(pp))) validMoves.add(pp);
		
    	return validMoves;
    }
    
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
