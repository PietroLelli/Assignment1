package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;





public class ConcurrentGUI  extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 5914194939766504964L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JButton btnUp = new JButton("UP");
    private final JButton btnDown = new JButton("Down");
    private final JButton btnStop = new JButton("Stop");
    private static JLabel lbl = new JLabel();
    private final  Agent counter = new Agent();

    public JButton getBtnUp() {
        return btnUp;
    }
    public JButton getBtnDown() {
        return btnDown;
    }
    public JButton getBtnStop() {
        return btnStop;
    }
    public Agent getCounter() {
        return counter;
    }



    public ConcurrentGUI() {

        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        final JPanel panel = new JPanel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panel.add(lbl);
        panel.add(btnUp);
        panel.add(btnDown);
        panel.add(btnStop);


        new Thread(counter).start();

        btnUp.addActionListener(e -> {
           counter.changeOp(Operation.UP);
        });

        btnDown.addActionListener(e -> {
            counter.changeOp(Operation.DOWN);
        });

        btnStop.addActionListener(e -> {
            counter.changeStop();
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
        });

        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setVisible(true);
    }
    enum Operation {
        UP, DOWN;
    }
    public class Agent implements Runnable {

        private volatile int counter;
        private volatile Operation op;
        private volatile boolean stop = true;

        public Agent() {
            super();
            this.counter = 0;
            this.op = Operation.UP;
        }


        public void run() {
            while (stop) {
                try {
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.lbl.setText(Integer.toString(this.counter)));
                    if (op == Operation.UP) {
                        this.counter++;
                    } else if (op == Operation.DOWN) {
                        this.counter--;
                    }
                    Thread.sleep(100);
                } catch (InterruptedException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
        public void changeStop() {
            stop = false;
        }
        public void changeOp(final Operation ope) {
            if (ope == Operation.UP) {
                op = Operation.UP;
            } else {
                op = Operation.DOWN;
            }

        }


    }
}
