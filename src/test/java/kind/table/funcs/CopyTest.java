package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class CopyTest {

    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(0, 0));
        table.addRow(Row.of(5, 6));

        final Table result = table.eval(new Copy());
        assertNotEquals(table, result);
        assertEquals(3, result.getRowCount());
        assertEquals(2, result.getColCount());

        assertEquals((Integer)6, result.eval(Sum.of("Odd")));
        assertEquals((Integer)8, result.eval(Sum.of("Even")));
    }
}