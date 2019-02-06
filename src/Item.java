import java.util.ArrayList;
import java.util.Map;

public class Item {
    // class variables ---------------------------------------------------------
    private String id = null;


    private String name = null;
    private ArrayList<String> def = new ArrayList<>();
    private ArrayList<String> alt_id = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();
    private ArrayList<String> consider = new ArrayList<>();
    private ArrayList<String> created_by = new ArrayList<>();
    private ArrayList<String> creation_date = new ArrayList<>();
    private ArrayList<String> is_a = new ArrayList<>();
    private ArrayList<String> is_anonymous = new ArrayList<>();
    private ArrayList<String> is_obsolete = new ArrayList<>();
    private ArrayList<String> property_value = new ArrayList<>();
    private ArrayList<String> replaced_by = new ArrayList<>();
    private ArrayList<String> subset = new ArrayList<>();
    private ArrayList<String> synonym = new ArrayList<>();
    private ArrayList<String> xref = new ArrayList<>();
    private ArrayList<String> parentIds = new ArrayList<>();
    private ArrayList<Item> children = new ArrayList<>();
    private ArrayList<Item> parents = new ArrayList<>();


    // constructors ------------------------------------------------------------

    public Item(String id) {
        setId(id);
        setName(name);
    }


    // accessors ---------------------------------------------------------------

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

    public ArrayList<Item> getChildren() {
        return children;
    }

    public ArrayList<Item> getParents() {
        return parents;
    }

    public void printData(){
        System.out.println("<< Item start.");
        System.out.println(getId());
        System.out.println(getName());
        if (getDef().size()>0){
            System.out.println(getDef());
        }
        if (getAlt_id().size()>0) {
            System.out.println(getAlt_id());
        }
        if (getComment().size()>0) {
            System.out.println(getComment());
        }
        if (getConsider().size()>0) {
            System.out.println(getConsider());
        }
        if (getCreated_by().size()>0) {
            System.out.println(getCreated_by());
        }
        if (getCreation_date().size()>0) {
            System.out.println(getCreation_date());
        }
        if (getIs_a().size()>0) {
            System.out.println(getIs_a());
        }
        if (getIs_anonymous().size()>0) {
            System.out.println(getIs_anonymous());
        }
        if (getIs_obsolete().size()>0) {
            System.out.println(getIs_obsolete());
        }
        if (getProperty_value().size()>0) {
            System.out.println(getProperty_value());
        }
        if (getReplaced_by().size()>0) {
            System.out.println(getReplaced_by());
        }
        if (getSubset().size()>0) {
            System.out.println(getSubset());
        }
        if (getSynonym().size()>0) {
            System.out.println(getSynonym());
        }
        if (getXref().size()>0) {
            System.out.println(getXref());
        }
        System.out.println("item end. >> \n");


    }

    // mutators ----------------------------------------------------------------

    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

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

    public void addParent(Item parent) {
        this.parents.add(parent);
    }

    public void addChild(Item child) {
        this.children.add(child);
    }

    // behavior methods --------------------------------------------------------



}

