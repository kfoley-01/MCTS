package mcts.connect4;


import java.util.concurrent.SynchronousQueue;

public class dataStore_game {
	
	public SynchronousQueue<methodCall_game> methodCalls = new SynchronousQueue<methodCall_game>();
	
	
	public SynchronousQueue<methodCall_game> getMethodCalls() {
		return methodCalls;
	}
}
