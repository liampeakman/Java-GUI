package testingArea.actionTesting;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class simpleRectangleColourTesting extends JPanel {


    public static void main(String[] args) {
        JFrame window = new JFrame("Print Rectangles with colours");
        window.setContentPane( new simpleRectangleColourTesting() );
        window.setSize(500,400);

        window.setLocation(150,100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }


    public simpleRectangleColourTesting() {

        setBackground(Color.LIGHT_GRAY);

        DrawingArea canvas = new DrawingArea();  // create the canvas

        colorChoice = new JComboBox<String>();  // color choice menu
        colorChoice.addItem("Red");
        colorChoice.addItem("Green");
        colorChoice.addItem("Blue");
        colorChoice.addItem("Cyan");
        colorChoice.addItem("Magenta");
        colorChoice.addItem("Yellow");
        colorChoice.addItem("Black");
        colorChoice.addItem("White");
        colorChoice.addActionListener(canvas);

        JButton rectButton = new JButton("Rect");    // buttons for adding shapes
        rectButton.addActionListener(canvas);


        JPanel bottom = new JPanel();   // a Panel to hold the control buttons
        bottom.setLayout(new GridLayout(1,4,3,3));
        bottom.add(rectButton);
        bottom.add(colorChoice);

        setLayout(new BorderLayout(3,3));
        add(canvas, BorderLayout.CENTER);              // add canvas and controls to the panel
        add(bottom, BorderLayout.SOUTH);

    }

    JComboBox<String> colorChoice;  // The color selection menu


    class DrawingArea extends JPanel
            implements ActionListener  {

        Shape[] shapes = new Shape[500]; // holds a list of up to 500 shapes
        int shapeCount = 0;  // the actual number of shapes
        Color currentColor = Color.RED;  // current color; when a shape is created, this is its color


        DrawingArea() {
            // Constructor: set background color to white set up listeners to respond to mouse actions
            setBackground(Color.WHITE);

        }

        public void paintComponent(Graphics g) {
            // In the paint method, all the shapes in ArrayList are
            // copied onto the canvas.
            g.setColor(Color.WHITE);
            g.fillRect(0,0,getSize().width,getSize().height);
            for (int i = 0; i < shapeCount; i++) {
                Shape s = shapes[i];
                s.draw(g);
            }
            g.setColor(Color.BLACK);  // draw a black border around the edge of the drawing area
            g.drawRect(0,0,getWidth()-1,getHeight()-1);
        }

        public void actionPerformed(ActionEvent evt) {
            // Called to respond to action events.  The three shape-adding
            // buttons have been set up to send action events to this canvas.
            // Respond by adding the appropriate shape to the canvas.
            if (evt.getSource() == colorChoice) {
                switch ( colorChoice.getSelectedIndex() ) {
                    case 0: currentColor = Color.RED;     break;
                    case 1: currentColor = Color.GREEN;   break;
                    case 2: currentColor = Color.BLUE;    break;
                    case 3: currentColor = Color.CYAN;    break;
                    case 4: currentColor = Color.MAGENTA; break;
                    case 5: currentColor = Color.YELLOW;  break;
                    case 6: currentColor = Color.BLACK;   break;
                    case 7: currentColor = Color.WHITE;   break;
                }
            }
            else {
                String command = evt.getActionCommand();
                if (command.equals("Rect"))
                    addShape(new RectShape());
            }
        }

        void addShape(Shape shape) {
            shape.setColor(currentColor);
            shape.reshape(3,3,80,50);
            shapes[shapeCount] = shape;
            shapeCount++;
            repaint();
        }

    }



    static abstract class Shape {

        int left, top;      // Position of top left corner of rectangle that bounds this shape.
        int width, height;  // Size of the bounding rectangle.
        Color color = Color.white;  // Color of this shape.

        void reshape(int left, int top, int width, int height) {
            // Set the position and size of this shape.
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }

        void setColor(Color color) {
            // Set the color of this shape
            this.color = color;
        }

        abstract void draw(Graphics g);
        // Draw the shape in the graphics context g.
        // This must be overriden in any concrete subclass.

    }  // end of class Shape



    static class RectShape extends Shape {
        // This class represents rectangle shapes.
        void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(left,top,width,height);
            g.setColor(Color.black);
            g.drawRect(left,top,width,height);
        }
    }
}
