public class Leetcode {
    public static String compress(String s) {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                counter++;
            } else {
                sb.append(s.charAt(i - 1));
                sb.append(counter);
                counter = 1;
            }
        }
        sb.append(s.charAt(s.length() - 1));
        sb.append(counter);
        if (sb.length() >= s.length()) return s;
        else return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(compress("abca"));
    }
}
