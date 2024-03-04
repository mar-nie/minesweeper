import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Board
{
    // CREATE MINESWEEPER BOARD ----------------------------------------------------------------------------------------

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

    public static SweeperCell[][] addMines(int boardSize, int noMines)
            // Adds mines to the hidden array
    {
        SweeperCell[][] cellArray = populateArray(boardSize);

        // MINES
        // - Create an ArrayList of random numbers equal to BoardSize - these will be the mines
        ArrayList<Integer> randoms = new ArrayList<>();

        for (int i = 0; i < noMines; i++)
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
                for (int k = 0; k < noMines; k++)
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

    public static void revealCells (int i, int j, int boardSize, SweeperCell[][] mineData)
    // If the cell selected is a mine, all mines are revealed, and if not, it does the explodey thing.
    {
        mineData[i][j].isVisible = true;

        // If the chosen cell contains a mine, display all mines
        if (mineData[i][j].mine) {
            for (int x = 0; x < boardSize; x++) {
                for (int y = 0; y < boardSize; y++) {
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

    public static void explodey (int i, int j, int  boardSize, SweeperCell[][] mineData)
            // Checks all surrounding cells to see if they are already visible or out of bounds, if they are neither,
            // it makes those cells visible, and for each newly visible cell it repeats the same cycle recursively
            // and stops when it reveals a numbered square or the edge of the grid.
    {

        if (i != 0 && j != 0 && !mineData[i - 1][j - 1].isVisible) {
            mineData[i - 1][j - 1].isVisible = true;
            mineData[i - 1][j - 1].flagged = false;
            if (mineData[i - 1][j - 1].proximityNumber == 0) {
                explodey(i - 1,j - 1,boardSize,mineData);
            }
        }
        if (i != 0 && j != 0 && !mineData[i - 1][j].isVisible) {
            mineData[i - 1][j].isVisible = true;
            mineData[i - 1][j].flagged = false;
            if (mineData[i - 1][j].proximityNumber == 0) {
                explodey(i - 1, j, boardSize, mineData);
            }
        }
        if (i != 0 && j < (boardSize - 1) && !mineData[i - 1][j + 1].isVisible) {
            mineData[i - 1][j + 1].isVisible = true;
            mineData[i - 1][j + 1].flagged = false;
            if (mineData[i - 1][j + 1].proximityNumber == 0) {
                explodey(i - 1, j + 1, boardSize, mineData);
            }
        }
        if (j != 0  && !mineData[i][j - 1].isVisible) {
            mineData[i][j - 1].isVisible = true;
            mineData[i][j - 1].isVisible = false;
            if (mineData[i][j - 1].proximityNumber == 0) {
                explodey(i, j - 1, boardSize, mineData);
            }
        }
        if (j < (boardSize - 1) && !mineData[i][j + 1].isVisible) {
            mineData[i][j + 1].isVisible = true;
            mineData[i][j + 1].flagged = false;
            if (mineData[i][j + 1].proximityNumber == 0) {
                explodey(i, j + 1, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && j != 0  && !mineData[i + 1][j - 1].isVisible) {
            mineData[i + 1][j - 1].isVisible = true;
            mineData[i + 1][j - 1].flagged = false;
            if (mineData[i + 1][j - 1].proximityNumber == 0) {
                explodey(i + 1, j - 1, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && !mineData[i + 1][j].isVisible) {
            mineData[i + 1][j].isVisible = true;
            mineData[i + 1][j].flagged = false;
            if (mineData[i + 1][j].proximityNumber == 0) {
                explodey(i + 1, j, boardSize, mineData);
            }
        }
        if (i < (boardSize - 1) && j < (boardSize - 1) && !mineData[i + 1][j + 1].isVisible) {
            mineData[i + 1][j + 1].isVisible = true;
            mineData[i + 1][j + 1].flagged = false;
            if (mineData[i + 1][j + 1].proximityNumber == 0) {
                explodey(i + 1, j + 1, boardSize, mineData);
            }
        }

    }

    public static String[][] displayArray(int boardSize, SweeperCell[][] mineData)
            // Creates the array full of display strings
    {

        // Create a 2d array to hold the display
        String [][] mineDisplay = new String [boardSize][boardSize];

        // Iterate through every cell in the data board
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                // If the cell has not been revealed yet, display a *
                if (!mineData[i][j].isVisible)
                {
                    mineDisplay[i][j] = "*";
                }
                else
                {
                    // If the cell is a mine, display an X
                    if (mineData[i][j].mine) {
                        mineDisplay[i][j] = "X";
                    }
                    // If the cell is a proximity number, display that number
                    else if (mineData[i][j].proximityNumber != 0)
                    {
                        mineDisplay[i][j] = String.valueOf(mineData[i][j].proximityNumber);
                    }
                    // If the cell is blank, display a _
                    else
                    {
                        mineDisplay[i][j] = "_";
                    }
                }
                // If the user has flagged the cell, display an F
                if (mineData[i][j].flagged)
                {
                    mineDisplay[i][j] = "F";
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

        // Print x-axis on top of grid
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
            // Print y-axis label before grid
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
                // Print display grid
                System.out.print(" " + mineDisplay[i][j] + " |");

                // Print y-axis label after grid
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

        // Print x-axis below grid
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


    // MAIN MENU AND GAME SET-UP ---------------------------------------------------------------------------------------

    public static void mainMenu ()
            // Takes user difficulty input and produces the game, then they either win or lose, it asks if they would
            // like to play again or end the program.
    {
        boolean playing = true;

        int noMines = 0;        // Initialise number of mines variable
        int boardSize = 0;      // Initialise board size variable

        System.out.println("-----------------MINESWEEPER-----------------");

        while (playing)
        {

            // User chooses their difficulty level from a menu
            int choice = chooseDifficulty();

            // If the user chooses easy mode:
            if (choice == 1) {
                boardSize = 10;
                noMines = 10;
                System.out.print("--------------------EASY---------------------");
            }
            // If the user chooses medium mode:
            else if (choice == 2) {
                boardSize = 16;
                noMines = 40;
                System.out.print("-------------------------------MEDIUM--------------------------------");
            }
            // If the user chooses hard mode:
            else if (choice == 3) {
                boardSize = 20;
                noMines = 99;
                System.out.print("----------------------------------------HARD-----------------------------------------");
            }
            // If the user chooses to create a custom game:
            else if (choice == 4) {
                System.out.println("-----------------CUSTOM BOARD----------------");
                boardSize = customBoard();
                noMines = customMine(boardSize);
                System.out.println("-----------------CUSTOM BOARD----------------");
            }

            gamePlay(boardSize, noMines);

            System.out.print("Would you like to play another game of minesweeper?\n");
            playing = yesNoAsk();

        }
    }

    public static int chooseDifficulty ()
            // Displays a selection of difficulty choices and prompts the user to select one, or lets the user choose
            // if they would like to enter a custom difficulty.
    {
        Scanner Reader = new Scanner(System.in);

        System.out.print("""
                Welcome to Minesweeper!
                What difficulty would you like to play on?
                1. Easy     (10 x 10, 10 mines)
                2. Medium   (16 x 16, 40 mines)
                3. Hard     (20 x 20, 99 mines)
                4. Custom""");


        int input;
        while (true)
        {
            System.out.print("\n: ");

            try {
                input = Reader.nextInt();

                if (input <= 0 || input > 4)
                {
                    System.out.print("Error, please enter a number from 1 to 4.");
                }
                else
                {
                    break;
                }
            }
            catch (InputMismatchException x)
            {
                System.out.print("Error, please enter a number from 1 to 4.");
                Reader.next();
            }
        }
        return input;
    }

    public static int customBoard ()
            // Asks the user how wide they would like their minesweeper board to be, if the number is higher than 40,
            // it presents an error and asks for input again.
    {
        Scanner Reader = new Scanner(System.in);
        System.out.print("How wide would you like your board to be? (1-40)\n");

        int input;
        while (true)
        {
            System.out.print(": ");

            try {
                input = Reader.nextInt();

                if (input <= 0)
                {
                    System.out.print("Sadly, you will need at least once cell in your board, please try again.\n");
                }
                // 40 Is the maximum width that is reasonably viewable in the console.
                else if (input > 40)
                {
                    System.out.print("This board size is too large to be fully viewable, please try again.\n");
                }

                else
                {
                    break;
                }
            }
            catch (InputMismatchException x)
            {
                System.out.print("Error, please enter a number from 1 to 4.");
                Reader.next();
            }
        }

        return input;
    }

    public static int customMine (int boardSize)
            // Asks the user for the number of mines they would like on the board. If the number they enter is 0 or
            // higher than the total number of cells on the board, it prompts them to enter again.
    {
        Scanner Reader = new Scanner(System.in);
        System.out.print("How many mines do you want?\n");
        int input;
        while (true)
        {
            System.out.print(": ");

            try {
                input = Reader.nextInt();

                if (input <= 0)
                {
                    System.out.println("Sadly, you will need at least one mine to make this a fair game, try again.");
                }
                else if (input >= boardSize * boardSize)
                {
                    System.out.println("That is way too many mines, try less than " + boardSize * boardSize + " mines.");
                }
                else
                {
                    break;
                }
            }
            catch (InputMismatchException x)
            {
                System.out.println("Error, please enter a number from 1 to " + (boardSize * boardSize - 1) + ".");
                Reader.next();
            }
        }

        return input;
    }

    public static boolean yesNoAsk ()
            // Asks the user a yes or no question by entering 1 or 0, and returns a boolean.
    {
        boolean yesNoIng = true;    // While loop boolean flag
        boolean complete = false;   // Return variable
        int yesNo = 0;              // Initialise input variable

        while (yesNoIng)
        {
            Scanner Reader = new Scanner(System.in);
            System.out.println("Enter 1 for yes or 0 for no:");

            boolean success = true; // Current Java sub for try/except/else

            try
            {
                yesNo = Reader.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Please enter a number, either 1 or 0.");
                success = false;
            }
            if (success)
            {
                if (yesNo == 0)
                {
                    yesNoIng = false;   // Ends the loop
                }
                else if (yesNo == 1)    // Ends the loop
                {
                    yesNoIng = false;
                    complete = true;    //  Method returns true
                }
                else
                {
                    System.out.println("Error: Please enter either 1 or 0.");
                }
            }
        }
        return complete;    // Returns true if yes, false if no
    }

    // GAMEPLAY --------------------------------------------------------------------------------------------------------

    public static void gamePlay(int boardSize, int noMines)
            // Main gameplay loop.
    {
        // Create new game board based on specifications.
        SweeperCell[][] mineData = addMines(boardSize,noMines);

        int x;                      // Initialise x-coordinate
        int y;                      // Initialise y-coordinate
        boolean loseWin = false;    // Initialise lose/win condition

        while (!loseWin)
        {
            while (true)
            {
                printBoard(boardSize, mineData);

                // User chooses from a menu every turn, and selects whether to flag, un-flag, or reveal a cell.
                int choice = flagOrReveal();

                // If they choose to flag a cell, they input its coordinates.
                if (choice == 2) {
                    System.out.print("Enter cell's x-coordinate: ");
                    int yFlag = intInputValidate(boardSize);
                    System.out.print("Enter cell's y-coordinate: ");
                    int xFlag = intInputValidate(boardSize);

                    mineData[xFlag][yFlag].flagged = true;

                    continue;
                }
                // If they choose to un-flag a cell, they input its coordinates.
                if (choice == 3)
                {
                    System.out.print("Enter cell's x-coordinate: ");
                    int yFlag = intInputValidate(boardSize);
                    System.out.print("Enter cell's y-coordinate: ");
                    int xFlag = intInputValidate(boardSize);

                    mineData[xFlag][yFlag].flagged = false;
                }
                // If they choose to reveal a cell, we exit the inner menu loop to make the next move in the game.
                else
                {
                    break;
                }
            }

            // User inputs coordinates to reveal the next cell.
            System.out.print("Enter cell's x-coordinate: ");
            y = intInputValidate(boardSize);
            System.out.print("Enter cell's y-coordinate: ");
            x = intInputValidate(boardSize);

            revealCells(x, y, boardSize, mineData);

            // Checks to see if the user has won or lost.
            loseWin = loseWinCondition(x, y, boardSize, noMines, mineData);

        }
    }

    public static int flagOrReveal ()
            // This displays a menu offering the user of they want to reveal, flag, or un-flag a cell.
            // Receives, validates, and outputs a user chosen integer of either 1, 2 or 3.
    {
        Scanner Reader = new Scanner(System.in);

        System.out.print("""
                    \n1. Reveal Cell
                    2. Flag Cell
                    3. Remove Flag
                    Enter 1, 2 or 3 to choose.""");

        int input;
        while (true)
        {
            System.out.print("\n: ");

            try {
                input = Reader.nextInt();

                if (input <= 0 || input > 3)
                {
                    System.out.print("Error, please enter 1, 2 or 3.");
                }
                else
                {
                    break;
                }
            }
            catch (InputMismatchException x)
            {
                System.out.print("Error, please enter 1, 2 or 3.");
                Reader.next();
            }
        }
        return input;
    }

    public static int intInputValidate (int boardSize)
            // Receives, validates and returns numerical co-ordinate input to receive a positive number within the
            // range of the game board as an integer.
    {
        int input = 0;    // Initialise input variable

        while (true)
        {
            Scanner Reader = new Scanner(System.in);

            boolean success = true; // Current Java sub for try/except/else

            try
            {
                input = Reader.nextInt() - 1; // Test if the input is a number
            }
            catch (InputMismatchException x)
            {
                System.out.print("Error: Please enter a number in range 1 - " + boardSize + "\n: ");
                success = false;
            }
            if (success)
            {
                if (input >= 0) // Test if the number is positive
                {
                    if (input <= boardSize)
                    {
                        break;
                    }
                    else
                    {
                        System.out.print("Error: Please enter a number in range 1 - " + boardSize + "\n: ");
                    }
                }
                else
                {
                    System.out.print("Error: Please enter a number in range 1 - " + boardSize + "\n: ");
                }
            }
        }
        return input; // Return clean user input as a positive integer.
    }

    public static boolean loseWinCondition (int x, int y, int boardSize, int noMines, SweeperCell[][] mineData)
            // Checks if the user has just won or lost the game and returns true if they have.
    {
        if (mineData[x][y].mine)
        {
            printBoard(boardSize, mineData);
            System.out.println("\n------------------GAME OVER------------------");
            return true;
        }

        int counter = 0;        // Initialise counter to count total visible cells

        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                if (mineData[i][j].isVisible)
                {
                    counter++;
                }
            }
        }

        // If the total number of visible cells == the total number of cells minus the number of cells with mines,
        // The user has won the game.
        if (counter == (boardSize * boardSize - noMines))
        {
            printBoard(boardSize, mineData);
            System.out.println("\n-------------------YOU WIN-------------------");
            return true;
        }

        // If neither condition has been met, return true to continue the game.
        return false;
    }

}
