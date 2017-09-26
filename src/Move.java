// This class is an abstaction to move a chess piece
import java.util.List;
import java.awt.Point;

public class Move {
    private Point startLoc;
    private Point endLoc;
    private List<Move> sideEffects;

    public Move (Point startLoc, Point endLoc, List<Move> sideEffects) {
        // startLoc : starting location
	// endLoc : ending location (if null, piece will be removed)
	// sideEffects : List of moves that will also be executed
	this.startLoc = startLoc;
	this.endLoc = endLoc;
	this.sideEffects = sideEffects;
    }

    // Getters
    public Point getStartLoc () {return startLoc;}
    public Point getEndLoc () {return endLoc;}
    public List<Move> getSideEffects () {return sideEffects;}
}
