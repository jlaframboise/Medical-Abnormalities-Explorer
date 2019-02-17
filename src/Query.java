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
        if (node.getId().equals("HP:0000001")){
            try{
                if (outputToFile){
                    node.writeData();
                    HPOExplorer.writer.write("\n");
                }
            }catch(IOException ie){
                ie.printStackTrace();
            }
        }else if(node.getParents().size()>0){
            if (outputToFile){
                node.writeData();
                try{
                    HPOExplorer.writer.write("\n");
                }catch(IOException ie){
                    ie.printStackTrace();
                }
            }
            traverseToRoot(node.getParents().get(0), outputToFile);
            //node.printData();
        }
    }

    // a simple function to call the recursive function
    public void runQuery(boolean outputToFile){
        traverseToRoot(leaf, outputToFile);
    }




}
