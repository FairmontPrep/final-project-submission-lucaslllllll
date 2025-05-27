import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    
    public static ArrayList<ArrayList<Integer>> test_array_2 = new ArrayList<>();

    static {
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(9, 0, 0, 1, 0, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0)));
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1)));
    }

    public static void main(String[] args) {
        System.out.println("Original Map:");
        for (ArrayList<Integer> row : test_array_2) {
            System.out.println(row);
        }
        System.out.println("\nFinding Path...");
        PathFinder.run(); 
    }
}