package mcts.connect4;

import java.lang.Math.*;
import java.util.ArrayList;
import java.util.random.*;

	public class TreeNodeConnect4 implements Cloneable{
		
		public connect4 board;
		public boolean is_terminal;
		public TreeNodeConnect4 parent;
		public int visits;
		public int score;
		TreeNodeConnect4  [] children;
		public boolean expanded;

		TreeNodeConnect4(connect4 board,TreeNodeConnect4 parent){
			//initialise board state
			this.board=board;
			this.parent=parent;
			
		//check if terminal state
			if(this.board.checkWin()||this.board.checkDraw()) {
				this.is_terminal=true;
			}
			else {
				this.is_terminal=false;
			}
			
			
			this.parent=parent;
			this.visits=0;
			this.score=0;
			this.children= new TreeNodeConnect4[this.board.computeLegal().size()];
			this.expanded=false;
		}
		
		  protected Object clone() throws CloneNotSupportedException {
	        TreeNodeConnect4 copy = (TreeNodeConnect4) super.clone();
	 
	        copy.board = (connect4) board.clone();
	 
	        return copy;
	    }
		
		public void calcChildren() {
			
			if(!this.is_terminal) {
			for(int i =0 ;i<this.board.computeLegal().size();i++) {
				TreeNodeConnect4 child =new TreeNodeConnect4(this.board.makeMove(this.board.computeLegal().get(i)), this);
				this.children[i]=child;
				}}else {
			System.out.println("state is terminal (children)");
			}
			this.expanded=true;
			
		}
		
		public void printChildren() {
			
			System.out.println("******\nPRINTING ALL CHILDREN\n\n");
			for(int i = 0;i< this.children.length;i++) {
				if(this.children[i]!=null) {
				System.out.println("CHILD ["+i+"]\n\n");
				this.children[i].board.boardToString();
				System.out.println("******");
				}
			}
		}
		
		public void updateTerminal() {
			if(this.board.checkWin()|this.board.checkDraw()) {
				this.is_terminal=true;
			}
			else {
				this.is_terminal=false;
			}
		}
		
		
		
	}