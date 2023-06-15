package mcts.connect4;

import java.util.concurrent.CountDownLatch;


public class methodCall_game {

    public final String methodName;

    public final CountDownLatch resultReady;

    public connect4 g;

    public methodCall_game(String methodName) {
        this.methodName = methodName;
        this.resultReady = new CountDownLatch(1);
    }

    public void setResult(connect4 g) {
        this.g = g;
        resultReady.countDown();
    }

    public connect4 getResult() throws InterruptedException {
        resultReady.await();
        return g;
    }
}
