package ojvm.util;

/**
 * A pair (name,descriptor) used as keys when building field tables
 * and method tables out of hashtables. The methods hashcode and
 * equals have to be consistent with each other other strange behavior
 * happens.
 * 
 * @author Amr Sabry 
 * @version jdk-1.2
 * 
 * File created June 21, 2000 
 **/

public class NameAndDescriptor {
    private String name;
    private String desc; 

    public NameAndDescriptor (String name, String desc) { 
        this.name = name;
        this.desc = desc;
    }
    
    public String getName () { return name; }
    public String getDesc () { return desc; }

    public int hashCode () {
        return name.hashCode() ^ desc.hashCode();
    }

    public boolean equals (Object obj) {
        if (obj != null && obj instanceof NameAndDescriptor) {
            NameAndDescriptor other = (NameAndDescriptor)obj;
            return other.name.equals(name) && other.desc.equals(desc);
        }
        else return false;            
    }

    public String toString() { 
        return desc + " " + name;
    }
}

