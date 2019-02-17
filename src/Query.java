import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Query {

    // class variables ---------------------------------------------------------
    private Term leaf;
    private int length = 0;

    // constructors ------------------------------------------------------------


    public Query(String id, ArrayList<Term> terms){
        this.leaf = terms.get(HPOExplorer.index.get(id));
    }

    // accessors ---------------------------------------------------------------

    public int getLength(){ return length -1; }

    public Term getLeaf(){ return leaf; }

    // behavior methods --------------------------------------------------------

    public void traverseToRoot(Term node, boolean outputToFile){
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
        }else{
            ;
            //System.out.println("!!!!! Island found!!!!!!!!!!!!!");
        }
    }

    public void runQuery(boolean outputToFile){
        traverseToRoot(leaf, outputToFile);
    }




}
