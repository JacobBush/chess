import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece {	
    public King (Point location, Piece.Color color, Game game) {
    	super(location, color, Piece.Type.KING, game);
    }
    
    public King (int x, int y, Piece.Color color, Game game) {
    	super(x, y, color, Piece.Type.KING, game);
    }

    public List<Point> getValidMoves() {
    	List<Point> validMoves = new ArrayList<Point>();
    	Point p = this.getLocation();
    	Game g = this.getGame();
    	for (int x = -1; x <= 1; x ++) {
    		for (int y = -1; y <= 1; y++) {
    			if (x == 0 && y == 0) continue;
    			Point pp = new Point(p.x + x, p.y + y);
    			Piece piece = g.getPieceAt(pp);
    			if (isEmpty(piece) || isEnemy(piece)) validMoves.add(pp);
    		}
    	}
    	return validMoves;
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
