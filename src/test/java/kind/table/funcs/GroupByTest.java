package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import kind.table.cols.RowCol;
import kind.table.cols.StrCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupByTest {


    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addCol(StrCol.of("team"));
        table.addCol(StrCol.of("player"));
        table.addCol(IntCol.of("points"));

        table.addRow(new Row("a-team", "player-1", 1));
        table.addRow(new Row("a-team", "player-1", 2));
        table.addRow(new Row("a-team", "player-1", 1));

        table.addRow(new Row("a-team", "player-2", 4));
        table.addRow(new Row("a-team", "player-2", 10));

        table.addRow(new Row("b-team", "player-1", 4));
        table.addRow(new Row("b-team", "player-1", 1));

        table.addRow(new Row("c-team", "player-1", 10));
        table.addRow(new Row("c-team", "player-1", 8));
        table.addRow(new Row("c-team", "player-2", 15));
        table.addRow(new Row("c-team", "player-2", 12));

        final Table result = table.eval(GroupBy.from(0));

        assertEquals(3, result.getRowCount());
        assertEquals(2, result.getColCount());
        assertTrue(result.getColByIndex(0) instanceof StrCol);
        assertTrue(result.getColByIndex(1) instanceof RowCol);

    }

}