package testingArea.frameTesting;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author group 269
 *
 */

public class colorPickerTesting extends JFrame implements ActionListener, Runnable {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;

    //create objects
    JPanel panel1 = new JPanel();
    JLabel sampleText  = new JLabel("Label");
    JButton chooseButton = new JButton("Choose Color");


    /**
     * @param arg0
     * @throws HeadlessException
     */

    public colorPickerTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //add action listener to button
        chooseButton.addActionListener(new ButtonListener());

        //add contents to panel
        panel1.add(sampleText);
        panel1.add(chooseButton);


        // add content to frame
        this.getContentPane().add(panel1,BorderLayout.CENTER);


        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Choose a Color", sampleText.getForeground());
            if (c != null)
                sampleText.setForeground(c);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */

    @Override
    public void run() {
        createAndShowGUI();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * @param args
     */

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new colorPickerTesting("Pick a colour for the label"));
    }

}


