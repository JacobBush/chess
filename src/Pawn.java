import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {	
    public Pawn (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.PAWN, game);
    }
    
    public Pawn (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.PAWN, game);
    }

    public List<Point> getValidMoves() {
    	List<Point> validMoves = new ArrayList<Point>();
    	int direction = this.getColor() == Piece.Color.BLACK ? -1 : 1;
    	int homeRow = direction == 1 ? 1 : 6;
    	Point p = this.getLocation();
    	Game g = this.getGame();
    	
    	Point pp = new Point(p.x, p.y + direction);
    	if (g.getPieceAt(pp) == null) validMoves.add(pp);
    	
		pp = new Point(p.x, p.y + 2*direction);
		if (p.y == homeRow && g.getPieceAt(pp) == null) validMoves.add(pp);
		
		pp = new Point (p.x - 1, p.y + direction);
		Piece piece = g.getPieceAt(pp);
		if (piece != null && piece.getColor() != this.getColor()) validMoves.add(pp);
    	
		pp = new Point (p.x + 1, p.y + direction);
		piece = g.getPieceAt(pp);
		if (piece != null && piece.getColor() != this.getColor()) validMoves.add(pp);
		
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
