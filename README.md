# UMLMaker

This is the repo for our CSSE 374 - Software Design Project - Team Magic Squirrels
<br /><br />
Andrew Ma, Nathan Blank, Sean McPherson
<br /><br />

#Design:
Milestone 1 
<br /><br />
For our UMLMaker, we used the Visitor Pattern to visit each Class and parse the Java code out with the ASM5 tool. It seems that adding the Factory Method to our project seems quite feasible, but for right now it seems that using the Factory Method is overkill.
<br /><br />
First, we create a HashMap of all the java classes in the desired package. We then use Lab3-1 as a guide, and created a ClassVisitor that first visits a class's declaration, then fields, and finally methods. We append all of the information that we gather in our ClassVisitor in a String Buffer which we write into a .dot file. Using GraphViz, we use the GraphViz command-line tool to generate a .png of the UML based off of the information in the .dot file.
<br /><br />
One design choice that we consciously made was to keep pointers(extends, implements, etc arrows) to built in Java functions. This gives the reader more knowledge, and although it is not technically UML Standard, we believe this is a better approach. It is simple enough to filter out Java classes that aren't in the package, we just choose not to do it.
<br /><br />
Milestone 2
<br /><br />
After careful consideration, we decided to add the Strategy Pattern to our design. This will promote better scalability so that our project can process files other than .dot files. Our visitors all inherent from a super class, and using the Strategy Pattern we simply point to the superclass of each visitor. Doing this also allowed us create specialized reference arrows between classes in our UML diagrams, such as "uses", "implements", and "association". 
<br /><br />
We realized after further evaluation that the design choice we made in Milestone 1 (keeping pointers to built-in Java classes) was not a good idea once the scale of the package got too large. Therefore, we removed them from the UML document builder.
<br /><br />
Milestone 3
<br /><br />
After our Milestone 2 meeting with Dr. Hays, we realized that we weren't constructing the "uses" arrow correctly. We were pointing to the interface, not the concrete objects. To fix this, we added a MethodBodyVisitor class which just goes into each method and pulls out the return object. This way, we point our arrows at the correct object.
<br /><br />
Milestone 4
<br /><br />
After our Milestone 3 meeting with Dr. Hays, we realized that we still had a lot of work to accomplish. We started this milestone by cleaning up, fixing, and enhancing the capabilities of our UMLMaker. We finally got sequence diagrams to come out 100% correctly by 1/20.
<br /><br />
Adding Singleton detection was fairly easy. The two key identifiers in a Singleton class are that it has itself has a static field, and has a method which returns itself. If a class meets these two requirements, it is a Singleton and we color it blue and add a \<Singleton\> marker. We made sure to test both the lazy and eager singleton initializations.
<br /><br />
Milestone 5
<br /><br />
We refactored our code in order to add Pattern Detectors and Pattern Decorators.
<br /><br />
Milestone 6
<br /><br />
We added multiple color functionality (for multiple patterns) and added a composite pattern detector.
<br/><br/>
Milestone7
<br/><br/>
We added a GUI to our UMLMaker. This allows users to use a confinguration .properties file to specify all parameters for our program. You can specify which diagram you would like to create, what folder you would like to read from, what classes you would like to read from, which folder you would like to putput to, the dot file path for GraphViz, each phase of the program, additional visitors, node title decorators, node decorators, and arrow decorators.
<br/><br/>
Config File Explanation
<br/><br/>
Each phase determines which pattern detectors you would like to identify in the project. If you omit a phase, you omit the resulting pattern detection and the node will not be labeled/colored in any special way. We chose to only create phases for pattern detectors and not input/ouput because we believe that if you use the GUI, you should use all of the GUI's functionality in terms of reading and writing. There isn't an instance where you would like to create the .png file and not view it with the GUI. You can also add a new Detection phase by creating a class, extending TypeDetector, and then creating a new class that extends Phase. The class name should then be added to the Phases list of the configuration file. We use reflection so that we can find all the necessary detectors.
<br /><br />
Each Visitor is an additional visitor that will visit all the class nodes at runtime. This way, if you want to add a new visitor, you can do so without changing any pre-exisiting code. Simply create a new class that extends ClassVisitorBuffered and add the classname to Visitors. We use reflection so that we can find all the necessary visitors.
<br/><br/>
Node Title Decorators decorate the name of the node. To add a new Node Title Decorator, simply create a class that extends AbstractTitleDecorator and add the class name to the configuration file. We use reflection so that we can find all the necessary Node Title Detectors and that we can do so without modifying our existing code.
<br/><br/>
Node Decorators decorate the internal of the node. To add a new Node Decorator, simply create a class that extends TypeDecorator and add the class name to the configuration file. We use reflection so that we can find all the necessary Node Decorators and that we can do so without modifying our existing code. 
<br/><br/>
Arrow Decorators decorate arrows in the UML diagram. To add a new arrow decorator, you must first create a class which extends AbstractPairDetector. Add the class name into the configuration file. We use reflection so that we can find all the necessary Arrow Decorators and so that we can do so without modifying our existing code.

#Who Did What
Andrew:
- Added access modifier method
- Added ability to make a list of all the class names in a given Java project
- Formatted buffer string to create nodes (UML class boxes)
- Authored the README.md document
- Helped change the design of the code for scalability (Strategy Pattern)
- Implemented "uses" and "association" arrows
- Added Sequence Diagram compatibility
- Added Singleton Detection

Nathan:
- Initialized the project
- Modified all visitors for correct string formatting
- Added inheritance visitor for easier usage
- Added .dot file writing and automatic .png generation
- Helped with edge generation
- Hand tested formatting of GraphViz
- Lots of debugging of GraphViz formatting
- Helped change the design of the code for scalability (Strategy Pattern)
- Implemented "uses" and "association" arrows
- Added Sequence Diagram compatibility
- Added Singleton Detection
<br />

Sean:
- Created UML diagrams for 1-3 and UMLMaker code
- Added formatting for edges
- Corrected formatting for types in boxes
- Automated execution of dot program, may implement automated opening later
- Added unit tests for each of the Visitor methods
- Updated README for Milestone 1 and 2
- Created unit tests for uses, inheritance, and associations visitors

