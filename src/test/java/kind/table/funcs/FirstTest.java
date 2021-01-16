package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.LngColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirstTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(0, 0));
        table.addRow(new Row(5, 6));

        final Integer value = table.eval(new First<>(0));
        assertEquals((Integer)1, value);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addCol(new DblColumn(("Odd")));
        table.addCol(new DblColumn("Even"));

        table.addRow(new Row(3.3, 2.2));
        table.addRow(new Row(5.5, 4.4));
        table.addRow(new Row(1.1, 6.6));

        final Double value = table.eval(new First<>(1));
        assertEquals((Double) 2.2, value);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addCol(new LngColumn(("Odd")));
        table.addCol(new LngColumn("Even"));

        table.addRow(new Row(10L, 20L));
        table.addRow(new Row(30L, 40L));
        table.addRow(new Row(50L, 60L));

        final Long value = table.eval(new First<>(1));
        assertEquals((Long)20L, value);
    }

}