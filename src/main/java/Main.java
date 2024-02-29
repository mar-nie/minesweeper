import java.util.Arrays;

public class Main {

    static int [][] mineData = new int [10][10];

    public static void main(String[] args)
    {
        System.out.println(Arrays.deepToString(mineData));
        SweeperCell SweeperCell1 = new SweeperCell(false,0);
        System.out.println(SweeperCell1.proximityNumber);
        System.out.println(SweeperCell1.mine);
    }
}
