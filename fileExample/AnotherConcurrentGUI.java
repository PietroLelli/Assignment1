package it.unibo.oop.lab.reactivegui03;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;


public class AnotherConcurrentGUI extends ConcurrentGUI {
    /**
     * 
     */
    private static final long serialVersionUID = -4277927931293157204L;



    public AnotherConcurrentGUI() {
        super();
        final Agent2 counter2 = new Agent2();
        new Thread(counter2).start();
    }



    public class Agent2 implements Runnable {
        private volatile boolean stop = true; 
        private volatile int counter;
        private final int num = 100;
        public void run() {
            while (stop) {
                try {
                    this.counter++;
                    Thread.sleep(100);
                    if (counter == num) {
                        stop = false;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            try {
                SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.super.getCounter().changeStop());
                SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.super.getBtnDown().setEnabled(false));
                SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.super.getBtnUp().setEnabled(false));
                SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.super.getBtnStop().setEnabled(false));

            } catch (InvocationTargetException | InterruptedException e) {

                e.printStackTrace();
            }
        }

    }
}
