package mcts.tictactoe;


public class TreeNodeTicTacToe implements Cloneable{
	
	public tictactoe board;
	public boolean is_terminal;
	public TreeNodeTicTacToe parent;
	public int visits;
	public double score;
	TreeNodeTicTacToe  [] children;
	public boolean expanded;

	TreeNodeTicTacToe(tictactoe board,TreeNodeTicTacToe parent){
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
		this.children= new TreeNodeTicTacToe[this.board.computeLegal().size()];
		this.expanded=false;
	}
	
	  protected Object clone() throws CloneNotSupportedException {
        TreeNodeTicTacToe copy = (TreeNodeTicTacToe) super.clone();
 
        copy.board = (tictactoe) board.clone();
 
        return copy;
    }
	
	public void calcChildren() {
		
		if(!this.is_terminal) {
		for(int i =0 ;i<this.board.computeLegal().size();i++) {
			TreeNodeTicTacToe child =new TreeNodeTicTacToe(this.board.makeMove(this.board.computeLegal().get(i)), this);
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
