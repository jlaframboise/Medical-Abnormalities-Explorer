import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Term {
    // class variables ---------------------------------------------------------

    private String id = null;
    private String name = null;
    // lists to hold vavirous attributes of a term
    private final ArrayList<String> def = new ArrayList<>();
    private final ArrayList<String> alt_id = new ArrayList<>();
    private final ArrayList<String> comment = new ArrayList<>();
    private final ArrayList<String> consider = new ArrayList<>();
    private final ArrayList<String> created_by = new ArrayList<>();
    private final ArrayList<String> creation_date = new ArrayList<>();
    private final ArrayList<String> is_a = new ArrayList<>();
    private final ArrayList<String> is_anonymous = new ArrayList<>();
    private final ArrayList<String> is_obsolete = new ArrayList<>();
    private final ArrayList<String> property_value = new ArrayList<>();
    private final ArrayList<String> replaced_by = new ArrayList<>();
    private final ArrayList<String> subset = new ArrayList<>();
    private final ArrayList<String> synonym = new ArrayList<>();
    private final ArrayList<String> xref = new ArrayList<>();
    private final ArrayList<String> parentIds = new ArrayList<>();
    private final ArrayList<Term> children = new ArrayList<>();
    private final ArrayList<Term> parents = new ArrayList<>();


    // constructors ------------------------------------------------------------

    // a constructor of the term object, using just the name and id and the rest
    // is added as they appear in the file.
    public Term(String id) {
        setId(id);
        setName(name);
    }


    // accessors ---------------------------------------------------------------

    // these functions are self explanatory and are the getters for all
    // the attributes of the terms
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDef() {
        return def;
    }

    public ArrayList<String> getAlt_id() {
        return alt_id;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public ArrayList<String> getConsider() {
        return consider;
    }

    public ArrayList<String> getCreated_by() {
        return created_by;
    }

    public ArrayList<String> getCreation_date() {
        return creation_date;
    }

    public ArrayList<String> getIs_a() {
        return is_a;
    }

    public ArrayList<String> getIs_anonymous() {
        return is_anonymous;
    }

    public ArrayList<String> getIs_obsolete() {
        return is_obsolete;
    }

    public ArrayList<String> getProperty_value() {
        return property_value;
    }

    public ArrayList<String> getReplaced_by() {
        return replaced_by;
    }

    public ArrayList<String> getSubset() {
        return subset;
    }

    public ArrayList<String> getSynonym() {
        return synonym;
    }

    public ArrayList<String> getXref() {
        return xref;
    }

    public ArrayList<String> getParentIds() {
        return parentIds;
    }

    public ArrayList<Term> getParents() {
        return parents;
    }

    // this method will return all of the data for an item so it can be
    // taken and either printed or written to file.
    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("[Term]");
        data.add("id");
        data.add(getId());
        data.add("name");
        data.add(getName());
        if (getDef().size() > 0) {
            data.addAll(getDef());
        }
        if (getAlt_id().size() > 0) {
            data.addAll(getAlt_id());
        }
        if (getComment().size() > 0) {
            data.addAll(getComment());
        }
        if (getConsider().size() > 0) {
            data.addAll(getConsider());
        }
        if (getCreated_by().size() > 0) {
            data.addAll(getCreated_by());
        }
        if (getCreation_date().size() > 0) {
            data.addAll(getCreation_date());
        }
        if (getIs_a().size() > 0) {
            data.addAll(getIs_a());
        }
        if (getIs_anonymous().size() > 0) {
            data.addAll(getIs_anonymous());
        }
        if (getIs_obsolete().size() > 0) {
            data.addAll(getIs_obsolete());
        }
        if (getProperty_value().size() > 0) {
            data.addAll(getProperty_value());
        }
        if (getReplaced_by().size() > 0) {
            data.addAll(getReplaced_by());
        }
        if (getSubset().size() > 0) {
            data.addAll(getSubset());
        }
        if (getSynonym().size() > 0) {
            data.addAll(getSynonym());
        }
        if (getXref().size() > 0) {
            data.addAll(getXref());
        }
        return data;


    }


    // mutators ----------------------------------------------------------------

    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    // instead of setting, these are adding because there could be any number
    // of some attrs
    public void setName(String name) {
        this.name = name;
    }

    public void addDef(String def) {
        this.def.add(def);
    }

    public void addAlt_id(String alt_id) {
        this.alt_id.add(alt_id);
    }

    public void addComment(String comment) {
        this.comment.add(comment);
    }

    public void addConsider(String consider) {
        this.consider.add(consider);
    }

    public void addCreated_by(String created_by) {
        this.created_by.add(created_by);
    }

    public void addCreation_date(String creation_date) {
        this.creation_date.add(creation_date);
    }

    public void addIs_a(String is_a) {
        this.is_a.add(is_a);
    }

    public void addIs_anonymous(String is_anonymous) {
        this.is_anonymous.add(is_anonymous);
    }

    public void addIs_obsolete(String is_obsolete) {
        this.is_obsolete.add(is_obsolete);
    }

    public void addProperty_value(String property_value) {
        this.property_value.add(property_value);
    }

    public void addReplaced_by(String replaced_by) {
        this.replaced_by.add(replaced_by);
    }

    public void addSubset(String subset) {
        this.subset.add(subset);
    }

    public void addSynonym(String synonym) {
        this.synonym.add(synonym);
    }

    public void addXref(String xref) {
        this.xref.add(xref);
    }

    public void addParentId(String id) {
        this.parentIds.add(id);
    }

    public void addParent(Term parent) {
        this.parents.add(parent);
    }

    public void addChild(Term child) {
        this.children.add(child);
    }

    // behavior methods --------------------------------------------------------

    // a function to take the data from an object and print it to file
    public void writeData() {
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("def", "name", "alt_id", "id", "comment", "synonym", "xref",
                "is_a", "created_by", "creation_date", "subset", "consider", "is_anonymous",
                "is_obsolete", "property_value", "replaced_by"));
        for (String str : getData()) {
            try {
                if (keywords.contains(str)) {
                    HPOExplorer.writer.write(str + ": ");
                } else {
                    HPOExplorer.writer.write(str + "\n");
                }
            } catch (IOException ie) {
                System.out.println("OOps error in node writing. ");
                ie.printStackTrace();
            }
        }
    }

    // a method to take the data from an object and print it to console.
    public void printData() {
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("def", "name", "alt_id", "id", "comment", "synonym", "xref",
                "is_a", "created_by", "creation_date", "subset", "consider", "is_anonymous",
                "is_obsolete", "property_value", "replaced_by"));
        for (String str : getData()) {
            if (keywords.contains(str)) {
                System.out.print(str + ": ");
            } else {
                System.out.println(str);
            }
        }
    }
}

