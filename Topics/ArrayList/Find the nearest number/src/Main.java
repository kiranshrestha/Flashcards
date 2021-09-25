import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        ArrayList<Integer> arrayList = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String arr = s.nextLine();
        var arrayLst = arr.split("\\s");
        for (String value : arrayLst) {
            arrayList.add(Integer.parseInt(value));
        }
        arrayList = (ArrayList<Integer>) arrayList.stream().sorted().collect(Collectors.toList());
        //System.out.println(arrayList);
        final int search = s.nextInt();
        ArrayList<Integer> res = new ArrayList<>();

        int diff = search;
        for (Integer integer : arrayList) {
            if (search != integer) {
                int newDiff = Math.abs(search - integer);
                /*System.out.println("integer = " + integer);
                System.out.printf("newDiff = %d oldDiff = %d\n", newDiff, diff);*/
                if (newDiff == diff) {
                    res.add(integer);
                    //System.out.println("res eqq = " + res);
                }
                if (newDiff < diff && newDiff>0) {
                    //System.out.println("res b4= " + res);
                    res.clear();
                    res.add(integer);
                    //System.out.println("res aft= " + res);
                    diff = newDiff;
                }

            }
        }

        //System.out.println("res = " + res);

        res.forEach(item -> System.out.printf("%d ",item));

    }
}