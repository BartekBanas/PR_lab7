import java.util.concurrent.RecursiveTask;

class DivideTask extends RecursiveTask<int[]> {

    int[] arrayToDivide;

    public DivideTask(int[] arrayToDivide) {
        this.arrayToDivide = arrayToDivide;
    }

    protected int[] compute() {
        DivideTask task1 = new DivideTask(new int[0]);
        DivideTask task2 = new DivideTask(new int[0]);

        //Wait for results from both tasks
        int[] tab1 = task1.join();
        int[] tab2 = task2.join();

        int[] scaledTab = new int[0];
        ScaleTab(tab1, tab2, scaledTab);

        return scaledTab;
    }

    private void ScaleTab(int[] tab1, int[] tab2, int[] scaledTab)
    {
        int i = 0, j = 0, k = 0;

        while ((i < tab1.length) && (j < tab2.length)) {

            if (tab1[i] < tab2[j]) {
                scaledTab[k] = tab1[i++];
            } else {
                scaledTab[k] = tab2[j++];
            }

            k++;
        }

        if (i == tab1.length) {

            for (int a = j; a < tab2.length; a++) {
                scaledTab[k++] = tab2[a];
            }

        } else {

            for (int a = i; a < tab1.length; a++) {
                scaledTab[k++] = tab1[a];
            }
        }
    }
}