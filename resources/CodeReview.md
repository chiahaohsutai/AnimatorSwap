# Assignment 7: Code Review

The design of the code was adequate. In terms of the model, the model uses several base classes
that Java already has an implementation for; hence, the code for these base classes (Color, CartPt)
seemed a little repetitive. Another drawback of the design is the use of the command class to 
describe the movement and transformation of shapes. Due to the lack of an interface, this class is 
hard to extend and the model is strongly coupled to this class. Ergo, if we were to add a new type 
of transformation, for example rotating a shape, we will have to make changes to the model and 
likewise find a way to add functionalities to the command class. 

The doc string or documentation of the code clear and concise throughout the entire code. There
were some files where the parameters were poorly described, but these parameters could be inferred 
from the method signature. Therefore, understanding the purpose of each method was straightforward.