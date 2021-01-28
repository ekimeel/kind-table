package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class SmearTest {

    @Test
    public void test_eval_withIntegerCol(){
        final Table table = new Table();

        table.addCol(new IntCol("ColA"));
        table.addCol(new IntCol("ColB"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));
        table.addRow(new Row(7, null));  // 6
        table.addRow(new Row(9, null));  // 6
        table.addRow(new Row(11, null)); // 6
        table.addRow(new Row(13, null)); // 6
        table.addRow(new Row(15, 16));
        table.addRow(new Row(17, 18));

        final Table result = table.eval(Smear.from(1));

        assertEquals( (Integer) 6, result.get(2, 1)); //
        assertEquals( (Integer) 6, result.get(3, 1)); // was null
        assertEquals( (Integer) 6, result.get(4, 1)); // was null
        assertEquals( (Integer) 6, result.get(5, 1)); // was null
        assertEquals( (Integer) 6, result.get(6, 1)); // was null
        assertEquals( (Integer) 16, result.get(7, 1));

    }
}