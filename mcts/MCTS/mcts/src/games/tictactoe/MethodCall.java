package games.tictactoe;

import java.util.concurrent.CountDownLatch;

public class MethodCall {

    public final String methodName;

    public final CountDownLatch resultReady;

    public camera camera;

    public MethodCall(String methodName) {
        this.methodName = methodName;
        this.resultReady = new CountDownLatch(1);
    }

    public void setResult(camera camera) {
        this.camera = camera;
        resultReady.countDown();
    }

    public camera getResult() throws InterruptedException {
        resultReady.await();
        System.out.println("result sent 2");
        return camera;
    }
}
