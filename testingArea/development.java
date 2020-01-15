package testingArea;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.text.DecimalFormat;


/**
 * @author group 269
 *
 */

public class development extends JFrame implements ActionListener, Runnable {
    public int SCREENWIDTH = 900;
    public int SCREENHEIGHT = 900;

    private float scale = 1;


    File openedFile;// VEC file that has been opened
    File savedFile;// VEC file that has been saved
    File save; //VEC file that is being saved
    String line =""; // Main string that holds everything
    String buttonType = "LINE";  //What shape is current


    //Canvas to draw on
    private MyCanvas canvas = new MyCanvas();;


    //menu bar
    private JMenuBar menuBar;

    //menus
    private JMenu fileMenu;
    private JMenu editMenu;

    //items
    private JMenuItem fileOpen;
    private JMenuItem fileSave;
    private JMenuItem fileSaveAs;
    private JMenuItem editUndo;
    private JMenuItem editFillOff;

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




    /**
     * @param arg0
     * @throws HeadlessException
     */

    public development(String arg0) throws HeadlessException {
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
        editMenu = createMenu("Edit", Color.WHITE);

        // create menu items
        fileOpen = createMenuItem("Open");
        fileSave = createMenuItem("Save");
        fileSaveAs = createMenuItem("Save As");
        editUndo = createMenuItem("Undo");
        editFillOff = createMenuItem("Fill Off");


        // create buttons
        plotBtn = createButton("PLOT");
        lineBtn = createButton("LINE");
        rectangleBtn = createButton("RECTANGLE");
        ellipseBtn = createButton("ELLIPSE");
        polygonBtn = createButton("POLYGON");
        doneBtn = createButton("DONE");
        penColourBtn = createButton("Pen Colour");
        fillColourBtn = createButton("Fill Colour");


        // add content to frame
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        this.getContentPane().add(canvas,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);


        // set layout for bottom panel
        bottomPanel.setLayout(new GridLayout(1, 8, 0, 1));
        // add buttons to bottom panel
        bottomPanel.add(plotBtn);
        bottomPanel.add(lineBtn);
        bottomPanel.add(rectangleBtn);
        bottomPanel.add(ellipseBtn);
        bottomPanel.add(polygonBtn);
        bottomPanel.add(doneBtn);
        bottomPanel.add(penColourBtn);
        bottomPanel.add(fillColourBtn);

        //add menu items
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);
        editMenu.add(editUndo);
        editMenu.add(editFillOff);



        fileOpen.addActionListener(new readFile()); //open the file
        fileSave.addActionListener(new saveFile()); //save the file
        fileSaveAs.addActionListener(new saveFileAs()); //save as
        editUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] oldLine = line.split("\\n");
                String newLine ="";
                for (int i = 0; i < oldLine.length - 1; i++) {
                    newLine = newLine+oldLine[i]+"\n";
                }
                line = newLine;
                canvas.setString(line);
            }
        });//undo last action

        editFillOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                line = line + "FILL OFF\n";
            }
        });//turn color fill off

        // Set button type to the corresponding name
        plotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { buttonType = "PLOT"; }
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
                buttonType = "DONE";
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

        //See when the window size changes and scale the shapes
        this.getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if(line== ""){
                }else{
                    canvas.setString(line); //send line to the canvas
                }

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


    //open and read vec file, set output to string line
    private class readFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            //set extension to vec files only
            fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","vec"));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                openedFile = selectedFile;
                StringBuilder sb = new StringBuilder();

                String fileLine = null;
                try {
                    // FileReader reads text files in the default encoding.
                    FileReader fileReader =
                            new FileReader(selectedFile);

                    //  Wrap FileReader in BufferedReader.
                    BufferedReader bufferedReader =
                            new BufferedReader(fileReader);

                    while((fileLine = bufferedReader.readLine()) != null) {

                        sb.append(fileLine);
                        sb.append(System.lineSeparator());
                    }
                    String everything = sb.toString();
                    line = everything;
                    canvas.setString(line);

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
    //overwrite the current opened file
    private class saveFile implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {//Writes over the existing file
                //Check to see if a file has been opened or just savedAs and overwrite that file
                if (openedFile == null){
                    save = savedFile;
                }
                else {
                    save = openedFile;
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(save));

                writer.write(line);
                writer.close();

            } catch (IOException e1) {
                System.out.println("Error saving file '" + save + "'");
            }

        }

    }
    //save as a new vec file
    private class saveFileAs implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("file.vec"));

            fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","vec"));
            int returnValue =  fileChooser.showSaveDialog(fileSaveAs);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                savedFile = saveFile;

                try {//Writes over a new file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile));

                    writer.write(line);
                    writer.close();

                } catch (IOException e1) {
                    System.out.println("Error saving file '" + savedFile + "'");
                }
            }
        }

    }


    private class MyCanvas extends Canvas {


        private String shape; //holds the main string

        int totalItems; //total items in string

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

        DecimalFormat df = new DecimalFormat("#.##"); //decimal

        int startX; //starting x & y values
        int startY;
        int dragX; //changing x & y values
        int dragY;

        int polyAmount = 0; // number of polygons

        int [] polyPointX = new int[300];// x & y values
        int [] polyPointY = new int[300];



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
                    startX = e.getX(); //set outline
                    startY = e.getY();

                    if (buttonType == "POLYGON"){
                        polyPointX[polyAmount] = e.getX();
                        polyPointY[polyAmount] = e.getY();
                        repaint();
                        polyAmount++;

                    }
                    ssx = Double.valueOf(df.format(valX/getWidth())); //convert to double
                    ssy = Double.valueOf(df.format(valY/getHeight()));

                }
                public void mouseReleased(MouseEvent e) {

                    double valX = Double.valueOf(e.getX()); //get end x & y value
                    double valY = Double.valueOf(e.getY());
                    startX = e.getX(); //end outline
                    startY = e.getY();


                    ssw = Double.valueOf(df.format(valX/getWidth())); //convert to double
                    ssh = Double.valueOf(df.format(valY/getHeight()));

                    //set values in array
                    values[0] = buttonType;
                    values[1] = String.valueOf(ssx);
                    values[2] = String.valueOf(ssy);
                    values[3] = String.valueOf(ssw);
                    values[4] = String.valueOf(ssh);

                    //Every other shape


                    //Polygon
                    if (buttonType == "POLYGON") {
                        //only needs x and y values
                        for(int i=1; i<3; i++) {
                            polyLine = polyLine+values[i]+" ";
                        }

                    }
                    //finished polygon
                    else if (buttonType == "DONE") {
                        line = line + "POLYGON "+ polyLine  + "\n";
                        canvas.setString(line);
                        polyLine="";
                        polyAmount = 0;
                        buttonType = "POLYGON";
                    }
                    else {
                        //add each value to the string
                        for(int i=0; i<5; i++) {
                            line = line+values[i]+" ";
                        }
                        line = line + "\n"; //new line
                        canvas.setString(line); //send line to the canvas
                    }

                    System.out.println(line);
                }
            });

            //For the shape outline before drawn
            this.addMouseMotionListener(new MouseMotionAdapter() {


                public void mouseDragged(MouseEvent e) {
                     dragX = e.getX(); //get x & y value
                     dragY = e.getY();

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

                    this.pl[i] = arrOfVal.length;
                    int start = 0;
                    int start2 = 0;

                    //convert each string in the values array to doubles
                    for (int j = 0; j < arrOfVal.length; j+=2) {
                        Double x =  Double.parseDouble(arrOfVal[j]) * getWidth();
                        this.px[i][start] = (int) Math.round(x);
                        start++;

                    }

                    for (int k = 1; k < arrOfVal.length; k+=2) {
                        Double y = Double.parseDouble(arrOfVal[k]) * (getHeight());
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
                        this.sx[i] = values[0] * getWidth();
                        this.sy[i] = values[1] * (getHeight());
                        this.sw[i] = (values[2]) * getWidth();
                        this.sh[i] = (values[3]) * (getHeight());
                    }// use x and y values only
                    else if(arrOfStr[0].contains("PLOT")){
                        this.sx[i] = values[0] * getWidth();
                        this.sy[i] = values[1] * (getHeight());
                        this.sw[i] = (values[0]) * getWidth();
                        this.sh[i] = (values[1]) * (getHeight());
                    }
                    else {
                        if(values[0]>values[2]){
                            this.sx[i] = values[2] * getWidth();
                            this.sw[i] = (values[0] - values[2]) * getWidth();

                        }else{
                            this.sx[i] = values[0] * getWidth();
                            this.sw[i] = (values[2] - values[0]) * getWidth();
                        }

                        if(values[1]>values[3]){
                            this.sy[i] = values[3] * (getHeight());
                            this.sh[i] = (values[1] - values[3]) * (getHeight());
                        }else{
                            this.sy[i] = values[1] * (getHeight());
                            this.sh[i] = (values[3] - values[1]) * (getHeight());
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
            AffineTransform at = new AffineTransform();

            at.scale(scale, scale);
            g2.setTransform(at);

            int newStartX; //new positions for ellipse and rectangle outline
            int newStartY;
            int newDragX;
            int newDragY;


            // draw non-permanent outline of shapes

            if (buttonType == ("LINE")){
                g2.drawLine(startX, startY, dragX, dragY );
            }
            if (buttonType == ("RECTANGLE") || buttonType == ("ELLIPSE")){
                if(startX>dragX){
                    newStartX = dragX;
                    newDragX = startX - dragX;

                }else{
                    newStartX = startX;
                    newDragX = dragX - startX;
                }

                if(startY>dragY){
                    newStartY = dragY;
                    newDragY = startY - dragY;

                }else{
                    newStartY = startY;
                    newDragY = dragY - startY;
                }
                if (buttonType == ("ELLIPSE")){
                    g2.drawOval(newStartX, newStartY, newDragX, newDragY );
                } else {
                    g2.drawRect(newStartX, newStartY, newDragX, newDragY );
                }
            }
            if (buttonType == ("POLYGON")){
                if (polyAmount == 0){
                    g2.drawLine(polyPointX[0], polyPointY[0], polyPointX[0], polyPointY[0] );
                }else{
                    for(int i=0; i<polyAmount; i++){
                        g2.drawLine(polyPointX[i], polyPointY[i], polyPointX[i+1], polyPointY[i+1] );

                    }

                }

            }




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



    @Override
    public void run() {
        createAndShowGUI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }


    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new development("Draw away! Make sure to save!!"));
    }

}


