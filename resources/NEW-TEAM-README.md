# Our Implementation of New Features

## Were we able to implement the new features?

Adding the layering to the model was successful, you can find more details about the implementation
below. The model is able to track the laying on the shapes and likewise transmit the layering
information to the views. The text view works appropriately; however, we have no way to check if the
SVG works because the SVG implementation by the providers is not functional. The SVG view seems 
functional for some files and for some other files it fails to render properly.

Adding the add shape feature to the interactive view was successful, you can find more details
about the implementation below. Here is a very high level overview. The new GUI takes in text and 
passes it to the controller. The controller then processes the string and if the inputs are valid,
adds it to the shape or command queue in the model. This queue is then processed when the
program is not iterating over the list to avoid the ConcurrentModificationException. 
The processing of the queue basically means adding the user inputs to the model to be drawn, which 
leads to the shapes and motions being added to the currently playing
animation. The controller uses an object called EditInputReader that functions similarly to the 
AnimationFileReader to process the input.

## Challenges or issues with the code.

There were some challenges with the code. We decided to continue with this code and not ask for 
a new group's code since it did provide us with the basic requirements. We detailed what how we
implemented the new features in this README, but the program may not run 100% correctly due to 
the not functional portions of the team's original code. 

Here are some of the not functional parts.
When we tried running the program we encountered a very buggy behavior where the speed 
was set to a default even if declared otherwise. Sometimes, in the middle of the program, the speed
would increase or decrease by itself without any inputs from the user. Moreover, the motion of 
shapes was sometimes incorrect for some provided text files from assignment 5. In the interactive 
view, some buttons did not work and there was no option for increasing the speed of the animation. 
Additionally, the input order for the program(-view, -in, -out) does not work as expected. The 
order of input arguments does seem to matter when working with the SVG view. Another issue that 
should be pointed out is that shapes don't have a start tick or end tick. Hence, once a shape is 
added based on the user input in the additional feature of adding shapes to a currently playing 
animation, it will appear in the animation instantaneously even if declared otherwise (i.e. if the 
current tick is 0 and the shape appears in tick 10, the shape will 
be visible at tick 0 once added). This also means that the shapes and transformations are not 
sequential. In order words, transportation is possible in the providers model as well as 
overlapping commands (a shape may move to two different places during the same interval). Another 
thing to point out is that the program prints the state to console. 

## Changes to the AnimationFileReader and Builder.

We added a setLayer command to the Animation file reader. The animation will set the layer of a
shape using the following command: "setLayer shapeID layerNumber". Where the shape ID is shapes id
or name and the layerNumber is in which layer the user wants the shape to be in (we have a sample
txt file with a demo of this testReadingWithLayers.txt). The layers will work in creasing order,
this means that a higher layerNumber leads to a shape being on top. 

The implementation of layers in the AnimationFileReader followed the same logic or approach as the 
rest of the private methods and classes in the file. We added a new switch case, created a class 
that stores the layer info and then had a private method that creates the class with the given 
input from the scanner. Then we had to add a new method to the TweenModelBuilder interface and 
implement the method in the animator builder. This new method calls the layering method from the 
animator. Finally, the reader uses this new method in the TweenModelBuilder to add layers to the 
animator. 

This changes to the AnimationFileReader required us to change the data type of the builder 
implementation. The new builder class uses the AnimationLayers interface, which has the same 
functionalities as the previous implementation as well as the new layering feature. Hence, all the
previously existing functionalities (reading with no laying info) still work. 

## How was the Layering Implemented.

We decided to use a square pattern. We created a new extending interface and had a new class
implement this new interface and extend the previous implementation of the animator. This allowed us
to maintain the already existing functionality of the animator and add the layering feature. 
We implemented the layering by using a hashmap that stores the layer and the shapes at the layer. 
We added some getter functions, which allowed for easy access to the layering information. We
also overrode some methods in the superclass in order to add make sure no leftover state of the 
shapes layering is left when being added or removed from the animator. 

The default layer for all shapes is set to layer 1. This means that when a shape is added, if the
wants to change the layer, the user must explicitly set the layer user the setLayer method in the 
interface.

## Editing the Text and SVG.

No new implementation for the SVG was created. We just override the function from the model, so that
the SVG class receives the shapes according to the laying order. Since, SVG format layers shapes
according to the order by which they are coded, this makes this re-using the existing implementation
possible. For the text view, also did not change the existing implementation. The existing 
implementation delegates the formatting of the text to the model. Hence, we just re-implemented the 
method that originally printed the text, and added layering to it. 

## Editing the Visual View to Support Layering Shapes.

Layering was implemented or added to the visual by changing the order in which the shapes are drawn.
Before the view paints the shapes onto the canvas, the shapes are being sorted according to layer.
This change to the visual view required us to overwrite the updatePanel method and sort the shapes
before they are passed onto the JPanel (ShapePanel) to be drawn. The changes were implemented in a
new visual view class that extended the previous visual view and implemented the existing interface.
This allowed us to keep the same functionalities in the previous visual view while being able to 
add the new feature of layering.

## Editing the Interactive View to support Adding Shapes and Layering.

The assignment required us to implement the additional features of layering and adding shapes to 
the currently playing animation. Layering is accommodated by this new interactive view according to
explanation in the previous section. We overrode the updatePanel method and sort the shapes
before they are passed onto the JPanel (ShapePanel) to be drawn. Similar to the visual view, 
this allows the interactive view to keep the same functionalities as before as well as add the 
new feature of layering.

We followed the same approach as the team did when they implemented the interactive
view, which was via inheritance, to build our. We used the square design pattern to add the new 
feature of adding shapes to the currently playing animation. We created a new extending interface 
and a new class that implemented this new interface while extending the previous implementation of 
the view (in this case it would be the existing interactive view). We believed this was the best 
way since the new interactive view contained the same components and functionality as the previous 
interactive view, but with additional features. In our new interface and class, we constructed a 
GUI with additional components and implemented new methods that are required to accomplish the new 
feature. In the GUI, we added a text box, where the user inputs the description of shapes and 
motions to be added, a button, which processes the edits inputted by the user in the text box 
when clicked, and a label, that notifies the user if the user inputted contains errors (i.e. 
incorrect formatting, missing values, complete bogus input). The user can enter more than one edit 
into the text box. For example, the user may enter the description to add a shape and transform 
the shape three times into the text box and then click the "Add Edits" button to add all the shapes 
and transformations to the playing animation. The view interface had two new methods, one to 
retrieve the text inputted in the text box by the user and one to set the text of the label. 
These two methods will help the controller in its responsibilities.

In addition to the new interface and class in the view component, we utilized the same square 
design pattern to create a new interactive view controller that handles the new feature. We 
created an extending interface and a new class that implemented this new interface while extending
the previous implementation of the controller (in this case it would be the existing interactive
view controller). We believed this was the best way since the new interactive view controller 
accomplished the same tasks as the previous interactive view controller, but with additional 
functionalities. This additional functionality was to process the text entered into the text box
by the user and to make changes to the model based on it. When the "Add Edits" button is clicked,
the controller grabs the text entered into the view's text box and then pass it along to the
EditInputReader.

The EditInputReader implements the ReadEdits interface, and it functions similar to the 
AnimationFileReader; it actually extends the AnimationFileReader to make use of
methods such as ReadRectangleInfo to extract information about adding a rectangle, readMoveInfo to
extract information about adding a move motion, etc. In the EditInputReader, there are two temp
lists, one to hold the shapes that the user wishes to add and one to hold the motions that the user
wishes to add. As the EditInputReader processes the text input by the user, it adds the
corresponding shape and motion objects to these temporary lists. If all the inputs are valid,
then the model gets mutated. If a single input is invalid, that means there was an invalid shape or 
motion addition in the description. When this happens, the temp lists will be emptied and no shapes
or motions that were entered by the user in the text box will be added. Essentially, the 
description entered by the user must all be correct in order to add to the currently playing 
animation. However, this mutation does not occur in the list of shapes and
commands that hold what is actually going on in the animation. Rather, we added two lists to the 
model to specifically hold these shapes and motions that the user wishes to add, which is where the
mutation is occurring. The reason for this is that it allows us to avoid the
ConcurrentModificationException. Essentially, the team used a Thread as a "timer" to run the 
animator application. As 
to include all the added shapes and motions. 
These changes included adding shapes and
adding motions to the animator model. By changing the model, the shapes and motions will be added
to the currently playing animation since the ShapePanel is drawn based on the shapes in the model.

