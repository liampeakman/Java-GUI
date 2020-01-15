package testingArea.drawingTesting;


import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class lineTesting extends JFrame implements ActionListener, Runnable {

    public static final int SCREENWIDTH = 500;
    public static final int SCREENHEIGHT = 500;

    private MyCanvas canvas = new MyCanvas();




    /**
     * @param arg0
     * @throws HeadlessException
     */

    public lineTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());



        // add content to frame
        this.getContentPane().add(canvas,BorderLayout.CENTER);


        setVisible(true);
    }


    private class MyCanvas extends Canvas {

        private String shape;
        int totalItems;

        String [] type = new String[500]; // Pen or Fill or Shape

        double[] sx = new double[500];
        double[] sy = new double[500];   // X and Y values of shape
        double[] sw = new double[500];
        double[] sh = new double[500];  // Width and Height of shape

        DecimalFormat df = new DecimalFormat("#.##");



        public MyCanvas() {

            this.addMouseListener(new MouseAdapter() {
                double ssx;
                double ssy;
                double ssw;
                double ssh;

                String [] values = new String [5];
                String line ="";

                public void mousePressed(MouseEvent e) {
                    double valX = Double.valueOf(e.getX());
                    double valY = Double.valueOf(e.getY());

                    ssx = Double.valueOf(df.format(valX/SCREENWIDTH));
                    ssy = Double.valueOf(df.format(valY/SCREENWIDTH));



                }
                public void mouseReleased(MouseEvent e) {

                    double valX = Double.valueOf(e.getX());
                    double valY = Double.valueOf(e.getY());

                    ssw = Double.valueOf(df.format(valX/SCREENWIDTH));
                    ssh = Double.valueOf(df.format(valY/SCREENWIDTH));

                    values[0] = "LINE";
                    values[1] = String.valueOf(ssx);
                    values[2] = String.valueOf(ssy);
                    values[3] = String.valueOf(ssw);
                    values[4] = String.valueOf(ssh);

                    for(int i=0; i<5; i++) {
                        line = line+values[i]+" ";
                    }
                    line = line+"\n";

                    System.out.println(line);
                    canvas.setString(line);

                }

            });

        }


        void setString(String string) {
            //set the read vec output to the new string
            this.shape = string;
            System.out.println(shape);
            //split the string of all shapes to read each shapes
            String [] arrOfAll = shape.split("\n");

            //Amount of shapes
            int amount = arrOfAll.length ;
            this.totalItems = amount;

            //Split the shape into [0] = type, [1] = values
            for(int i=0; i<amount; i++) {

                String arrOfStr[] = arrOfAll[i].split(" ", 2);

                if(arrOfStr[0].contains("LINE")){ //all other shapes

                    //split up values in shape [0] = 0.0, [1] = 0.4 ...
                    String [] arrOfVal = arrOfStr[1].split(" ");

                    //make a double array to insert each value
                    double[] values = new double[arrOfVal.length];

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j++) {
                        values[j] = Double.parseDouble(arrOfVal[j]);

                    }

                    // set each value and * it by the screen
                    this.sx[i] = values[0] * SCREENWIDTH;
                    this.sy[i] = values[1] * (SCREENHEIGHT -30); // -30 for the menubar
                    this.sw[i] = (values[2]) * SCREENWIDTH;
                    this.sh[i] = (values[3]) * (SCREENHEIGHT - 30);

                }
                //sets type of input line, plot, ...
                this.type[i] = arrOfStr[0];

            }

            //draw shape
            repaint();

        }

        @Override
        public void paint(Graphics g){


            Graphics2D g2 = (Graphics2D)g;

            //For the total amount of items go through and find each type and apply appropriate actions
            for(int i=0; i<totalItems; i++) {

                if(type[i].contains("LINE")){
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.draw(shape);
                }

            }

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
        SwingUtilities.invokeLater(new lineTesting("Draw Lines!!"));
    }

}


