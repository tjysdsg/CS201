package app;

public class Problem_3_1_17 {
    private static String reverseDomain(String domain) {
        String[] subdomains = domain.split("\\.");
        String result = "";
        for (int i = subdomains.length - 1; i >= 0; --i) {
            String s = subdomains[i];
            result += s;
            if (i > 0) {
                result += ".";
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing command line argument for input string");
            return;
        }
        System.out.println(reverseDomain(args[0]));
    }
}
