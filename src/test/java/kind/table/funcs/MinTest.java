package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.LngColumn;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(0, 0));
        table.addRow(new Row(5, 6));

        final Integer min = (Integer) table.eval(Min.of(0));
        assertEquals((Integer)0, min);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addCol(new DblColumn(("Odd")));
        table.addCol(new DblColumn("Even"));

        table.addRow(new Row(3.3, 2.2));
        table.addRow(new Row(5.5, 4.4));
        table.addRow(new Row(1.1, 6.6));

        final Double sum = table.eval(Min.of(0));
        assertEquals((Double) 1.1, sum);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addCol(new LngColumn(("Odd")));
        table.addCol(new LngColumn("Even"));

        table.addRow(new Row(10L, 20L));
        table.addRow(new Row(30L, 40L));
        table.addRow(new Row(50L, 60L));

        final Long min = table.eval(Min.of("Odd"));
        assertEquals((Long) 10L, min);
    }
}
