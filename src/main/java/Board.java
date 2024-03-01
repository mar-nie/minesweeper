import java.util.ArrayList;

public class Board
{
    public static SweeperCell[][] populateArray(int boardSize)
    {
        // Create a 2d array to be filled with mine cell class objects
        SweeperCell[][] arr;
        arr = new SweeperCell[boardSize][boardSize];

        int cellNo = 1; // Initializing cell number counter

        // POPULATING
        // Filling the board with cell objects

        // Iterating through all cells on the board
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                // Assign a cell object with default attributes to each coordinate
                arr[i][j] = new SweeperCell(false, false, false,0, cellNo);

                // Add one to cell counter
                cellNo++;
            }
        }
        return arr;
    }

    public static SweeperCell[][] addMines(int boardSize)
    {
        SweeperCell[][] cellArray = populateArray(boardSize);

        // MINES
        // - Create an ArrayList of random numbers equal to BoardSize - these will be the mines
        ArrayList<Integer> randoms = new ArrayList<Integer>();

        for (int i = 0; i < boardSize; i++)
        {
            boolean duplicate = true;

            // If the random number generated already exists in the array, generate a new one until a unique number is found.
            do
            {
                int random = (int)(Math.random() * (boardSize * boardSize) + 1);
                if (!randoms.contains(random))
                {
                    randoms.add(random);
                    duplicate = false;
                }

            } while(duplicate);
        }

        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                // Iterate through all random numbers in array to check if any match the current cell number
                for (int k = 0; k < boardSize; k++)
                {
                    // If one of the random numbers generated above matches the current cell number
                    if (randoms.get(k) == cellArray[i][j].cellNumber)
                    {
                        // Add a mine to this location
                        cellArray[i][j].mine = true;

                        // Increment proximityNumber of all surrounding cells by 1
                        // (Accounting for out of bounds index values)
                        if (i != 0 && j !=0) {
                            cellArray[i - 1][j - 1].proximityNumber++;
                        }
                        if (i != 0) {
                            cellArray[i - 1][j].proximityNumber++;
                        }
                        if (i != 0 && j < (boardSize - 1)) {
                            cellArray[i - 1][j + 1].proximityNumber++;
                        }
                        if (j != 0) {
                            cellArray[i][j - 1].proximityNumber++;
                        }
                        if (j < (boardSize - 1)) {
                            cellArray[i][j + 1].proximityNumber++;
                        }
                        if (i < (boardSize - 1) && j != 0) {
                            cellArray[i + 1][j - 1].proximityNumber++;
                        }
                        if (i < (boardSize - 1)) {
                            cellArray[i + 1][j].proximityNumber++;
                        }
                        if (i < (boardSize - 1) && j < (boardSize - 1)) {
                            cellArray[i + 1][j + 1].proximityNumber++;
                        }

                    }
                }
            }
        }

        return cellArray;
    }

    public static String[][] displayArray(int boardSize)
            // Creates the array to be seen by the
    {
        // Call populateArray to create hidden grid with mines
        SweeperCell[][] mineData = addMines(boardSize);

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
                else if (mineData[i][j].proximityNumber != 0)
                {
                    mineDisplay[i][j] = String.valueOf(mineData[i][j].proximityNumber);
                }
                else
                {
                    mineDisplay[i][j] = "_";
                }
            }
        }
        return mineDisplay;
    }

    public static void revealCells ()
    {

    }

    public static void printBoard(int boardSize)
            // Takes the array full of strings and displays them in a grid format with index values on the axes
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

    public static void gamePlay ()
    {

    }
}
