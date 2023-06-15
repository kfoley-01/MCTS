package mcts.connect4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class connect4 implements Cloneable{
	
	 dataStore_camera ds_camera = new dataStore_camera();
	 dataStore_game ds_game = new dataStore_game();
	 static camera c;
	 
	private int plyMove;
	private  int board[][];
	private List<Integer> legal = new ArrayList<Integer>();
	
	
	connect4(){
		board= new int [6][7];
		plyMove=1;
	}
	 connect4(connect4 c4) {
		this.board=c4.board;
		this.plyMove=c4.plyMove;
	}
	 
	 @Override
		protected Object clone() throws CloneNotSupportedException {
	        connect4 c4 = (connect4) super.clone();
	        c4.board=deepCopy(this.getBoard());
	        c4.setPlyMove(c4.getPlyMove());
	        return c4;
	    }
	 
	 public static int[][] deepCopy(int[][] original) {
		    if (original == null) {
		        return null;
		    }

		    final int[][] result = new int[original.length][];
		    for (int i = 0; i < original.length; i++) {
		        result[i] = Arrays.copyOf(original[i], original[i].length);
		    }
		    return result;
		}
	 
	
	
	public List<Integer> computeLegal(){
		this.legal.clear();
		for(int i =0;i<this.board[0].length;i++)
		{
			
			if(board[0][i]==0) {
				legal.add(i);
				
			}
		}
		return legal;
	}
	
	public int  gameLoopVsRandom(int turn,long time){
		Scanner sc = new Scanner(System.in);
		this.computeLegal();
		MCTSconnect4 mcts1 = new MCTSconnect4(turn);
		Random rd = new Random();
		//this.boardToString();
		while(true) {
			
			
			//System.out.println("Player "+this.getPlyMove()+" to move");
			
			//all possible moves show debugging to be removed
			//TreeNodeConnect4 debug = new TreeNodeConnect4(this,null);
			connect4 c4 = new connect4();
			if(this.plyMove==1) {
			c4 = mcts1.search(this,time).board;
			}
			else {
				c4 =this.makeMove(this.getLegal().get(rd.nextInt(this.getLegal().size())));
				
			}
			this.board=c4.board;
			
			if(this.checkWin()) {
				//System.out.println("game Won by ply "+this.plyMove);
				if ( this.plyMove==turn)
				{
					return 1;
				}
				else {
					return -1;
				}
			}
			else if (this.checkDraw()){
				//System.out.println("Game is a draw")
				return 0;
			}
			this.plyMove=c4.plyMove;
	}
	}
	public int  gameLoopVsRandom(int turn){
		Scanner sc = new Scanner(System.in);
		this.computeLegal();
		MCTSconnect4 mcts1 = new MCTSconnect4(turn);
		Random rd = new Random();
		//this.boardToString();
		while(true) {
			
			
			//System.out.println("Player "+this.getPlyMove()+" to move");
			
			//all possible moves show debugging to be removed
			//TreeNodeConnect4 debug = new TreeNodeConnect4(this,null);
			connect4 c4 = new connect4();
			if(this.plyMove==1) {
			c4 = mcts1.search(this).board;
			}
			else {
				c4 =this.makeMove(this.getLegal().get(rd.nextInt(this.getLegal().size())));
				
			}
			this.board=c4.board;
			
			if(this.checkWin()) {
				//System.out.println("game Won by ply "+this.plyMove);
				if ( this.plyMove==turn)
				{
					return 1;
				}
				else {
					return -1;
				}
			}
			else if (this.checkDraw()){
				//System.out.println("Game is a draw")
				return 0;
			}
			this.plyMove=c4.plyMove;
	}
	}
	
	public void gameLoopVsPlayer(int turn ){
		Window w = new Window();
		Scanner sc = new Scanner(System.in);
		this.computeLegal();
		MCTSconnect4 mcts1 = new MCTSconnect4(turn);
		Random rd = new Random();
		this.boardToString();
		
		if(turn ==1) {
			System.out.println("MCTS will go first");
		}
		else {
			System.out.println("Player will go first");
			turn=2;
		}
		while(true) {
			
			System.out.println("Player "+this.getPlyMove()+" to move");
			
			//all possible moves show debugging to be removed
			//TreeNodeConnect4 debug = new TreeNodeConnect4(this,null);
			connect4 c4 = new connect4();
			if(this.plyMove==turn) {
			c4 = mcts1.search(this,300).board;
			}
			else {
				int input=100;
				System.out.println("player move");
				while(true) {
					this.computeLegal();
					System.out.println("legal moves are "+this.legal.toString());
					input = sc.nextInt();
					input--;
				
					if(legal.contains(input)) {
						break;
					}
					else {
						System.out.println("illegal move");
					}
					}
				
				c4 =this.makeMove(input);
				
			}
			this.board=c4.board;
			
			
			this.boardToString();
			if(this.checkWin()) {
				if( this.plyMove==turn) {
					System.out.println("MCTS wins ");
				}
				else {
					System.out.println("player wins ");
				}
				System.out.println("game Won by ply "+this.plyMove);
				
				break;
			}
			else if (this.checkDraw()){
				System.out.println("Game is a draw (gl)");
				this.boardToString();
				System.out.println("*******");
				break;
			}
			this.plyMove=c4.plyMove;
			w.setWindow(this.board);
			w.repaint();
			
	}
		w.setWindow(this.board);
		w.repaint();
		
	}
	
	public void playerVSPlayerr(int turn ){
		Window w = new Window();
		Scanner sc = new Scanner(System.in);
		this.computeLegal();
		this.boardToString();
		
		if(turn ==1) {
			System.out.println("MCTS will go first");
		}
		else {
			System.out.println("Player will go first");
			turn=2;
		}
		while(true) {
			
			System.out.println("Player "+this.getPlyMove()+" to move");
			
			//all possible moves show debugging to be removed
			//TreeNodeConnect4 debug = new TreeNodeConnect4(this,null);
			connect4 c4 = new connect4();
			if(this.plyMove==turn) {
				int input=100;
				System.out.println("player move");
				while(true) {
					this.computeLegal();
					System.out.println("legal moves are "+this.legal.toString());
					boolean isDone=false;
					while(!isDone){
					try {
					input = sc.nextInt();
					isDone=true;
					}
					catch(Exception e)
					{
						System.out.println("invalid input");
					}
					}
					if(legal.contains(input)) {
						break;
					}
					else {
						System.out.println("illegal move");
					}
			}
				c4 =this.makeMove(input);
			}
			else {
				int input=100;
				System.out.println("player move");
				while(true) {
					this.computeLegal();
					System.out.println("legal moves are "+this.legal.toString());
					boolean isDone=false;
					while(!isDone){
					try {
					input = sc.nextInt();
					isDone=true;
					}
					catch(Exception e)
					{
						System.out.println("invalid input");
					}
					}
					if(legal.contains(input)) {
						break;
					}
					else {
						System.out.println("illegal move");
					}
				}
				c4 =this.makeMove(input);
				
			}
			this.board=c4.board;
			
			
			this.boardToString();
			if(this.checkWin()) {
				if( this.plyMove==turn) {
					System.out.println("MCTS wins ");
				}
				else {
					System.out.println("player wins ");
				}
				System.out.println("game Won by ply "+this.plyMove);
				
				break;
			}
			else if (this.checkDraw()){
				System.out.println("Game is a draw (gl)");
				this.boardToString();
				System.out.println("*******");
				break;
			}
			this.plyMove=c4.plyMove;
			w.setWindow(this.board);
			w.repaint();
			
	}
		w.setWindow(this.board);
		w.repaint();
		
	}
	public void boardToString() {

		StringBuilder stringOfBoard = new StringBuilder();
		for(int i=0 ;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				stringOfBoard.append(board[i][j]);
				stringOfBoard.append(" ");
			}
			stringOfBoard.append("\n");
		}
		
		System.out.println(stringOfBoard);
		
	}
	
	public StringBuilder boardAsString() {

		StringBuilder stringOfBoard = new StringBuilder();
		for(int i=0 ;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				stringOfBoard.append(board[i][j]);
				stringOfBoard.append(" ");
			}
			stringOfBoard.append("\n");
		}
		
		System.out.println(stringOfBoard);
		return stringOfBoard;
		
	}

	public connect4 makeMove(int move) {
		connect4 copy = null;
		try {
			copy = (connect4)this.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("no clone");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copy.computeLegal();
		//System.out.println("legal moves are "+copy.legal.toString());
		if(copy.legal.contains(move)) {
			int checker=0;
			while(checker<copy.board.length&&copy.board[checker][move]==0) {
				checker++;
			}
			
			 copy.board[checker-1][move]=copy.plyMove;
		}
		else {
			System.out.println("Illegal Move:  **"+move+"**");
		}
		if(copy.plyMove==1) {
			copy.plyMove=2;
		}
		else {
			copy.plyMove=1;
		}
		if(copy.checkWin()) {
			//System.out.println("game over ");
		}
		return copy;
	}

	public boolean checkWin() {
		String win="";
		win = horizontalCheck();
		if(win.contains("win"))
		{
			return true;
		}else
		{
			win=verticalCheck();
		}
		if(win.contains("win"))
		{
			return true;
		}
		else
		{
			win= diagonalCheckright();
		}
		if(win.contains("win")) {
			return true;
		}
		else {
			win=diagonalCheckLeft();
		}
		if(win.contains("win")) {
			return true;
		}
		return false;
	}

	private String diagonalCheckLeft() {
		int current=0;
		int count=0;
		for(int i=0;i<board.length-3;i++)
		{
			for(int j=0;j<board[0].length-3;j++)
			{
				if(board[i][j]!=0)
				{
					current=board[i][j];
					for(int z=1;z<=3;z++)
					{
						if(current==board[i+z][j+z]) {
							count++;
							
						}
						else {
						count=0;
						break;
						}
						}
					}
				if(count==3)
				{
					
					return (current+" wins from diagonal left ");
				}
				}
			current=0;
				
			}
		
		return "game still on";
	}

	private String diagonalCheckright() {
		int current=0;
		int count=0;
		
		for(int i=3;i<board.length;i++)
		{
			for(int j=0;j<board[0].length-3;j++)
			{
				if(board[i][j]!=0)
				{
					current=board[i][j];
					for(int z=1;z<=3;z++)
					{
						if(current==board[i-z][j+z]) {
							count++;
							
							
						}
						else {
						count=0;
						break;
						}
						}
					}
				if(count==3)
				{
					
					return current+" wins from diagonal right ";
				}
				}
			current=0;
				
			}
		
		return "Game still on";
		
	}

	private String verticalCheck() {
		int count=0;
		int current=0;
		for(int i =0;i<board[0].length;i++) {
			for(int j=0;j<board.length;j++)
			{
				if(board[j][i]!=0)
				{
					if(board[j][i]==current) {
						count++;
					}
					else {
						count=0;
						current=board[j][i];
					}
				}else
				{
					count=0;
					current=0;
				}
				if(count==3)
				{
					return (current+" wins from vertical ");
				}
			}
			current=0;
			
		}
		return "Game still on";
		
	}

	private String horizontalCheck() {
		int count=0;
		int current=0;
		for(int i =0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++)
			{
				if(board[i][j]!=0)
				{
					if(board[i][j]==current) {
						count++;
					}
					else {
						count=0;
						current=board[i][j];
					}
				}else
				{
					count=0;
					current=0;
				}

				if(count==3)
				{
					return (current+" wins from horizontal ");
				}
				
			}
			current=0;
			
		}
		return"game still on";
	}
	public boolean checkDraw() {
		this.computeLegal();
		if(this.legal.size()==0)
		{
			return true;
		}
		return(false);
	}
	public List<Integer>getLegal(){
		this.computeLegal();
		return this.legal;
	}
	private void setBoard(int [][] b) {
		this.board=b;
	}
	public  int [][] getBoard() {
		return this.board;
	}
	private void setPlyMove(int a ) {
		 this.plyMove=a;
	}
	int getPlyMove() {
		return this.plyMove;
	}
	
	
	public void gameLoopVscamera(int turn ) throws InterruptedException{
		Window w = new Window();
		Scanner sc = new Scanner(System.in);
		this.computeLegal();
		MCTSconnect4 mcts1 = new MCTSconnect4(turn);
		Random rd = new Random();
		this.boardToString();
		int playermove =20,ref=20;
		openCamera();
		
		if(turn ==1) {
			System.out.println("MCTS will go first");
		}
		else {
			System.out.println("Player will go first");
			turn=2;
		}
		while(true) {
			
			System.out.println("Player "+this.getPlyMove()+" to move");
			
			//all possible moves show debugging to be removed
			//TreeNodeConnect4 debug = new TreeNodeConnect4(this,null);
			connect4 c4 = new connect4();
			if(this.plyMove==turn) {
				System.out.println("MCTS making move");
			c4 = mcts1.search(this,300).board;
			}
			else {
			
				System.out.println("player move");
				while(true){
					System.out.println("still here ");
					playermove=ref;
					 while(playermove==ref) {
					 playermove=c.output();
					 System.out.println("c outpuut "+playermove);
					
					 }
					 if(this.legal.contains(playermove)) {
						 c4=makeMove(playermove);
						 break;
					 }
					 else {
						 System.out.println("illegal move in loop: "+playermove);
					 }
				
				
				
			}
			}
			this.board=c4.board;
			
			
			this.boardToString();
			w.setWindow(this.board);
			w.repaint();
			if(this.checkWin()) {
				if( this.plyMove==turn) {
					System.out.println("MCTS wins ");
				}
				else {
					System.out.println("player wins ");
				}
				System.out.println("game Won by ply "+this.plyMove);
				
				break;
			}
			else if (this.checkDraw()){
				System.out.println("Game is a draw (gl)");
				this.boardToString();
				System.out.println("*******");
				break;
			}
			this.plyMove=c4.plyMove;
			
	
		}
	}
	
	
	
	public void openCamera() throws InterruptedException {
		
		Thread cam = new Thread() {
	    	@Override
	    	public void run() {
	    		methodCall_game call = new methodCall_game("sending game to camera");
	    		
	            try {
					ds_game.getMethodCalls().put(call);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            connect4 t = null;
				try {
					t = call.getResult();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		try {
					new camera(t);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    };
	    cam.start();
	    methodCall_game game_send = ds_game.getMethodCalls().take();
	    game_send.setResult(this);
	    
	    MethodCall_camera call = new MethodCall_camera("opening camera");
	    ds_camera.getMethodCalls().put(call);
	    this.c = call.getResult();
	    if(c==null) {
	    	System.out.println("c is null");
	    }
	    else {
	    	System.out.println("c is not null");
	    }
	    Thread.sleep(10000);
	    }


	public dataStore_camera getds_camera() {
			return ds_camera;
		}


}
	
