package games.tictactoe;

import java.util.concurrent.SynchronousQueue;



public class dataStore {
	
	public SynchronousQueue<MethodCall> methodCalls = new SynchronousQueue<MethodCall>();
	
	
	public SynchronousQueue<MethodCall> getMethodCalls() {
		return methodCalls;
	}
}
