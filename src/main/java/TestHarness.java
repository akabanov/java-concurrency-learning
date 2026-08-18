import java.util.concurrent.CountDownLatch;

/**
 * Two CountDownLatch used as gates. The start gate holds every worker until all of them
 * are created, so the measurement is not skewed by thread startup. The end gate lets
 * the main thread wait until the last worker is done.
 */
@SuppressWarnings("unused")
public class TestHarness {

    public long timeTask(int nThreads, Runnable task) throws InterruptedException {

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException ignored) {
                }
            }).start();
        }

        long start = System.currentTimeMillis();
        startGate.countDown();
        endGate.await();

        return System.currentTimeMillis() - start;
    }
}
