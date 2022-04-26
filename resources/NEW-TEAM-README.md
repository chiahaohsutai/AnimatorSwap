### Were we able to implement the new features?

Adding the layering to the model was successful, you can find more details about the implementation
below. The model is able to track the laying on the shapes and likewise transmit the layering
information to the views. The text view and SVG view both are able to respect the layering of the
shapes as indicated by the model. 

The interactive view .... 

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

### Editing the Visual View

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
explanation in the previous section. To add the new feature of adding shapes to the currently 
playing animation, we used the square design pattern since we wanted followed the same 
design that the team used to implement their interactive view, which was via inheritance.
