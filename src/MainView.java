import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainView extends JPanel {
	
	private ViewController vc;
	
	public MainView(ViewController vc) {
		super();
		this.vc = vc;
		this.setBackground(Color.BLUE);
		
		this.setLayout(new BorderLayout());
		
		this.add(new MainViewHeader(), BorderLayout.NORTH);
		this.add(new MainViewBody(), BorderLayout.CENTER);
		this.add(new MainViewFooter(), BorderLayout.SOUTH);
		
	}

	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
	}
	
	private class MainViewHeader extends JPanel {
		public MainViewHeader() {
			super();
			this.setPreferredSize(new Dimension(0,vc.HEADER_HEIGHT));
			this.setBackground(Color.YELLOW);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	private class MainViewFooter extends JPanel {
		public MainViewFooter() {
			super();
			this.setPreferredSize(new Dimension(0,vc.FOOTER_HEIGHT));
			this.setBackground(Color.GREEN);
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
	
	private class MainViewBody extends JPanel {
		public MainViewBody() {
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBackground(Color.RED);
			
			this.add(Box.createVerticalGlue()); // spacing
			JButton b = new JButton ("Play");
			b.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					vc.selectView(ViewController.ViewSelector.GAME);
		        }
			});
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(b);
			this.add(Box.createVerticalGlue()); // spacing
		}
		@Override
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
		}
	}
}