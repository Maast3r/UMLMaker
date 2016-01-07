# UMLMaker

This is the repo for our CSSE 374 - Software Design Project - Team Magic Squirrels
<br /><br />
Andrew Ma, Nathan Blank, Sean McPherson
<br /><br />

#Design:\n
For our UMLMaker, we used the Visitor Pattern to visit each Class and parse the Java code out with the ASM5 tool. It seems that adding the Factory Method to our project seems quite feasible, but for right now it seems that using the Factory Method is overkill.
<br /><br />
First, we create a hashmap of all the java classes in the desired package. We then use Lab3-1 as a guide, and created a ClassVisitor that first visits a class's declaration, then fields, and finally methods. We append all of the information that we gather in our ClassVisitor in a String Buffer which we write into a .dot file. Using GraphViz, we use the GraphViz command-line tool to generate a .png of the UML based off of the information in the .dot file.
\n\n

#Who Did What\n
Andrew:\n
- Added access modifier method\n
- Added ability to make a list of all the class names in a given Java project\n
- Formatted buffer to make nodes correctly\n
- Authored the README.md document\n

\nNathan:\n
\n
\nSean:\n
\n

#INSTRUCTIONS:\n
FirstASM.java is the main class, so to use our UMLMaker, you need to set the path and package of your code in main(String[] args). Once the path and package variables are set, simply just run our project to create a .dot file, named "output.dot". This is the file that will generate the UML diagram. Now, cd to the UMLMaker folder (using PowerShell, Terminal, Command Line, etc.) and run "dot -T png -o [package name].png [package name].dot". This will generate a .png file called "output.png", and simply open output.png to view your UML Diagram.