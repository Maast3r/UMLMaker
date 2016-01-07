# UMLMaker

This is the repo for our CSSE 374 - Software Design Project - Team Magic Squirrels__
Andrew Ma, Nathan Blank, Sean McPherson
__
For our UMLMaker, we used the Visitor Pattern to visit each Class and parse the Java code out with the ASM5 tool. It seems that adding the Factory Method to our project seems quite feasible, but for right now it seems that using the Factory Method is overkill.
__
First, we create a hashmap of all the java classes in the desired package. We then use Lab3-1 as a guide, and created a ClassVisitor that first visits a class's declaration, then fields, and finally methods. We append all of the information that we gather in our ClassVisitor in a String Buffer which we write into a .dot file. Using GraphViz, we use the GraphViz command-line tool to generate a png of the UML based off of the information in the .dot file.

