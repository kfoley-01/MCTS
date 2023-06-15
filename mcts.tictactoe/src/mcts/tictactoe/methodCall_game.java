package mcts.tictactoe;

import java.util.concurrent.CountDownLatch;


public class methodCall_game {

    public final String methodName;

    public final CountDownLatch resultReady;

    public tictactoe g;

    public methodCall_game(String methodName) {
        this.methodName = methodName;
        this.resultReady = new CountDownLatch(1);
    }

    public void setResult(tictactoe g) {
        this.g = g;
        resultReady.countDown();
    }

    public tictactoe getResult() throws InterruptedException {
        resultReady.await();
        return g;
    }
}
