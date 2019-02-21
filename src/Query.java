// Author 		: Jacob Laframboise
// Date			: February, 2019
// Description 	: This class holds the information for a query of the terms
//                stored from the ontology.
// Version		: 1.0

import java.io.IOException;
import java.util.ArrayList;

public class Query {

    // class variables ---------------------------------------------------------
    private final Term leaf;
    private int length = 0;

    // constructors ------------------------------------------------------------

    // a constructor for the query class, retrieves the leaf node from the
    // ontology
    public Query(String id, ArrayList<Term> terms){
        this.leaf = terms.get(HPOExplorer.index.get(id));
    }

    // accessors ---------------------------------- -----------------------------

    // a getter for length
    public int getLength(){ return length -1; }

    // behavior methods --------------------------------------------------------

    // a recursive function to stop on the root node, and recall itself focused
    // on its parent node. This will run up the tree to root, and write the
    // query on the forward direction of recursion.
    private void traverseToRoot(Term node, boolean outputToFile){
        length++;
        // end case for recursion
        if (node.getId().equals("HP:0000001")){
            try{
                if (outputToFile){
                    node.writeData();
                    HPOExplorer.writer.write("\n");
                }
            }catch(IOException ie){
                ie.printStackTrace();
            }
        // recursive condition
        }else if(node.getParents().size()>0){
            if (outputToFile){
                node.writeData();
                try{
                    HPOExplorer.writer.write("\n");
                }catch(IOException ie){
                    ie.printStackTrace();
                }
            }
            // recursive step, occurs after output so the root will come after
            // the leaf.
            traverseToRoot(node.getParents().get(0), outputToFile);
        }
    }

    // a simple intuitive function to call the recursive function
    public void runQuery(boolean outputToFile){
        traverseToRoot(leaf, outputToFile);
    }




}
