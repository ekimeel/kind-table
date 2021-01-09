package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DoubleColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.LongColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirstTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addColumn(new IntegerColumn("Odd"));
        table.addColumn(new IntegerColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(0, 0));
        table.addRow(new Row(5, 6));

        final Integer value = table.eval(new First(0));
        assertEquals((Integer)1, value);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addColumn(new DoubleColumn(("Odd")));
        table.addColumn(new DoubleColumn("Even"));

        table.addRow(new Row(3.3, 2.2));
        table.addRow(new Row(5.5, 4.4));
        table.addRow(new Row(1.1, 6.6));

        final Double value = table.eval(new First(1));
        assertEquals((Double) 2.2, value);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addColumn(new LongColumn(("Odd")));
        table.addColumn(new LongColumn("Even"));

        table.addRow(new Row(10L, 20L));
        table.addRow(new Row(30L, 40L));
        table.addRow(new Row(50L, 60L));

        final Long value = table.eval(new First(1));
        assertEquals((Long)20L, value);
    }

}