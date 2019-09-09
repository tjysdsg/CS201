package app;

public class Problem_3_1_16 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing command line argument for input string");
            return;
        }

        String[] subdomains = args[0].split("\\.");
        String tld = subdomains[subdomains.length - 1].split("\\/")[0];
        System.out.println(tld);
    }
}
