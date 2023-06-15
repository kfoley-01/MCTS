package mcts.tictactoe;

import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class frame extends JFrame {
	
	JFrame frame;
	
		public frame(){
	    	initialise();
		}
	    	
	    	
		private void initialise() {
	    	setVisible(true);
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setSize(640,480);
	    	setLayout(new GridBagLayout());
	    	add(new JLabel("Loading......"));
		}


		public void addPic(BufferedImage img) {
			getContentPane().removeAll();
			 add(new JLabel(new ImageIcon(img)));
			 pack();
			 repaint();
		}
	    	
	        
	    	
	    }
