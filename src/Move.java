// This class is an abstaction to move a chess piece
import java.util.List;
import java.awt.Point;

public class Move {
    private Piece piece;
    private Point startLoc;
    private Point endLoc;
    private List<Move> sideEffects;

    public Move (Piece piece, Point startLoc, Point endLoc, List<Move> sideEffects) {
        // piece : Piece that is being moved
	// startLoc : starting location
	// endLoc : ending location (if null, piece will be removed)
	// sideEffects : List of moves that will also be executed
	this.piece = piece;
	this.startLoc = startLoc;
	this.endLoc = endLoc;
	this.sideEffects = sideEffects;
    }

    // Getters
    public Piece getPiece () {return piece;}
    public Point getStartLoc () {return startLoc;}
    public Point getEndLoc () {return endLoc;}
    public List<Move> getSideEffects () {return sideEffects;}
}
