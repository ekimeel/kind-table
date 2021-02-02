package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblCol;
import kind.table.cols.IntCol;
import kind.table.cols.LngCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class LastTest {

    @Test
    public void test_eval_withIntegerCol(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(0, 0));
        table.addRow(Row.of(5, 6));

        final Integer value = table.eval(Last.from(0));
        assertEquals((Integer)5, value);
    }

    @Test
    public void test_eval_withDoubleCol(){
        final Table table = new Table();

        table.addCol(new DblCol(("Odd")));
        table.addCol(new DblCol("Even"));

        table.addRow(Row.of(3.3, 2.2));
        table.addRow(Row.of(5.5, 4.4));
        table.addRow(Row.of(1.1, 6.6));

        final Double value = table.eval(Last.from("Even"));
        assertEquals((Double)6.6, value);
    }

    @Test
    public void test_eval_withLongCol(){
        final Table table = new Table();

        table.addCol(new LngCol(("Odd")));
        table.addCol(new LngCol("Even"));

        table.addRow(Row.of(10L, 20L));
        table.addRow(Row.of(30L, 40L));
        table.addRow(Row.of(50L, 60L));

        final Long value = table.eval(Last.from(1));
        assertEquals((Long)60L, value);
    }


}