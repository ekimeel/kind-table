package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeepColsTest {

    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addCol(StrCol.of("team"));
        table.addCol(StrCol.of("player"));
        table.addCol(IntCol.of("points"));

        table.addRow(Row.from("a-team", "player-1", 1));
        table.addRow(Row.from("a-team", "player-1", 2));
        table.addRow(Row.from("a-team", "player-1", 3));

        final Table result = table.eval(new KeepCols(0, 2));
        assertEquals(2, result.getColCount());
        assertEquals(3, result.getRowCount());

        assertEquals(StrCol.class, result.getColByIndex(0).getClass());
        assertEquals(IntCol.class, result.getColByIndex(1).getClass());

    }
}