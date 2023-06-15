package mcts.tictactoe;

import java.util.ArrayList;
import java.util.Random;



public class MCTSTicTacToe {
	
	private TreeNodeTicTacToe root;
	private TreeNodeTicTacToe node;
	private double score;
	private int explorationConst=2;
	private int turn;
	
	MCTSTicTacToe(int turn ){
		this.turn=turn;
		
	}

	//search for best move in current position
	public TreeNodeTicTacToe search(tictactoe initialState) {
		//create root node 
		this.root= new TreeNodeTicTacToe(initialState,null);
		//num of iterations 
		for(int i =0 ; i<30000;i++) {
			//select node
			this.node=select(this.root);
			//score current node
			try {
				this.score=this.rollout(this.node);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//backpropogate num of visits and score to root node
			this.backpropogate(this.node,this.score);
		}
		//return best move in current position
			return get_best_move();

	}
	
	public TreeNodeTicTacToe search(tictactoe initialState, long time) {
		 long current=System.currentTimeMillis();
		//create root node 
		this.root= new TreeNodeTicTacToe(initialState,null);
		//num of iterations 
		while(System.currentTimeMillis()<current+time|this.root.visits<500) {
			//select node
			this.node=select(this.root);
			//score current node
			try {
				this.score=this.rollout(this.node);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//backpropogate num of visits and score to root node
			this.backpropogate(this.node,this.score);
			MainTicTacToe.iterations+=this.root.visits;
		
		}
		//System.out.println("iterations completed = "+this.root.visits);
		//return best move in current position
			return get_best_move();

	}
		
	private void backpropogate(TreeNodeTicTacToe node1, double score) {
		
		node1.visits++;
		node1.score+=score;
		if(node1.parent!=null) {
			backpropogate(node1.parent,score);
		}
				}

	private TreeNodeTicTacToe get_best_move() {
		//define best score and best moves
		double best = -Double.MAX_VALUE;
		ArrayList <TreeNodeTicTacToe>  bestMoves = new ArrayList <TreeNodeTicTacToe>  ();
		double move_score;
		
		
		for(int i = 0; i<root.children.length;i++)
		{
			
			
			
			move_score=(root.children[i].score*root.visits)/root.children[i].visits;
			 
			//System.out.println("score ["+i+"] is "+ move_score);
		//	System.out.println("score  ["+i+"] "+move_score);
			//better move found
			if(move_score>best) {
				best=move_score;
				bestMoves.clear();
				bestMoves.add(root.children[i]);
			}
			else if (move_score==best) {
				bestMoves.add(root.children[i]);
			}
		}
		
		//return one of best moves randomly
		Random rd= new Random();
		int pick = rd.nextInt(bestMoves.size());
		//System.out.println("chose move "+ bestMoves.get(pick)+" "+pick + " with a score of "+ bestMoves.get(pick).score);
		
		return bestMoves.get(pick);
	}

	public double rollout(TreeNodeTicTacToe c4) throws CloneNotSupportedException {
		Random rd = new Random();
		TreeNodeTicTacToe copy = (TreeNodeTicTacToe)c4.clone();
		while(!copy.is_terminal&&copy.board.legal.size()>0) {
			copy.board=copy.board.makeMove(copy.board.legal.get(rd.nextInt(copy.board.legal.size())));
			copy.updateTerminal();
		}
		double reward=getReward(copy);
		return reward;
	}

	private TreeNodeTicTacToe select(TreeNodeTicTacToe node) {
		double UCB1=-Double.MAX_VALUE;
		int find=0;
		node.updateTerminal();
		if (node.visits==0||node.is_terminal){
			//System.out.println("no visits return node");
			return node;
		}
		else if(node.visits>0&&node.expanded==false) {
			node.calcChildren();
			for( int i =0 ;i<node.children.length;i++) {
				
				if(UCB1<UCB1Value(node.children[i])){
					UCB1=UCB1Value(node.children[i]);
					find=i;
				}
			}
			//System.out.println("choose "+find );
			
			return select(node.children[find]);
		}
		else if(node.visits>0&&node.expanded==true) {
			for( int i =0 ;i<node.children.length;i++) {
				
				//System.out.println("UCB1 values "+i+" "+ UCB1Value(node.children[i]));
				if(UCB1<UCB1Value(node.children[i])){
					UCB1=UCB1Value(node.children[i]);
					find=i;
					
				}
			}
			
			//System.out.println("choose "+find );
			return select(node.children[find]);
		}
		
		System.out.println(":)");
			return node;
		
	}
	
	private int getReward(TreeNodeTicTacToe node) {
		if(node.is_terminal) {
			if(node.board.checkWin()) {
				if(node.board.getPlyMove()!=turn) {
					return 1;
				}
				else {
					return -1;
				}
			}
		}
	
				return 0;
		}


	private double UCB1Value(TreeNodeTicTacToe node) {
		double UCB1=0;
		int multiplier=0;
		if(node.board.getPlyMove()==turn) {
			multiplier=-1;
		}
		else {
			multiplier=1;
		}
		if(node.visits==0) {return 1000000;}
		else {
		UCB1 = multiplier*(node.score/node.visits)+this.explorationConst*Math.sqrt(Math.log(this.root.visits)/node.visits);
//		System.out.println(node.score+"/"+node.visits+"+"+this.explorationConst+"*sqrt(log"+this.root.visits+"/"+node.visits);
//		System.out.println("= "+UCB1);
		}
		return UCB1;
		
	}

}
