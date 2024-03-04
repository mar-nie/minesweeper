public class SweeperCell
    // Class defines cell objects.
{
    boolean mine;           // A true or false indicating if there is a mine in this cell
    boolean flagged;        // A true or false indicating if the user has flagged that square
    boolean isVisible;      // A true or false indicating if the contents of the cell are on display
    int proximityNumber;    // The number of mines within the 8 cells surrounding the current cell
    int cellNumber;         // The unique integer number associated with this cell


    // Constructor
    public SweeperCell (boolean containsMine, boolean flag, boolean visible, int proximity, int cell)
    {
        mine = containsMine;
        flagged = flag;
        proximityNumber = proximity;
        cellNumber = cell;
        isVisible = visible;
    }
}
