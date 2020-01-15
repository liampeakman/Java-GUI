package testingArea.frameTesting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class toolsTesting extends JFrame implements ActionListener, Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 400;


    private JPanel canvasPanel;
    private JPanel bottomPanel;


    private JButton plotBtn;
    private JButton lineBtn;
    private JButton rectangleBtn;
    private JButton ellipseBtn;
    private JButton polygonBtn;

    private JComboBox penComboBox;
    private JComboBox fillComboBox;



    /**
     * @param arg0
     * @throws HeadlessException
     */

    public toolsTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create panels
        canvasPanel = createPanel(Color.WHITE);
        bottomPanel = createPanel(Color.LIGHT_GRAY);


        // create combo box
        penComboBox = createComboBox("Pen Colour");
        fillComboBox = createComboBox("Fill Colour");
        // create buttons
        plotBtn = createButton("PLOT");
        lineBtn = createButton("LINE");
        rectangleBtn = createButton("RECTANGLE");
        ellipseBtn = createButton("ELLIPSE");
        polygonBtn = createButton("POLYGON");

        // add content to frame
        this.getContentPane().add(canvasPanel,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);


        // set layout for bottom panel
        bottomPanel.setLayout(new GridLayout(1, 7, 0, 1));
        // add buttons to bottom panel
        bottomPanel.add(plotBtn);
        bottomPanel.add(lineBtn);
        bottomPanel.add(rectangleBtn);
        bottomPanel.add(ellipseBtn);
        bottomPanel.add(polygonBtn);
        // add items to combo box then add combo box to panel
        penComboBox.addItem("Red");
        penComboBox.addItem("Blue");
        penComboBox.addItem("Green");
        bottomPanel.add(penComboBox);
        // add items to combo box then add combo box to panel
        fillComboBox.addItem("Red");
        fillComboBox.addItem("Blue");
        fillComboBox.addItem("Green");
        bottomPanel.add(fillComboBox);


        setVisible(true);
    }


    private JPanel createPanel(Color c) {
        JPanel jp = new JPanel();
        jp.setBackground(c);
        return jp;
    }


    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }

    private JComboBox createComboBox(String str) {
        String[] jcbStr = new String[] {str};
        JComboBox<String> jcb = new JComboBox<>(jcbStr);
        return jcb;
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
        SwingUtilities.invokeLater(new toolsTesting("Displaying the VEC tools"));
    }

}
