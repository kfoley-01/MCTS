package mcts.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;


public class tictactoe implements Cloneable{

 dataStore_camera ds_camera = new dataStore_camera();
 dataStore_game ds_game = new dataStore_game();
 
 
static camera c;
private char board [][] = new char [3][3];
private int plyMove;
public List<Point> legal = new ArrayList<Point>();

tictactoe(){
	for(int i=0;i<board[0].length;i++)
	{
		for(int j=0;j<board.length;j++) {
			board[i][j]='-';
			legal.add(new Point(i,j));
		}
		plyMove=1;
	}
}


public void gameLoopVsPlayer(int turn) {
	Scanner sc = new Scanner(System.in);
	
	JFrame frame = new JFrame("Tic Tac Toe");
    TicTacToeGraphics graphics = new TicTacToeGraphics();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(graphics);
    frame.pack();
    frame.setVisible(true);
	while(true) {
		boardToString();
		graphics.setBoard(this.getBoard());
		tictactoe tcc = new tictactoe();
		this.computeLegal();
		MCTSTicTacToe mcts1 = new MCTSTicTacToe(turn);
		if(this.plyMove==turn) {
			tcc = mcts1.search(this,10).board;
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
					input = inputConverter(input);
					 System.out.println("input converter "+inputConverter(input));
					isDone=true;
					}
					catch(Exception e)
					{
						System.out.println("invalid input");
					}
					}
					if(legal.size()>input&&input>=0) {
						break;
					}
					else {
						System.out.println("illegal move");
					}
				}
				tcc =this.makeMove(legal.get(input));
			}
this.board=tcc.board;
		
		if(this.checkWin()) {
			if( this.plyMove==turn) {
				System.out.println("MCTS wins ");
			}
			else {
				System.out.println("player wins ");
			}
			System.out.println("game Won by ply "+this.plyMove);
			this.boardToString();
			graphics.setBoard(this.getBoard());
			break;
		}
		else if (this.checkDraw()){
			System.out.println("Game is a draw (gl)");
			this.boardToString();
			graphics.setBoard(this.getBoard());
			System.out.println("*******");
			break;
		}
		this.plyMove=tcc.plyMove;
		
	}
		
	}
		
		public void testCamera() throws InterruptedException {
			this.openCamera();
			
			while(true) {
				Point x = this.c.output();
				System.out.println("point : "+x.x+" "+x.y);
			}
				
			}
		
		public void gameLoopVsCamera(int turn) throws InterruptedException {
			Scanner sc = new Scanner(System.in);
			openCamera();
			Point playermove=new Point(100,100);
			Point ref = new Point(100,100);
			
			JFrame frame = new JFrame("Tic Tac Toe");
		    TicTacToeGraphics graphics = new TicTacToeGraphics();
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.getContentPane().add(graphics);
		    frame.pack();
		    frame.setVisible(true);
			while(true) {
				boardToString();
				graphics.setBoard(this.getBoard());
				tictactoe tcc = new tictactoe();
				this.computeLegal();
				MCTSTicTacToe mcts1 = new MCTSTicTacToe(turn);
				if(this.plyMove==turn) {
					tcc = mcts1.search(this,300).board;
					}
					else {
						while(true){
							System.out.println("still here ");
							playermove=ref;
							 while(playermove==ref) {
							 playermove=c.output();
							 System.out.println("making move  x:"+playermove.x+" y:"+playermove.y);
							 }
							 if(this.legal.contains(playermove)) {
								 tcc=this.makeMove(playermove);
								 break;
							 }
							 else {
								 System.out.println("illegal move in loop");
							 }
						}
						
					}
		
		this.board=tcc.board;
		
		if(this.checkWin()) {
			if( this.plyMove==turn) {
				System.out.println("MCTS wins ");
			}
			else {
				System.out.println("player wins ");
			}
			System.out.println("game Won by ply "+this.plyMove);
			this.boardToString();
			graphics.setBoard(this.getBoard());
			break;
		}
		else if (this.checkDraw()){
			System.out.println("Game is a draw (gl)");
			this.boardToString();
			graphics.setBoard(this.getBoard());
			System.out.println("*******");
			break;
		}
		this.plyMove=tcc.plyMove;
		
	}
		
}

public int gameLoopVsRandom(int turn,int time) {
	Random rd = new Random();
	while(true) {
		tictactoe tcc = new tictactoe();
		this.computeLegal();
		
		MCTSTicTacToe mcts1 = new MCTSTicTacToe(turn);
		if(this.plyMove==turn) {
			tcc = mcts1.search(this,time).board;
			}
			else {
			
				tcc =this.makeMove(legal.get(rd.nextInt(this.legal.size())));
				
			}
		
		this.board=tcc.board;
		
		if(this.checkWin()) {
			if( this.plyMove==turn) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else if (this.checkDraw()){
			return 0;
		}
		
		this.plyMove=tcc.plyMove;
	}
	}
		
public tictactoe makeMove(Point move) {
	tictactoe copy = null;
	
	try {
		copy = (tictactoe)this.clone();
	} catch (CloneNotSupportedException e) {
		System.out.println("no clone");
	}
	copy.computeLegal();
	//System.out.println("legal moves are "+copy.legal.toString());
	if(copy.legal.contains(move)) {
		copy.board[move.x][move.y]=copy.plyMoveToChar();
	}
	else {
		System.out.println("illegal move in make move ");
	}
	if(copy.plyMove==1) {
		copy.plyMove=2;
	}
	else {
		copy.plyMove=1;
	}
//	if(copy.checkWin()) {
//		System.out.println("game over ");
//	}
	return copy;
	
}
@Override
	protected Object clone() throws CloneNotSupportedException {
       tictactoe tcc = (tictactoe) super.clone();
       tcc.board=deepCopy(this.getBoard());
       tcc.setPlyMove(tcc.getPlyMove());
       return tcc;
   }

public static char[][] deepCopy(char[][] original) {
    if (original == null) {
        return null;
    }

    final char[][] result = new char[original.length][];
    for (int i = 0; i < original.length; i++) {
        result[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return result;
}

private void setPlyMove(int plyMove2) {
	this.plyMove=plyMove2;
	
}
private char [][] getBoard() {
	// TODO Auto-generated method stub
	return this.board;
}
public int getPlyMove() {
	return this.plyMove;
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

public List<Point> computeLegal() {
	this.legal.clear();
	for(int i=0;i<board.length;i++)
	{
		for(int j=0;j<board[0].length;j++) {
			if(board[i][j]=='-') {
				legal.add(new Point(i,j));
			}
		}
}
	return this.legal;
}
public  boolean checkWin() {
    // Check for horizontal win
    for (int i = 0; i < 3; i++) {
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
            return true;
        }
    }
    
    // Check for vertical win
    for (int i = 0; i < 3; i++) {
        if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
            return true;
        }
    }
    
    // Check for diagonal win
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
        return true;
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
        return true;
    }
    
    // No win found
    return false;
}
public boolean checkDraw() {
	this.computeLegal();
	if(this.legal.size()==0)
	{
		return true;
	}
	return(false);
}
private char plyMoveToChar() {
	if(this.plyMove==1) {
		return 'x';
	}
	else {
		return 'o';
	}
}
public int inputConverter(int a) {
	
int ar [] = new int[9];
int count=0;
for( int i=0;i<this.board.length;i++) {
	for ( int j =0;j< this.board[0].length;j++) {
	if(this.board[i][j]=='-') {
		ar[i+j*3]=count;
		count++;	
	}
	}
		
	}
	return ar[a];
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
            tictactoe t = null;
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

