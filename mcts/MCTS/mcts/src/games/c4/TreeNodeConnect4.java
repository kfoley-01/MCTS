package games.c4;

import games.tictactoe.Constants;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.Arrays;
import java.util.LinkedList; 
import java.util.List;
import java.util.Random;

public class TreeNodeConnect4 {
	public int depth,
    typeNode,
    direction;

	Random rnd;
	double C;
	
	TreeNodeConnect4 [] children;
	GameStateInterface GS;
	double nVisits, totValue;	
	boolean executed, 
	 		expanded;
	public int firstPlayer;
	
	public TreeNodeConnect4(Random rnd, int firstPlayer, double C){
		this.rnd = rnd;
		this.firstPlayer = firstPlayer;
		this.C = C;
	}
	
	public void selectAction(GameStateInterface gs, int firstPlayer){
		//boolean finished = false;
		boolean applyRollOut = false;
		double value;
		String result = "";
		List<TreeNodeConnect4> visited = new LinkedList<TreeNodeConnect4>();
		TreeNodeConnect4 current = this;
		TreeNodeConnect4 child = new TreeNodeConnect4(rnd, firstPlayer,C);		
		current.GS = gs.copy();
		current.typeNode = Constants.ROOT;
		current.executed = false;
		current.expanded = false;
		visited.add(current);
		TreeNodeConnect4 parent;		
		while (!current.isLeaf()) {   
        	parent = current;        	
            current = current.selectDescendByUCB1MinMax();//.selectDescendByUCB1MinMax();//.selectDescendByUCB1MinMax();// .selectDescendByUCB1();// .selectDescendByUCBTuned();// ..selectDescendByUCB1();//.selectDescendByUCBTuned();// .selectDescendByUCB1();// .selectDescendByUCBTuned();           
            current.depth = (parent.depth==0) ? 1 : parent.depth +1;                  
            visited.add(current);
        }		 
			if (!current.isLeaf()){
				System.out.println("There is an error. Nodes have to be visited at least once before expanding");
				System.exit(0);
			}
			
			if (current.executed== false && current.typeNode != Constants.ROOT){
				child = current;
				applyRollOut = true;
			}else{
				GameState gsTmp = (GameState)current.GS.copy();		
				
				if (gsTmp.getNumberOfMoves() > 0){				
					current.expand(firstPlayer);
					child = current.selectUnvisitedChild(); //current.selectDescendByUCBTuned();										
			        visited.add(child);
			        applyRollOut = true;
		        }else{
		        	applyRollOut = false;	
		        	result = Arrays.toString(gsTmp.getOutcomes());		
		        }				
			}
			if (applyRollOut){
		        value = rollOut(child, firstPlayer);
			}
			else{
				value = getReward(result, child);
			}
		   /* for (TreeNodeTicTacToe node : visited) {                  	
		            node.updateStats(value);         		                
			}*/
			for (int i = visited.size()-1; i>=0; i--){
				TreeNodeConnect4 node = visited.get(i);
				node.updateStats(value, child);
			}
	}
	
	 public TreeNodeConnect4 selectUnvisitedChild(){
	    	ArrayList<TreeNodeConnect4> toBeVisited = new ArrayList<TreeNodeConnect4>();
	    	for (TreeNodeConnect4 c: children){
	    		if (c.nVisits == 0){
	    			toBeVisited.add(c);
	    		}
	    	}
	    	if (toBeVisited.size() > 0){
	    		return (toBeVisited.get(rnd.nextInt(toBeVisited.size())));
	    	}else{
	    		System.out.println("This function must be called when there is at least one child unvisited");
	    		System.exit(0);
	    	}
	    	return null; //should never get here!
	}
	 
	 public void updateStats(double value, TreeNodeConnect4 child){
		 nVisits++;
		 if (typeNode == Constants.MCTS || typeNode == Constants.ROOT){
			 if (child.typeNode == Constants.MCTS){
				 totValue += value;
				 //totValue /= nVisits;
			 }else if (child.typeNode == Constants.CPU){
				 totValue -= value;
				 //totValue /= nVisits;
			 }
		 }else if (typeNode == Constants.CPU){
			 if (child.typeNode == Constants.CPU){
				 totValue += value;
				 //totValue /= nVisits;
			 }else if (child.typeNode == Constants.MCTS){
				 totValue -= value;
				 //totValue /= nVisits;
			 }
		 }		 		
	 }
	
	 public double getReward(String result, TreeNodeConnect4 child){
		 int player = child.typeNode;
		 double value = 0.0;
		 double win = 1.0,
		        loss = -1.0,
		        draw = 0.0;
		 if (player == Constants.MCTS){
			   if (firstPlayer == Constants.MCTS){
				   if (result.contains("[WIN")){//we win
						value = win;
					}else if (result.contains(", WIN]")){//computer wins
						value = loss;
					}else {
						value = draw;
					}
			   }else if (firstPlayer == Constants.CPU){					  
				   if (result.contains("[WIN")){//we win
						value = loss;
					}else if (result.contains(", WIN]")){//computer wins
						value = win;
					}else {
						value = draw;
					}
			   }					
		}else if (player == Constants.CPU){
			   if (firstPlayer == Constants.MCTS){
				   if (result.contains("[WIN")){//we win
						value = loss;//-1.0;
					}else if (result.contains(", WIN]")){//computer wins
						value = win;//1.0;//-1.0;
					}else {
						value = draw;
					}
			   }else if (firstPlayer == Constants.CPU){					  
				   if (result.contains("[WIN")){//we win
						value = win;
					}else if (result.contains(", WIN]")){//computer wins
						value = loss;
					}else {
						value = draw;
					}
			   }					
		}
		return value;
	 }
	 
	 private double rollOut(TreeNodeConnect4 child, int player1){
			double value = 0.0;		
			GameState gs = (GameState) child.GS.copy();
			while (!gs.isGameOver()){
				int numberOfMoves = gs.getNumberOfMoves();
				int move = rnd.nextInt(numberOfMoves);
					gs.makeMove(move);			
			}		
			child.executed = true;
			String result = Arrays.toString(gs.getOutcomes());		
			value = getReward(result, child);			
			return value;
	}
	 
	 public void expand(int firstPlayer){
			GameState gs = (GameState)this.GS.copy();
			int numberOfMoves = gs.getNumberOfMoves();		
			children = new TreeNodeConnect4[numberOfMoves];
			for (int i = 0; i < numberOfMoves; i++){
				children[i] = new TreeNodeConnect4(rnd, firstPlayer, C);			
				children[i].GS = gs.copy();
				children[i].GS.makeMove(i);
				children[i].executed = false;
				children[i].expanded = true;
				children[i].direction = i;	
				children[i].depth = this.depth+1;
				if ((children[i].depth%2)==1){
					children[i].typeNode = Constants.MCTS;
				}else{
					children[i].typeNode = Constants.CPU;
				}				
			}
	}
	 
	 private TreeNodeConnect4 selectDescendByUCB1MinMax(){
	        TreeNodeConnect4 selected = null;
	        ArrayList<TreeNodeConnect4> toBeVisited = new ArrayList<TreeNodeConnect4>();
	        ArrayList<TreeNodeConnect4> equallyGood = new ArrayList<TreeNodeConnect4>();
	        double bestValueMin = -100000000.0,
	               bestValueMax =  100000000.0,               
	               nb = 0.0;//,
	               //C = 2.0;//2        
	        for (TreeNodeConnect4 c : children){
	            nb += c.nVisits; 
	        }
	        for (TreeNodeConnect4 c : children){
	            if (c.nVisits == 0 ){                
	                toBeVisited.add(c);
	                selected = c;
	            }else{
	            	//David
	            	 double exploitation = c.totValue / c.nVisits;
	                 double exploration = C * Math.sqrt(Math.log(nb) / (double) c.nVisits);// c.nVisits; //node.getActionVisits(move));
	                 double uctValue = exploitation ;	                 
	                 if (c.typeNode == Constants.MCTS){
	                	 uctValue += exploration;
	                 }else if (c.typeNode == Constants.CPU){
	                	 uctValue += - exploration;
	                 }
	                 //value += max ? exploration : -exploration;
	                 
	            	//double uctValue = c.totValue/c.nVisits + Math.sqrt(C*Math.log(nb)) / c.nVisits; 
	            	if (c.typeNode==Constants.CPU){
	            		if (uctValue < bestValueMax){
	            			selected = c;
	            			bestValueMax = uctValue;
	            		}else if (uctValue == bestValueMax){
	            			equallyGood.add(c);
	            		}
	            	}else if (c.typeNode == Constants.MCTS){
	            		if (uctValue > bestValueMin){
	                        selected = c;
	                        bestValueMin = uctValue;
	                    }else if (uctValue == bestValueMin){
	                    	equallyGood.add(c);
	                    }
	            	}                     
	            }
	        }
	        if (toBeVisited.size() > 0){
	            selected = toBeVisited.get(rnd.nextInt(toBeVisited.size())); //we ensure to pick at random any unvisited leaf
	        }else if (equallyGood.size() > 0){
	        	selected = equallyGood.get(rnd.nextInt(equallyGood.size()));
	        }
	        return selected;
	    }
	 
	 public boolean isLeaf() {
	        return children == null;
	    }

		public int arity() {
	        return children == null ? 0 : children.length;
	    }
}
