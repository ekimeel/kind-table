package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeepColsTest {

    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addCol(StrColumn.of("team"));
        table.addCol(StrColumn.of("player"));
        table.addCol(IntColumn.of("points"));

        table.addRow(new Row("a-team", "player-1", 1));
        table.addRow(new Row("a-team", "player-1", 2));
        table.addRow(new Row("a-team", "player-1", 3));

        final Table result = table.eval(new KeepCols(0, 2));
        assertEquals(2, result.getColCount());
        assertEquals(3, result.getRowCount());

        assertEquals(StrColumn.class, result.getColByIndex(0).getClass());
        assertEquals(IntColumn.class, result.getColByIndex(1).getClass());

    }
}