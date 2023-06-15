package mcts.connect4;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class Window extends JPanel {
  private static final int ROWS = 6;
  private static final int COLS = 7;
  private static final int CELL_SIZE = 100;

  private int[][] board = new int[ROWS][COLS];

  public Window() {
    JFrame frame = new JFrame("Connect 4");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(COLS * CELL_SIZE+15, (ROWS + 1) * CELL_SIZE+CELL_SIZE);
    frame.add(this);
    frame.setVisible(true);
    setBackground(Color.BLUE);
    frame.setAlwaysOnTop(true);
    frame.setLocationRelativeTo(null);

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the game board
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        g.setColor(Color.BLUE);
        g.drawRect(col * CELL_SIZE, (row + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        if (board[row][col] == 0) {
          g.setColor(Color.WHITE);
        } else if (board[row][col] == 1) {
          g.setColor(Color.RED);
        } else if (board[row][col] == 2) {
          g.setColor(Color.YELLOW);
        }
        g.fillOval(col * CELL_SIZE, (row + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
       
      }
    }
  }
  public void setWindow(int [][] board) {
	  this.board=board;
  }
}
  