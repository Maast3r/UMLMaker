# UMLMaker

This is the repo for our CSSE 374 - Software Design Project - Team Magic Squirrels
<br /><br />
Andrew Ma, Nathan Blank, Sean McPherson
<br /><br />

#Design:
For our UMLMaker, we used the Visitor Pattern to visit each Class and parse the Java code out with the ASM5 tool. It seems that adding the Factory Method to our project seems quite feasible, but for right now it seems that using the Factory Method is overkill.
<br /><br />
First, we create a hashmap of all the java classes in the desired package. We then use Lab3-1 as a guide, and created a ClassVisitor that first visits a class's declaration, then fields, and finally methods. We append all of the information that we gather in our ClassVisitor in a String Buffer which we write into a .dot file. Using GraphViz, we use the GraphViz command-line tool to generate a .png of the UML based off of the information in the .dot file.
<br /><br />
One design choice that we consciously made was to keep pointers(extends, implements, etc arrows) to built in Java functions. This gives the reader more knowledge, and although it is not technically UML Standard, we believe this is a better approach. It is simple enough to filter out Java classes that aren't in the package, we just choose not to do it.

#Who Did What
Andrew:
- Added access modifier method
- Added ability to make a list of all the class names in a given Java project
- Formatted buffer string to create nodes (UML class boxes)
- Authored the README.md document

Nathan:
- Initialized the project
- Modified all visitors for correct string formatting
- Added inheritance visitor for easier usage
- Added .dot file writing and automatic .png generation
- Helped with edge generation
- Hand tested formatting of GraphViz
- Lots of debugging of GraphViz formatting
Sean:
- Created UML diagrams for 1-3 and UMLMaker code
- Added formatting for edges
- Corrected formatting for types in boxes
- Automated execution of dot program, may implement automated opening later


#INSTRUCTIONS:
FirstASM.java is the main class, so to use our UMLMaker, you need to set the path and package of your code in main(String[] args). Once the path and package variables are set, simply just run our project. This will generate a .png file called "[package name].png", and simply open [package name].png to view your UML Diagram.
<br /><br />
INSTRUCTIONS AS A LIST
- Set the path and package variables in FirstASM.java's main method
- Run the Java Project
- Open [package name].png that was generated