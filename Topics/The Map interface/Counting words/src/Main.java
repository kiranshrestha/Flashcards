import java.util.*;

class MapUtils {

    public static SortedMap<String, Integer> wordCount(String[] strings) {
        // write your code here
        SortedMap<String,Integer> sortedMap = new TreeMap<>();
        for (String string : strings) {
            if (sortedMap.get(string) == null) {
                sortedMap.put(string, 1);
            } else {
                final Integer value = sortedMap.get(string) + 1;
                sortedMap.put(string, value);
            }
        }
        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map) {
        // write your code here
        for (var nameSet :
                map.entrySet()) {
            System.out.printf("%s : %d\n", nameSet.getKey() , nameSet.getValue());
        }
    }

}

/* Do not change code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(" ");
        MapUtils.printMap(MapUtils.wordCount(words));
    }
}