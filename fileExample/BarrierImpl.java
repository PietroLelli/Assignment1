package pcd.lab04.monitors.barrier;

public class BarrierImpl implements Barrier{
    private int nArrivedSoFair;
    private int nTotal;

    public BarrierImpl(int nTotal) {
        this.nTotal = nTotal;
        nArrivedSoFair = 0;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        nArrivedSoFair++;
        if(nArrivedSoFair == nTotal){
            notifyAll();
        }else{
            while (nArrivedSoFair <nTotal){
                wait();
            }
        }

    }
}
