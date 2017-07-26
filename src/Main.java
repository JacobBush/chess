import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			runProgram();
    		}
    	});        
    }
    
    private static void runProgram () {
    	Game game = new Game();
    	System.out.println(game);
    }
}
