EASYANIMATOR README
---------------------

 * INTRODUCTION

EasyAnimator is an animation program that can create simple animations from a series of basic, single sentence commands. The program can only handle rectangle and elipse shapes, and can remove, create, move, resize and change color of each idividual shape on a tick based system. 

 * DESIGN

The design follows a MODEL-VIEW-CONTROLLER structure.
The MODEL keeps track of all data needed to run the animator.
The VIEW outputs the data from the MODEL in a visual way, effectivly generating the animation itself.
The CONTROLLER takes in all user input and passes the MODEL sanitized and translated commands to execute in order to make the animation.

 * MODEL

- Interfaces

AnimatorModel - This defines all the methods that SimpleAnimatorModel (currently its only child) will have to have implemented.

Shape - This defines all the methods that any Shape (Elipse or Rectangle) needs to have implemented. Each Shape name must be unique so it can be refered to specificaly later.

- Abstract Classes

AShape - This implements the non-specific methods that any Shape (Elipse or Rectangle). This includes methods like getLocation() which returns the location variable, or getColor() which returns the color variable. Specific methods like createShape() or equals(Object o) depend on the child classes, and are therefore defined there. Each Shape name must be unique so it can be refered to specificaly later.

- Concrete Classes
CartPt - CartPt represents a location stored as two doubles, which would be applied to a Shape in order to render and keep track of its location. Its methods allow for getting, modifying, creating, comparing and validating CartPt objects.

Color - Color represents an #RGB color which would be applied to a Shape. Its methods allow for getting, modifying, comparing, creating and validating Color objects.

Command - The Command class is built from a user command. Once built, it caluculates and consolidates the values that it needs to call the respective methods on Shapes. Examples could be the starting tick and ending tick (startTick and endTick respectivley).

Elipse extends AShape - Represents a elpise shape on the animation. When rendered in the VIEW, this will show up as an elipse as defined in the object paramaters, (i.e., if the Color represents the #RGB code for yellow it will appear yellow, if the Size represents a 100 pixel width it will appear 100 pixels wide, etc.). Each Shape name must be unique so it can be refered to specificaly later.

Rectangle extends AShape - Represents a rectangle shape on the animation. When rendered in the VIEW, this will show up as an rectangle as defined in the object paramaters, (i.e., if the Color represents the #RGB code for red it will appear red if the Size represents a 100 pixel height it will appear 100 pixels high, etc.). Each Shape name must be unique so it can be refered to specificaly later.

SimpleAnimatorModel implements AnimatorModel - A class that represents an instance of an Animator model. It keeps track of its Shapes through a List of Shapes and tracks the respective Shape Commands by storing a Map with key type String and a value of List of commands. The map maps the name of a Shape to its corisponding List of Commands and is intially as a new empty list. The map assumes that each shape's name is unique.

Size - Represents the height and width of a Shape. When rendered in the VIEW, this attribute will define the dimensions of the Shape object it is part of, (i.e, if the Size represents a 100px high by 100px wide, then the shape will appear as 100px high by 100px wide).

- Test Classes
Abstract AShapeTest - This implements tests that are relevant for all AShapes.
CartPtTest - This implements tests of methods for CartPt objects.
ColorTest - This implements tests of methods for Color objects.
CommandTest - This implements tests of methods for Command objects.
ElipseTest extends AShapeTest - This implements tests of methods for Elipse objects that are specific to the Elipse child.
RectangleTest extends AShapeTest - This implements tests of methods for Rectangle objects that are specific to the Rectangle child.
SimpleAnimatorModelTest - This implements tests of methods for the SimpleAnimatorModel objects.
SizeTest - This implements tests of methods for Size objects.
InteractiveAnimatorGraphicsTest - This tests the interactive view for reading from files and manual input.
InteractiveControllerTest - This tests the interactive controller calls pause, rewind and loop when it is supposed to.


 * VIEW 

- Interfaces

AnimatorView - Overarching type of what a view can be. Made to be used in constructors in order to simplify them. This interface has no methods.


TweenModelBuilder<T> - This interface contains all the methods that the AnimationFileReader class calls as it reads a file containing the animation and builds a model It is arameterized over the actual model type.

VisualView extends AnimatorView - Represents all functionality of an AnimatorView. Views should have a ModelState object and a Controller object.



- Concrete Classes

AnimationFileReader - This class represents a file reader for the animation file. This reads in the file in the prescribed file format, and relies on a model builder interface. The user of this class should create a model builder that implements this interface.

AnimatorGraphicsView extends JFrame implements InternalView, VisualView - This class represents a VIEW for making an animation based on the commands and shapes provided and displays it in a window. Implements the Java Swing library to build the animations.

AnimatorTextView implements AnimatorView- This class represents a VIEW for displaying text information about animated shapes. AnimatorTextView outputs the commands in text form to an appendable in different wording.

SVGView implements AnimatorView- This class represents a VIEW for making SVG files. SVGView takes in commands and turns formats and translates them into XML code such that they can be read as .svg files. It can write either to an SVG file or just as text to the command line.

ShapePanel extends JPanel - Makes a panel that the animations created by the AnimatorGraphicsView class are drawn on. ShapePanel extends JPanel to accomplish this.

InteractiveView - This class extends AnimatorGraphicsView and adds functionality to the visual view. Interactive view allows the user to pause, loop, and rewind while the animation is running.



 * CONTROLLER

- Interfaces 

AnimatorController - The controller interface for the Animator program.The functions here have been designed to give.control to the controller, and the primary operation for the controller to function.

Interactive Controller - extends AnimatorController, adds functionality for a controller to control a visual view. A visual controller handles time for theanimation and decides when shapes need to move. 
			It interacts with both the view and a read onlyversion of the model.

- Concrete Classes

InteractiveVisualController - implments VisualAnimatorController, ActionListener. A class for controlling an interactive version of the Animator program. An Interactive controller supports the ability to pause, play, loop, and rewind an animation. 
		 		The InteractiveController keeps track of a copy of the orginalShapes list to reset an animation.

SVGController - Implements AnimatorController. A controller that controls SVG version of the Animator. An SVG controller takes in a view and a ticks per second. The controller sends the view the tick rate and lets the view
			compute what is necessary for an SVG view.

TextController - implments AnimatorController. A class for controller the text view. A TextController does not need access to the model. The
 			 textController's only job is to the views refresh method once.




 * MORE INFO

For this assignment, we added interactive commands by creating both a new view and controller. The view, called InteractiveAnimatorGraphicsView extends AnimatorGraphics view and adds functionality to the view. The controller
supports the buttons and checkbox adaded by this new view. Minor changes were made to exisitng code, most notably in the model with a new method called setShapeList(List<Shape> shapes). This method is used for resetting the model, 
and simply sets its shape list to the given list. We ensured that each shape and field within the shape is copied, so there is no mutation. 

Our animations are called "hos-y-juke.txt" and "ProgrammaticAnimation (1)". One represents one of my favorite football plays and the other is a procedually genarted color fading program. 