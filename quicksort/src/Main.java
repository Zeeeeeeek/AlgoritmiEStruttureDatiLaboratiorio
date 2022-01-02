import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(6);
        a.add(3);
        a.add(9);
        a.add(5);
        a.add(4);
        a.add(2);
        a.add(8);
        a.add(7);
        Quicksort sortino = new Quicksort(a);
        sortino.sort(0,8);
        System.out.println(a.toString());
        System.out.println(sortino.getCountCompare());
    }
}
