# Java-GUI
GUI Software Design.

### Overview 
Our company makes use of several pieces of hardware called plotters. These are devices for drawing
vector-based graphics on various pieces of physical material from an input file, like a simplified
version of PostScript. The challenge here is that our plotters make use of a proprietary design
language called VEC which is not used by any other hardware or software on the market. This makes
it difficult for our designers to create designs to be drawn on the plotters for two reasons:

1. Our designers have to create new VEC files for the plotters by editing text files and entering
in coordinates. This makes design a slow, laborious process, involving first sketching out the
design on graph paper, then writing out the coordinates of each vertex, then typing out the
file.

2. Because no software supports this proprietary format, our designers can only properly test
their designs by using the plotting hardware. This is time-consuming as there are more
designers than plotters and we need to be making better use of our resources.

The task we have for your team is to create a piece of Java software (from scratch) allowing for the
preview of and editing of VEC files for the plotters. The idea is that this will allow designers to create
their designs using a mouse in a WYSIWYG (what you see is what you get) environment instead of
messing around with graph paper and editing text files. Furthermore, the software must also be able
to load our existing VEC files, view them and edit their contents (e.g. to touch up or add to an
existing design.)

The design for this program is left in your hands. You can look at programs intended for similar tasks
to get an idea of how the UI should be structured, button placement etc. Please note that we care a
great deal about the usability of this software as the software will be used by designers who are not
necessarily computer experts. We want you to create for them a piece of software that is user
friendly and intuitive, similar to tools like MS Paint.

### The VEC file format 
As explained before, the design files we send to the plotters are in a file format called VEC. This is a
fairly simple file format. VEC files are just ASCII-format text files with UNIX-style line endings (that is,
LF line endings). We do not mind if your application supports Windows-style (CR/LF) line endings or
any other formats for its input files, but the files your application.


### Vector Design Tool
The task for this assignment is to create a Java program that can load and save VEC format images.
The user interface design of the program is up to you, but use other graphics editing software as
inspiration. Typically graphics editing software will have, at the very minimum, a menu bar with the
typical ‘File’ menu containing items for opening and saving images, as well as a separate tool palette
that is visible at all times, containing drawing tools, as well as a colour palette used to set colours to
be used by those drawing tools.

For this assignment you will need to create a GUI application with similar controls. At the very least
this will mean having a display canvas where your current design appears, buttons for loading and
saving VEC files, drawing tools associated with each VEC drawing command (PLOT, LINE, RECTANGLE,
ELLIPSE and POLYGON) and a colour palette allowing the user to set the colours used, which will
generate appropriate PEN and FILL commands in the output file.

