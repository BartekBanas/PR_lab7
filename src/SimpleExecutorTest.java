import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutorTest {
    private static final int THREADS = 2;
    private static final int TASKS = 10;
    private static final double dx = 0.00001;


    public static void main(String[] args) {
        double a = 0;
        double b = Math.PI;

        //sequentially
        Integral sequentialResult = new Integral(a, b, dx);
        System.out.println("\nSequential Result: " + sequentialResult.computeIntegral() + "\n");

        List<Future<Double>> partialResults = new ArrayList<>();

        ExecutorService executor;
        executor = Executors.newFixedThreadPool(THREADS);


        double fragment = (b - a) / TASKS;
        for (int i = 0; i < TASKS; i++) {
            partialResults.add(executor.submit(new Integral(i * fragment, (i + 1) * fragment, dx)));
        }

        double parallelResult = 0;

        for (Future<Double> partialResult : partialResults) {
            try {
                parallelResult += partialResult.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("\nParallel Result: " + parallelResult);

//        Counter counter = new Counter();
//        for (int i = 0; i < 50; i++) {
//            Runnable worker = new CounterPlus(counter);
//            executor.execute(worker);
//        }
//        System.out.format("\nCounter_1: %d, Counter_2 %d\n\n",
//                counter.get_c1(), counter.get_c2());

        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();

        // Wait until all threads finish
        while (!executor.isTerminated()) {}

        System.out.println("\nFinished all threads");
    }
}