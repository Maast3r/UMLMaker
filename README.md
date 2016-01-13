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
<Milestone 3
<br /><br />
After our Milestone 2 meeting with Dr. Hays, we realized that we weren't constructing the "uses" arrow correctly. We were pointing to the interface, not the concrete objects. To fix this, we added a MethodBodyVisitor class which just goes into each method and pulls out the return object. This way, we point our arrows at the correct object.
<br /><br />

#Who Did What
Andrew:
- Added access modifier method
- Added ability to make a list of all the class names in a given Java project
- Formatted buffer string to create nodes (UML class boxes)
- Authored the README.md document
- Helped change the design of the code for scalability (Strategy Pattern)
- Implemented "uses" and "association" arrows

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
<br />
Sean:
- Created UML diagrams for 1-3 and UMLMaker code
- Added formatting for edges
- Corrected formatting for types in boxes
- Automated execution of dot program, may implement automated opening later
- Added unit tests for each of the Visitor methods
- Updated README for Milestone 1 and 2
- Created unit tests for uses, inheritance, and associations visitors


#INSTRUCTIONS:
FirstASM.java is the main class, so to use our UMLMaker, you need to set the path and package of your code in main(String[] args). Once the path and package variables are set, simply just run our project. This will generate a .png file called "target.png", and simply open target.png to view your UML Diagram.
<br /><br />
INSTRUCTIONS TO CREATE A UML
- Remove all files (if there are any) in the "target" package
- Copy and Paste all files you want a UML Diagram of into the "target" package
- Run the Java Project
- Open target.png that was generated
<br /><br />
INSTRUCTIONS TO CREATE A SEQUENCE DIAGRAM
- Remove all files (if there are any) in the "target" package
- Copy and Paste all files you want a Sequence Diagram of into the "target" package
- Run the Java Project
- Open target.png that was generated