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

        Point pp = new Point(p.x, p.y + direction);
        if (isEmpty(g.getPieceAt(pp))) validMoves.add(new Move (this,p,pp,null));

        pp = new Point(p.x, p.y + 2*direction);
        if (p.y == homeRow && isEmpty(g.getPieceAt(pp))) validMoves.add(new Move(this,p,pp,null));

        ArrayList<Move> sideEffects;

        pp = new Point (p.x - 1, p.y + direction);
	sideEffects = new ArrayList<Move>();
        sideEffects.add(new Move(g.getPieceAt(pp), pp, null, null)); // Piece is captured and moved off board
	Piece piece = g.getPieceAt(pp);
	if (isEnemy(g.getPieceAt(pp))) validMoves.add(new Move(this,p,pp,sideEffects));
    	
	pp = new Point (p.x + 1, p.y + direction);
	sideEffects = new ArrayList<Move>();
	sideEffects.add(new Move(g.getPieceAt(pp), pp, null, null));
	piece = g.getPieceAt(pp);
	if (isEnemy(g.getPieceAt(pp))) validMoves.add(new Move(this,p,pp,sideEffects));
		
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
