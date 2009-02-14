class Test0 {
    public static void main (String[] args) {
        String s0 = args[0]; // "aaa"
        String s1 = args[1]; // "bbb"
        String s2 = args[2]; // "ccc"
        String s3 = args[3]; // "aaa"
        String s4 = args[4]; // "ccc"
        String s5 = args[5]; // "ddd"
        String s6 = new String("aaa");
        String s7 = "aaa";
        if (s0 == s3 && s2 == s4 && s0 != s6 && s0 == s7) return;
        else main(args); // diverge
    }
}
