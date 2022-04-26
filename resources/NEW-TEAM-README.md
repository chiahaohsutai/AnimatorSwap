### Were we able to implement the new features?

Adding the layering to the model was successful, you can find more details about the implementation
below. The model is able to track the laying on the shapes and likewise transmit the layering
information to the views. The text view and SVG view both are able to respect the layering of the
shapes as indicated by the model. 

Adding the add shape feature to the interactive view was successful, you can find more details
about the implementation below. Here is a very high level overview. The new GUI takes in text and 
passes it to the controller. The controller then processes the string and makes the corresponding
changes to the model, which leads to the shapes and motions being added to the currently playing
animation. The controller uses an object called EditInputReader that functions similarly to the 
AnimationFileReader to process the input.

### Challenges or issues with the code.

There were some challenges with the code. The code was not 100% functional. When we tried running
the program we encounter a very buggy behavior where the speed was set to a default even if declared
otherwise. Moreover, the motion of shapes was sometimes incorrect for some provided text
files from assignment 5. In the interactive view, some buttons did not work and there was no option
for increasing the speed of the animation (some required features from the previous 
assignments were missing). 

### Changes to the AnimationFileReader and Builder.

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

### How was the Layering Implemented.

We decided to use a square pattern. We created a new extending interface and had a new class
implement this new interface and extend the previous implementation of the animator. This allowed us
to maintain the already existing functionality of the animator and add the layering feature. 
We implemented the layering by using a hashmap that stores the layer and the shapes at the layer. 
We added some getter functions, which allowed for easy access to the layering information.
also override some methods in the superclass in order to add make sure no leftover state of the 
shapes layering is left when being added or removed from the animator. 

The default layer for all shapes is set to layer 1. This means that when a shape is added, if the
wants to change the layer, the user must explicitly set the layer user the setLayer method in the 
interface.

### Editing the Text and SVG.

No new implementation for the SVG was created. We just override the function from the model, so that
the SVG class receives the shapes according to the laying order. Since, SVG format layers shapes
according to the order by which they are coded, this makes this re-using the existing implementation
possible. For the text view, also did not change the existing implementation. The existing 
implementation delegates the formatting of the text to the model. Hence, we just re-implemented the 
method that originally printed the text, and added layering to it. 

### Editing the Visual View and Supporting Layering Feature.

The ShapePanel (the JPanel where the actual animation takes place) is housed in the very
first view that was required for the Easy Animator application, which is the AnimatorGraphicsView 
(the visual view). We edited the ShapePanel to accommodate layering. ________________

Just by editing the ShapePanel, all visual views will be able to support the layering feature.
This is because in this code, each new view extends the previous version. For example, the 
InteractiveAnimatorGraphicsView (the interactive view implementation) extends the 
AnimatorGraphicsView (the visual view implementation). Therefore, the ShapePanel and the way it
displays an animation in AnimatorGraphicsView is inherited by the Interactive AnimatorGraphicsView.
The same logic can be applied to how the new interactive view with the ability to add shapes to the
currently playing animation will support layering.

### Editing the Interactive View.

The assignment required us to implement the additional features of layering and adding shapes to 
the currently playing animation. Layering is accommodated by this new interactive view according to
explanation in the previous section. 

We followed the same approach as the team did when they implemented the interactive
view, which was via inheritance. We used the square design pattern to add the new feature of adding 
shapes to the currently playing animation. We created a new extending interface and a new class
that implemented this new interface while extending the previous implementation of the view (in 
this case it would be the existing interactive view). We believed this was the best way since the
new interactive view contained the same components and functionality as the previous interactive 
view, but with additional features. In our new interface and class, we constructed a GUI with 
additional components and implemented new methods that are required to accomplish the new feature. 
In the GUI, we added a text box, where the user inputs the description of shapes and motions to be 
added, a button, which processes the edits inputted by the user in the text box when clicked, 
and a label, that notifies the user if the edits were added to the playing animation were 
successful or not (not successful meaning that the description that the user inputted contained 
errors (i.e. incorrect formatting, missing values)). The user can enter more than one edit into the 
text box. For example, the user may enter the description to add a shape and transform the shape
three times into the text box and then click the "Add Edits" button to add all the shapes and 
transformations to the playing animation. The view interface had two new methods, one to 
retrieve the text inputted in the text box by the user and one to set the text of the label. 
These two methods will help the controller in its responsibilities.

In addition to the new interface and class in the view component, we utilized the same square 
design pattern to create a new interactive view controller that handles the new feature. We 
created an extending interface and a new class that implemented this new interface while extending
the previous implementation of the controller (in this case it would be the existing interactive
view controller). We believed this was the best way since the new interactive view controller 
accomplished the same tasks as the previous interactive view controller, but with additional 
functionalities. This additional functionality was to process the text entered into the text box
by the user and to make changes to the model based on it. These changes included adding shapes and
adding motions to the animator model. By changing the model, the shapes and motions will be added
to the currently playing animation since the ShapePanel is drawn based on the shapes in the model.

The new interactive view controller utilized another object that we created that basically 
processes the text inputted by the user called the EditInputReader. The controller grabs the text 
inputted into the text box and then passes the String to the EditInputReader. This object functions
similarly to the AnimationFileReader; it actually extends the AnimationFileReader to make use of
methods such as ReadRectangleInfo to extract information about adding a rectangle, readMoveInfo to
extract information about adding a move motion, etc. In the EditInputReader, there are two temp
lists, one to hold the shapes that the user wishes to add and one to hold the motions that the user
wishes to add. As the EditInputReader processes the text input by the user, it adds the 
corresponding shape and motion objects to these temporary lists. If all the inputs are valid,
then the model gets mutated to include all the added shapes and motions. If a single input is 
invalid, that means there was an invalid shape or motion addition in the description. When this
happens, the temp lists will be emptied and no shapes or motions that were entered by the user in 
the text box will be added. Essentially, the description entered by the user must all be correct
in order to add to the currently playing animation.
