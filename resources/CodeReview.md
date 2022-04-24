# Assignment 7: Code Review

Overall, the design of the code was adequate. The interfaces were flexible and easy to extend and
add new features. The interfaces do a good job of segregating the functionalities of each class.
None of the classes are forced to depend on method they do not use, which further emphasis the
flexibility of the existing interfaces. 

The implementation of the code was clear but sometimes repetitive. There are some areas where code
is being repeated and similar functions could be abstracted. This repetition appears in the model 
and the controller classes. Moreover, in the views, there is a large amount of inheritance between 
panel and visual views. It seems that the team did this with the goal of adding functionalities to 
the interactive view; however, these subclasses define new public methods which are not included in 
any interface. Lastly, some methods return or require class type instead of interface types, which 
removes flexibility from the code. 

The in most cases the code was well documented, but in some areas the @return was missing for the
methods. However, the missing return tags did not reduce the understandability of the code. The 
documentation of the code allow us to understand the implementation and the functionality of each
object on the program. 

One strength of the code is the interface segregation. No class is require to have methods that 
they do not implement. As a result of this, they are not required to suppress or provide defaults 
for functionalities they do not implement. Some limitations in the design is the use of the command 
class for describing the transformation of shapes. The command class does not have and interface 
and the model is coupled to this class. Hence, adding new type of transformation or feature
(i.e. rotations) would be complicated. This addition would require the command class to be 
edited or the model itself. This design limitation could be reduced or fixed by adding an 
interface for the command class and separating the command class into smaller classes. 
Currently, the command class describes all transformations though a single class.
This could be refactored into individual classes where each class could represent a 
different command. 
