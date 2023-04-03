package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;


//import it.unibo.oop.lab.mvcio.JTextF;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

    private final JFrame frame = new JFrame();

    public static void main(final String... args) throws IOException {
        new SimpleGUIWithFileChooser(new Controller());
    }

    public SimpleGUIWithFileChooser(final Controller controller) {

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        frame.setLocationByPlatform(true);

        final JPanel canvas = new JPanel(); //canvas principale
        canvas.setLayout(new BorderLayout());
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel secondaryCanvas = new JPanel();

        secondaryCanvas.setLayout(new BorderLayout());
        canvas.add(secondaryCanvas, BorderLayout.NORTH);

        final JTextField textMessage = new JTextField(controller.getFile().getPath());
        textMessage.setEditable(false); 
        secondaryCanvas.add(textMessage, BorderLayout.CENTER);

        final JButton browse = new JButton("Browse...");
        secondaryCanvas.add(browse, BorderLayout.LINE_END);

        final JTextArea textArea = new JTextArea();
        canvas.add(textArea, BorderLayout.CENTER);

        final JButton save = new JButton("Save");
        canvas.add(save, BorderLayout.SOUTH);

        save.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.insertString(textArea.getText());
                } catch (IOException er) {

                    JOptionPane.showMessageDialog(frame, er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        browse.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChoos = new JFileChooser("Choose a file..");

                //fileChoos.showSaveDialog(fileChoos.getParent());
                switch (fileChoos.showSaveDialog(frame)) {
                case JFileChooser.APPROVE_OPTION:
                    controller.setPath(fileChoos.getSelectedFile().getPath());
                    textMessage.setText(controller.getPathFile());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, fileChoos.showSaveDialog(frame), "Attention!", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        frame.setVisible(true);
    }
}
