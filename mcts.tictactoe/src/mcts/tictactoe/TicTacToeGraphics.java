package mcts.tictactoe;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TicTacToeGraphics extends JPanel {
    private static final long serialVersionUID = 1L;
    JFrame frame = new JFrame("Tic-Tac-Toe");
    private char[][] board = new char[][] {
        {'-', '-', '-'},
        {'-', ' ', ' '},
        {'-', '-', '-'}
    };
    private int squareSize = 100;
    
    public TicTacToeGraphics() {
        // Set the size of the panel to fit the board
        this.setPreferredSize(new java.awt.Dimension(squareSize * 3, squareSize * 3));
   
        
    }
    
    public void setBoard(char[][] board) {
        this.board = board;
        // Repaint the panel to update the graphics
        this.repaint();
        frame.repaint();
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, squareSize * 3, squareSize * 3);
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char symbol = board[row][col];
                if (symbol != '-') {
                    // Draw X or O symbol
                    g.setColor(symbol == 'x' ? Color.RED : Color.BLUE);
                    int x = col * squareSize + squareSize/2-10 ;
                    int y = row * squareSize + squareSize/2+10 ;
                    g.setFont(g.getFont().deriveFont((float)squareSize / 2));
                    g.drawString(Character.toString(symbol), x, y);
                }
            }
        }
        
        // Draw grid lines
        g.setColor(Color.black);
        for (int i = 1; i < 3; i++) {
            g.drawLine(0, i * squareSize, squareSize * 3, i * squareSize);
            g.drawLine(i * squareSize, 0, i * squareSize, squareSize * 3);
        }
    }
}