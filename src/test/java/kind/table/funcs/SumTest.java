package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.LngColumn;
import org.junit.Test;
import static org.junit.Assert.*;

public class SumTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));

        final Integer sum = table.eval(new Sum<>(0));
        assertEquals((Integer)9, sum);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addCol(new DblColumn(("Odd")));
        table.addCol(new DblColumn("Even"));

        table.addRow(new Row(1.1, 2.2));
        table.addRow(new Row(3.3, 4.4));
        table.addRow(new Row(5.5, 6.6));

        final Double sum = table.eval(new Sum<>(0));
        assertEquals((Double) 9.9, sum);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addCol(new LngColumn(("Odd")));
        table.addCol(new LngColumn("Even"));

        table.addRow(new Row(10L, 20L));
        table.addRow(new Row(30L, 40L));
        table.addRow(new Row(50L, 60L));

        final Long sum = table.eval(Sum.of(0));
        assertEquals((Long) 90L, sum);
    }

}