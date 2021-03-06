package testingArea.fileTesting;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class readingEverythingTesting extends JFrame implements ActionListener, Runnable {

    public static final int SCREENWIDTH = 500;
    public static final int SCREENHEIGHT = 500;

    private MyCanvas canvas = new MyCanvas();


    //bar
    private JMenuBar menuBar;

    //menus
    private JMenu fileMenu;

    //items
    private JMenuItem fileOpen;



    /**
     * @param arg0
     * @throws HeadlessException
     */

    public readingEverythingTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // create menu bar // colors not changing for some reason
        menuBar = createMenuBar(Color.WHITE);

        //create menu
        fileMenu = createMenu("File", Color.WHITE);

        // create menu items
        fileOpen = createMenuItem("Open");

        // add content to frame
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        this.getContentPane().add(canvas,BorderLayout.CENTER);

        // add menu items
        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);

        //read the opened file
        fileOpen.addActionListener(new readFile());


        setVisible(true);
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

    private class readFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            //set extension to vec files only
            fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","vec"));
            fileChooser.setCurrentDirectory(new File("/Users/liampeakman/Desktop"));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    // FileReader reads text files in the default encoding.
                    FileReader fileReader =
                            new FileReader(selectedFile);

                    //  Wrap FileReader in BufferedReader.
                    BufferedReader bufferedReader =
                            new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {

                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                    String everything = sb.toString();
                    canvas.setString(everything);

                    //  Close files.
                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file '" + selectedFile + "'");
                }
                catch(IOException ex) {
                    System.out.println("Error reading file '" + selectedFile + "'");

                }

                System.out.println(selectedFile.getName());

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
        Color[] pc = new  Color[500];
        Color[] fc = new Color[500]; // Pen and Fill values for shape

        int [] pl = new int[500]; // Length of polygon
        int[][]px = new int[500][500];
        int[][] py = new int[500][500]; // X and Y values of polygons



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
                    //split up values in shape
                    String [] arrOfVal = arrOfStr[1].split(" ");

                    this.pl[i] = arrOfVal.length;

                    //counter
                    int start = 0;
                    int start2 = 0;

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j+=2) {
                        Double x =  Double.parseDouble(arrOfVal[j]) * SCREENWIDTH;
                        this.px[i][start] = (int) Math.round(x);
                        start++;
                    }

                    for (int k = 1; k < arrOfVal.length; k+=2) {
                        Double y = Double.parseDouble(arrOfVal[k]) * (SCREENHEIGHT - 30);
                        this.py[i][start2] =(int) Math.round(y);
                        start2++;
                    }

                }

                else if(arrOfStr[0].contains("PLOT")){

                    //split up values in plot
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

                }

                else{ //all other shapes

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
                    //line needs separate values
                    if(arrOfStr[0].contains("LINE")){
                        this.sw[i] = (values[2]) * SCREENWIDTH;
                        this.sh[i] = (values[3]) * (SCREENHEIGHT - 30);
                    }
                    else{
                        this.sw[i] = (values[2] - values[0]) * SCREENWIDTH;
                        this.sh[i] = (values[3] - values[1]) * (SCREENHEIGHT - 30);
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

                if(type[i].contains("PEN")) {
                    pencolor = (pc[i]);
                }
                else if(type[i].contains("FILL")) {
                    fillcolor = (fc[i]);
                }
                else if(type[i].contains("PLOT")){
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sx[i], sy[i]);
                    g2.setColor(pencolor);
                    g2.draw(shape);
                }
                else if(type[i].contains("LINE")){
                    Line2D.Double shape = new Line2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    g2.setColor(pencolor);
                    g2.draw(shape);
                }
                else if(type[i].contains("RECTANGLE")){
                    Rectangle2D.Double shape = new Rectangle2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    if(fillcolor != null){
                        g.setColor(fillcolor);
                        g2.fill(shape);
                    }
                    g2.setColor(pencolor);
                    g2.draw(shape);
                }
                else if(type[i].contains("ELLIPSE")){
                    Ellipse2D.Double shape = new Ellipse2D.Double(sx[i], sy[i], sw[i], sh[i]);
                    if(fillcolor != null){
                        g.setColor(fillcolor);
                        g2.fill(shape);
                    }
                    g2.setColor(pencolor);
                    g2.draw(shape);

                }
                else if(type[i].contains("POLYGON")){

                    System.out.println(pl[i]);

                    if(fillcolor != null){
                        g.setColor(fillcolor);
                        g2.fillPolygon(px[i], py[i],pl[i]/2 );
                    }
                    g2.setColor(pencolor);

                    g2.drawPolygon(px[i], py[i], pl[i]/2 );

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
        SwingUtilities.invokeLater(new readingEverythingTesting("Import anything!!"));
    }

}


