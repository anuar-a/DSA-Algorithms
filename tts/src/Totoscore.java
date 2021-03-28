import java.util.Scanner;

public class Totoscore {
    public static void main(String[] args) {
        int[] nums = new int[49];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
        }
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] ss = s.split(" ");
        for (String each : ss) {
            System.out.println(each);
        }
    }
}
