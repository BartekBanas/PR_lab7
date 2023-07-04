import java.util.concurrent.Callable;

public class Integral implements Callable<Double> {
    private final double dx;
    private final double xp;
    private final double xk;
    private final int N;

    public Integral (double startingPoint, double endingPoint, double height) {
        this.xp = startingPoint;
        this.xk = endingPoint;
        this.N = (int) Math.ceil((endingPoint - startingPoint) / height);
        this.dx = (endingPoint - startingPoint) / N;
    }

    private double sine(double x) {
        return Math.sin(x);
    }

    public double computeIntegral() {
        double integral = 0;
        int i;
        for (i = 0; i < N; i++) {
            double x1 = xp + i * dx;
            double x2 = x1 + dx;
            integral += ((sine(x1) + sine(x2)) / 2.) * dx;
        }
        return integral;
    }

    @Override
    public Double call() throws Exception {
        double partialIntegral = computeIntegral();
        System.out.println("\tPartial Integral: " + partialIntegral);
        return partialIntegral;
    }
}