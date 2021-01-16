package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DoubleColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.LongColumn;
import org.junit.Test;
import static org.junit.Assert.*;

public class SumTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addColumn(new IntegerColumn("Odd"));
        table.addColumn(new IntegerColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));

        final Integer sum = table.eval(new Sum<>(0));
        assertEquals((Integer)9, sum);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addColumn(new DoubleColumn(("Odd")));
        table.addColumn(new DoubleColumn("Even"));

        table.addRow(new Row(1.1, 2.2));
        table.addRow(new Row(3.3, 4.4));
        table.addRow(new Row(5.5, 6.6));

        final Double sum = table.eval(new Sum<>(0));
        assertEquals((Double) 9.9, sum);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addColumn(new LongColumn(("Odd")));
        table.addColumn(new LongColumn("Even"));

        table.addRow(new Row(10L, 20L));
        table.addRow(new Row(30L, 40L));
        table.addRow(new Row(50L, 60L));

        final Long sum = table.eval(Sum.of(0));
        assertEquals((Long) 90L, sum);
    }
}