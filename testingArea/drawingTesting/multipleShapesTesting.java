package testingArea.drawingTesting;


import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.*;

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class multipleShapesTesting extends JFrame implements ActionListener, Runnable {

    public static final int width = 500;
    public static final int height = 500;


    String line ="";
    String buttonType = "LINE";  //What shape is current
    private boolean polygonDone = false; //Check if polygon is finished


    private MyCanvas canvas = new MyCanvas();


    private JPanel bottomPanel;


    private JButton plotBtn;
    private JButton lineBtn;
    private JButton rectangleBtn;
    private JButton ellipseBtn;
    private JButton polygonBtn;
    private JButton doneBtn;


    /**
     * @param arg0
     * @throws HeadlessException
     */

    public multipleShapesTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        //create panels
        bottomPanel = createPanel(Color.LIGHT_GRAY);

        // create buttons
        plotBtn = createButton("PLOT");
        lineBtn = createButton("LINE");
        rectangleBtn = createButton("RECTANGLE");
        ellipseBtn = createButton("ELLIPSE");
        polygonBtn = createButton("POLYGON");
        doneBtn = createButton("DONE");

        // add content to frame
        this.getContentPane().add(canvas,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);


        // set layout for bottom panel
        bottomPanel.setLayout(new GridLayout(1, 6, 0, 1));
        // add buttons to bottom panel
        bottomPanel.add(plotBtn);
        bottomPanel.add(lineBtn);
        bottomPanel.add(rectangleBtn);
        bottomPanel.add(ellipseBtn);
        bottomPanel.add(polygonBtn);
        bottomPanel.add(doneBtn);

        Dimension size = canvas.getBounds().getSize();
        System.out.println(size);

        //See when the window size changes and scale the shapes
        this.getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if(line== ""){
                }else{
                    canvas.setString(line); //send line to the canvas

                }

            }
        });


        // Set button type to the corresponding name
        plotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonType = "PLOT";
            }
        });
        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonType = "LINE";
            }
        });
        rectangleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonType = "RECTANGLE";
            }
        });
        ellipseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonType = "ELLIPSE";
            }
        });
        polygonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonType = "POLYGON";
            }
        });
        doneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                polygonDone = true;
            }
        });

        setVisible(true);
    }
    //make panel
    private JPanel createPanel(Color c) {
        JPanel jp = new JPanel();
        jp.setBackground(c);
        return jp;
    }
    //make button
    private JButton createButton(String str) {
        JButton jb = new JButton(str);
        jb.addActionListener(this);
        return jb;
    }


    private class MyCanvas extends Canvas {

        private String shape;
        int totalItems;

        String [] type = new String[500]; // Pen or Fill or Shape

        double[] sx = new double[500];
        double[] sy = new double[500];   // X and Y values of shape
        double[] sw = new double[500];
        double[] sh = new double[500];  // Width and Height of shape

        int startx;
        int starty;
        int pointx;
        int pointy;

        int [] polylength = new int[500]; // Length of polygon
        int[][]polyx = new int[500][500];
        int[][] polyy = new int[500][500]; // X and Y values of polygons

        DecimalFormat df = new DecimalFormat("#.##");



        public MyCanvas() {


            //Listen to the mouse
            this.addMouseListener(new MouseAdapter() {
                double ssx;
                double ssy;
                double ssw;
                double ssh;

                String [] values = new String [5];
                String polyLine="";


                //When mouse is clicked
                public void mousePressed(MouseEvent e) {

                    double valX = Double.valueOf(e.getX()); //get x & y value
                    double valY = Double.valueOf(e.getY());

                    ssx = Double.valueOf(df.format(valX/getWidth())); //convert to double
                    ssy = Double.valueOf(df.format(valY/getHeight()));

                    startx = e.getX();
                    starty = e.getY();
                }
                public void mouseReleased(MouseEvent e) {

                    double valX = Double.valueOf(e.getX());
                    double valY = Double.valueOf(e.getY());

                    ssw = Double.valueOf(df.format(valX/getWidth()));
                    ssh = Double.valueOf(df.format(valY/getHeight()));

                    //set values in array
                    values[0] = buttonType;
                    values[1] = String.valueOf(ssx);
                    values[2] = String.valueOf(ssy);
                    values[3] = String.valueOf(ssw);
                    values[4] = String.valueOf(ssh);


                    //Every other shape
                    if (buttonType != "POLYGON") {
                        //add each value to the string
                        for(int i=0; i<5; i++) {
                            line = line+values[i]+" ";
                        }
                        line = line + "\n"; //new line
                        canvas.setString(line); //send line to the canvas
                    }

                    //Polygon
                    else{
                        //Check if user is done, user has to click again to show polygon
                        if (polygonDone) {
                            line = line + "POLYGON "+ polyLine  + "\n";
                            canvas.setString(line);
                            polyLine="";
                            polygonDone = false; //Set boolean back
                            buttonType = "POLYGON"; //Set type back

                        //Otherwise keep adding points
                        }else{
                            //only needs x and y values
                            for(int i=1; i<3; i++) {
                                polyLine = polyLine+values[i]+" ";
                            }

                        }

                    }

                    System.out.println(line);
                }


            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {

                    pointx = e.getX();
                    pointy = e.getY();

                    repaint();


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
                if(arrOfStr[0].contains("POLYGON")){
                    //split up values in shape [0] = 0.0, [1] = 0.4 ...
                    String [] arrOfVal = arrOfStr[1].split(" ");
//                    System.out.println(arrOfVal[1]);


                    this.polylength[i] = arrOfVal.length;

                    int start = 0;
                    int start2 = 0;

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j+=2) {
                        Double x =  Double.parseDouble(arrOfVal[j]) * getWidth();
                        this.polyx[i][start] = (int) Math.round(x);
                        start++;

                    }

                    for (int k = 1; k < arrOfVal.length; k+=2) {
                        Double y = Double.parseDouble(arrOfVal[k]) * getHeight();
                        this.polyy[i][start2] =(int) Math.round(y);
                        start2++;
                    }

                    this.sx[i] = 0;
                    this.sy[i] = 0;
                    this.sw[i] = 0;
                    this.sh[i] = 0;

                }
                else{
                    //split up values in shape [0] = 0.0, [1] = 0.4 ...
                    String[] arrOfVal = arrOfStr[1].split(" ");

                    //make a double array to insert each value
                    double[] values = new double[arrOfVal.length];

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j++) {
                        values[j] = Double.parseDouble(arrOfVal[j]);

                    }

                    // set each value and * it by the screen

                    if (arrOfStr[0].contains("LINE")) {
                        this.sx[i] = values[0] * getWidth();
                        this.sy[i] = values[1] * getHeight();
                        this.sw[i] = (values[2]) * getWidth();
                        this.sh[i] = (values[3]) * getHeight();
                    }// use x and y values only
                    else if(arrOfStr[0].contains("PLOT")){
                        this.sx[i] = values[0] * getWidth();
                        this.sy[i] = values[1] * getHeight();
                        this.sw[i] = (values[0]) * getWidth();
                        this.sh[i] = (values[1]) * getHeight();
                    }
                    else {
                        if(values[2]<values[0]){
                            if(values[3]>values[1]){ //top right to bottom left
                                this.sx[i] = values[3] * getWidth();
                                this.sy[i] = values[1] * getHeight();
                                this.sw[i] = (values[0] - values[2]) * getWidth();
                                this.sh[i] = (values[3] - values[1]) * getHeight();
                            }else{ //bottom right to top left
                                this.sx[i] = values[2] * getWidth();
                                this.sy[i] = values[3] * getHeight();
                                this.sw[i] = (values[0] - values[2]) * getWidth();
                                this.sh[i] = (values[1] - values[3]) * getHeight();
                            }


                        }else{ // else draw normally

                            if(values[3]>values[1]){ //bottom left to top right
                                this.sx[i] = values[0] * getWidth();
                                this.sy[i] = values[1] * getHeight();
                                this.sw[i] = (values[2] - values[0]) * getWidth();
                                this.sh[i] = (values[3] - values[1]) * getHeight();
                            }else{ //top left to bottom right
                                this.sx[i] = values[0] * getWidth();
                                this.sy[i] = values[3] * getHeight();
                                this.sw[i] = (values[2] - values[0]) * getWidth();
                                this.sh[i] = (values[1] - values[3]) * getHeight();
                            }
                        }

                    }

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

            System.out.println(startx);
            System.out.println(starty);
            System.out.println(pointx);
            System.out.println(pointy);


            if (buttonType == "LINE") {
                g2.drawLine(startx, starty, pointx, pointy);

            }


            //For the total amount of items go through and find each type and apply appropriate actions
            for(int i=0; i<totalItems; i++) {
                if(type[i].contains("PLOT")){
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.draw(shape);
                }

                if(type[i].contains("LINE")){

                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.draw(shape);
                }

                if(type[i].contains("RECTANGLE")){
                    Rectangle2D.Double shape = new Rectangle2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.draw(shape);
                }

                if(type[i].contains("ELLIPSE")){
                    Ellipse2D.Double shape = new Ellipse2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.draw(shape);
                }

                if(type[i].contains("POLYGON")){
                    g2.drawPolygon(polyx[i], polyy[i], polylength[i]/2 );
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
        SwingUtilities.invokeLater(new multipleShapesTesting("Draw Shapes!"));
    }

}


