import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        var n = scanner.nextInt();
        var a = scanner.nextInt();
        var b = scanner.nextInt();
        var sum = 0;

        var random = new Random(a + b);

        for (int i = 0; i < n; i++) {
            sum += random.nextInt(b - a + 1) + a;
        }
        System.out.println(sum);
    }
}