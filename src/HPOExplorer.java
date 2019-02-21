// Author 		: Jacob Laframboise
// Date			: February, 2019
// Description 	: This program reads in data from HPO.txt, and stores all the
//                terms in a way that they can be queried using the term class
//                and the query class.
// Version		: 1.0

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

// this class will load in the data, create the term objects and create the
// network of parent child references, and run the queries.
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
                // a switch to add the attributes to a term based on the tag
                switch (tokens[0]) {
                    case "name":
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
            System.out.println("IOError while reading in terms. ");
            ie.printStackTrace();
        }
        return null;
    }

    // a function that creates a hashmap 'index' which maps a string id to
    // an integer index of where to find the term in the terms arraylist
    // takes in a string id and returns an integer index
    // this will save time when doing a query.
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
            // for every parent, add self as a child
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

        // query every term
        for (Term term : terms) {
            Query q = new Query(term.getId(), terms);
            q.runQuery(false);
            if (q.getLength() > longestQuery.getLength()) {
                longestQuery = q;
            }
        }

        // output the longest query to file
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
            // for every line in the file, run a query with it
            while ((line = reader.readLine()) != null) {
                String id = line.split(": ")[1];
                Query q = new Query(id, terms);
                writer.write("[query_answer]\n");
                q.runQuery(true);
                writer.write("");
            }
        } catch (IOException ie) {
            System.out.println("error in io while running a queries ");
            ie.printStackTrace();
        }
    }

    // a main function to drive the parsing of HPO.txt, organizing the ontology,
    // running queries and writing the results.
    public static void main(String[] args) {
        System.out.println("Welcome to the HPO Explorer!");
        System.out.println("This program will read in the terms from HPO.txt");
        System.out.println("It will store and query the data and will output");
        System.out.println("the results to results.txt, and output the longest");
        System.out.println("query to maxpath.txt.");
        System.out.println();
        System.out.println("Running now!");

        // load the data and make the index
        loadData("res\\HPO.txt");
        HPOExplorer.index = indexTerms();
        System.out.println("Done loading the data. ");

        // create all the parent child reference relationships
        for (Term term : terms) {
            addParentsToTerm(term, index);
        }
        System.out.println("Done adding parent-child references. ");

        // run all the queries.
        try {
            HPOExplorer.writer = new BufferedWriter(new FileWriter("res\\results.txt"));
            runQueries();
            HPOExplorer.writer.close();
        } catch (IOException ie) {
            System.out.println("Error in running and saving the queries to file. ");
            ie.printStackTrace();
        } finally{
            System.out.println("Done writing results of queries. ");
        }

        // find and output the maxpath
        try {
            HPOExplorer.writer = new BufferedWriter(new FileWriter("res\\maxpath.txt"));
            findLongestPath(terms);
            HPOExplorer.writer.close();
        } catch (IOException ie) {
            System.out.println("Error in getting and outputting maxpath. ");
            ie.printStackTrace();
        } finally {
            System.out.println("Done outputting maxpath. ");
        }

        System.out.println("Completed!");
    }


}
