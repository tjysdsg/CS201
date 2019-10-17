package app;

public class Problem_4_5_35 {
    public static void main(String[] args) {
        String start;
        String end;
        if (args.length == 2) {
            start = args[0];
            end = args[1];
        } else {
            start = "music";
            end = "swing";
            System.err
                    .println("Invalid command line argument, using '" + start + "' as start and '" + end + "' as end");
        }
        In filein = new In("sowpods.txt");
        WordLadder wl = new WordLadder(filein.readAllLines(), 5);
        System.out.println(wl.findPath(start, end));
    }
}