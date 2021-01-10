package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntegerColumn;
import kind.table.cols.ListColumn;
import kind.table.cols.StringColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupByTest {


    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addColumn(StringColumn.of("team"));
        table.addColumn(StringColumn.of("player"));
        table.addColumn(IntegerColumn.of("points"));

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

        final Table result = table.eval(new GroupBy(0));

        assertEquals(3, result.getRowCount());
        assertEquals(2, result.getColumnCount());
        assertTrue(result.getCol(0) instanceof StringColumn);
        assertTrue(result.getCol(1) instanceof ListColumn);

    }

}