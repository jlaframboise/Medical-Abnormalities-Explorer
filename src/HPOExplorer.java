import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;



public class HPOExplorer {

    private static ArrayList<Item> items = new ArrayList<>();

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

    public static void main(String[] args) {

        loadData("C:\\_Root Folder\\ComputerPrograming\\HPO-Explorer\\res\\HPO.txt");
        HashMap<String, Integer> index = indexItems();
        //System.out.println(index);

        items.get(index.get("HP:0010982")).printData();

    }


}
