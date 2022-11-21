import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class Simple_executor_test {
    private static final int NTHREADS = 10;
    private static final double a = 0;
    private static final double b = Math.PI;
    private static final double dx = 0.001;
    private static final int n = 100;

    public static void main(String[] args) {

        //sekwencyjnie (0.0)
        Calka_callable calka_sekw = new Calka_callable(a, b, dx);
        System.out.println("\nCałka sekwencyjnie: " + calka_sekw.compute_integral() + "\n");

        //calka podzial na watki, basic (3.0)
        List<Future<Double>> partialResults = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

        double fragment = (b - a) / n;
        for (int i = 0; i < n; i++) {
            partialResults.add(executor.submit(new Calka_callable(i * fragment, (i + 1) * fragment, dx)));
        }

        double theResult = 0.;

        for (Future<Double> r : partialResults) {
            try {
                theResult += r.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Całka równolegle: " + theResult);

        Counter counter = new Counter();
        for (int i = 0; i < 50; i++) {
            Runnable worker = new CounterPlus(counter);
            executor.execute(worker);
        }

        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();

        // Wait until all threads finish
        while (!executor.isTerminated()) {}

        System.out.println("Finished all threads");
        System.out.format("\nCounter_1: %d, Counter_2 %d\n\n",
                counter.get_c1(), counter.get_c2());
    }
} 
