public class Counter {
    private long counter1 = 0;
    private long counter2 = 0;

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized (lock1) {
            try {
                counter1++;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // some code
            }
        }
    }

    public void inc2() {
        synchronized (lock2) {
            try {
                counter2++;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // some code
            }
        }
    }

    public long get_c1() {
        return (counter1);
    }

    public long get_c2() {
        return (counter2);
    }
}






