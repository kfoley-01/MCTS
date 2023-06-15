package mcts.connect4;

import java.util.concurrent.SynchronousQueue;


public class dataStore_camera {
	
	public SynchronousQueue<MethodCall_camera> methodCalls = new SynchronousQueue<MethodCall_camera>();
	
	
	public SynchronousQueue<MethodCall_camera> getMethodCalls() {
		return methodCalls;
	}
}
