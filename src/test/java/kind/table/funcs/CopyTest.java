package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntegerColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class CopyTest {

    @Test
    public void test_eval(){
        final Table table = new Table();

        table.addColumn(new IntegerColumn("Odd"));
        table.addColumn(new IntegerColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(0, 0));
        table.addRow(new Row(5, 6));

        final Table result = table.eval(new Copy());
        assertNotEquals(table, result);
        assertEquals(3, result.getRowCount());
        assertEquals(2, result.getColumnCount());

        assertEquals((Integer)6, result.eval(new Sum(0)));
        assertEquals((Integer)8, result.eval(new Sum(1)));
    }
}