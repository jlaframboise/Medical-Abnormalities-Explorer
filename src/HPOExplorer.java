import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class HPOExplorer {

    // class variables ---------------------------------------------------------

    private static final ArrayList<Term> terms = new ArrayList<>();
    public static BufferedWriter writer;
    public static HashMap<String, Integer> index;

    // behavior methods --------------------------------------------------------

    // a function to read data from HPO.txt and load in each term when it finds
    // a line with [Term], into an array list called terms
    private static void loadData(String fileName) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            String line;
            while (((line = reader.readLine()) != null)) {
                if (line.equals("[Term]")) {
                    //System.out.println("Calling loadTerm");
                    terms.add(loadTermByTokens(reader));
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    // a method to go line by line until it finds end of file or a blank line,
    // and at each line it looks for one of a set of attributes and it adds
    // that attribute to the term object. It returns the term object.
    private static Term loadTermByTokens(BufferedReader reader) {
        try {
            String line;
            String id = reader.readLine().substring(4);
            Term t = new Term(id);
            while ((line = reader.readLine()) != null && !line.equals("")) {
                String[] tokens = line.split(": ");
                //System.out.println(tokens);
                switch (tokens[0]) {
                    case "name":
                        //System.out.println(tokens.get(1));
                        t.setName(tokens[1]);
                        break;
                    case "comment":
                        t.addComment(tokens[0]);
                        t.addComment(tokens[1]);
                        break;
                    case "alt_id":
                        t.addAlt_id(tokens[0]);
                        t.addAlt_id(tokens[1]);
                        break;
                    case "consider":
                        t.addConsider(tokens[0]);
                        t.addConsider(tokens[1]);
                        break;
                    case "created_by":
                        t.addCreated_by(tokens[0]);
                        t.addCreated_by(tokens[1]);
                        break;
                    case "creation_date":
                        t.addCreation_date(tokens[0]);
                        t.addCreation_date(tokens[1]);
                        break;
                    case "is_a":
                        t.addIs_a(tokens[0]);
                        t.addIs_a(tokens[1]);
                        t.addParentId(tokens[1].split(" ! ")[0]);
                        break;
                    case "is_anonymous":
                        t.addIs_anonymous(tokens[0]);
                        t.addIs_anonymous(tokens[1]);
                        break;
                    case "is_obsolete":
                        t.addIs_obsolete(tokens[0]);
                        t.addIs_obsolete(tokens[1]);
                        break;
                    case "property_value":
                        t.addProperty_value(tokens[0]);
                        t.addProperty_value(tokens[1]);
                        break;
                    case "replaced_by":
                        t.addComment(tokens[0]);
                        t.addReplaced_by(tokens[1]);
                        break;
                    case "subset":
                        t.addSubset(tokens[0]);
                        t.addSubset(tokens[1]);
                        break;
                    case "synonym":
                        t.addSynonym(tokens[0]);
                        t.addSynonym(tokens[1]);
                        break;
                    case "xref":
                        t.addXref(tokens[0]);
                        t.addXref(tokens[1]);
                        break;
                    case "def":
                        t.addDef(tokens[0]);
                        t.addDef(tokens[1]);
                        break;
                    default:
                        System.out.println("Unexpected attr type!");
                        System.out.println(line);
                        t.printData();
                }

            }
            return t;
        } catch (IOException ie) {
            //System.out.println("Heck, IOError");
            ie.printStackTrace();
        }
        return null;
    }

    // a function that creates a hashmap 'index' which maps a string id to
    // an integer index of where to find the term in the terms arraylist
    // takes in a string id and returns an integer index
    private static HashMap<String, Integer> indexTerms() {
        //System.out.println(terms.size());
        HashMap<String, Integer> index = new HashMap<>();
        for (int i = 0; i < terms.size(); i++) {
            index.put(terms.get(i).getId(), i);
        }
        return index;
    }

    // A method that takes in the child term and adds references to the parent
    // term objects to the child. It takes in the child object and the index
    private static void addParentsToTerm(Term child, HashMap<String, Integer> index) {
        if (child.getParentIds().size() > 0) {
            //System.out.println(child.getParentIds());
            for (String parentId : child.getParentIds()) {
                Term parent = terms.get(index.get(parentId));
                child.addParent(parent);
                if (!(parentId.equals("HP:0000001"))) {
                    parent.addChild(child);
                }
            }
        }
    }

    // a method that queries every possible id and keeps the query that has
    // the most links back to the root node. It returns nothing but writes the
    // longest query to file.
    private static void findLongestPath(ArrayList<Term> terms) {
        Query longestQuery = new Query(terms.get(0).getId(), terms);

        for (Term term : terms) {
            Query q = new Query(term.getId(), terms);
            q.runQuery(false);
            if (q.getLength() > longestQuery.getLength()) {
                longestQuery = q;
            }
        }
        try {
            HPOExplorer.writer.write("max_path=" + longestQuery.getLength() + "\n");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        longestQuery.runQuery(true);
    }

    // a method to run all the queries that are listed by id in the queries.txt file.
    // writes the query answer to file.
    private static void runQueries() {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("res\\queries.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String id = line.split(": ")[1];
                Query q = new Query(id, terms);
                writer.write("[query_answer]\n");
                q.runQuery(true);
                writer.write("");
            }
        } catch (IOException ie) {
            //System.out.println("error in io. ");
            ie.printStackTrace();
        }
    }

    // a main function to drive the parsing of HPO.txt, organizing the ontology,
    // running queries and writing the results.
    public static void main(String[] args) {

        loadData("res\\HPO.txt");
        HPOExplorer.index = indexTerms();
        //System.out.println(index);

        for (Term term : terms) {
            addParentsToTerm(term, index);
        }

        try {
            HPOExplorer.writer = new BufferedWriter(new FileWriter("res\\results.txt"));
            runQueries();
            HPOExplorer.writer.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            //System.out.println("\nDone execution.");
        }

        try {
            HPOExplorer.writer = new BufferedWriter(new FileWriter("res\\maxpath.txt"));
            findLongestPath(terms);
            HPOExplorer.writer.close();
        } catch (IOException ie) {
            //System.out.println("IO OOps in main.");
            ie.printStackTrace();
        } finally {
            //System.out.println("Done filing maxpath. ");
        }


    }


}
