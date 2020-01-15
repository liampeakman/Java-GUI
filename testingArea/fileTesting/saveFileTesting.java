package testingArea.fileTesting;



import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.*;
import java.io.*;


/**
 * @author group 269
 *
 */

public class saveFileTesting extends JFrame implements ActionListener, Runnable {
    public int SCREENWIDTH = 900;
    public int SCREENHEIGHT = 900;

    String line =""; // Main string that holds everything
    String buttonType = "LINE";  //What shape is current
    private boolean polygonDone = false; //Check if polygon is finished

    private MyCanvas canvas = new MyCanvas();

    //bar
    private JMenuBar menuBar;

    //menus
    private JMenu fileMenu;

    //items
    private JMenuItem fileSaveAs;

    //panels
    private JPanel bottomPanel;

    //buttons
    private JButton plotBtn;
    private JButton lineBtn;
    private JButton rectangleBtn;
    private JButton ellipseBtn;
    private JButton polygonBtn;
    private JButton doneBtn;
    private JButton penColourBtn;
    private JButton fillColourBtn;
    private JButton noFillBtn;


    /**
     * @param arg0
     * @throws HeadlessException
     */

    public saveFileTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create panels
        bottomPanel = createPanel(Color.LIGHT_GRAY);

        // create menu bar // colors not changing for some reason
        menuBar = createMenuBar(Color.WHITE);

        //create menu
        fileMenu = createMenu("File", Color.WHITE);

        // create menu items
        fileSaveAs = createMenuItem("Save As");

        // create buttons
        plotBtn = createButton("PLOT");
        lineBtn = createButton("LINE");
        rectangleBtn = createButton("RECTANGLE");
        ellipseBtn = createButton("ELLIPSE");
        polygonBtn = createButton("POLYGON");
        doneBtn = createButton("DONE");
        penColourBtn = createButton("Pen Colour");
        fillColourBtn = createButton("Fill Colour");
        noFillBtn = createButton("Fill Off");


        // add content to frame
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        this.getContentPane().add(canvas,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);


        // set layout for bottom panel
        bottomPanel.setLayout(new GridLayout(1, 9, 0, 1));
        // add buttons to bottom panel
        bottomPanel.add(plotBtn);
        bottomPanel.add(lineBtn);
        bottomPanel.add(rectangleBtn);
        bottomPanel.add(ellipseBtn);
        bottomPanel.add(polygonBtn);
        bottomPanel.add(doneBtn);
        bottomPanel.add(penColourBtn);
        bottomPanel.add(fillColourBtn);
        bottomPanel.add(noFillBtn);

        //add menu items
        menuBar.add(fileMenu);
        fileMenu.add(fileSaveAs);

        //save the file
        fileSaveAs.addActionListener(new saveFileAs());


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
        penColourBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);
                if (c != null)
                    line = line + "PEN "+color2HexString(c)+"\n";

            }
        });

        fillColourBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);
                if (c != null)
                    line = line + "FILL "+color2HexString(c)+"\n";
            }
        });
        noFillBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                line = line + "FILL OFF\n";
            }
        });



        setVisible(true);
    }

    //returns hex value for colors
    public String color2HexString(Color color) {
        return "#" + Integer.toHexString(color.getRGB() & 0x00ffffff);
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

    private JMenuBar createMenuBar(Color c) {
        JMenuBar jmb = new JMenuBar();
        jmb.setOpaque(true);
        jmb.setBackground(c);
        return jmb;
    }

    private JMenu createMenu(String str, Color c) {
        JMenu jm = new JMenu(str);
        jm.setBackground(c);
        return jm;
    }

    private JMenuItem createMenuItem(String str) {
        JMenuItem jmi = new JMenuItem(str);
        return jmi;
    }

    private class saveFileAs implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("file.vec"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","vec"));
            int returnValue =  fileChooser.showSaveDialog(fileSaveAs);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                    writer.write(line);
                    writer.close();

                } catch (IOException e1) {
                    System.out.println("Error saving file '" + file + "'");
                }
            }
        }

    }


    private class MyCanvas extends Canvas {

        private String shape;
        int totalItems;

        String [] type = new String[500]; // Pen or Fill or Shape

        double[] sx = new double[500];
        double[] sy = new double[500];   // X and Y values of shape
        double[] sw = new double[500];
        double[] sh = new double[500];  // Width and Height of shape

        int [] pl = new int[500]; // Length of polygon
        int[][]px = new int[500][500];
        int[][]py = new int[500][500]; // X and Y values of polygons

        Color[] pc = new  Color[500];
        Color[] fc = new Color[500]; // Pen and Fill values for shape

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

                    ssx = Double.valueOf(df.format(valX/SCREENWIDTH)); //convert to double
                    ssy = Double.valueOf(df.format(valY/SCREENWIDTH));

                }
                public void mouseReleased(MouseEvent e) {

                    double valX = Double.valueOf(e.getX());
                    double valY = Double.valueOf(e.getY());

                    ssw = Double.valueOf(df.format(valX/SCREENWIDTH));
                    ssh = Double.valueOf(df.format(valY/SCREENWIDTH));

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

                //If pen type, set the color and give null values for other arrays
                if(arrOfStr[0].contains("PEN")){
                    this.pc[i] = (Color.decode(arrOfStr[1]));

                }
                //If fill type, check if its off, if it is then set all values of arrays to null
                //else set color in array
                else if(arrOfStr[0].contains("FILL")){
                    if(arrOfStr[1].contains("OFF")){
                        this.fc[i] = (null);
                    }
                    else{
                        this.fc[i] = (Color.decode(arrOfStr[1]));

                    }

                }
                else if(arrOfStr[0].contains("POLYGON")){
                    //split up values in shape [0] = 0.0, [1] = 0.4 ...
                    String [] arrOfVal = arrOfStr[1].split(" ");
//                    System.out.println(arrOfVal[1]);


                    this.pl[i] = arrOfVal.length;

                    int start = 0;
                    int start2 = 0;

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j+=2) {
                        Double x =  Double.parseDouble(arrOfVal[j]) * SCREENWIDTH;
                        this.px[i][start] = (int) Math.round(x);
                        start++;

                    }

                    for (int k = 1; k < arrOfVal.length; k+=2) {
                        Double y = Double.parseDouble(arrOfVal[k]) * (SCREENHEIGHT);
                        this.py[i][start2] =(int) Math.round(y);
                        start2++;
                    }

                    this.sx[i] = 0;
                    this.sy[i] = 0;
                    this.sw[i] = 0;
                    this.sh[i] = 0;

                }//every other shape
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
                        this.sx[i] = values[0] * SCREENWIDTH;
                        this.sy[i] = values[1] * (SCREENHEIGHT);
                        this.sw[i] = (values[2]) * SCREENWIDTH;
                        this.sh[i] = (values[3]) * (SCREENHEIGHT);
                    }// use x and y values only
                    else if(arrOfStr[0].contains("PLOT")){
                        this.sx[i] = values[0] * SCREENWIDTH;
                        this.sy[i] = values[1] * (SCREENHEIGHT);
                        this.sw[i] = (values[0]) * SCREENWIDTH;
                        this.sh[i] = (values[1]) * (SCREENHEIGHT);
                    }
                    else {
                        if(values[2]<values[0]){
                            if(values[3]>values[1]){ //top right to bottom left
                                this.sx[i] = values[3] * SCREENWIDTH;
                                this.sy[i] = values[1] * (SCREENHEIGHT);
                                this.sw[i] = (values[0] - values[2]) * SCREENWIDTH;
                                this.sh[i] = (values[3] - values[1]) * (SCREENHEIGHT);
                            }else{ //bottom right to top left
                                this.sx[i] = values[2] * SCREENWIDTH;
                                this.sy[i] = values[3] * (SCREENHEIGHT);
                                this.sw[i] = (values[0] - values[2]) * SCREENWIDTH;
                                this.sh[i] = (values[1] - values[3]) * (SCREENHEIGHT);
                            }


                        }else{ // else draw normally

                            if(values[3]>values[1]){ //bottom left to top right
                                this.sx[i] = values[0] * SCREENWIDTH;
                                this.sy[i] = values[1] * (SCREENHEIGHT);
                                this.sw[i] = (values[2] - values[0]) * SCREENWIDTH;
                                this.sh[i] = (values[3] - values[1]) * (SCREENHEIGHT);
                            }else{ //top left to bottom right
                                this.sx[i] = values[0] * SCREENWIDTH;
                                this.sy[i] = values[3] * (SCREENHEIGHT);
                                this.sw[i] = (values[2] - values[0]) * SCREENWIDTH;
                                this.sh[i] = (values[1] - values[3]) * (SCREENHEIGHT);
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

            Color pencolor = Color.BLACK;
            Color fillcolor = null;

            Graphics2D g2 = (Graphics2D)g;

            //For the total amount of items go through and find each type and apply appropriate actions
            for(int i=0; i<totalItems; i++) {

                if (type[i].contains("PEN")) {
                    pencolor = (pc[i]);
                } else if (type[i].contains("FILL")) {
                    fillcolor = (fc[i]);
                } else if (type[i].contains("PLOT")) {
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sx[i], sy[i]);
                    g2.setColor(pencolor);
                    g2.draw(shape);
                } else if (type[i].contains("LINE")) {
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.setColor(pencolor);
                    g2.draw(shape);
                } else if (type[i].contains("RECTANGLE")) {
                    Rectangle2D.Double shape = new Rectangle2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    if (fillcolor != null) {
                        g.setColor(fillcolor);
                        g2.fill(shape);
                    }
                    g2.setColor(pencolor);
                    g2.draw(shape);
                } else if (type[i].contains("ELLIPSE")) {
                    Ellipse2D.Double shape = new Ellipse2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    if (fillcolor != null) {
                        g.setColor(fillcolor);
                        g2.fill(shape);
                    }
                    g2.setColor(pencolor);
                    g2.draw(shape);

                } else if (type[i].contains("POLYGON")) {

                    System.out.println(pl[i]);

                    if (fillcolor != null) {
                        g.setColor(fillcolor);
                        g2.fillPolygon(px[i], py[i], pl[i] / 2);
                    }
                    g2.setColor(pencolor);

                    g2.drawPolygon(px[i], py[i], pl[i] / 2);

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
        SwingUtilities.invokeLater(new saveFileTesting("Draw Shapes with colour!"));
    }

}


