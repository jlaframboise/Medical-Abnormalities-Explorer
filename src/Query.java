import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Query {
    private Item leaf;
    private int length = 0;

    public Query(String id, ArrayList<Item> items, HashMap<String, Integer> index){
        this.leaf = items.get(index.get(id));
    }

    public int getLength(){ return length; }

    public Item getLeaf(){ return leaf; }

    public void traverseToRoot(Item node){
        length++;
        if (node.getId().equals("HP:0000001")){

            node.printData();
        }else if(node.getParents().size()>0){
            traverseToRoot(node.getParents().get(0));
            node.printData();
        }else{
            System.out.println("!!!!! Island found!!!!!!!!!!!!!");
        }
    }

    public void runQuery(){
        traverseToRoot(leaf);
    }




}
