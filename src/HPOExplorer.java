import javax.lang.model.type.ArrayType;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;



public class HPOExplorer {

    private static ArrayList<Item> items = new ArrayList<>();
    private static Query longestQuery;

    private static void loadData(String fileName) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            String line = null;
            int count = 0;
            while (((line = reader.readLine()) != null)) {
                if (line.equals("[Term]")) {
                    //System.out.println("Calling loadItem");
                    items.add(loadItemByTokens(reader));
                }
                count++;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    private static Item loadItemByTokens(BufferedReader reader){
        try{
            String line = null;
            String id = reader.readLine().substring(4);
            Item t = new Item(id);
            while ((line = reader.readLine()) != null && !line.equals("")){
                String[] tokens = line.split(": ");
                //System.out.println(tokens);
                switch (tokens[0]){
                    case "name":
                        //System.out.println(tokens.get(1));
                        t.setName(tokens[1]);
                        break;
                    case "comment":
                        t.addComment(tokens[1]);
                        break;
                    case "alt_id":
                        t.addAlt_id(tokens[1]);
                        break;
                    case "consider":
                        t.addConsider(tokens[1]);
                        break;
                    case "created_by":
                        t.addCreated_by(tokens[1]);
                        break;
                    case "creation_date":
                        t.addCreation_date(tokens[1]);
                        break;
                    case "is_a":
                        t.addIs_a(tokens[1]);
                        t.addParentId(tokens[1].split(" ! ")[0]);
                        break;
                    case "is_anonymous":
                        t.addIs_anonymous(tokens[1]);
                        break;
                    case "is_obsolete":
                        t.addIs_obsolete(tokens[1]);
                        break;
                    case "property_value":
                        t.addProperty_value(tokens[1]);
                        break;
                    case "replaced_by":
                        t.addReplaced_by(tokens[1]);
                        break;
                    case "subset":
                        t.addSubset(tokens[1]);
                        break;
                    case "synonym":
                        t.addSynonym(tokens[1]);
                        break;
                    case "xref":
                        t.addXref(tokens[1]);
                        break;
                    case "def":
                        t.addDef(tokens[1]);
                        break;
                    default:
                        System.out.println("Unexpected attr type!");
                        System.out.println(line);
                        t.printData();
                }

            }
            return t;
        }catch(IOException ie){
            System.out.println("Heck, IOError");
            ie.printStackTrace();
        }
        return null;
    }


    private static HashMap<String, Integer> indexItems(){
        System.out.println(items.size());
        HashMap<String, Integer> index = new HashMap<>();
        for (int i = 0; i<items.size();i++){
            index.put(items.get(i).getId(), i);
        }
        return index;
    }


    private static void addParentsToItem(Item child, HashMap<String, Integer> index){
        if (child.getParentIds().size()>0){
            System.out.println(child.getParentIds());
            for (String parentId : child.getParentIds()){
                Item parent = items.get(index.get(parentId));
                child.addParent(parent);
                if (!(parentId.equals("HP:0000001"))){
                    parent.addChild(child);
                }
            }
        }
    }

    public static void main(String[] args) {

        loadData("C:\\_Root Folder\\ComputerPrograming\\HPO-Explorer\\res\\HPO.txt");
        HashMap<String, Integer> index = indexItems();
        //System.out.println(index);

        for (Item item : items){
            addParentsToItem(item, index);
        }

        //addParentsToItem(items.get(index.get("HP:0010982")), index);
        //items.get(index.get("HP:0010982")).printData();
        //items.get(index.get("HP:0010982")).getParents().get(0).printData();
        longestQuery = new Query(items.get(0).getId(), items, index);

        for (Item item : items){
            Query q = new Query(item.getId(), items, index);
            q.runQuery();
            if (q.getLength()>longestQuery.getLength()){
                longestQuery = q;
            }
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!The longest Query is length: ");
        System.out.println(longestQuery.getLength());
        System.out.println("From the item: ");
        longestQuery.getLeaf().printData();
        System.out.println("Here is the query:");
        System.out.println(" ");
        longestQuery.runQuery();



    }


}
