import java.util.HashSet; 

class A {
    private int i;
    A (int i) { this.i = i; }
    public int hashCode () { return new Integer(i).hashCode(); }
    public boolean equals (Object obj) {
        if (obj != null && obj instanceof A) {
            A other = (A)obj;
            return other.i == i;
        }
        return false;
    }
}

class Unique {
    public static void main (String[] args) {
        java.util.HashSet hs = new java.util.HashSet();
        for (int i=0; i<3; i++) {
            if (! hs.add(new A(i))) System.out.println("Repeated element new A(" + i + ")");
        }
        System.out.println(new A(1).equals(new A(1)));
        if (hs.contains(new A(1))) System.out.println("Repeated element new A(1)");
        System.out.println(hs);
    }
}
