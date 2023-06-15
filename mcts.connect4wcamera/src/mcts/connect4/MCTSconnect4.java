package mcts.connect4;

import java.util.ArrayList;
import java.util.Random;

public class MCTSconnect4 {

	private TreeNodeConnect4 root;
	private TreeNodeConnect4 node;
	private double score;
	private int explorationConst=2;
	private int turn;
	
	MCTSconnect4(int turn ){
		this.turn=turn;
		
	}

	//search for best move in current position
	public TreeNodeConnect4 search(connect4 initialState) {
		//create root node 
		this.root= new TreeNodeConnect4(initialState,null);
		//num of iterations 
		for(int i =0 ; i<3000;i++) {
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
	
	public TreeNodeConnect4 search(connect4 initialState,long time) {
		//create root node 
		this.root= new TreeNodeConnect4(initialState,null);
		long current = System.currentTimeMillis();
		//num of iterations 
		while(System.currentTimeMillis()<current+time|this.root.visits<10 ) {
			//select node
			this.node=select(this.root);
			//score current node
			try {
				this.score=this.rollout(this.node);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			//backpropogate num of visits and score to root node
			this.backpropogate(this.node,this.score);
		}
		//return best move in current position
		 MainConnect4.iterations+=root.visits;
			return get_best_move();

	}
		
	private void backpropogate(TreeNodeConnect4 node1, double score) {
		
		node1.visits++;
		node1.score+=score;
		if(node1.parent!=null) {
			backpropogate(node1.parent,score);
		}
				}

	private TreeNodeConnect4 get_best_move() {
		//define best score and best moves
		double best = -Double.MAX_VALUE;
		ArrayList <TreeNodeConnect4>  bestMoves = new ArrayList <TreeNodeConnect4>  ();
		long move_score;
		
		
		for(int i = 0; i<root.children.length;i++)
		{
			
			
			//scores so small here double precision is not good enough for average will all be equal to zero
			move_score=(root.children[i].score*root.visits)/root.children[i].visits;
			//System.out.println("score  ["+i+"] "+move_score);
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

	public double rollout(TreeNodeConnect4 c4) throws CloneNotSupportedException {
		Random rd = new Random();
		TreeNodeConnect4 copy = (TreeNodeConnect4)c4.clone();
		while(!copy.is_terminal&&copy.board.getLegal().size()>0) {
			copy.board=copy.board.makeMove(copy.board.getLegal().get(rd.nextInt(copy.board.getLegal().size())));
			copy.updateTerminal();
		}
		double reward=getReward(copy);
		return reward;
	}

	private TreeNodeConnect4 select(TreeNodeConnect4 node) {
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
				//System.out.println("UCB1 values "+i+" "+ UCB1Value(node.children[i]));
				
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
	
	private int getReward(TreeNodeConnect4 node) {
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


	private double UCB1Value(TreeNodeConnect4 node) {
		double UCB1=0;
		int multiplier=0;
		if(node.board.getPlyMove()!=turn) {
			multiplier=-1;
		}
		else {
			multiplier=1;
		}
		if(node.visits==0) {return Double.MAX_VALUE;}
		else {
		UCB1 = multiplier*(node.score/node.visits)+this.explorationConst*Math.sqrt(Math.log(this.root.visits)/node.visits);
//		System.out.println(node.score+"/"+node.visits+"+"+this.explorationConst+"*sqrt(log"+this.root.visits+"/"+node.visits);
//		System.out.println("= "+UCB1);
		}
		return UCB1;
		
	}
}
