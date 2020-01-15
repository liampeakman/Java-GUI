package testingArea.colorsTesting;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author group 269
 *
 */

public class colorChooserShapesTesting extends JFrame implements ActionListener, Runnable {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;



    private MyCanvas canvas = new MyCanvas();
    private JPanel bottomPanel;

    private JButton penColourBtn;
    private JButton fillColourBtn;

    /**
     * @param arg0
     * @throws HeadlessException
     */

    public colorChooserShapesTesting(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createAndShowGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create panels
        bottomPanel = createPanel(Color.LIGHT_GRAY);

        //create buttons
        penColourBtn = createButton("Pen Colour");
        fillColourBtn = createButton("Fill Colour");

        penColourBtn.addActionListener(new penListener());
        fillColourBtn.addActionListener(new fillListener());


        // add content to frame
        this.getContentPane().add(canvas,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);


        // set layout for bottom panel
        bottomPanel.setLayout(new GridLayout(1, 2, 0, 1));
        // add buttons to bottom panel
        bottomPanel.add(penColourBtn);
        bottomPanel.add(fillColourBtn);

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


    private class MyCanvas extends Canvas {

        private Color penColor;
        private Color fillColor;

        public void setPenColor(Color color) {
            this.penColor = color;
            repaint();
        }
        public void setFillColor(Color color) {
            this.fillColor = color;
            repaint();
        }


        @Override
        public void paint(Graphics g){
            g.setColor(fillColor);
            g.fillRect(200,100,100,100);
            g.setColor(penColor);
            g.drawRect(200,100,100,100);
        }

    }
    //action that opens the colorChooser and sets the g color
    private class penListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);
            if (c != null)
                canvas.setPenColor(c);

        }
    }

    private class fillListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);
            if (c != null)
                canvas.setFillColor(c);

        }
    }



    @Override
    public void run() {
        createAndShowGUI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }


    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new colorChooserShapesTesting("Changing shape colors"));
    }

}


