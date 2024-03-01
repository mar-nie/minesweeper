import java.util.ArrayList;
import java.util.Scanner;

public class Board
{
    public static SweeperCell[][] populateArray(int boardSize)
            // Populates an array with cell objects
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
            // Adds mines to the hidden array
    {
        SweeperCell[][] cellArray = populateArray(boardSize);

        // MINES
        // - Create an ArrayList of random numbers equal to BoardSize - these will be the mines
        ArrayList<Integer> randoms = new ArrayList<>();

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

    public static void explodey (int i, int j, int  boardSize, SweeperCell[][] mineData)
            // Checks all surrounding cells to see if they are already visible or out of bounds, if they are neither,
            // it makes those cells visible, and for each newly visible cell it repeats the same cycle recursively
            // and stops when it reveals a numbered square or the edge of the grid/
    {

        if (i != 0 && j != 0 && !mineData[i - 1][j - 1].isVisible) {
            mineData[i - 1][j - 1].isVisible = true;
            if (mineData[i - 1][j - 1].proximityNumber == 0) {
                explodey(i - 1,j - 1,boardSize,mineData);
            }
        }
        if (i != 0 && j != 0) {
            mineData[i - 1][j].isVisible = true;
            if (mineData[i - 1][j].proximityNumber == 0 && !mineData[i - 1][j].isVisible) {
                explodey(i - 1, j, boardSize, mineData);
            }
        }
        if (i != 0 && j < (boardSize - 1) && !mineData[i - 1][j + 1].isVisible) {
            mineData[i - 1][j + 1].isVisible = true;
            if (mineData[i - 1][j + 1].proximityNumber == 0) {
                explodey(i - 1, j + 1, boardSize, mineData);
            }
        }
        if (j != 0  && !mineData[i][j - 1].isVisible) {
            mineData[i][j - 1].isVisible = true;
            if (mineData[i][j - 1].proximityNumber == 0) {
                explodey(i, j - 1, boardSize, mineData);
            }
        }
        if (j < (boardSize - 1) && !mineData[i][j + 1].isVisible) {
            mineData[i][j + 1].isVisible = true;
            if (mineData[i][j + 1].proximityNumber == 0) {
                explodey(i, j + 1, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && j != 0  && !mineData[i + 1][j - 1].isVisible) {
            mineData[i + 1][j - 1].isVisible = true;
            if (mineData[i + 1][j - 1].proximityNumber == 0) {
                explodey(i + 1, j - 1, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && !mineData[i + 1][j].isVisible) {
            mineData[i + 1][j].isVisible = true;
            if (mineData[i + 1][j].proximityNumber == 0) {
                explodey(i + 1, j, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && j < (boardSize - 1) && !mineData[i + 1][j + 1].isVisible) {
            mineData[i + 1][j + 1].isVisible = true;
            if (mineData[i + 1][j + 1].proximityNumber == 0) {
                explodey(i + 1, j + 1, boardSize, mineData);
            }
        }

    }

    public static void revealCells (int i, int j, int boardSize, SweeperCell[][] mineData)
            // If the cell selected is a mine, all mines are revealed, and if not, it does the explodey thing.
    {
        mineData[i][j].isVisible = true;

        // If the chosen cell contains a mine, display all mines
        if (mineData[i][j].mine) {
            for (int x = 0; x < boardSize - 1; x++) {
                for (int y = 0; y < boardSize - 1; y++) {
                    if (mineData[x][y].mine) {
                        mineData[x][y].isVisible = true;
                    }
                }
            }
        }
        // If it's blank, do the explodey thing.
        else if (mineData[i][j].proximityNumber == 0)
        {
            explodey(i,j,boardSize,mineData);
        }

    }

    public static String[][] displayArray(int boardSize, SweeperCell[][] mineData)
            // Creates the array full of display strings
    {

        // Create a 2d array to hold the display
        String [][] mineDisplay = new String [10][10];


        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                if (!mineData[i][j].isVisible)
                {
                    mineDisplay[i][j] = "*";
                }
                else {

                    if (mineData[i][j].mine) {
                        mineDisplay[i][j] = "X";
                    } else if (mineData[i][j].proximityNumber != 0) {
                        mineDisplay[i][j] = String.valueOf(mineData[i][j].proximityNumber);
                    } else {
                        mineDisplay[i][j] = "_";
                    }
                }
            }
        }
        return mineDisplay;
    }

    public static void printBoard(int boardSize, SweeperCell[][] mineData)
            // Takes the array full of strings and displays them in a grid format with index values on the axes
    {
        String[][] mineDisplay = displayArray(boardSize,mineData);
        System.out.print("\n  | ");

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

                if (j == boardSize - 1)
                {
                    if (i >= 9)
                    {
                        System.out.print(i + 1);
                    }
                    else
                    {
                        System.out.print(" " + (i + 1));
                    }
                }
            }
        }

        System.out.print("\n  | ");

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
    }

    public static void gamePlay ()
    {
        System.out.println("----------------MINESWEEPER----------------");

        int boardSize = 10;

        SweeperCell[][] mineData = addMines(boardSize);
//        printBoard(boardSize,mineData);

        int x;
        int y;

        while (true) {

            printBoard(boardSize,mineData);

            Scanner Reader = new Scanner(System.in);
            System.out.print("\nEnter cell's x-coordinate: ");
            y = Reader.nextInt() - 1;
            System.out.print("Enter cell's y-coordinate: ");
            x = Reader.nextInt() - 1;

            revealCells(x, y, boardSize, mineData);
//            printBoard(boardSize, mineData);

            if (mineData[x][y].mine) {
                System.out.println("BOOM! You lose :)");
                break;
            }


        }
//        System.out.println("[" + x + "," + y + "]");


    }
}
