package testingArea.fileTesting;
import java.awt.geom.Line2D;
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

public class readingLinesTesting extends JFrame implements ActionListener, Runnable {

    public static final int SCREENWIDTH = 500;
    public static final int SCREENHEIGHT = 400;

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

    public readingLinesTesting(String arg0) throws HeadlessException {
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
                String line = null;
                try {
                    // FileReader reads text files in the default encoding.
                    FileReader fileReader =
                            new FileReader(selectedFile);

                    //  Wrap FileReader in BufferedReader.
                    BufferedReader bufferedReader =
                            new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        canvas.setString(line);
                    }


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
        double sx, sy;   // X and Y values of shape
        double sw, sh;  // Width and Height of shape


        void setString(String line) {
            //set the read vec output to the new string
            this.shape = line;
            System.out.println(shape);
            //split the string to read each value
            String [] arrOfStr = shape.split(" ");

            //size of array
            int size = arrOfStr.length - 1;

            //make a double array to insert each value
            double [] values = new double [size];

            //convert each string in the array to doubles
            for(int i=0; i<size; i++) {
                values[i] = Double.parseDouble(arrOfStr[i+1]);
            }

            // set each value and * it by the screen
            this.sx = values[0] * SCREENWIDTH;
            this.sy = values[1] * SCREENHEIGHT;
            this.sw = values[2] * SCREENWIDTH;
            this.sh = values[3] * SCREENHEIGHT;


            System.out.println(arrOfStr[0]);
            System.out.println(sx);
            System.out.println(sy);
            System.out.println(sw);
            System.out.println(sh);

            //draw shape
            repaint();

        }

        @Override
        public void paint(Graphics g){

            //draws line (0.0, 0.0, 1.0, 1.0)

            Graphics2D g2 = (Graphics2D)g;
            Line2D.Double shape = new Line2D.Double(sx, sy, sw, sh);
            g2.draw(shape); System.out.println(sh);

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
        SwingUtilities.invokeLater(new readingLinesTesting("Import a shape"));
    }

}


