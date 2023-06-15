package games.tictactoe;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class Window{

	/**
	 * 
	 */
	
	GameState gs;
	JLabel x_o[]= new JLabel[9];
	JPanel panel= new JPanel(new GridLayout(3,3));
	JFrame f = new JFrame();
	
	Window(GameState gs){
		this.gs=gs;
	for(int i=0;i<x_o.length;i++) {
		x_o[i]=new JLabel(gs.gameToArray()[i]);
		x_o[i].setSize(200,200);
		panel.add(x_o[i]);	
	}
	f.add(panel);
	f.setSize(400,400);
	f.setVisible(true);
	f.setLocationRelativeTo(null);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	Window(){
		Border blackline = BorderFactory.createLineBorder(Color.black);
		for(int i=0;i<x_o.length;i++) {
			x_o[i]=new JLabel("-",SwingConstants.CENTER);
			x_o[i].setSize(200,200);
			x_o[i].setFont(new Font("Calibri", Font.BOLD, 40));
			x_o[i].setBorder(blackline);
			panel.add(x_o[i]);	
		}
		f.add(panel);
		f.setSize(500,500);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void refresh(GameState gs) {
		for(int i=0;i<x_o.length;i++) {
			System.out.println(gs.gameToArray()[i]);
			x_o[i].setText(gs.gameToArray()[i]);
	}

}
}
