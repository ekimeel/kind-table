package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblCol;
import kind.table.cols.IntCol;
import kind.table.cols.LngCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirstTest {

    @Test
    public void test_eval_withIntegerCol(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.from(1, 2));
        table.addRow(Row.from(0, 0));
        table.addRow(Row.from(5, 6));

        final Integer value = table.eval(First.of("Odd"));
        assertEquals((Integer)1, value);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addCol(new DblCol(("Odd")));
        table.addCol(new DblCol("Even"));

        table.addRow(Row.from(3.3, 2.2));
        table.addRow(Row.from(5.5, 4.4));
        table.addRow(Row.from(1.1, 6.6));

        final Double value = table.eval(First.of("Even"));
        assertEquals((Double) 2.2, value);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addCol(new LngCol(("Odd")));
        table.addCol(new LngCol("Even"));

        table.addRow(Row.from(10L, 20L));
        table.addRow(Row.from(30L, 40L));
        table.addRow(Row.from(50L, 60L));

        final Long value = table.eval(First.of(1));
        assertEquals((Long)20L, value);
    }

}