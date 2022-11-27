import java.util.concurrent.Callable;

public class Calka_callable implements Callable<Double> {
    private final double dx;
    private final double xp;
    private final double xk;
    private final int N;

    public Calka_callable(double startingPoint, double endingPoint, double height) {
        this.xp = startingPoint;
        this.xk = endingPoint;
        this.N = (int) Math.ceil((endingPoint - startingPoint) / height);
        this.dx = (endingPoint - startingPoint) / N;
        //System.out.println("Creating an instance of Calka_callable");
        //System.out.println("startingPoint = " + startingPoint + ", endingPoint = " + endingPoint + ", N = " + N);
        //System.out.println("height requested = " + height + ", height final = " + this.height);

    }

    private double getFunction(double x) {
        return Math.sin(x);
    }

    public double compute_integral() {
        double calka = 0;
        int i;
        for (i = 0; i < N; i++) {
            double x1 = xp + i * dx;
            double x2 = x1 + dx;
            calka += ((getFunction(x1) + getFunction(x2)) / 2.) * dx;
        }
        return calka;
    }

    @Override
    public Double call() throws Exception {
        double partialIntegral = compute_integral();
        System.out.println("\tPartial Integral: " + partialIntegral);
        return partialIntegral;
    }
}
