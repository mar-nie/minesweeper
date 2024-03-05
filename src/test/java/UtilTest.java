import org.junit.jupiter.api.*;

public class UtilTest {

    @ Test
    public void TrivialTest()
    {
        Assertions.assertEquals(1, 1 + 0, "This is a trivial test");
    }

    @Test
    public void CellTest()
    {
        SweeperCell newCell = SweeperCell.SweeperCellInit();
        Assertions.assertFalse(newCell.mine, "Default Cell.mine returns true.");
        Assertions.assertFalse(newCell.flagged, "Default Cell.flagged returns true.");
        Assertions.assertFalse(newCell.isVisible, "Default Cell.visible returns true.");
        Assertions.assertEquals(0,newCell.proximityNumber, "Default Cell.proximityNumber != 0");
        Assertions.assertEquals(0,newCell.cellNumber, "Default Cell.cellNumber != 0");
    }

    @Test
    public void populateArrayTest()
    {
        int boardSize = 10;
        SweeperCell[][] array = Board.populateArray(boardSize);

        for (int i = 0; i < boardSize; i++)
        {
            
        }

        Assertions.assertEquals(array[boardSize][boardSize],SweeperCell);
    }
}
