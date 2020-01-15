package testingArea.gridTesting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Gridwithuserinput extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    // created main panels 2 panels
    public JPanel pnlcanvas;
    private JPanel pnlbutton;

    // user input panel
    private JPanel pnlInput;

    // create 1 button
    private JButton btngrid;

    // get user input
    public String usersize;


    public Gridwithuserinput(String arg0) throws HeadlessException {
        super(arg0);
    }

    public void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlcanvas = createPanel(Color.WHITE);
        pnlbutton = createPanel(Color.LIGHT_GRAY);
        pnlInput = createPanel(Color.blue);


        btngrid = createButton("GRID");


        pnlcanvas.setLayout(new BorderLayout());

        layoutButtonPanel();

        this.getContentPane().add(pnlcanvas,BorderLayout.CENTER);
        this.getContentPane().add(pnlbutton,BorderLayout.NORTH);

        repaint();
        this.setVisible(true);
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


    private void layoutButtonPanel() {

        //add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        //Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;

        addToPanel(pnlbutton, btngrid,constraints,0,0,2,1);

    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    public void inputbox(){
        usersize = JOptionPane.showInputDialog("Please enter size of grid");
    }

    @Override
    public void run() {
        createGUI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // import graphics
        Graphics grid = getGraphics();

        //Get event source
        Object src=e.getSource();


        if (src== btngrid) {

            // ask the user for their grid sie

            JButton btn = ((JButton) src);
            inputbox();

        }
        drawgrid(grid);
    }

    public void drawgrid(Graphics grid){
        // starting x and y coordinates
        int x = 4;
        int y = 65;

        // size of the grid square
        int size = 50;

        // for loop to change x and y coordinates
        for (int i = 0; i < 51; i++) {
            // for loop for the creation of grid squares
            for(int j = 0; j < 51; j++){
                grid.drawRect(x, y, size, size);
                y = y + size;
            }
            // change x and y coordinates;
            x = x + size;
            y = 65;
            System.out.println(usersize);
        }
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Gridwithuserinput("Grid Testing"));

    }

}

