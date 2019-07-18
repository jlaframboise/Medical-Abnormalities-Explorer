# HPO-Explorer
### By Jacob Laframboise

HPO Explorer is a program to read in a medical ontology and to 
organize it with children parent relationships emulating a tree
structure. 

This uses recursion to allow one to query the ontology and 
get the entire path up the tree to the root from any
node on the way. 

This is for a project in a Queen's University Java course. 
The dataset is the Human Phenotype Ontology, from
https://hpo.jax.org/app/

### Organization
This project is organized into a subfolder for resources, and 
a subfolder for source code. The files correctly reference 
each other in this configuration. 

#### Run instructions
To run:
-execute from within HPO-Explorer directory
-it works in InteliJ Idea. 

to run from windows command line:
inside HPO-Explorer directory run:
javac -classpath src/ src/HPOExplorer.java

then inside the same folder run:
java -classpath src/ HPOExplorer


