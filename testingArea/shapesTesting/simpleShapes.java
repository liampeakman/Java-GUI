package testingArea.shapesTesting;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class simpleShapes extends JFrame implements ActionListener, Runnable {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private MyCanvas canvas = new MyCanvas();


    /**
     * @param arg0
     * @throws HeadlessException
     */

    public simpleShapes(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // show canvas
        this.getContentPane().add(canvas,BorderLayout.CENTER);

        setVisible(true);
    }


    private class MyCanvas extends Canvas {

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g.drawOval(50,50,100,100);
            g.drawRect(100,100,100,100);
            g.setColor(Color.GREEN);
            g.fillRect(300,200,100,200);
            // x coordinates of vertices
            int x[] = { 10, 30, 40, 50, 110, 140 };

            // y coordinates of vertices
            int y[] = { 140, 110, 50, 40, 30, 10 };

            // number of vertices
            int numberofpoints = 6;

            // set the color of line drawn to blue
            g.setColor(Color.blue);

            // draw the polygon using drawPolygon function
            g.fillPolygon(x, y, numberofpoints);



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
        SwingUtilities.invokeLater(new simpleShapes("Drawing Shapes on a canvas"));

    }

}
