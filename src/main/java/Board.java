import java.util.ArrayList;

public class Board
{
    public static SweeperCell[][] populateArray(int boardSize)
    {
        // Create a 2d array to be filled with mine cell class objects
        SweeperCell[][] arr;
        arr = new SweeperCell[boardSize][boardSize];

        int cellNo = 1; // Initializing cell number counter

        ArrayList<Integer> randoms = new ArrayList<Integer>();

        // MINES
        // - Create an ArrayList of random numbers equal to BoardSize - these will be the mines
        for (int i = 0; i < boardSize; i++)
        {
            int random = (int)(Math.random() * (boardSize * boardSize) + 1);
            randoms.add(random);
        }

        // POPULATING
        // Filling the board with cell objects

        // Iterating through all cells on the board
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                boolean mine = false;

                // Iterate through all random numbers in array to check if any match the current cell number
                for (int k = 0; k < boardSize; k++)
                {
                    // If one of the random numbers generated above matches the current cell number, assign mine to TRUE
                    if (randoms.get(k) == cellNo) {
                        mine = true;
                        break;
                    }
                }

                // If mine is true, assign this cell object's 'containsMine' attribute to TRUE and all other values to neutral
                if (mine)
                {
                    arr[i][j] = new SweeperCell(true,false,0,cellNo);
                }
                // If it isn't, assign the cell with neutral values.
                else
                {
                    arr[i][j] = new SweeperCell(false, false, 0, cellNo);
                }
                // Add one to cell counter
                cellNo++;
            }
        }

        return arr;
    }

    public static String[][] displayArray(int boardSize)
    {
        // Call populateArray to create hidden grid with mines
        SweeperCell[][] mineData = populateArray(boardSize);

        // Create a 2d array to hold the display
        String [][] mineDisplay = new String [10][10];


        for (int i = 0; i < boardSize; i++)
        {
            boolean mine = false;

            for (int j = 0; j < boardSize; j++)
            {
                mine = mineData[i][j].mine;

                if (mine)
                {
                    mineDisplay[i][j] = "X";
                }
                else
                {
                    mineDisplay[i][j] = "_";
                }
            }
        }
        return mineDisplay;
    }

    public static void printBoard(int boardSize)
    {
        String[][] mineDisplay = displayArray(boardSize);
        System.out.print("  | ");

        for(int i = 0; i < boardSize; i++)
        {
            if (i >= 9)
            {
                System.out.print((i + 1) + "| ");
            }
            else
            {
                System.out.print((i + 1) + " | ");
            }
        }

        for (int i = 0; i < boardSize; i++)
        {
            if (i >= 9)
            {
                System.out.print("\n" + (i + 1) + "|");
            }
            else
            {
                System.out.print("\n" + (i + 1) + " |");
            }

            for (int j = 0; j < boardSize; j++)
            {
                System.out.print(" " + mineDisplay[i][j] + " |");
            }
        }
    }

}
