import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameView extends JLayeredPane  { 
	
	private ViewController vc;
	private GameViewBoard board;
	private Game game;
	
	public GameView (ViewController vc, Game game) {
		super();
		this.vc = vc;
		this.game = game;
		this.setLayout(new BorderLayout());
		this.board = new GameViewBoard(vc, game);
		this.add(this.board, BorderLayout.CENTER);
		this.add(new GameViewHeader(), BorderLayout.NORTH);
		this.add(new GameViewFooter(), BorderLayout.SOUTH);
	}
	
	private class GameViewHeader extends JPanel {
		public GameViewHeader () {
			super();
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
			this.setBackground(Color.BLACK);
			this.setLayout(new BorderLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.VERTICAL;
			
			// Left Buttons
			gbc.insets = new Insets(0, 5, 0, 0);
			
			JPanel leftButtons = new JPanel();
			leftButtons.setBackground(Color.BLACK);
			leftButtons.setLayout(new GridBagLayout());
			this.add(leftButtons, BorderLayout.WEST);
			
			JButton home = new JButton("Home");
			home.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  vc.selectView(ViewController.ViewSelector.MAIN);
				  }
			});
			home.setToolTipText("Return to home screen");
			leftButtons.add(home, gbc);
			
			// Right buttons
			gbc.insets = new Insets(0, 0, 0, 5);
			
			JPanel rightButtons = new JPanel();
			rightButtons.setBackground(Color.BLACK);
			rightButtons.setLayout(new GridBagLayout());
			this.add(rightButtons, BorderLayout.EAST);

			
			JButton undo = new JButton(Character.toString((char) 8678) + " Undo");// Unicode: LEFTWARDS WHITE ARROW
			undo.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  game.undo();
				  }
			});
			undo.setToolTipText("Undo last move");
			rightButtons.add(undo, gbc);
			
			JButton redo = new JButton("Redo " + Character.toString((char) 8680));// Unicode: LEFTWARDS WHITE ARROW
			redo.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  game.redo();
				  }
			});
			redo.setToolTipText("Redo last undo");
			rightButtons.add(redo, gbc);
		}
	}
	
	private class GameViewFooter extends JPanel {
		private final String loudSpeaker = "\uD83D\uDD0A";
		private final String speaker = "\uD83D\uDD08";
		private final String muteSpeaker = "\uD83D\uDD07";
		
		public GameViewFooter () {
			super();
			this.setPreferredSize(new Dimension(0,vc.FOOTER_HEIGHT));
			this.setBackground(Color.BLACK);
			this.setLayout(new BorderLayout());
			
			JPanel rightButtons = new JPanel();
			rightButtons.setBackground(Color.BLACK);
			rightButtons.setLayout(new GridBagLayout());
			this.add(rightButtons, BorderLayout.EAST);
			
			GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.VERTICAL;
			gbc.insets = new Insets(0, 0, 0, 5);
			
			JButton muteButton = new JButton(loudSpeaker);
			muteButton.setToolTipText("Click to mute sounds");
			
			muteButton.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  if (board == null) return;
					  board.toggleMute();
					  if (board.isMuted()) {
						  muteButton.setToolTipText("Click to enable sounds");
						  muteButton.setText(speaker);
					  } else {
						  muteButton.setToolTipText("Click to mute sounds");
						  muteButton.setText(loudSpeaker);
					  }
				  }
			});
			
			rightButtons.add(muteButton, gbc);
		}
	}
}
