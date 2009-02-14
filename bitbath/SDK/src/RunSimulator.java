public class RunSimulator {
    /**
     * convenience class to launch the simulator with correct classpath
     */
    public static void main(String[] args) {
        org.hacker.engine.war.WarChooser.main(new String[] {"-cp", "lib/rt.jar"});
    }
}
