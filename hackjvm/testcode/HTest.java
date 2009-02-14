import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;


public class HTest {
    private static final boolean DEBUG = false;
    Random r = new Random();
//    public static int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
//    public static int[] zoo = {3};
    
    private void foo(int x) {
        boolean b = r.nextBoolean();
        
    }
    
    public class A {
        void foo(int a, int b) {}
        void foo(int a) {}
    }
    public class B extends A {
        void foo(int a) {}
    }

    public void x() {
        A a = new A();

        a.foo(8);
        a.foo(81);
        
        B b = new B();
        b.foo(2);
        b.foo(2, 6);
    }

    public static void main(String[] args) {
      HTest ht = new HTest();
      ht.x();
        
        
//        HTest ht = new HTest();
//        ht.foo(0);
//        int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
////    int[][] zoo = {{33}};
//        int x = 2;
//        x++;
//        x++;
//        // System.out.print('~');
//        if (DEBUG) {
//            String s = "yer mamma";
//            String z = "yooooo";
//            String zz = "ydfdsfooooo";
//        }
//        
//        ht.foo(x);
//        int []a = new int[3];
//        a[1] = 34;
        
//         char[][] b = new char[2][2];

//        int[][] b = new int[5][7];
//        b[3][4] = 23;
//        
//        int k = b[2][3];
//        int kk = b[2][38];

        //        int[][] b = new int[5][];
//        for (int i = 0; i < a.length; i++)
//            a[i] = i * 3;
//        String t = "foo" + s;
//        
//        CharArrayWriter w = new CharArrayWriter();
//        try {
//            w.write("foo");
//            w.toString();
//            System.out.print('x');
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        //        System.out.println();
//        System.out.println(x);
//        System.out.println("foo");
    }

}
