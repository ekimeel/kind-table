package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntegerColumn;
import kind.table.cols.StringColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeepColsTest {

    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addColumn(StringColumn.of("team"));
        table.addColumn(StringColumn.of("player"));
        table.addColumn(IntegerColumn.of("points"));

        table.addRow(new Row("a-team", "player-1", 1));
        table.addRow(new Row("a-team", "player-1", 2));
        table.addRow(new Row("a-team", "player-1", 3));

        final Table result = table.eval(new KeepCols(0, 2));
        assertEquals(2, result.getColumnCount());
        assertEquals(3, result.getRowCount());

        result.print(System.out);
    }
}