package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblCol;
import kind.table.cols.IntCol;
import kind.table.cols.LngCol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeTest {

    @Test
    public void test_eval_withIntegerCol(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(0, 0));
        table.addRow(Row.of(5, 6));

        final Integer result = table.eval(Range.of("Odd"));
        assertEquals((Integer)5, result);
    }

    @Test
    public void test_eval_withDoubleCol(){
        final Table table = new Table();

        table.addCol(new DblCol(("Odd")));
        table.addCol(new DblCol("Even"));

        table.addRow(Row.of(3.3, 2.2));
        table.addRow(Row.of(5.5, 4.4));
        table.addRow(Row.of(1.1, 6.6));

        final Double result = table.eval(Range.of("Odd"));
        assertEquals((Double) 4.0, result);
    }

    @Test
    public void test_eval_withLongCol(){
        final Table table = new Table();

        table.addCol(new LngCol(("Odd")));
        table.addCol(new LngCol("Even"));

        table.addRow(Row.of(10L, 20L));
        table.addRow(Row.of(30L, 40L));
        table.addRow(Row.of(50L, 60L));

        final Long min = table.eval(Range.of("Odd"));
        assertEquals((Long) 40L, min);
    }
}
