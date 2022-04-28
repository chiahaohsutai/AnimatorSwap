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
into individual classes where each class could represent a different command. Another downside is 
that the team created more stand-alone classes such as the CartPt, Color, and Size classes,
which creates tight coupling. The methods use these concrete classes, so if we were to extend the 
model or create a new implementation, we are forced to use the existing stand-alone classes. For 
example, if a method in the model takes in a CartPt, and we want to override the method in the 
extended model, we are still forced to depend on the CartPt class. A suggestion to alleviate this
tight coupling is either to use primitives to represent these classes or design an interface that 
allows for more flexibility.

## Implementation Critique

The implementation of the code also had some positives and some places that can be worked on.
A major strength of the implementation is the abidance to the "I" in the SOLID principles (interface
segregation). No class is required to have methods that they do not implement. As a result of this, 
they are not required to suppress or provide defaults for functionalities they do not implement. 
A downside is the heavy use of inheritance in the implementation of the visual views (i.e. the 
interactive view extends the visual view). We decided to continue this pattern in our implementation
of the new features (making the new interactive view extend the previous interactive view) because
we wanted to reuse as much as the previous code as possible. By doing this, we did run into some 
inconveniences as we experienced a lot of type mismatch errors when calling to a superclass, which 
would then call to its superclass. However, we do see the motive behind using inheritance; it seems 
that the team did this with the goal of adding functionalities to the interactive view. A
suggestion to fix this issue is to simply reuse just the JPanel displaying the animation in each of 
the visual views instead of using extension to inherit this JPanel as we create new views. 
Another caveat of this use of inheritance is that some subclasses defined new public methods which
are not included in any interface. Another downside is that some methods returned or required class 
types instead of interface types, which reduces flexibility in the code. 

## Documentation Critique

In terms of the documentation, the majority of the code was well-documented. In a few areas the 
@return in the Javadoc was missing for the methods. However, the missing return tags did not reduce 
the understandability of the code. The documentation of the code allowed us to understand the 
implementation and the functionality of each object in the program and reuse the team's code to our
advantage when implementing the new features.

## More Design/Code Strengths and Limitations

A downside of the code is a small digression that we noticed from the MVC architecture. The model 
overstepped its responsibilities in some areas and performed some functionalities that was supposed
to be for the view. For example, the text view essentially delegates to the model the responsibility 
of creating the shape motion table and what it looks like and the view just simply displays it. The 
model is supposed to be the keeper of the data, but in this specific case, it was also in charge of 
creating the visual aspect.