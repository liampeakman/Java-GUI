package testingArea.fileTesting;
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

public class fileReadingTesting extends JFrame implements ActionListener, Runnable {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;


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

    public fileReadingTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(WIDTH, HEIGHT);
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
        SwingUtilities.invokeLater(new fileReadingTesting("Here's a menu"));
    }

}


