
// What happens when you compare NaN with something? 

class DoubleComparison {
    public static void main (String[] args) {
        double x = 0.0 / 0.0;
        System.out.println("x = " + x);
        double y = 3.0;
        System.out.println("y = " + y);
        if (x > y) System.out.println("Greater");
        else if (x == y) System.out.println("Equal");
        else if (x < y) System.out.println("Less");
        else System.out.println("Not related"); // RESULT <<----------
    }
}
