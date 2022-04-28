# Assignment 7: Code Review

## Design Critique

Overall, the design of the code was adequate, but there definitely exists some pitfalls. A positive 
about the design is that the interfaces were flexible and easily extendable to add new features. 
This allowed us to conveniently apply the square design pattern to assist in our implementations of
the new features of Assignment 7. Also, the interfaces do a great job segregating the 
functionalities of each implementing class (i.e. read-only interface for model, interfaces to 
represent each type of view/controller, etc.). None of the classes are forced to depend on methods 
they do not use, which further emphasizes the flexibility of the existing interfaces. A downside of 
the design is the existence of a stand-alone Command class. The command class describes all 
transformations though a single class. The Command class does not have an interface and the model
is coupled to this class. Hence, adding new types of transformations or features (i.e. rotations) 
in the future would be very complicated. This addition would require the command class or the model 
to be directly edited. This design limitation could be reduced or fixed by adding an interface for 
the command class and separating the command class into smaller classes. This could be refactored 
into individual classes where each class could represent a different command. 

## Implementation Critique

The implementation of the code was . 
First, there are some areas, specifically in the model and controller classes where code is being 
repeated. These functions could be abstracted to create cleaner code. Moreover, in the views, there 
is a heavy use of inheritance between the visual views. The inheritance did create some 
inconveniences when adding the new features. For example, when we would want to change one 
It seems that the team did this with the goal of adding functionalities to 
the interactive view; however, these subclasses defined new public methods which are not included in 
any interface. ome methods return or require class types instead of interface types, which 
reduces flexibility from the code. 

## Documentation Critique

In terms of the documentation, the majority of the code was well-documented, but in a few areas the 
@return in the Javadoc was missing for the methods. However, the missing return tags did not reduce 
the understandability of the code. The documentation of the code allowed us to understand the 
implementation and the functionality of each object in the program. 

## Design/Code Strengths and Limitations

One strength of the code is the interface segregation. No class is require to have methods that 
they do not implement. As a result of this, they are not required to suppress or provide defaults 
for functionalities they do not implement. A limitation of the design is 