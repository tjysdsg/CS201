
package app;

public class Problem_4_5_35 {
    public static void main(String[] args) {
        In filein = new In("sowpods.txt");
        WordLadder wl = new WordLadder(filein.readAllLines(), 5);
        System.out.println(wl.findPath("start", "stood"));
    }
}