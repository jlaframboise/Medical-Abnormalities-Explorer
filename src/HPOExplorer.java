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
            while ((line = reader.readLine()) != null) {
                if (line.equals("[Term]")) {
                    //System.out.println("Calling loadItem");
                    items.add(loadItem(reader));
                }
                count++;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            System.out.println("done.");
        }

    }

    private static Item loadItem(BufferedReader reader) {
        try {
            String line = null;
            String id = reader.readLine().substring(4);
            String name = reader.readLine().substring(6);
            Item t = new Item(id, name);
            while ((line = reader.readLine()) != null && !line.equals("")) {
                if (line.substring(0,4).equals("name")){
                    // There are two cases where the [is anonymous comes before the name]
                    // fix this by reomving name from the constructor and have it be added to the object as it parses file.
                }
                if (line.substring(0, 7).equals("comment")) {
                    t.addComment(line.substring(8));
                } else if (line.substring(0, 6).equals("alt_id")) {
                    t.addAlt_id(line.substring(7));
                } else if (line.substring(0, 8).equals("consider")) {
                    t.addConsider(line.substring(9));
                } else if (line.substring(0, 10).equals("created_by")) {
                    t.addCreated_by(line.substring(11));
                } else if (line.substring(0, 13).equals("creation_date")) {
                    t.addCreation_date(line.substring(14));
                } else if (line.substring(0, 4).equals("is_a")) {
                    t.addIs_a(line.substring(5));
                } else if (line.substring(0, 12).equals("is_anonymous")) {
                    t.addIs_anonymous(line.substring(13));
                } else if (line.substring(0, 11).equals("is_obsolete")) {
                    t.addIs_obsolete(line.substring(12));
                } else if (line.substring(0, 14).equals("property_value")) {
                    t.addProperty_value(line.substring(15));
                } else if (line.substring(0, 11).equals("replaced_by")) {
                    t.addReplaced_by(line.substring(12));
                } else if (line.substring(0, 6).equals("subset")) {
                    t.addSubset(line.substring(7));
                } else if (line.substring(0, 7).equals("synonym")) {
                    t.addSynonym(line.substring(8));
                } else if (line.substring(0, 4).equals("xref")) {
                    t.addXref(line.substring(5));
                } else if (line.substring(0, 3).equals("def")) {
                    t.addDef(line.substring(4));
                } else {
                    System.out.println("There is another attr type!!!!!!!!!!!!!!");
                    System.out.println(line);
                    t.printData();
                }

            }
            return t;
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            //System.out.println("done Item");
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

        System.out.println("Hello world!");
        loadData("C:\\_Root Folder\\ComputerPrograming\\HPO-Explorer\\res\\HPO.txt");
        HashMap<String, Integer> index = indexItems();
        System.out.println(index);

        items.get(index.get("HP:0010982")).printData();

    }


}
