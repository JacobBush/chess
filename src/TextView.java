// Simple text-based listener to game
// Will print to screen when a change occurs

public class TextView implements Observer {
	
	private Game game;
	
	public TextView(Game game) {
		this.game = game;
	}
	
	public void update(Object observable, String message) {
		System.out.println(this.game);
	}
    public void update(Object observable) {
    	this.update(observable, "");
    }
	
}